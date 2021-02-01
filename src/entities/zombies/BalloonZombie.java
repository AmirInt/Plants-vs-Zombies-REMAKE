package entities.zombies;

import entities.plants.Plant;
import managers.GamePlayer;

import javax.swing.*;
import java.awt.*;

public class BalloonZombie extends Zombie {

    public BalloonZombie(GamePlayer gamePlayer, int xLocation, int yLocation) {
        super(gamePlayer, 800, xLocation, yLocation,10, 200, 200);
        setMovingSpeed(15);
        affectedMovingSpeed = movingSpeed;
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\Balloon-Zombie-unscreen.gif").getImage());
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
    public void downGrade() { }

    @Override
    public void burn() {
        life -= 100;
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
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        super.run();
    }
}
