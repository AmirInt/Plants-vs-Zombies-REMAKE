package managers;

import enums.*;
import graphics.*;
import menus.FinishingMenu;
import menus.LaunchingMenu;
import menus.MainMenu;
import menus.PauseMenu;
import sounds.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class GameManager implements Serializable {

    transient private GameFrame frame;
    transient private MainMenu mainMenu;
    transient private LaunchingMenu launchingMenu;
    private HashSet<AvailableZombies> availableZombies;
    private HashSet<AvailablePlants> availablePlants;
    private GameDifficulty gameDifficulty;
    static final int[] cardXs = {180, 250, 320, 390, 460, 530, 600, 670, 740, 810};
    static final int cardY = 80;
    private String username;
    private boolean isSignedIn;
    private int score;
    private int wins;
    private int losses;
    transient private SoundPlayer soundPlayer;
    private boolean isMuted;
    private static final String path = "Game accessories\\sounds\\menu.wav";


    public GameManager() {
        gameDifficulty = GameDifficulty.MEDIUM;
        availableZombies = new HashSet<>();
        availablePlants = new HashSet<>();
        wins = 0;
        losses = 0;
        score = 0;
        isMuted = false;
    }

    public void initialise() {
        frame = new GameFrame();
        frame.setLocationRelativeTo(null); // put frame at center of screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu = new MainMenu(this, frame);
        launchingMenu = new LaunchingMenu(this, frame);
        soundPlayer = new SoundPlayer(path, 0, true);
    }

    public void replay() {
        soundPlayer = new SoundPlayer(path, 0, true);
        ThreadPool.execute(soundPlayer);
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        System.out.println("Here");
        isMuted = muted;
        if(isMuted) {
            soundPlayer.setFinished(true);
        } else {
            soundPlayer = new SoundPlayer(path, 0, true);
            ThreadPool.execute(soundPlayer);
        }
    }

    public void setAvailableEntities() {

        availableZombies.add(AvailableZombies.NormalZombie);
        availableZombies.add(AvailableZombies.BucketHeadZombie);
        availableZombies.add(AvailableZombies.ConeHeadZombie);
        availableZombies.add(AvailableZombies.BalloonZombie);
        availableZombies.add(AvailableZombies.CatapultZombie);
        availableZombies.add(AvailableZombies.CreepyZombie);
        availableZombies.add(AvailableZombies.DoorShieldZombie);
        availableZombies.add(AvailableZombies.FootballZombie);
        availableZombies.add(AvailableZombies.YetiZombie);

        availablePlants.add(AvailablePlants.SUNFLOWER);
        availablePlants.add(AvailablePlants.PEASHOOTER);
        availablePlants.add(AvailablePlants.WALNUT);
        availablePlants.add(AvailablePlants.FROZEN_PEASHOOTER);
        availablePlants.add(AvailablePlants.CHERRY_BOMB);
        availablePlants.add(AvailablePlants.CABBAGE);
        availablePlants.add(AvailablePlants.CHOMPER);
        availablePlants.add(AvailablePlants.GALTING_SHOOTER);
        availablePlants.add(AvailablePlants.WINTERMELON);
    }

    public void removeAvailableZombie(AvailableZombies zombie) {
        availableZombies.remove(zombie);
    }

    public void addToAvailableZombies(AvailableZombies zombie) {
        availableZombies.add(zombie);
    }

    public void removeAvailablePlant(AvailablePlants plant) {
        availablePlants.remove(plant);
    }

    public void addToAvailablePlants(AvailablePlants plant) {
        availablePlants.add(plant);
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

    public HashSet<AvailablePlants> getAvailablePlants() {
        return availablePlants;
    }

    public HashSet<AvailableZombies> getAvailableZombies() {
        return availableZombies;
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

    public int addUser(String username, String password) {
        try (Socket socket = new Socket("127.0.0.1", 10002);
             ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream())
             ; ObjectInputStream reader = new ObjectInputStream(socket.getInputStream())) {
            writer.writeObject("Sign up");
            writer.writeObject(username);
            writer.writeObject(password);
            String str = (String) reader.readObject();
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
        } catch (IOException | ClassNotFoundException e) {
            return 3;
        }
    }

    public int getUser(String username, String password) {
        try (Socket socket = new Socket("127.0.0.1", 10002);
             ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream())
             ; ObjectInputStream reader = new ObjectInputStream(socket.getInputStream())) {
            String str;
            writer.writeObject("Sign in");
            writer.writeObject(username);
            str = (String) reader.readObject();
            if(str.equals("Does not exist")) {
                return 1;
            }
            writer.writeObject(password);
            str = (String) reader.readObject();
            if(str.equals("Correct")) {
                this.username = username;
                score = (int) reader.readObject();
                wins = (int) reader.readObject();
                losses = (int) reader.readObject();
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
        } catch (IOException | ClassNotFoundException e) {
            return 3;
        }
    }

    public boolean updateUser(String newUsername, String newPassword) {
        try (Socket socket = new Socket("127.0.0.1", 10002);
             ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream())
             ; ObjectInputStream reader = new ObjectInputStream(socket.getInputStream())) {
            String str;
            writer.writeObject("Update");
            writer.writeObject(username);
            writer.writeObject(newUsername);
            writer.writeObject(newPassword);
            writer.writeObject(wins);
            writer.writeObject(losses);
            writer.writeObject(score);
            str = (String) reader.readObject();
            if(str != null && str.equals("Done")) {
                username = newUsername;
                mainMenu.updateUsername();
                store();
                return true;
            }
            return false;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    public String[] getPlayers() {
        try (Socket socket = new Socket("127.0.0.1", 10002) ;
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())
             ; ObjectInputStream objectReader = new ObjectInputStream(socket.getInputStream())) {
            outputStream.writeObject("Get users");
            return (String[]) objectReader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public boolean saveGame(GamePlayer gamePlayer) {
        try (Socket socket = new Socket("127.0.0.1", 10002)
             ; ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream())
             ; ObjectInputStream reader = new ObjectInputStream(socket.getInputStream())) {
            writer.writeObject("Save game");
            writer.writeObject(username);
            writer.writeObject(gamePlayer);
            String str = (String) reader.readObject();
            return str.equals("Done");
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    public String[] getLoadedGames() {
        try (Socket socket = new Socket("127.0.0.1", 10002);
             ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream())
             ; ObjectInputStream reader = new ObjectInputStream(socket.getInputStream())) {
            writer.writeObject("Take loaded games");
            writer.writeObject(username);
            return (String[]) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public GamePlayer getGame(String date) {
        try (Socket socket = new Socket("127.0.0.1", 10002);
             ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream())
             ; ObjectInputStream reader = new ObjectInputStream(socket.getInputStream())) {
            writer.writeObject("Get game");
            writer.writeObject(username);
            writer.writeObject(date);
            return (GamePlayer) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public void launchGame() {
        // Initialize the global thread-pool
        ThreadPool.init();
        initialise();

        if(!isMuted)
            ThreadPool.execute(soundPlayer);
        // Show the game menu ...

        EventQueue.invokeLater(() -> {
            if(isSignedIn) {
                frame.displayMenu(mainMenu);
            }
            else frame.displayMenu(launchingMenu);
            frame.setVisible(true);
            frame.init();
        });
        // After the player clicks 'PLAY' ...
    }

    public void gameFinished(GamePlayer gamePlayer) {
        score += gamePlayer.getScore();
        if(gamePlayer.getScore() > 0)
            ++wins;
        if(gamePlayer.getScore() < 0)
            ++losses;
        store();
        updateUser(username, username);

        frame.displayMenu(mainMenu);
    }

    public void play(GamePlayer gamePlayer) {
        soundPlayer.setFinished(true);
        EventQueue.invokeLater(() -> {
//             Create and execute the game-loop
            gamePlayer.initialise(this);
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
