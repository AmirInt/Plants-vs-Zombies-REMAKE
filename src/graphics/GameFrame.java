package graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import javax.swing.*;
import entities.Entity;
import cards.Card;
import managers.GamePlayer;
import menus.Menu;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 *    http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 *    http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio

    private BufferStrategy bufferStrategy;
    private final Image image;
    private ArrayList<Entity> entities;
    private ArrayList<Card> availablePlants;

    public GameFrame() {
        super("Plants vs. Zombies");
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        image = new ImageIcon("Game accessories\\images\\mainBG.png").getImage();
    }

    public void setEntities(GamePlayer gamePlayer) {
        entities = gamePlayer.getEntities();
    }

    public void setAvailablePlants(GamePlayer gamePlayer) {
        availablePlants = gamePlayer.getAvailablePlants();
    }

    /**
     * This must be called once after the JFrame is shown:
     *    frame.setVisible(true);
     * and before any rendering is started.
     */
    public void init() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

    public void displayMenu(Menu menu) {
        menu.update();
        setContentPane(menu);
        menu.getListenersReady();
        revalidate();
        repaint();
    }

    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private synchronized void doRendering(Graphics2D g2d, GameState state) {
        // Draw background
        g2d.setFont(new Font("", Font.BOLD, 16));
        g2d.setColor(new Color(200, 150, 40));

        g2d.drawImage(image, 0, 30, GAME_WIDTH, GAME_HEIGHT - 20, Color.BLACK, null);

        g2d.drawString(state.getEnergy() + "", 50, 120);

        for (Card availablePlant :
                availablePlants) {
            g2d.drawImage(availablePlant.getCardImage(), availablePlant.getXLocation() - availablePlant.getWidth() / 2,
                    availablePlant.getYLocation() - availablePlant.getHeight() / 2, null);
        }

        try {
            for (Entity entity :
                    entities) {
                int width = entity.getWidth();
                int height = entity.getHeight();
                int x = entity.getXLocation() - width / 2;
                int y = entity.getYLocation() - height / 2;
                g2d.drawImage(entity.getAppearance(), x, y, width, height, null);
            }
        } catch (ConcurrentModificationException ignore) { }

        if(state.getToPlant()) {
            g2d.drawImage(state.getSelectedCardImage(), state.getMouseX(), state.getMouseY(), null);
        }
    }

}
