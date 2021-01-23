package entities.plants;

import entities.Entity;
import java.awt.*;

public abstract class Plant extends Entity {

    public Plant(int life, int xLocation, int yLocation, Image appearance) {
        super(life, xLocation, yLocation, 70, 100, appearance);
    }

    @Override
    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
    }

    @Override
    public void setGameFinished(boolean gameFinished) {
        super.setGameFinished(gameFinished);
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

    @Override
    public void die() {
        super.die();
    }
}
