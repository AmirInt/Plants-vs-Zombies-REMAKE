package server;

import managers.GamePlayer;

import java.io.*;
import java.net.Socket;

public class SubServer implements Runnable {

    Server server;
    Socket communicationSocket;

    public SubServer(Server server, Socket communicationSocket) {
        this.server = server;
        this.communicationSocket = communicationSocket;
    }

    @Override
    public synchronized void run() {
        try (ObjectOutputStream writer = new ObjectOutputStream(communicationSocket.getOutputStream())
             ; ObjectInputStream reader = new ObjectInputStream(communicationSocket.getInputStream())) {
            String str = (String) reader.readObject();
            switch (str) {
                case "Sign in":
                    User user = server.checkForUser((String) reader.readObject());
                    if (user == null) {
                        writer.writeObject("Does not exist");
                    } else {
                        writer.writeObject("Does exist");
                        str = (String) reader.readObject();
                        if (str.equals(user.getPassword())) {
                            writer.writeObject("Correct");
                            writer.writeObject(user.getScore());
                            writer.writeObject(user.getWins());
                            writer.writeObject(user.getLosses());
                        } else writer.writeObject("Incorrect");
                    }
                    break;
                case "Sign up":
                    boolean done = server.addUser((String) reader.readObject(), (String) reader.readObject());
                    writer.writeObject((done ? "Success" : "Failure"));
                    break;
                case "Update":
                    String username = (String) reader.readObject();
                    String newUsername = (String) reader.readObject();
                    String password = (String) reader.readObject();
                    int wins = (int) reader.readObject();
                    int losses = (int) reader.readObject();
                    int score = (int) reader.readObject();
                    if (server.updateUser(username, newUsername, password, wins, losses, score))
                        writer.writeObject("Done");
                    else writer.writeObject("Not allowed");
                    break;
                case "Get users":
                    writer.writeObject(server.getUsersList());
                    break;
                case "Save game":
                    str = (String) reader.readObject();
                    GamePlayer gamePlayer = (GamePlayer) reader.readObject();
                    if (server.saveGame(gamePlayer, str))
                        writer.writeObject("Done");
                    else
                        writer.writeObject("Not done");
                    break;
                case "Take loaded games":
                    str = (String) reader.readObject();
                    writer.writeObject(server.getLoadedGamesOf(str));
                    break;
                case "Get game":
                    str = (String) reader.readObject();
                    String date = (String) reader.readObject();
                    writer.writeObject(server.getSavedGameOf(str, date));
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("wrong sub server");
        }
    }
}
