package entities.plants;

import entities.Entity;
import managers.GamePlayer;

import java.awt.*;

public abstract class Plant extends Entity {

    public Plant(int life, int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(life, xLocation, yLocation, 70, 100, gamePlayer);
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

    public void getReadyForSabotage() { }

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
