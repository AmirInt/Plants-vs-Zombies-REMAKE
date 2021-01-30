package server;

import managers.GamePlayer;

import java.io.*;
import java.net.Socket;

public class SubServer implements Runnable {

    Server serverSocket;
    Socket communicationSocket;

    public SubServer(Server serverSocket, Socket communicationSocket) {
        this.serverSocket = serverSocket;
        this.communicationSocket = communicationSocket;
    }

    @Override
    public synchronized void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(communicationSocket.getInputStream()))
             ; DataOutputStream writer = new DataOutputStream(communicationSocket.getOutputStream())) {
            String str = reader.readLine();
            if(str.equals("Sign in")) {
                User user = serverSocket.checkForUser(reader.readLine());
                if (user == null)
                    writer.write("Does not exist\n".getBytes());
                else {
                    writer.write("Does exist\n".getBytes());
                    str = reader.readLine();
                    if (str.equals(user.getPassword())) {
                        writer.write("Correct\n".getBytes());
                        writer.write((user.getScore() + "\n").getBytes());
                        writer.write((user.getWins() + "\n").getBytes());
                        writer.write((user.getLosses() + "\n").getBytes());
                    } else writer.write("Incorrect".getBytes());
                }
            }
            else if(str.equals("Sign up")) {
                boolean done = serverSocket.addUser(reader.readLine(), reader.readLine());
                writer.write((done ? "Success\n" : "Failure\n").getBytes());
            }
            else if(str.equals("Update")) {
                String username = reader.readLine();
                String newUsername = reader.readLine();
                String password = reader.readLine();
                int wins = Integer.parseInt(reader.readLine());
                System.out.println(wins);
                int losses = Integer.parseInt(reader.readLine());
                System.out.println(losses);
                int score = Integer.parseInt(reader.readLine());
                System.out.println(score);
                if (serverSocket.updateUser(username, newUsername, password, wins, losses, score))
                    writer.write("Done\n".getBytes());
                else writer.write("Not allowed\n".getBytes());
            }
            else if(str.equals("Get users")) {
                try (ObjectOutputStream objectWriter = new ObjectOutputStream(communicationSocket.getOutputStream())) {
                    objectWriter.writeObject(serverSocket.getUsersList());
                } catch (IOException e) {
                    System.out.println("Wrong in object writer");
                }
            }
            else if(str.equals("Save game")) {
                str = reader.readLine();
                try (ObjectInputStream objectReader = new ObjectInputStream((communicationSocket.getInputStream()))){
                    GamePlayer gamePlayer = (GamePlayer) objectReader.readObject();
                    if(serverSocket.saveGame(gamePlayer, str))
                        writer.write("Done\n".getBytes());
                    else throw new ClassCastException();
                } catch (IOException | ClassNotFoundException e) {
                    writer.write("Not done\n".getBytes());
                }
            }
        } catch (IOException e) {
            System.out.println("wrong sub server");
        }
    }
}
