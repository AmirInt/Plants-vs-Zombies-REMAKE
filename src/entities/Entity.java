package entities;

import managers.GamePlayer;
import java.awt.*;
import java.io.Serializable;

/**
 * Class Entity is responsible for the different attributes and actions of the entities of
 * the game
 */
public abstract class Entity implements Runnable, Serializable {

    protected int life;
    protected int xLocation;
    protected int yLocation;
    protected int width;
    protected int height;
//    The associating image of this entity
    transient protected Image appearance;
    transient protected GamePlayer gamePlayer;

    /**
     * Instantiates this entity
     * @param life This entity's life
     * @param xLocation This entity's (initial x location)
     * @param yLocation This entity's (initial y location)
     * @param width This entity's image width
     * @param height This entity's image height
     * @param gamePlayer The game player this entity belongs to
     */
    public Entity(int life, int xLocation, int yLocation, int width, int height, GamePlayer gamePlayer) {
        this.life = life;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.width = width;
        this.height = height;
        this.gamePlayer = gamePlayer;
    }

    /**
     * Generally initialises the game player this entity belongs to and
     * sets the images and integrated sounds
     * @param gamePlayer The owner game player
     */
    public void initialise(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    /**
     * Sets the life
     * @param life The life status
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * @return This entity's x location
     */
    public int getXLocation() {
        return xLocation;
    }

    /**
     * @return This entity's y location
     */
    public int getYLocation() {
        return yLocation;
    }

    /**
     * @return This entity's image width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return This entity's image height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets this entity's image
     * @param appearance The new appearance
     */
    public void setAppearance(Image appearance) {
        this.appearance = appearance;
    }

    /**
     * @return This entity's image
     */
    public Image getAppearance() {
        return appearance;
    }

    /**
     * Removes this entity from the list of the game's entities
     */
    public void die() {
        gamePlayer.remove(this);
    }
}
