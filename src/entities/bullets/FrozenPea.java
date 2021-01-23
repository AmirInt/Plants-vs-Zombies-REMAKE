package entities.bullets;

import entities.zombies.Zombie;

import javax.swing.*;
import java.awt.*;

public class FrozenPea extends Bullet {

    public FrozenPea(int life, int xLocation, int yLocation) {
        super(life, xLocation, yLocation,
                new ImageIcon("Game accessories\\images\\freezepea.png").getImage(), 35);
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

    @Override
    public void hit(Zombie zombie) {
        zombie.injure(destructionPower);
        zombie.setMovingSpeed(zombie.getMovingSpeed() * 2);
    }
}
