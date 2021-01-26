package entities.plants;

import enums.GameDifficulty;
import manager.GamePlayer;
import javax.swing.*;
import java.awt.*;

public class Sunflower extends Plant {

    private final int productionRate;

    public Sunflower(int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(50, xLocation, yLocation,
                new ImageIcon("Game accessories\\images\\Gifs\\sunflower.gif").getImage(), gamePlayer);
        if(gamePlayer.getGameDifficulty() == GameDifficulty.MEDIUM)
            productionRate = 20000;
        else productionRate = 25000;
    }

    @Override
    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
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
    public Image getAppearance() {
        return super.getAppearance();
    }

    @Override
    public void getReadyForSabotage() {
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\sun_flower.gif").getImage());
    }

    @Override
    public synchronized void injure(int lifeTakenAway) {
        super.injure(lifeTakenAway);
    }

    private void produce() {
        gamePlayer.dropASun(xLocation, yLocation - height, yLocation + height);
    }

    @Override
    public void die() {
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\sun_flower_dying.gif").getImage());
        try {
            Thread.sleep(250);
        } catch (InterruptedException ignore) { }
        life = 0;
        super.die();
    }

    @Override
    public void run() {
        while (!gamePlayer.isGameFinished() && life > 0) {
            try {
                Thread.sleep(productionRate);
                produce();
            } catch (InterruptedException ignore) {
            }
        }
    }
}
