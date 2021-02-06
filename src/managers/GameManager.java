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
import java.util.HashSet;

/**
 * Class GameManager executes the different operation as saving the game onto the server,
 * getting the saved games from the server, launching the main menu, starting games, signing
 * the player in, up or out
 * @author Amir Fazlollahi
 * @since There is oppression
 * @version 0.0
 */
public class GameManager implements Serializable {

    transient private GameFrame frame;
    transient private MainMenu mainMenu;
    transient private LaunchingMenu launchingMenu;
//    The accessible plants and zombies
    private final HashSet<AvailableZombies> availableZombies;
    private final HashSet<AvailablePlants> availablePlants;

    private GameDifficulty gameDifficulty;
//    The locations of the cards upon the frame during the game
    static final int[] cardXs = {180, 250, 320, 390, 460, 530, 600, 670, 740, 810};
    static final int cardY = 80;

//    The signed-in player
    private String username;
    private boolean isSignedIn;
    private int score;
    private int wins;
    private int losses;

//    The menu soundtrack player
    transient private SoundPlayer soundPlayer;
    private boolean isMuted;
    private static final String path = "Game accessories\\sounds\\menu.wav";

    /**
     * Instantiates this class, used when there is no file previously saved to load
     * attributes from
     */
    public GameManager() {
        gameDifficulty = GameDifficulty.MEDIUM;
        availableZombies = new HashSet<>();
        availablePlants = new HashSet<>();
        wins = 0;
        losses = 0;
        score = 0;
        isMuted = false;
    }

    /**
     * Initialises the operation centre
     */
    public void initialise() {
        frame = new GameFrame();
        frame.setLocationRelativeTo(null); // put frame at center of screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu = new MainMenu(this, frame);
        launchingMenu = new LaunchingMenu(this, frame);
        soundPlayer = new SoundPlayer(path, 0, true);
    }

    /**
     * Replays the soundtrack if sounds are not muted
     */
    public void replay() {
        if(!isMuted) {
            soundPlayer = new SoundPlayer(path, 0, true);
            ThreadPool.execute(soundPlayer);
        }
    }

    /**
     * @return The sound playing state
     */
    public boolean isMuted() {
        return isMuted;
    }

    /**
     * Sets background and entity sounds muted or unmuted
     * @param muted The new state
     */
    public void setMuted(boolean muted) {
        isMuted = muted;
        if(isMuted) {
            soundPlayer.setFinished(true);
        } else {
            soundPlayer = new SoundPlayer(path, 0, true);
            ThreadPool.execute(soundPlayer);
        }
    }

    /**
     * If no file is available to load the attributes, this class' object
     * receives "factory reset" and sets entities to the predetermined state of
     * the game
     */
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

    /**
     * Removes from the available zombies
     * @param zombie The zombie to be removed
     */
    public void removeAvailableZombie(AvailableZombies zombie) {
        availableZombies.remove(zombie);
    }

    /**
     * Adds to the available zombies
     * @param zombie The zombie to be added
     */
    public void addToAvailableZombies(AvailableZombies zombie) {
        availableZombies.add(zombie);
    }

    /**
     * Removes from the available plants
     * @param plant The plant to be removed
     */
    public void removeAvailablePlant(AvailablePlants plant) {
        availablePlants.remove(plant);
    }

    /**
     * Adds to the available plants
     * @param plant The plant to be added
     */
    public void addToAvailablePlants(AvailablePlants plant) {
        availablePlants.add(plant);
    }

    /**
     * Sets the sign-in status
     * @param signedIn The sign-in status
     */
    public void setSignedIn(boolean signedIn) {
        isSignedIn = signedIn;
    }

    /**
     * Sets the game difficulty to a new state
     * @param gameDifficulty The new game difficulty status
     */
    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    /**
     * @return The general difficulty of the games
     */
    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    /**
     * @return The signed-in user
     */
    public String getUser() {
        return username;
    }

    /**
     * @return The signed-in players total score
     */
    public int getScore() {
        return score;
    }

    /**
     * @return The available plants
     */
    public HashSet<AvailablePlants> getAvailablePlants() {
        return availablePlants;
    }

    /**
     * @return The available zombie
     */
    public HashSet<AvailableZombies> getAvailableZombies() {
        return availableZombies;
    }

    /**
     * Signs the user out
     */
    public void signOut() {
        setSignedIn(false);
        boolean done = store();
        if(done) {
            frame.displayMenu(launchingMenu);
            frame.revalidate();
            frame.requestFocus();
        }
    }

    /**
     * Stores all of the status of this game manager to file
     * @return True if the operation has properly done
     */
    public boolean store() {
        try (FileOutputStream outputStream = new FileOutputStream("Game Manager\\Game Manager.dat")
             ; ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Signs a new user up
     * @param username The new user's username
     * @param password The new user's password
     * @return 0 if username is unique and correct, 1 if username already exists on the
     * server and 2 if there is a problem on and about the server
     */
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

    /**
     * Signs a user in
     * @param username The username
     * @param password The password
     * @return 0 if credentials are correct, 1 if incorrect and 2 if there is any network error
     */
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

    /**
     * Updates to user condition to the server, including its username, password and score
     * @param newUsername The new username chosen by the user
     * @param newPassword The new password chosen by the user
     * @return 0 if username is unique and the operation is fully carried out,
     * 1 if username already exists on the server side and 2 if there's any network error
     */
    public int updateUser(String newUsername, String newPassword) {
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
            if(str.equals("Done")) {
                username = newUsername;
                mainMenu.updateUsername();
                store();
                return 0;
            }
            return 1;
        } catch (IOException | ClassNotFoundException e) {
            return 2;
        }
    }

    /**
     * @return The list all the players on the server
     */
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

    /**
     * Saves a game onto the server
     * @param gamePlayer The game player object to be saved
     * @return true if done properly, false otherwise for any reason
     */
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

    /**
     * @return The list of all the games that the player has saved and left unplayed
     */
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

    /**
     * @param date The date of the game
     * @return The previously saved game
     */
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

    /**
     * Launches the menus and the starts the game
     */
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

    /**
     * Accomplishes the operations upon the end of a game.
     * Updates the user condition and displays the final menu.
     * @param gamePlayer The game player that has just ended
     */
    public void gameFinished(GamePlayer gamePlayer) {
        score += gamePlayer.getScore();
        try {
            Thread.sleep(500);
            frame.displayMenu(new FinishingMenu(gamePlayer.getScore(), frame));
            Thread.sleep(4000);
        } catch (InterruptedException ignore) { }
        if(gamePlayer.getScore() > 0)
            ++wins;
        if(gamePlayer.getScore() < 0)
            ++losses;
        store();
        updateUser(username, username);

        frame.displayMenu(mainMenu);
    }

    /**
     * Starts the game it is given
     * @param gamePlayer The game to be started, either loaded or newly created
     */
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
//        Attempting to load the game manager from file
        try (FileInputStream inputStream = new FileInputStream("Game Manager\\Game Manager.dat")
             ; ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)){
            gameManager = (GameManager) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ignore) {
//            Creating a new game manager if no file is available
            gameManager = new GameManager();
            gameManager.setAvailableEntities();
        }

        gameManager.launchGame();
    }
}
