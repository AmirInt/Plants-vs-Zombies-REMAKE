package entities.plants;

import javax.swing.*;
import java.awt.*;

public class CherryBomb extends Plant {

    public CherryBomb(int life, int xLocation, int yLocation) {
        super(life, xLocation, yLocation, new ImageIcon("Game accessories\\images\\Gifs\\newCherryBomb.gif").getImage());
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
    public Image getAppearance() {
        return super.getAppearance();
    }

    public void bust() {

    }

    @Override
    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
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
