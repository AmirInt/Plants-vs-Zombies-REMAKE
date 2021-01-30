package managers;

import enums.*;
import graphics.*;
import menus.LaunchingMenu;
import menus.MainMenu;
import menus.PauseMenu;
import server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class GameManager implements Serializable {

    transient private GameFrame frame;
    transient private MainMenu mainMenu;
    transient private LaunchingMenu launchingMenu;
    private ArrayList<AvailableZombies> availableZombies;
    private ArrayList<AvailablePlants> availablePlants;
    private GameDifficulty gameDifficulty;
    static final int[] cardXs = {180, 254, 328, 402, 476, 540};
    static final int cardY = 80;
    private String username;
    private boolean isSignedIn;
    private int score;
    private int wins;
    private int losses;
    private int plantsNumber;
    private int zombiesNumber;

    public GameManager() {
        gameDifficulty = GameDifficulty.MEDIUM;
        availableZombies = new ArrayList<>();
        availablePlants = new ArrayList<>();
        wins = 0;
        losses = 0;
        score = 0;
    }

    public void initialise() {
        frame = new GameFrame();
        frame.setLocationRelativeTo(null); // put frame at center of screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu = new MainMenu(this, frame);
        launchingMenu = new LaunchingMenu(this, frame);
    }

    private void setAvailableEntities() {

        availableZombies.add(AvailableZombies.NormalZombie);
        availableZombies.add(AvailableZombies.BucketHeadZombie);
        availableZombies.add(AvailableZombies.ConeHeadZombie);
        zombiesNumber = 3;

        availablePlants.add(AvailablePlants.SUNFLOWER);
        availablePlants.add(AvailablePlants.PEASHOOTER);
        availablePlants.add(AvailablePlants.WALNUT);
        availablePlants.add(AvailablePlants.FROZEN_PEASHOOTER);
        availablePlants.add(AvailablePlants.CHERRY_BOMB);
        plantsNumber = 5;
    }

    public void removeAvailableZombie(AvailableZombies zombie) {
        availableZombies.remove(zombie);
    }

    public void addToAvailableZombies(AvailableZombies zombie) {
        availableZombies.add(zombie);
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public void setSignedIn(boolean signedIn) {
        isSignedIn = signedIn;
    }

    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public String getUser() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<AvailablePlants> getAvailablePlants() {
        return availablePlants;
    }

    public ArrayList<AvailableZombies> getAvailableZombies() {
        return availableZombies;
    }

    public int addUser(String username, String password) {
        try (Socket socket = new Socket("127.0.0.1", 10002);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             DataOutputStream writer = new DataOutputStream(socket.getOutputStream())
        ) {
            writer.write("Sign up\n".getBytes());
            writer.write((username + "\n").getBytes());
            writer.write((password + "\n").getBytes());
            String str = reader.readLine();
            if(str.equals("Success")) {
                this.username = username;
                score = 0;
                wins = 0;
                losses = 0;
                isSignedIn = true;
                boolean done = store();
                if(done) {
                    mainMenu.updateUsername();
                    frame.displayMenu(mainMenu);
                    frame.revalidate();
                    frame.requestFocus();
                    return 0;
                }
                return 1;
            }
            return 2;
        } catch (IOException e) {
            e.printStackTrace();
            return 3;
        }
    }

    public int getUser(String username, String password) {
        try (Socket socket = new Socket("127.0.0.1", 10002);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             DataOutputStream writer = new DataOutputStream(socket.getOutputStream())
        ) {
            String str;
            writer.write("Sign in\n".getBytes());
            writer.write((username + "\n").getBytes());
            str = reader.readLine();
            if(str.equals("Does not exist"))
                return 1;
            writer.write((password + "\n").getBytes());
            str = reader.readLine();
            if(str.equals("Correct")) {
                this.username = username;
                score = Integer.parseInt(reader.readLine());
                wins = Integer.parseInt(reader.readLine());
                losses = Integer.parseInt(reader.readLine());
                isSignedIn = true;
                boolean done = store();
                if(done) {
                    mainMenu.updateUsername();
                    frame.displayMenu(mainMenu);
                    frame.revalidate();
                    frame.requestFocus();
                    return 0;
                }
                return 2;
            }
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 3;
        }
    }

    public void signOut() {
        setSignedIn(false);
        boolean done = store();
        if(done) {
            frame.displayMenu(launchingMenu);
            frame.revalidate();
            frame.requestFocus();
        }
    }

    public boolean updateUser(String newUsername, String newPassword) {
        try (Socket socket = new Socket("127.0.0.1", 10002);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             DataOutputStream writer = new DataOutputStream(socket.getOutputStream())
        ) {
            String str;
            writer.write("Update\n".getBytes());
            writer.write((username + "\n").getBytes());
            writer.write((newUsername + "\n").getBytes());
            writer.write((newPassword + "\n").getBytes());
            writer.write((wins + "\n").getBytes());
            writer.write((losses + "\n").getBytes());
            writer.write((score + "\n").getBytes());
            str = reader.readLine();
            if(str != null && str.equals("Done")) {
                username = newUsername;
                mainMenu.updateUsername();
                store();
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<User> getPlayers() {
        ObjectInputStream objectReader = null;
        try (Socket socket = new Socket("127.0.0.1", 10002) ;
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            outputStream.write("Get users\n".getBytes());
            objectReader = new ObjectInputStream(socket.getInputStream());
            return (ArrayList<User>) objectReader.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Caught");
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Caught differently");
            return null;
        }
        finally {
            if(objectReader != null) {
                try {
                    objectReader.close();
                } catch (IOException ignore) { }
            }
        }
    }

    public boolean store() {
        try (FileOutputStream outputStream = new FileOutputStream("Game Manager\\Game Manager.dat")
             ; ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean saveGame(GamePlayer gamePlayer) {
        ObjectOutputStream objectWriter = null;
        try (Socket socket = new Socket("127.0.0.1", 10002) ;
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())
             ; BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            outputStream.write("Save game\n".getBytes());
            outputStream.write((username + "\n").getBytes());
            objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectWriter.writeObject(gamePlayer);
            String str = reader.readLine();
            return str.equals("Done");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Caught");
            return false;
        } finally {
            if(objectWriter != null) {
                try {
                    objectWriter.close();
                } catch (IOException ignore) { }
            }
        }
    }

    public void launchGame() {
        // Initialize the global thread-pool
        ThreadPool.init();
        initialise();
        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(() -> {
            if(isSignedIn)
                frame.displayMenu(mainMenu);
            else frame.displayMenu(launchingMenu);
            frame.setVisible(true);
            frame.init();
        });
    }

    public void gameFinished(GamePlayer gamePlayer) {

        score += gamePlayer.getScore();
        if(score > 0)
            ++wins;
        else ++losses;
        store();
        updateUser(username, username);

        BufferStrategy bufferStrategy = frame.getBufferStrategy();
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.drawImage(new ImageIcon("Game accessories\\images\\gameOver.jpg").getImage(), 0, 0, frame);
        g2d.setFont(new Font("", Font.BOLD, 45));
        g2d.setColor(Color.WHITE);
        if(score > 0) {
            g2d.drawString("You Lost And...", 20, 100);
            g2d.drawString("Poor Lame Lad", 800, 100);
        } else {
            g2d.drawString("You Lost And...", 20, 100);
            g2d.drawString("Poor Lame Lad", 800, 100);
        }
        g2d.drawString("" + gamePlayer.getScore(), 600, 700);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignore) { }
        frame.displayMenu(mainMenu);
    }

    public void play(GamePlayer gamePlayer) {
        EventQueue.invokeLater(() -> {
//             Create and execute the game-loop
            gamePlayer.initialise();
            GameLoop game = new GameLoop(frame, gamePlayer, this);
            game.init();
            frame.setEntities(gamePlayer);
            frame.setAvailablePlants(gamePlayer);
            ThreadPool.execute(game);
            ThreadPool.execute(gamePlayer);
            frame.displayMenu(new PauseMenu(this, frame, game.getState()));
//             and the game starts ...
        });
    }

    public static void main(String[] args) {

        GameManager gameManager;
        try (FileInputStream inputStream = new FileInputStream("Game Manager\\Game Manager.dat")
             ; ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)){
            gameManager = (GameManager) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ignore) {
            gameManager = new GameManager();
            gameManager.setAvailableEntities();
        }

        gameManager.launchGame();
    }
}
