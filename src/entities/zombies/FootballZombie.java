package entities.zombies;

import entities.plants.Plant;
import managers.GamePlayer;
import javax.swing.*;
import java.awt.*;

/**
 * Represents them football zombies
 */
public class FootballZombie extends Zombie {

    /**
     * Instantiates this class
     * @param gamePlayer The owning game player
     * @param xLocation The initial x location
     * @param yLocation The initial y location
     */
    public FootballZombie(GamePlayer gamePlayer, int xLocation, int yLocation) {
        super(gamePlayer, 800, xLocation, yLocation,20, 106, 126);
        setMovingSpeed(20);
        affectedMovingSpeed = movingSpeed;
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\zombie_football.gif").getImage());
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
