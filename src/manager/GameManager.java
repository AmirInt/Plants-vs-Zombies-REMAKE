package manager;

import graphics.*;
import menus.Menu;

import javax.swing.*;
import java.awt.*;

public class GameManager {

    GameFrame frame;
    Menu menu;

    public GameManager() {
        frame = new GameFrame();
        menu = new Menu(this, frame);
    }

    public void launchGame() {
        // Initialize the global thread-pool
        ThreadPool.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(() -> {
            frame.setLocationRelativeTo(null); // put frame at center of screen
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.displayMenu(this, menu);
            frame.setVisible(true);
            frame.initBufferStrategy();
        });
    }

    public void play() {
        EventQueue.invokeLater(() -> {
//             Create and execute the game-loop
            GameLoop game = new GameLoop(frame, menu);
            game.init();
            ThreadPool.execute(game);
//             and the game starts ...
        });
    }

    public static void main(String[] args) {
        GameManager gameManager = new GameManager();

        gameManager.launchGame();
    }
}
