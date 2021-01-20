package entities.bullets;

import entities.Entity;
import entities.zombies.Zombie;

import javax.swing.*;
import java.awt.*;

public class Bullet extends Entity {

    int destructionPower;
    int movingSpeed;

    public Bullet(int life, int xLocation, int yLocation, Image appearance, int destructionPower) {
        super(life, xLocation, yLocation, appearance);
        movingSpeed = 10;
        this.destructionPower = destructionPower;
    }

    public void hit(Zombie zombie) { }

    @Override
    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {

    }
}
