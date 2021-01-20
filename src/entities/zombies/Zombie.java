package entities.zombies;

import entities.Entity;

import javax.swing.*;
import java.awt.*;

public abstract class Zombie extends Entity{

    protected int destructionPower;
    protected int movingSpeed;

    public Zombie(int life, int xLocation, int yLocation, Image appearance,
                  int destructionPower) {
        super(life, xLocation, yLocation, appearance);
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

    public int getMovingSpeed() {
        return movingSpeed;
    }

    public void injure(int destructionPower) {
        if(life - destructionPower > 0) life -= destructionPower;
        else die();
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
