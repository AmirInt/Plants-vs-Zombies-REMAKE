package entities;

import java.awt.*;

public abstract class Entity implements Runnable {

    protected int life;
    protected int xLocation;
    protected int yLocation;
    protected final int width;
    protected final int height;
    protected Image appearance;
    protected boolean gameFinished;

    public Entity(int life, int xLocation, int yLocation, int width, int height, Image appearance) {
        this.life = life;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.width = width;
        this.height = height;
        this.appearance = appearance;
        gameFinished = false;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
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
        life = 0;
    }
}
