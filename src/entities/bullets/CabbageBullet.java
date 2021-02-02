package entities.bullets;

import entities.zombies.Zombie;
import managers.GamePlayer;

import javax.swing.*;
import java.awt.*;

/**
 * Represents cabbages
 */
public class CabbageBullet extends Bullet {

    /**
     * Instantiates this class
     * @param xLocation The initial x location
     * @param yLocation The initial y location
     * @param gamePlayer The owning game player
     */
    public CabbageBullet(int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(xLocation, yLocation, 50, gamePlayer);
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        setAppearance(new ImageIcon("Game accessories\\images\\Cabbage Bullet.png").getImage());
    }

    @Override
    public int getXLocation() {
        return super.getXLocation();
    }

    @Override
    public int getYLocation() {
        return super.getYLocation();
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

    @Override
    public void hit(Zombie zombie) {
        super.hit(zombie);
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        super.run();
    }
}
