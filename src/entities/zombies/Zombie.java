package entities.zombies;

import entities.Entity;
import entities.plants.Plant;
import manager.GamePlayer;
import javax.swing.*;
import java.awt.*;

public abstract class Zombie extends Entity{

    protected GamePlayer gamePlayer;
    protected int destructionPower;
    protected int movingSpeed;

    public Zombie(GamePlayer gamePlayer, int life, int xLocation, int yLocation, Image appearance,
                  int destructionPower) {
        super(life, xLocation, yLocation, 70, 100, appearance, gamePlayer);
        this.gamePlayer = gamePlayer;
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

    public int getMovingSpeed() {
        return movingSpeed;
    }

    public void injure(int destructionPower) {
        life -= destructionPower;
        if(life <= 0) die();
    }

    public void destroy(Plant plant) {
        try {
            Thread.sleep(1000);
            plant.injure(destructionPower);
        } catch (InterruptedException ignore) { }
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

    public void finishTheGame() {
        gamePlayer.lose();
    }

    public void burn() {
        try {
            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\burntZombie.gif").getImage());
            Thread.sleep(3000);
            die();
        } catch (InterruptedException ignore) { }
    }

    @Override
    public void die() {
        life = 0;
        super.die();
    }

    @Override
    public void run() {
        while (gamePlayer.isNotGameFinished() && life > 0) {
            if(xLocation == 0) {
                finishTheGame();
                return;
            } if(gamePlayer.destroyPlants(this))
                move();
        }
    }
}
