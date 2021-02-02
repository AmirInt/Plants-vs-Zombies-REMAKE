package entities.zombies;

import entities.plants.Plant;
import enums.GameDifficulty;
import managers.GamePlayer;
import javax.swing.*;
import java.awt.*;

/**
 * Represents them cone-head zombies
 */
public class ConeHeadZombie extends Zombie{


    /**
     * Instantiates this class
     * @param gamePlayer The owning game player
     * @param gameDifficulty The difficulty of the game
     * @param xLocation The initial x location
     * @param yLocation The initial y location
     */
    public ConeHeadZombie(GamePlayer gamePlayer, GameDifficulty gameDifficulty, int xLocation, int yLocation) {
        super(gamePlayer, 560, xLocation, yLocation,0, 90, 100);
//        Setting the moving speed
        if(gameDifficulty == GameDifficulty.MEDIUM) {
            setMovingSpeed(25);
            setDestructionPower(10);
        } else {
            setMovingSpeed(20);
            setDestructionPower(15);
        }
        affectedMovingSpeed = movingSpeed * 2;
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        if(life > 200)
            setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\coneheadzombie.gif").getImage());
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
        super.die();
    }

    @Override
    public void run() {
        super.run();
    }
}
