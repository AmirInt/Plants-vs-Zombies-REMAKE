package entities.zombies;

import entities.plants.Plant;
import enums.GameDifficulty;
import manager.GamePlayer;
import javax.swing.*;
import java.awt.*;

public class ConeHeadZombie extends Zombie{

    public ConeHeadZombie(GamePlayer gamePlayer, GameDifficulty gameDifficulty, int xLocation, int yLocation) {
        super(gamePlayer, 560, xLocation, yLocation, new ImageIcon("Game accessories\\images\\Gifs\\coneheadzombie.gif").getImage(),
                0);
//        Setting the moving speed
        if(gameDifficulty == GameDifficulty.MEDIUM) {
            setMovingSpeed(25);
            setDestructionPower(10);
        } else {
            setMovingSpeed(20);
            setDestructionPower(15);
        }
    }

    @Override
    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
    }

    @Override
    public void setDestructionPower(int destructionPower) {
        super.setDestructionPower(destructionPower);
    }

    @Override
    public void setMovingSpeed(int movingSpeed) {
        super.setMovingSpeed(movingSpeed);
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
    public int getMovingSpeed() {
        return super.getMovingSpeed();
    }

    @Override
    public void injure(int destructionPower) {
        super.injure(destructionPower);
    }

    @Override
    public void destroy(Plant plant) {
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
    public void finishTheGame() {
        super.finishTheGame();
    }

    @Override
    public void burn() {
        super.burn();
    }

    @Override
    public void die() {
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\zombie_normal_dying.gif").getImage());
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignore) { }
        super.die();
    }

    @Override
    public void run() {
        super.run();
    }
}
