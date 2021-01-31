package entities.others;

import entities.Entity;
import managers.GamePlayer;

import javax.swing.*;
import java.awt.*;

public class LawnMower extends Entity {

    int movingSpeed;
    boolean isTriggered;

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

    public void setTriggered(boolean triggered) {
        isTriggered = triggered;
    }

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

    public void move() {
        xLocation += 10;
        movingSpeed -= 1;
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\lawnmowerActivated.gif").getImage());
        while (xLocation < 1350 && gamePlayer.isNotGameFinished()) {
            if(gamePlayer.isGamePaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                gamePlayer.runOverZombies(this);
                try {
                    Thread.sleep(movingSpeed);
                } catch (InterruptedException ignore) {
                }
                move();
            }
        }
        die();
    }
}
