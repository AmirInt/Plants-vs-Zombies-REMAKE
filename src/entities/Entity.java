package entities;

import manager.GamePlayer;

import java.awt.*;
import entities.others.*;
import entities.zombies.*;
import entities.plants.*;
import entities.bullets.*;

public abstract class Entity implements Runnable {

    protected int life;
    protected int xLocation;
    protected int yLocation;
    protected final int width;
    protected final int height;
    protected Image appearance;
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
        gamePlayer.remove(gamePlayer.getEntities(), this);
        if(this instanceof Sun)
            gamePlayer.remove(gamePlayer.getSuns(), (Sun) this);
        if(this instanceof Zombie)
            gamePlayer.remove(gamePlayer.getZombies(), (Zombie) this);
        if(this instanceof Plant)
            gamePlayer.remove(gamePlayer.getPlants(), (Plant) this);
    }
}
