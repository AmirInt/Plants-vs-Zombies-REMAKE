package server;

import managers.GamePlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Server {

    private ArrayList<User> usersList;
    private TreeMap<String, User> users;
    private ServerSocket serverSocket;

    public Server() {
        users = new TreeMap<>();
        usersList = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(10002);
        } catch (IOException e) {
            System.out.println("wrong in server");
        }
    }

    public synchronized User checkForUser(String username) {
        return users.get(username);
    }

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

    public synchronized boolean updateUser(String username, String newUsername, String newPassword, int wins, int losses, int score) {
        if(checkForUser(newUsername) != null && !username.equals(newUsername))
            return false;
        User user = users.get(username);
        user.update(newUsername, newPassword, wins, losses, score);
        if(!username.equals(newUsername)) {
            users.remove(username, user);
            System.out.println(users.get(username));
            users.put(newUsername, user);
        }
        return true;
    }

    public ArrayList<User> getUsersList() {
        return usersList;
    }

    public boolean saveGame(GamePlayer gamePlayer, String username) {
        User user = users.get(username);
        return user.saveGame(gamePlayer);
    }

    public static void main(String[] args) {
        Server server = new Server();
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            Socket communicationSocket;
            try {
                communicationSocket = server.serverSocket.accept();
                System.out.println("Someone reached out");
                //
                SubServer subServer = new SubServer(server, communicationSocket);
                executorService.execute(subServer);
            } catch (IOException e) {
                System.out.println("wrong accept");
            }
        }
    }
}
