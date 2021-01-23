package manager;

import enums.*;
import graphics.*;
import menus.Menu;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GameManager {

    GameFrame frame;
    Menu menu;
    HashMap<Integer, AvailableZombies> availableZombies;
    GameDifficulty gameDifficulty;

    public GameManager() {
        frame = new GameFrame();
        menu = new Menu(this, frame);
        gameDifficulty = GameDifficulty.MEDIUM;
        availableZombies = new HashMap<>();
        availableZombies.put(0, AvailableZombies.NormalZombie);
        availableZombies.put(1, AvailableZombies.BucketHeadZombie);
        availableZombies.put(2, AvailableZombies.ConeHeadZombie);
    }

    public void launchGame() {
        // Initialize the global thread-pool
        ThreadPool.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(() -> {
            frame.setLocationRelativeTo(null); // put frame at center of screen
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.displayMenu(menu);
            frame.setVisible(true);
            frame.initBufferStrategy();
        });
    }

    public void play() {
        EventQueue.invokeLater(() -> {
//             Create and execute the game-loop
            GamePlayer gamePlayer = new GamePlayer(GameDifficulty.MEDIUM, availableZombies);
            GameLoop game = new GameLoop(frame, menu, gamePlayer);
            game.init();
            frame.setEntities(gamePlayer);
            ThreadPool.execute(game);
            ThreadPool.execute(gamePlayer);
//             and the game starts ...
        });
    }

    public static void main(String[] args) {
        GameManager gameManager = new GameManager();

        gameManager.launchGame();
    }
}
