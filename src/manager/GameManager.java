package manager;

import enums.*;
import graphics.*;
import cards.*;
import menus.LaunchingMenu;
import menus.MainMenu;
import javax.swing.*;
import java.awt.*;
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
    private int plantsNumber;
    private int zombiesNumber;

    public GameManager() {
        gameDifficulty = GameDifficulty.MEDIUM;
        availableZombies = new ArrayList<>();
        availablePlants = new ArrayList<>();
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

    public String getUser() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public void setSignedIn(boolean signedIn) {
        isSignedIn = signedIn;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public int addUser(String username, String password) {
        try (Socket socket = new Socket("127.0.0.1", 2000);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             DataOutputStream writer = new DataOutputStream(socket.getOutputStream())
        ) {
            writer.write("Sign up\n".getBytes());
            System.out.println("sign up sent");
            writer.write((username + "\n").getBytes());
            System.out.println("username set");
            writer.write((password + "\n").getBytes());
            System.out.println("password sent");
            String str = reader.readLine();
            if(str.equals("Success")) {
                this.username = username;
                isSignedIn = true;
                boolean done = store();
                if(done) {
                    frame.displayMenu(mainMenu);
                    frame.revalidate();
                    frame.requestFocus();
                    return 0;
                }
                return 1;
            }
            return 2;
        } catch (IOException e) {
            return 3;
        }
    }

    public int getUser(String username, String password) {
        try (Socket socket = new Socket("127.0.0.1", 2000);
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
                score = reader.read();
                isSignedIn = true;
                boolean done = store();
                if(done) {
                    frame.displayMenu(mainMenu);
                    frame.revalidate();
                    frame.requestFocus();
                    return 0;
                }
                return 2;
            }
            return 1;
        } catch (IOException e) {
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

    public boolean store() {
        try (FileOutputStream outputStream = new FileOutputStream("Game Manager\\Game Manager.dat")
             ; ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            return false;
        }
        return true;
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

    public void play() {
        EventQueue.invokeLater(() -> {
//             Create and execute the game-loop
            GamePlayer gamePlayer = new GamePlayer(GameDifficulty.MEDIUM,
                    availableZombies, availablePlants, this);
            gamePlayer.initialise();
            GameLoop game = new GameLoop(frame, mainMenu, gamePlayer);
            game.init();
            frame.setEntities(gamePlayer);
            frame.setAvailablePlants(gamePlayer);
            ThreadPool.execute(game);
            ThreadPool.execute(gamePlayer);
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
