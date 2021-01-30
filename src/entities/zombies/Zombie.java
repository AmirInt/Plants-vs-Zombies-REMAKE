package entities.zombies;

import entities.Entity;
import entities.plants.Plant;
import managers.GamePlayer;
import javax.swing.*;
import java.awt.*;

public abstract class Zombie extends Entity{

    transient protected GamePlayer gamePlayer;
    protected int destructionPower;
    protected int movingSpeed;
    protected int affectedMovingSpeed;
    private boolean isBurnt;

    public Zombie(GamePlayer gamePlayer, int life, int xLocation, int yLocation, int destructionPower) {
        super(life, xLocation, yLocation, 70, 100, gamePlayer);
        this.gamePlayer = gamePlayer;
        this.destructionPower = destructionPower;
        isBurnt = false;
    }

    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
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

    public int getAffectedMovingSpeed() {
        return affectedMovingSpeed;
    }

    public void injure(int destructionPower) {
        life -= destructionPower;
        if(life < 200)
            downGrade();
        if(life <= 0) {
            life = 0;
        }
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
        gamePlayer.setGameFinished(true);
    }

    public void burn() {
        isBurnt = true;
    }

    @Override
    public void die() {
        life = 0;
        super.die();
    }

    @Override
    public void run() {
        while (gamePlayer.isNotGameFinished() && life > 0 && !isBurnt) {
            if(gamePlayer.isGamePaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                if (xLocation == 0) {
                    finishTheGame();
                    return;
                }
                Plant poorPlant = gamePlayer.whichEntityIsWithinReachOf(this);
                if (poorPlant != null)
                    destroy(poorPlant);
                else move();
            }
        }
        if(isBurnt)
            try {
                setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\burntZombie.gif").getImage());
                Thread.sleep(3000);
                die();
                return;
            } catch (InterruptedException ignore) { }
        if(life == 0)
            try {
                setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\zombie_normal_dying.gif").getImage());
                Thread.sleep(1000);
                die();
            } catch (InterruptedException ignore) { }
    }
}
