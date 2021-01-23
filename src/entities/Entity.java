package entities;

import java.awt.*;

public abstract class Entity implements Runnable {

    protected int life;
    protected int xLocation;
    protected int yLocation;
    protected Image appearance;

    public Entity(int life, int xLocation, int yLocation, Image appearance) {
        this.life = life;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.appearance = appearance;
    }

    public int getXLocation() {
        return xLocation;
    }

    public int getYLocation() {
        return yLocation;
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
