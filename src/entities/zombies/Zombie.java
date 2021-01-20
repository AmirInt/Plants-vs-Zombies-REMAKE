package entities.zombies;

import entities.Entity;

import javax.swing.*;
import java.awt.*;

public abstract class Zombie extends Entity{

    protected int destructionPower;
    protected int movingSpeed;

    public Zombie(int life, int xLocation, int yLocation,
                  int destructionPower) {
        super(life, xLocation, yLocation);
        this.destructionPower = destructionPower;
    }

    public void setMovingSpeed(int movingSpeed) {
        this.movingSpeed = movingSpeed;
    }

    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
    }

    public void setDestructionPower(int destructionPower) {
        this.destructionPower = destructionPower;
    }

    public void destroy(Entity plant) {
    }
    public void downGrade() {
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\normalzombie.gif").getImage());
    }

    public void move() {
        --xLocation;
        try {
            Thread.sleep(movingSpeed);
        } catch (InterruptedException ignore) { }
    }

    @Override
    public void die() {
        super.die();
    }
}
