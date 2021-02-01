package entities.plants;

import graphics.ThreadPool;
import managers.GamePlayer;
import sounds.SoundPlayer;

import javax.swing.*;
import java.awt.*;

public class Chomper extends Plant {

    int hungerStatus;
    private static final String path = "Game accessories\\sounds\\chomp.wav";

    public Chomper(int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(400, xLocation, yLocation, gamePlayer, 100, 100);
        hungerStatus = 0;
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\Chomper.gif").getImage());
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
            }
            else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) { }
                ++hungerStatus;
                hungerStatus %= 10;
                if(hungerStatus == 0) {
                    try {
                        if (gamePlayer.catchAZombie(this)) {
                            if(gamePlayer.isNotMuted())
                                ThreadPool.execute(new SoundPlayer(path, 500, false));
                            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\Chomper Chomping.gif").getImage());
                            Thread.sleep(3000);
                        }
                    } catch (InterruptedException ignore) { }
                    setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\Chomper.gif").getImage());
                }
            }
        }
        die();
    }
}
