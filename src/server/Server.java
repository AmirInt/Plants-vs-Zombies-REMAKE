package server;

import managers.GamePlayer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * Class Server is responsible for operations on the server side, like handling
 * users, saving and deleting their games and updating them
 */
public class Server {

    private final ArrayList<User> usersList;
    private final TreeMap<String, User> users;
    private ServerSocket serverSocket;

    /**
     * Instantiates this class
     */
    public Server() {
        users = new TreeMap<>();
        usersList = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(10002);
        } catch (IOException e) {
            System.out.println("wrong in server");
        }
    }

    /**
     * @param username The username of the quested user
     * @return The User object associating the the username given
     */
    public synchronized User checkForUser(String username) {
        return users.get(username);
    }

    /**
     * Adds a new user to the users of the server
     * @param username The new user's username
     * @param password The new user's password
     * @return True if the username is free to user and thus the operation is properly done
     */
    public synchronized boolean addUser(String username, String password) {
        User newUser = checkForUser(username);
        if(newUser == null) {
            newUser = new User(username, password);
            users.put(username, newUser);
            usersList.add(newUser);
            return true;
        }
        return false;
    }

    /**
     * Updated the username status
     * @param username The old username
     * @param newUsername The new username (probably different than the old on)
     * @param newPassword The new password
     * @param wins The number of the wins
     * @param losses The number of the losses
     * @param score The overall score
     * @return True if done properly
     */
    public synchronized boolean updateUser(String username, String newUsername, String newPassword, int wins, int losses, int score) {
        if(checkForUser(newUsername) != null && !username.equals(newUsername))
            return false;
        User user = users.get(username);
        user.update(newUsername, newPassword, wins, losses, score);
        if(!username.equals(newUsername)) {
            users.remove(username, user);
            users.put(newUsername, user);
        }
        return true;
    }

    /**
     * @return The array of the users
     */
    public synchronized String[] getUsersList() {
        String[] usersArray = new String[usersList.size()];
        int i = 0;
        for (User user:
             usersList) {
            usersArray[i] = user.toString();
            ++i;
        }
        return usersArray;
    }

    /**
     * Saves a game onto the user's account
     * @param gamePlayer The game player to be saved
     * @param username The user's username
     * @return True if done completely
     */
    public synchronized boolean saveGame(GamePlayer gamePlayer, String username) {
        User user = users.get(username);
        return user.saveGame(gamePlayer);
    }

    /**
     * @param username The requesting user's username
     * @return The array of the saved games dates
     */
    public synchronized String[] getLoadedGamesOf(String username) {
        User user = checkForUser(username);
        return user.getSavedGames();
    }

    /**
     * @param username The requesting user's username
     * @param date The date of the game
     * @return The game player previously stored associating with the date
     */
    public synchronized GamePlayer getSavedGameOf(String username, String date) {
        User user = checkForUser(username);
        return user.getSavedGame(date);
    }

    public static void main(String[] args) {
        Server server = new Server();
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            Socket communicationSocket;
            try {
                communicationSocket = server.serverSocket.accept();
                SubServer subServer = new SubServer(server, communicationSocket);
                executorService.execute(subServer);
            } catch (IOException e) {
                System.out.println("wrong accept");
            }
        }
    }
}
