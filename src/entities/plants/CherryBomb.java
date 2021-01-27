package entities.plants;

import manager.GamePlayer;

import javax.swing.*;
import java.awt.*;

public class CherryBomb extends Plant {

    public CherryBomb(int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(70, xLocation, yLocation,
                new ImageIcon("Game accessories\\images\\Gifs\\newCherryBomb.gif").getImage(), gamePlayer);
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

    public void bust() {

    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            bust();
            die();
        } catch (InterruptedException ignore) { }
    }

}
