package entities.plants;

import graphics.ThreadPool;
import managers.GamePlayer;
import sounds.SoundPlayer;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the cherry bombs
 */
public class CherryBomb extends Plant {

    private static final String path = "Game accessories\\sounds\\explosion.wav";

    /**
     * Instantiates this class
     * @param xLocation The initial x location
     * @param yLocation The initial y location
     * @param gamePlayer The owning game player
     */
    public CherryBomb(int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(70, xLocation, yLocation, gamePlayer, 40, 32);
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\newCherryBomb.gif").getImage());
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

    /**
     * Has all of the nearby zombies "burn"t
     */
    public void bust() {
        gamePlayer.bustThemZombies(this);
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            ThreadPool.execute(new SoundPlayer(path, 3000, false));
        } catch (InterruptedException ignore) { }
        bust();
        die();
    }

}
