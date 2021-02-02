package entities.others;

import entities.Entity;
import managers.GamePlayer;
import javax.swing.*;

/**
 * Represents suns in the game
 */
public class Sun extends Entity {

    private int movingSpeed;
//    Where this sun should land
    private final int yDestination;
    private boolean isFallen;

    /**
     * Instantiates this class
     * @param xLocation The initial x location
     * @param yLocation The initial y location
     * @param yDestination The final y location
     * @param gamePlayer The owning game player
     */
    public Sun(int xLocation, int yLocation, int yDestination, GamePlayer gamePlayer) {
        super(10, xLocation, yLocation, 50, 48, gamePlayer);
        this.yDestination = yDestination;
        movingSpeed = 0;
        isFallen = false;
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\sun.gif").getImage());
    }

    /**
     * This method models the falling of the sun and lands on its
     * predetermined place
     */
    public void fall() {
        while(yLocation < yDestination) {
            if(gamePlayer.isGamePaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                try {
                    Thread.sleep(30);
                    yLocation += movingSpeed;
                    xLocation += 5;
                    movingSpeed += 1;
                } catch (InterruptedException ignore) {
                }
            }
        }
        while (movingSpeed != 0) {
            if(gamePlayer.isGamePaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                try {
                    Thread.sleep(30);
                    yLocation -= movingSpeed;
                    xLocation += 5;
                    movingSpeed -= 15;
                    movingSpeed = Math.max(movingSpeed, 0);
                } catch (InterruptedException ignore) {
                }
            }
        }
        while(yLocation < yDestination) {
            if(gamePlayer.isGamePaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                try {
                    Thread.sleep(30);
                    yLocation += movingSpeed;
                    xLocation += 5;
                    movingSpeed += 1;
                } catch (InterruptedException ignore) {
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            if(gamePlayer.isGamePaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                try {
                    Thread.sleep(30);
                    xLocation += 10 - i;
                } catch (InterruptedException ignore) {
                }
            }
        }
        isFallen = true;
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        if(!isFallen)
            fall();
    }
}
