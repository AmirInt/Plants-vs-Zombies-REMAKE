package entities.plants;

import entities.bullets.Bullet;
import entities.bullets.Pea;
import graphics.ThreadPool;
import manager.GamePlayer;

import javax.swing.*;
import java.awt.*;

public class PeaShooter extends Plant {

    public PeaShooter(int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(70, xLocation, yLocation,
                new ImageIcon("Game accessories\\images\\Gifs\\peashooter.gif").getImage(), gamePlayer);
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
    public void getReadyForSabotage() {
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\pea_shooter.gif").getImage());
    }

    @Override
    public synchronized void injure(int lifeTakenAway) {
        super.injure(lifeTakenAway);
    }

    public void shoot() {
        Bullet newBullet = new Pea(xLocation, yLocation, gamePlayer);
        gamePlayer.addBullet(newBullet);
        ThreadPool.execute(newBullet);
    }

    @Override
    public void die() {
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\pea_shooter_dying.gif").getImage());
        try {
            Thread.sleep(250);
        } catch (InterruptedException ignore) { }
        super.die();
    }

    @Override
    public void run() {
        while (!gamePlayer.isGameFinished() && life > 0) {
            try {
                Thread.sleep(1500);
                shoot();
            } catch (InterruptedException ignore) {
            }
        }
    }
}
