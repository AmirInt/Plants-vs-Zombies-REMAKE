package entities.plants;

import managers.GamePlayer;
import javax.swing.*;
import java.awt.*;

/**
 * Represents the walnuts
 */
public class Walnut extends Plant {


    /**
     * Instantiates this class
     * @param xLocation The initial x location
     * @param yLocation The initial y location
     * @param gamePlayer The owning game player
     */
    public Walnut(int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(150, xLocation, yLocation, gamePlayer, 66, 75);
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        if(life > 100)
            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\walnut_full_life.gif").getImage());
        else if(life > 50)
            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\walnut_half_life.gif").getImage());
        else
            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\walnut_half_life (1).gif").getImage());
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

    @Override
    public synchronized void injure(int lifeTakenAway) {
        super.injure(lifeTakenAway);
        if(life < 100)
            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\walnut_half_life.gif").getImage());
        if(life < 50)
            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\walnut_half_life (1).gif").getImage());
        if(life <= 0) {
            life = 0;
            run();
        }
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\walnut_dead.gif").getImage());
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignore) { }
        die();
    }
}
