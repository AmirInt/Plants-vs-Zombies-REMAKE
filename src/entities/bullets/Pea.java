package entities.bullets;

import entities.zombies.Zombie;
import managers.GamePlayer;
import javax.swing.*;
import java.awt.*;

/**
 * Represents peas
 */
public class Pea extends Bullet {

    /**
     * Instantiates this class
     * @param xLocation The initial x location
     * @param yLocation The initial y location
     * @param gamePlayer The owning game player
     */
    public Pea(int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(xLocation, yLocation, 30, gamePlayer);
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        setAppearance(new ImageIcon("Game accessories\\images\\pea.png").getImage());
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
