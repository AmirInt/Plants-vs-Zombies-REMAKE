package entities.bullets;

import entities.Entity;
import entities.zombies.Zombie;
import manager.GamePlayer;
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
        life = 0;
        die();
    }

    public void move() {
        try {
            Thread.sleep(5);
            xLocation += 1;
            if (xLocation > 1000) {
                life = 0;
                die();
            }
        } catch (InterruptedException ignore) { }
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        while (gamePlayer.isNotGameFinished() && life > 0) {
            gamePlayer.bumpBullet(this);
            move();
        }
    }
}
