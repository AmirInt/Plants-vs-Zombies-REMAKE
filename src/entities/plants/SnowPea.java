package entities.plants;

import javax.swing.*;
import java.awt.*;

public class SnowPea extends Plant {

    public SnowPea(int xLocation, int yLocation) {
        super(100, xLocation, yLocation, new ImageIcon("Game accessories\\images\\Gifs\\freezepeashooter.gif").getImage());
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
    public int getYLocation() {
        return super.getYLocation();
    }

    @Override
    public int getXLocation() {
        return super.getXLocation();
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
    public void getReadyForSabotage() {
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\freezenpeashooter.gif").getImage());
    }

    public void shoot() {

    }

    @Override
    public void die() {
        super.die();
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\pea_shooter_dying.gif").getImage());
        try {
            Thread.sleep(250);
        } catch (InterruptedException ignore) { }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            shoot();
        } catch (InterruptedException ignore) { }
    }
}
