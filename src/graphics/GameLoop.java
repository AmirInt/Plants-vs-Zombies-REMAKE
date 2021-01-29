package graphics;

import manager.GamePlayer;
import menus.MainMenu;

import javax.swing.*;

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
    private final MainMenu mainMenu;
    private final GamePlayer gamePlayer;

    public GameLoop(GameFrame frame, MainMenu mainMenu, GamePlayer gamePlayer) {
        canvas = frame;
        this.mainMenu = mainMenu;
        this.gamePlayer = gamePlayer;
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init() {
        state = new GameState(gamePlayer, canvas);
        canvas.setContentPane(new JPanel());
        canvas.addMouseListener(state.getMouseListener());
        canvas.addMouseMotionListener(state.getMouseMotionListener());
    }

    public synchronized void run() {
        boolean gameOver = false;
        while (!gameOver) {
            try {
                long start = System.currentTimeMillis();
                //
                canvas.render(state);
                gameOver = state.gameOver;
                //
                long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
                if (delay > 0)
                    Thread.sleep(delay);
            } catch (InterruptedException ignore) { }
        }
        canvas.render(state);
        canvas.removeMouseListener(state.getMouseListener());
        canvas.removeMouseMotionListener(state.getMouseMotionListener());
        gamePlayer.win();
        canvas.setContentPane(mainMenu);
        mainMenu.getListenersReady();
    }
}
