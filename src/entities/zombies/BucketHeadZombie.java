package entities.zombies;

import entities.plants.Plant;
import enums.GameDifficulty;
import managers.GamePlayer;
import javax.swing.*;
import java.awt.*;

/**
 * Represents them bucket-head zombies
 */
public class BucketHeadZombie extends Zombie{

    /**
     * Instantiates this class
     * @param gamePlayer The owning game player
     * @param gameDifficulty The difficulty of the game as in Hard or Medium
     * @param xLocation The initial x location
     * @param yLocation The initial y location
     */
    public BucketHeadZombie(GamePlayer gamePlayer, GameDifficulty gameDifficulty, int xLocation, int yLocation) {
        super(gamePlayer, 1300, xLocation, yLocation, 0, 120, 120);
//        Setting the moving speed
        if(gameDifficulty == GameDifficulty.MEDIUM) {
            setMovingSpeed(25);
            setDestructionPower(20);
        } else {
            setMovingSpeed(20);
            setDestructionPower(25);
        }
        affectedMovingSpeed = movingSpeed * 2;
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        if(life > 200)
            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\Bucket-head-Zombie-unscreen.gif").getImage());
        else downGrade();
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
        super.die();
    }

    @Override
    public void run() {
        super.run();
    }
}
