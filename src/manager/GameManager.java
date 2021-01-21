package manager;

import graphics.*;

import javax.swing.*;
import java.awt.*;

public class GameManager {

    public static void main(String[] args) {
        // Initialize the global thread-pool
        ThreadPool.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(() -> {
            GameFrame frame = new GameFrame("Simple Ball !");
            frame.setLocationRelativeTo(null); // put frame at center of screen
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setVisible(true);
            frame.initBufferStrategy();
            // Create and execute the game-loop
            GameLoop game = new GameLoop(frame);
            game.init();
            ThreadPool.execute(game);
            // and the game starts ...
        });
    }
}
