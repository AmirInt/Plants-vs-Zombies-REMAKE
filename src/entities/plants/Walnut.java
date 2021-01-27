package entities.plants;

import manager.GamePlayer;

import javax.swing.*;
import java.awt.*;

public class Walnut extends Plant {

    public Walnut(int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(150, xLocation, yLocation,
                new ImageIcon("Game accessories\\images\\Gifs\\walnut_full_life.gif").getImage(), gamePlayer);
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

    @Override
    public synchronized void injure(int lifeTakenAway) {
        super.injure(lifeTakenAway);
        if(life < 100)
            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\walnut_half_life.gif").getImage());
        if(life < 50)
            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\walnut_half_life (1).gif").getImage());
    }

    @Override
    public void die() {
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\walnut_dead.gif").getImage());
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignore) { }
        super.die();
    }

    @Override
    public void run() {

    }
}
