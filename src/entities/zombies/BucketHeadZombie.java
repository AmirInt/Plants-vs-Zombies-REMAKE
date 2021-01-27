package entities.zombies;

import entities.Entity;
import enums.GameDifficulty;
import manager.GamePlayer;

import javax.swing.*;
import java.awt.*;

public class BucketHeadZombie extends Zombie{

    public BucketHeadZombie(GamePlayer gamePlayer, GameDifficulty gameDifficulty, int xLocation, int yLocation) {
        super(gamePlayer, 560, xLocation, yLocation, new ImageIcon("Game accessories\\images\\Gifs\\bucketheadzombie.gif").getImage(),
                0);
//        Setting the moving speed
        if(gameDifficulty == GameDifficulty.MEDIUM) {
            setMovingSpeed(25);
            setDestructionPower(20);
        } else {
            setMovingSpeed(20);
            setDestructionPower(25);
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
    public int getMovingSpeed() {
        return super.getMovingSpeed();
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
    public void finishTheGame() {
        super.finishTheGame();
    }

    @Override
    public void run() {
        while (!gamePlayer.isGameFinished()) {
            if(xLocation == 0) {
                finishTheGame();
            }
            move();
        }
    }
}
