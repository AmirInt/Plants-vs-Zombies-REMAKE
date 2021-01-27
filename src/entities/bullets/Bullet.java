package entities.bullets;

import entities.Entity;
import entities.zombies.Zombie;
import manager.GamePlayer;

import javax.swing.*;
import java.awt.*;

public class Bullet extends Entity {

    int destructionPower;
    int movingSpeed;

    public Bullet(int xLocation, int yLocation, Image appearance, int destructionPower, GamePlayer gamePlayer) {
        super(10, xLocation, yLocation, 28, 28, appearance, gamePlayer);
        movingSpeed = 10;
        this.destructionPower = destructionPower;
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

    public void hit(Zombie zombie) {
        zombie.injure(destructionPower);
        die();
    }

    public void move() {
        xLocation += 2;
        if(xLocation > 1400) {
            life = 0;
            die();
        }
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        while (!gamePlayer.isGameFinished() && life > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignore) { }
            for (Zombie zombie:
                 gamePlayer.getZombies()) {
                if(zombie.getYLocation() == yLocation && Math.abs(zombie.getXLocation() - xLocation) <= 20) {
                    hit(zombie);
                    break;
                }
            }
            move();
        }
    }
}
