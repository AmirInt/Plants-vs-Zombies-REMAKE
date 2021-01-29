package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Server {

    private TreeMap<Integer, User> usersRankings;
    private TreeMap<String, User> users;
    private ServerSocket serverSocket;

    public Server() {
        users = new TreeMap<>();
        usersRankings = new TreeMap<>(Collections.reverseOrder());
        try {
            serverSocket = new ServerSocket(2000);
        } catch (IOException e) {
            System.out.println("wrong in server");
        }
    }

    public synchronized User checkForUser(String username) {
        System.out.println("someone checked for username");
        System.out.println(username);
        return users.get(username);
    }

    public synchronized boolean addUser(String username, String password) {
        System.out.println("someone attempted to sign up");
        System.out.println(username + " " + password);
        User newUser = checkForUser(username);
        if(newUser == null) {
            newUser = new User(username, password);
            users.put(username, newUser);
            usersRankings.put(0, newUser);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Server server = new Server();
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            Socket communicationSocket;
            try {
                communicationSocket = server.serverSocket.accept();
                //
                System.out.println("someone just reached");
                SubServer subServer = new SubServer(server, communicationSocket);
                executorService.execute(subServer);
            } catch (IOException e) {
                System.out.println("wrong accept");
            }
        }
    }
}
