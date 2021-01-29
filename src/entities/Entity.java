package entities;

import manager.GamePlayer;

import java.awt.*;
import java.io.Serializable;

public abstract class Entity implements Runnable, Serializable {

    protected int life;
    protected int xLocation;
    protected int yLocation;
    protected final int width;
    protected final int height;
    transient protected Image appearance;
    protected final GamePlayer gamePlayer;

    public Entity(int life, int xLocation, int yLocation, int width, int height, Image appearance, GamePlayer gamePlayer) {
        this.life = life;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.width = width;
        this.height = height;
        this.appearance = appearance;
        this.gamePlayer = gamePlayer;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getXLocation() {
        return xLocation;
    }

    public int getYLocation() {
        return yLocation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setAppearance(Image appearance) {
        this.appearance = appearance;
    }

    public Image getAppearance() {
        return appearance;
    }

    public void die() {
        gamePlayer.remove(this);
    }
}
