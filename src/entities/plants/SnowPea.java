package entities.plants;

import javax.swing.*;
import java.awt.*;

public class SnowPea extends Plant {

    public SnowPea(int life, int xLocation, int yLocation) {
        super(life, xLocation, yLocation, new ImageIcon("Game accessories\\images\\Gifs\\freezenpeashooter.gif").getImage());
    }

    @Override
    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
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
