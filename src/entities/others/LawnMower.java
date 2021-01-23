package entities.others;

import entities.Entity;

import javax.swing.*;
import java.awt.*;

public class LawnMower extends Entity {

    int movingSpeed;
    boolean isTriggered;

    public LawnMower(int life, int xLocation, int yLocation) {
        super(life, xLocation, yLocation, new ImageIcon("Game accessories\\images\\Gifs\\lawn_mower.gif").getImage());
        isTriggered = false;
        movingSpeed = 10;
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
    public Image getAppearance() {
        return super.getAppearance();
    }

    public void trigger() {
        isTriggered = true;
    }

    public void move() {
        xLocation += movingSpeed;
    }

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
        if(isTriggered) {
            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\lawnmowerActivated.gif").getImage());
            while(xLocation < 1000) {
                move();
                if(movingSpeed < 100)
                    movingSpeed += 10;
            }
            die();
        }
    }
}
