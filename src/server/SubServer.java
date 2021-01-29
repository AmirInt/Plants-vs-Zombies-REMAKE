package server;

import java.io.*;
import java.net.Socket;

public class SubServer implements Runnable {

    Server serverSocket;
    Socket communicationSocket;

    public SubServer(Server serverSocket, Socket communicationSocket) {
        System.out.println("socket created");
        this.serverSocket = serverSocket;
        this.communicationSocket = communicationSocket;
    }

    @Override
    public synchronized void run() {
        System.out.println("socket started");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(communicationSocket.getInputStream()))
             ; DataOutputStream writer = new DataOutputStream(communicationSocket.getOutputStream())){
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
                        writer.write(user.getScore());
                    } else writer.write("Incorrect".getBytes());
                }
            }
            else if(str.equals("Sign up")) {
                boolean done = serverSocket.addUser(reader.readLine(), reader.readLine());
                writer.write((done ? "Success\n" : "Failure\n").getBytes());
            }
        } catch (IOException e) {
            System.out.println("wrong sub server");
        }
    }
}
