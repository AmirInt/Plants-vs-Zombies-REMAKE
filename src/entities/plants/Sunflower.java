package entities.plants;

import enums.GameDifficulty;
import managers.GamePlayer;
import javax.swing.*;
import java.awt.*;

public class Sunflower extends Plant {

    private final int productionRate;
    private int productState;

    public Sunflower(int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(50, xLocation, yLocation, gamePlayer);
        productState = 0;
        if(gamePlayer.getGameDifficulty() == GameDifficulty.MEDIUM)
            productionRate = 20;
        else productionRate = 25;
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\sunflower.gif").getImage());
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
        gamePlayer.dropASun(xLocation, yLocation - height, yLocation);
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        while (gamePlayer.isNotGameFinished() && life > 0) {
            if(gamePlayer.isGamePaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                try {
                    Thread.sleep(1000);
                    ++productState;
                    productState %= productionRate;
                    if (productState == 0)
                        produce();
                } catch (InterruptedException ignore) {
                }
            }
        }
        if(life == 0) {
            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\sun_flower_dying.gif").getImage());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) { }
            die();
        }
    }
}
