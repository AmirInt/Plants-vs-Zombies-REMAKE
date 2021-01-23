package entities.zombies;

import entities.Entity;
import manager.GamePlayer;

import javax.swing.*;
import java.awt.*;

public class NormalZombie extends Zombie {

    public NormalZombie(GamePlayer gamePlayer, int xLocation, int yLocation) {
        super(gamePlayer, 200, xLocation, yLocation, new ImageIcon("Game accessories\\images\\Gifs\\normalzombie.gif").getImage(),
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
        while (!gameFinished) {
            if(xLocation == 0) {
                finishTheGame();
                setGameFinished(true);
            }
            move();
        }
    }
}
