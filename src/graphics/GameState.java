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
    private PauseMenu pauseMenu;

    public GameState(GamePlayer gamePlayer, GameFrame gameFrame, GameManager gameManager) {
        isToPlant = false;

        this.gamePlayer = gamePlayer;
        this.gameFrame = gameFrame;
        this.gameManager = gameManager;
        availablePlants = gamePlayer.getAvailablePlants();
        pauseMenu = new PauseMenu(gameManager, gameFrame, this);

        mouseHandler = new MouseHandler();
        mouseMotionHandler = new MouseMotionHandler();
        keyHandler = new KeyHandler();
    }

    public int getMouseX() {
        return mouseX;
    }

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

    public boolean getToPlant() {
        return isToPlant;
    }

    public int getEnergy() {
        return gamePlayer.getEnergy();
    }

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

    public void pauseGame() {
        gameFrame.removeMouseMotionListener(mouseMotionHandler);
        gameFrame.removeMouseListener(mouseHandler);
        gameFrame.removeKeyListener(keyHandler);
        gamePlayer.setGamePaused(true);
    }

    public void unpauseGame() {
        gameFrame.addKeyListener(keyHandler);
        gameFrame.addMouseListener(mouseHandler);
        gameFrame.addMouseMotionListener(mouseMotionHandler);
        gamePlayer.setGamePaused(false);
    }

    public boolean saveGame() {
        return gameManager.saveGame(gamePlayer);
    }

    public void killGame() {
        gamePlayer.killGame(false);
    }

    /**
     * The mouse handler.
     */
    private class MouseHandler extends MouseAdapter {

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
                            && gamePlayer.getEnergy() >= card.getRequiredEnergy()) {
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

    private class MouseMotionHandler extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

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

