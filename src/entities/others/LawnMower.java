package entities.others;

import entities.Entity;
import manager.GamePlayer;

import javax.swing.*;
import java.awt.*;

public class LawnMower extends Entity {

    int movingSpeed;

    public LawnMower(int life, int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(life, xLocation, yLocation, 100, 100,
                new ImageIcon("Game accessories\\images\\Gifs\\lawn_mower.gif").getImage(), gamePlayer);
        movingSpeed = 0;
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
        xLocation += movingSpeed;
        ++movingSpeed;
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\lawnmowerActivated.gif").getImage());
        while (xLocation < 1350) {
            gamePlayer.runOverZombies(this);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) { }
            move();
        }
        die();
    }
}
