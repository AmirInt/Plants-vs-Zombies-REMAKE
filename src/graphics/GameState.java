package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import cards.*;
import entities.Entity;
import entities.plants.*;
import entities.others.Sun;
import managers.GameManager;
import managers.GamePlayer;
import menus.PauseMenu;
import sounds.SoundPlayer;

import javax.swing.*;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState {

    private int mouseX, mouseY;
    private boolean isToPlant;

    private final GamePlayer gamePlayer;
    private final GameFrame gameFrame;
    private final GameManager gameManager;
    private final MouseHandler mouseHandler;
    private final MouseMotionHandler mouseMotionHandler;
    private final KeyHandler keyHandler;
    private final ArrayList<Card> availablePlants;
    private Card selectedCard;
    private final PauseMenu pauseMenu;
    private static final String path = "Game Accessories\\sounds\\ting.wav";

    public GameState(GamePlayer gamePlayer, GameFrame gameFrame, GameManager gameManager) {
//        isToPlant determines whether the player is going to plant a new entity or not
        isToPlant = false;

        this.gamePlayer = gamePlayer;
        this.gameFrame = gameFrame;
        this.gameManager = gameManager;
        availablePlants = gamePlayer.getAvailablePlants();
//        pauseMenu is displayed when esc is typed on the keyboard
        pauseMenu = new PauseMenu(gameManager, gameFrame, this);

        mouseHandler = new MouseHandler();
        mouseMotionHandler = new MouseMotionHandler();
        keyHandler = new KeyHandler();
    }

    /**
     * @return The x position of the mouse
     */
    public int getMouseX() {
        return mouseX;
    }

    /**
     * @return The y position of the mouse
     */
    public int getMouseY() {
        return mouseY;
    }

    public KeyListener getKeyListener() {
        return keyHandler;
    }
    public MouseListener getMouseListener() {
        return mouseHandler;
    }
    public MouseMotionListener getMouseMotionListener() {
        return mouseMotionHandler;
    }

    /**
     * @return The planting status of the player (True if the player plans to
     * plant a new thing)
     */
    public boolean getToPlant() {
        return isToPlant;
    }

    /**
     * @return The ongoing game energy state
     */
    public int getEnergy() {
        return gamePlayer.getEnergy();
    }

    /**
     * @return The selected card image
     */
    public Image getSelectedCardImage() {

        Image image;

        if (selectedCard instanceof SunflowerCard)
            image = new ImageIcon("Game accessories\\images\\Gifs\\sun_flower.gif").getImage();
        else if (selectedCard instanceof PeaShooterCard)
            image = new ImageIcon("Game accessories\\images\\Gifs\\Pea-Shooter-unscreen.gif").getImage();
        else if (selectedCard instanceof SnowPeaCard)
            image = new ImageIcon("Game accessories\\images\\Gifs\\SnowPea-Shooter-unscreen.gif").getImage();
        else if (selectedCard instanceof WalnutCard)
            image = new ImageIcon("Game accessories\\images\\Gifs\\walnut_full_life.gif").getImage();
        else if (selectedCard instanceof CherryBombCard)
            image = new ImageIcon("Game accessories\\images\\Gifs\\newCherryBomb.gif").getImage();
        else if (selectedCard instanceof CabbageCard)
            image = new ImageIcon("Game accessories\\images\\Gifs\\Cauliflower-unscreen.gif").getImage();
        else if (selectedCard instanceof ChomperCard)
            image = new ImageIcon("Game accessories\\images\\Gifs\\Chomper Chomping.gif").getImage();
        else if (selectedCard instanceof GaltingPeaShooterCard)
            image = new ImageIcon("Game accessories\\images\\Gifs\\Gatling-Pea-unscreen.gif").getImage();
        else
            image = new ImageIcon("Game accessories\\images\\Gifs\\Winter-Melon-unscreen.gif").getImage();

        return image;
    }

    /**
     * Pauses the game
     */
    public void pauseGame() {
        gameFrame.removeMouseMotionListener(mouseMotionHandler);
        gameFrame.removeMouseListener(mouseHandler);
        gameFrame.removeKeyListener(keyHandler);
        gamePlayer.setGamePaused(true);
    }

    /**
     * Resumes the afoot game
     */
    public void unpauseGame() {
        gameFrame.addKeyListener(keyHandler);
        gameFrame.addMouseListener(mouseHandler);
        gameFrame.addMouseMotionListener(mouseMotionHandler);
        gamePlayer.setGamePaused(false);
    }

    /**
     * Attempts to save the ongoing game
     * @return True if it's done properly
     */
    public boolean saveGame() {
        return gameManager.saveGame(gamePlayer);
    }

    /**
     * Stops the game momentarily
     */
    public void killGame() {
        gamePlayer.killGame(false);
    }

    /**
     * The mouse handler.
     */
    private class MouseHandler extends MouseAdapter {
        /**
         * Checks the position of the mouse and compares it with that of other entities in the
         * game, like the suns and the cards
         * @param point The given point
         * @param location The entity's location
         * @param range The range of the entity's size
         * @return True if the point coincides with the entity's location
         */
        private boolean isInside(int point, int location, int range) {
            return !((point > location + range / 2) || (point < location - range / 2));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && !isToPlant) {
                for (Card card :
                        availablePlants) {
                    if (isInside(e.getX(), card.getXLocation(), card.getWidth())
                            && isInside(e.getY(), card.getYLocation(), card.getHeight())
                            && gamePlayer.getEnergy() >= card.getRequiredEnergy()
                            && card.getEnabled()) {
                        gameFrame.addMouseMotionListener(mouseMotionHandler);
                        selectedCard = card;
                        isToPlant = true;
                        return;
                    }
                }
                for (Entity entity :
                        gamePlayer.getEntities()) {
                    if(entity instanceof Sun)
                        if (isInside(e.getX(), entity.getXLocation(), entity.getWidth())
                                && isInside(e.getY(), entity.getYLocation(), entity.getHeight())) {
                            if(!gameManager.isMuted())
                                ThreadPool.execute(new SoundPlayer(path, 400, false));
                            gamePlayer.remove(entity);
                            gamePlayer.setEnergy(gamePlayer.getEnergy() + 25);
                            return;
                        }
                }
            }
            else if (e.getButton() == MouseEvent.BUTTON1 && isToPlant) {
                int row = gamePlayer.rowOf(e.getY());
                int column = gamePlayer.columnOf(e.getX());
                if(gamePlayer.isFree(row, column) && selectedCard.getEnabled()) {
                    Entity entity;
                    if (selectedCard instanceof SunflowerCard)
                        entity = new Sunflower(column, row, gamePlayer);
                    else if (selectedCard instanceof PeaShooterCard)
                        entity = new PeaShooter(column, row, gamePlayer);
                    else if (selectedCard instanceof SnowPeaCard)
                        entity = new SnowPea(column, row, gamePlayer);
                    else if (selectedCard instanceof WalnutCard)
                        entity = new Walnut(column, row, gamePlayer);
                    else if (selectedCard instanceof CherryBombCard)
                        entity = new CherryBomb(column, row, gamePlayer);
                    else if (selectedCard instanceof CabbageCard)
                        entity = new Cabbage(column, row, gamePlayer);
                    else if (selectedCard instanceof ChomperCard)
                        entity = new Chomper(column, row, gamePlayer);
                    else if (selectedCard instanceof GaltingPeaShooterCard)
                        entity = new GaltingPeaShooter(column, row, gamePlayer);
                    else entity = new WinterMelon(column, row, gamePlayer);

                    entity.initialise(gamePlayer);
                    gamePlayer.add(entity);
                    if(!(entity instanceof Walnut))
                        ThreadPool.execute(entity);

                    ThreadPool.execute(selectedCard);
                    gameFrame.removeMouseMotionListener(mouseMotionHandler);
                    gamePlayer.consumeEnergy(selectedCard.getRequiredEnergy());
                    isToPlant = false;
                }
            }
            else if (e.getButton() == MouseEvent.BUTTON3 && isToPlant) {
                gameFrame.removeMouseMotionListener(mouseMotionHandler);
                isToPlant = false;
            }
        }
    }

    /**
     * The mouse motion listener
     */
    private class MouseMotionHandler extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

    /**
     * The key listener
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                pauseGame();
                gameFrame.displayMenu(pauseMenu);
            }
        }
    }
}

