package graphics;

import managers.GameManager;
import managers.GamePlayer;
import menus.FinishingMenu;

import javax.swing.*;
import java.io.IOException;

/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods
 * in the while loop (update() and render()) should be
 * long running! Both must execute very quickly, without
 * any waiting and blocking!
 *
 * Detailed discussion on different game loop design
 * patterns is available in the following link:
 *    http://gameprogrammingpatterns.com/game-loop.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameLoop implements Runnable {

    /**
     * Frame Per Second.
     * Higher is better, but any value above 24 is fine.
     */
    public static final int FPS = 30;

    private final GameFrame canvas;
    private GameState state;
    private final GameManager gameManager;
    private final GamePlayer gamePlayer;

    public GameLoop(GameFrame frame, GamePlayer gamePlayer, GameManager gameManager) {
        canvas = frame;
        this.gamePlayer = gamePlayer;
        this.gameManager = gameManager;
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init() {
        state = new GameState(gamePlayer, canvas, gameManager);
        canvas.setContentPane(new JPanel());
    }

    public GameState getState() {
        return state;
    }

    public synchronized void run() {
        while (gamePlayer.isNotGameFinished()) {
            if(gamePlayer.isGamePaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                try {
                    long start = System.currentTimeMillis();
                    //
                    canvas.render(state);
                    //
                    long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
                    if (delay > 0)
                        Thread.sleep(delay);
                } catch (InterruptedException ignore) {
                }
            }
        }
        canvas.render(state);
        canvas.displayMenu(new FinishingMenu(gamePlayer.getScore(), canvas));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignore) { }
        canvas.removeKeyListener(state.getKeyListener());
        canvas.removeMouseListener(state.getMouseListener());
        canvas.removeMouseMotionListener(state.getMouseMotionListener());
    }
}
