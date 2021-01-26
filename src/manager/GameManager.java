package manager;

import enums.*;
import graphics.*;
import cards.*;
import menus.Menu;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameManager {

    private final GameFrame frame;
    private final Menu menu;
    private final ArrayList<AvailableZombies> availableZombies;
    private final ArrayList<Card> availablePlants;
    private final GameDifficulty gameDifficulty;
    private final int[] cardXs = {180, 254, 328, 402, 476, 540};
    private final int cardY = 80;

    public GameManager() {
        frame = new GameFrame();
        menu = new Menu(this, frame);
        gameDifficulty = GameDifficulty.MEDIUM;
        availableZombies = new ArrayList<>();
        availablePlants = new ArrayList<>();
        setAvailableEntities();
    }

    private void setAvailableEntities() {

        availableZombies.add(AvailableZombies.NormalZombie);
        availableZombies.add(AvailableZombies.BucketHeadZombie);
        availableZombies.add(AvailableZombies.ConeHeadZombie);

        availablePlants.add(CherryBombCard.getInstance(gameDifficulty, cardXs[4], cardY));
        availablePlants.add(WalnutCard.getInstance(cardXs[3], cardY));
        availablePlants.add(SunflowerCard.getInstance(cardXs[0], cardY));
        availablePlants.add(SnowPeaCard.getInstance(gameDifficulty, cardXs[2], cardY));
        availablePlants.add(PeaShooterCard.getInstance(cardXs[1], cardY));

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
            GamePlayer gamePlayer = new GamePlayer(GameDifficulty.MEDIUM, availableZombies, availablePlants);
            GameLoop game = new GameLoop(frame, menu, gamePlayer);
            game.init();
            frame.setEntities(gamePlayer);
            frame.setAvailablePlants(gamePlayer);
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
