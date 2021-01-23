package entities.bullets;

import entities.Entity;
import entities.zombies.Zombie;

import javax.swing.*;
import java.awt.*;

public class Bullet extends Entity {

    int destructionPower;
    int movingSpeed;

    public Bullet(int life, int xLocation, int yLocation, Image appearance, int destructionPower) {
        super(life, xLocation, yLocation, 28, 28, appearance);
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
    public Image getAppearance() {
        return super.getAppearance();
    }

    public void hit(Zombie zombie) { }

    public void move() {
        xLocation += 10;
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        while (!gameFinished) {
            move();
        }
    }
}
