package entities.zombies;

import entities.Entity;
import javax.swing.*;
import java.awt.*;

public class NormalZombie extends Zombie {

    public NormalZombie(int xLocation, int yLocation) {
        super(200, xLocation, yLocation, new ImageIcon("Game accessories\\images\\Gifs\\normalzombie.gif").getImage(),
                5);
//        Setting the moving speed
        setMovingSpeed(30);
    }

    @Override
    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
    }

    @Override
    public void setMovingSpeed(int movingSpeed) {
        super.setMovingSpeed(movingSpeed);
    }

    @Override
    public void setDestructionPower(int destructionPower) {
        super.setDestructionPower(destructionPower);
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

    @Override
    public int getMovingSpeed() {
        return super.getMovingSpeed();
    }

    @Override
    public void injure(int destructionPower) {
        super.injure(destructionPower);
    }

    @Override
    public void destroy(Entity plant) {
        super.destroy(plant);
    }

    @Override
    public void downGrade() {
        super.downGrade();
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            move();
        }
    }
}
