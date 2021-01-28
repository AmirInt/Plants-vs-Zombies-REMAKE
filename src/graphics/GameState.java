package graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import cards.*;
import entities.Entity;
import entities.plants.*;
import entities.others.Sun;
import manager.GamePlayer;
import javax.swing.*;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState {

    public boolean gameOver;
    private int mouseX, mouseY;
    private boolean isToPlant;

    private final GamePlayer gamePlayer;
    private final GameFrame gameFrame;
    private final MouseHandler mouseHandler;
    private final MouseMotionHandler mouseMotionHandler;
    private final ArrayList<Card> availablePlants;
    private Card selectedCard;

    public GameState(GamePlayer gamePlayer, GameFrame gameFrame) {
        gameOver = false;
        isToPlant = false;

        this.gamePlayer = gamePlayer;
        this.gameFrame = gameFrame;
        availablePlants = gamePlayer.getAvailablePlants();

        mouseHandler = new MouseHandler();
        mouseMotionHandler = new MouseMotionHandler();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
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

        Image image = null;

        if (selectedCard instanceof SunflowerCard) {
            image = new ImageIcon("Game accessories\\images\\Gifs\\sunflower.gif").getImage();
        } else if (selectedCard instanceof PeaShooterCard) {
            image = new ImageIcon("Game accessories\\images\\Gifs\\peashooter.gif").getImage();
        } else if (selectedCard instanceof SnowPeaCard) {
            image = new ImageIcon("Game accessories\\images\\Gifs\\freezepeashooter.gif").getImage();
        } else if (selectedCard instanceof WalnutCard) {
            image = new ImageIcon("Game accessories\\images\\Gifs\\walnut_full_life.gif").getImage();
        } else if (selectedCard instanceof CherryBombCard) {
            image = new ImageIcon("Game accessories\\images\\Gifs\\newCherryBomb.gif").getImage();
        }

        return image;
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
                    if (selectedCard instanceof SunflowerCard) {
                        Sunflower sunflower = new Sunflower(column, row, gamePlayer);
                        gamePlayer.add(sunflower);
                        ThreadPool.execute(sunflower);
                    } else if (selectedCard instanceof PeaShooterCard) {
                        PeaShooter peaShooter = new PeaShooter(column, row, gamePlayer);
                        gamePlayer.add(peaShooter);
                        ThreadPool.execute(peaShooter);
                    } else if (selectedCard instanceof SnowPeaCard) {
                        SnowPea snowPea = new SnowPea(column, row, gamePlayer);
                        gamePlayer.add(snowPea);
                        ThreadPool.execute(snowPea);
                    } else if (selectedCard instanceof WalnutCard) {
                        Walnut walnut = new Walnut(column, row, gamePlayer);
                        gamePlayer.add(walnut);
                        ThreadPool.execute(walnut);
                    } else if (selectedCard instanceof CherryBombCard) {
                        CherryBomb cherryBomb = new CherryBomb(column, row, gamePlayer);
                        gamePlayer.add(cherryBomb);
                        ThreadPool.execute(cherryBomb);
                    }
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
}

