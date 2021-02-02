package entities.plants;

import entities.Entity;
import managers.GamePlayer;

import java.awt.*;

/**
 * Plant represents the plant entities of the game
 */
public abstract class Plant extends Entity {
    /**
     * Instantiates this class
     * @param life The initial life
     * @param xLocation The initial x location
     * @param yLocation The initial x location
     * @param gamePlayer The owning game player
     * @param width The image width
     * @param height The image height
     */
    public Plant(int life, int xLocation, int yLocation, GamePlayer gamePlayer, int width, int height) {
        super(life, xLocation, yLocation, width, height, gamePlayer);
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
    }

    @Override
    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
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

    /**
     * Reduces this plant's life by "lifeTakenAway"
     * @param lifeTakenAway The amount to be reduced
     */
    public synchronized void injure(int lifeTakenAway) {
        life -= lifeTakenAway;
        if(life <= 0)
            life = 0;
    }

    @Override
    public void die() {
        super.die();
    }
}
