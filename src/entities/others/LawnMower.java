package entities.others;

import entities.Entity;
import graphics.ThreadPool;
import managers.GamePlayer;
import sounds.SoundPlayer;
import javax.swing.*;
import java.awt.*;

/**
 * Represents lawn-mowers
 */
public class LawnMower extends Entity {

    int movingSpeed;
//    isTriggered determines if this lawn-mower has yet been hit by a zombie or not
    boolean isTriggered;
    private static final String path = "Game Accessories\\sounds\\lawn-mower.wav";

    /**
     * Instantiates this class
     * @param life Nothing important
     * @param xLocation The initial x location
     * @param yLocation The initial y location
     * @param gamePlayer The owning game player
     */
    public LawnMower(int life, int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(life, xLocation, yLocation, 100, 100, gamePlayer);
        movingSpeed = 140;
        isTriggered = false;
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\lawn_mower.gif").getImage());
    }

    /**
     * Sets whether this lawn-mower is triggered or not
     * @param triggered The status of the lawn-mower
     */
    public void setTriggered(boolean triggered) {
        isTriggered = triggered;
    }

    /**
     * @return The running status of the lawn-mower
     */
    public boolean isTriggered() {
        return isTriggered;
    }

    @Override
    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
    }

    @Override
    public int getYLocation() {
        return super.getYLocation();
    }

    @Override
    public int getXLocation() {
        return super.getXLocation();
    }

    @Override
    public int getWidth() {
        return super.getWidth();
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }

    @Override
    public Image getAppearance() {
        return super.getAppearance();
    }

    /**
     * Moves this lawn-mower along the row
     */
    public void move() {
        xLocation += 7;
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        SoundPlayer soundPlayer = null;
        if(gamePlayer.isNotMuted()) {
            soundPlayer = new SoundPlayer(path, 0, true);
            ThreadPool.execute(soundPlayer);
        }
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\lawnmowerActivated.gif").getImage());
        while (xLocation < 1350 && gamePlayer.isNotGameFinished()) {
            if(gamePlayer.isGamePaused()) {
                try {
                    if(soundPlayer != null)
                        soundPlayer.pause();
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                if(soundPlayer != null)
                    soundPlayer.resume();
                if(xLocation > 600)
                    if(soundPlayer != null)
                        soundPlayer.setFinished(true);
                gamePlayer.runOverZombies(this);
                try {
                    Thread.sleep(movingSpeed);
                } catch (InterruptedException ignore) {
                }
                move();
            }
        }
        if(soundPlayer != null)
            soundPlayer.setFinished(true);
        die();
    }
}
