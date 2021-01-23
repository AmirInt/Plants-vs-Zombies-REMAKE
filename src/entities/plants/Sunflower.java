package entities.plants;

import enums.GameDifficulty;

import javax.swing.*;
import java.awt.*;

public class Sunflower extends Plant {

    private final int productionRate;

    public Sunflower(int life, int xLocation, int yLocation, GameDifficulty gameDifficulty) {
        super(life, xLocation, yLocation, new ImageIcon("Game accessories\\images\\Gifs\\sunflower.gif").getImage());
        if(gameDifficulty == GameDifficulty.MEDIUM)
            productionRate = 20000;
        else productionRate = 25000;
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
    public Image getAppearance() {
        return super.getAppearance();
    }

    @Override
    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
    }

    @Override
    public void getReadyForSabotage() {
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\sun_flower.gif").getImage());
    }

    private void produce() {

    }

    @Override
    public void die() {
        super.die();
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\sun_flower_dying.gif").getImage());
        try {
            Thread.sleep(250);
        } catch (InterruptedException ignore) { }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(productionRate);
            produce();
        } catch (InterruptedException ignore) { }
    }
}
