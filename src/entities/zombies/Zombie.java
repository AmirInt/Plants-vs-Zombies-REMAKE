package entities.zombies;

import entities.Entity;
import entities.plants.Plant;
import graphics.ThreadPool;
import managers.GamePlayer;
import sounds.SoundPlayer;
import javax.swing.*;
import java.awt.*;

/**
 * Class Zombie represents zombies in the game
 */
public abstract class Zombie extends Entity {

    protected int destructionPower;
    protected int movingSpeed;
//    affectedMovingSpeed is the moving speed of the zombie when shot at by a
//    freezing bullet
    protected int affectedMovingSpeed;
    private boolean isBurnt;
//    The addresses of the images and the sounds associated to this zombie
    private static final String path = "Game Accessories\\sounds\\chomp.wav";
    private static final String normalZombieImage = "Game accessories\\images\\Gifs\\Normal-Zombie-unscreen.gif";

    /**
     * Instantiates this class
     * @param gamePlayer The owning game player
     * @param life The initial life of the zombie
     * @param xLocation The initial x Location
     * @param yLocation The initial y location
     * @param destructionPower The munching power
     * @param width The width of the image
     * @param height The height of the image
     */
    public Zombie(GamePlayer gamePlayer, int life, int xLocation, int yLocation, int destructionPower, int width, int height) {
        super(life, xLocation, yLocation, width, height, gamePlayer);
        this.gamePlayer = gamePlayer;
        this.destructionPower = destructionPower;
        isBurnt = false;
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
    }

    /**
     * @return The owning game player
     */
    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    /**
     * Sets the moving speed
     * @param movingSpeed The new moving speed
     */
    public void setMovingSpeed(int movingSpeed) {
        this.movingSpeed = movingSpeed;
    }

    /**
     * Sets the image of graphically representing the zombie on the frame
     * @param appearance The new appearance
     */
    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
    }

    /**
     * Sets this zombie's destruction power
     * @param destructionPower The destruction power
     */
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

    /**
     * @return This zombies current moving speed
     */
    public int getMovingSpeed() {
        return movingSpeed;
    }

    /**
     * @return This zombie's frozen moving speed
     */
    public int getAffectedMovingSpeed() {
        return affectedMovingSpeed;
    }

    /**
     * Reduces this zombies life
     * @param destructionPower The amount by which life is subtracted
     */
    public void injure(int destructionPower) {
        life -= destructionPower;
        if(life < 200)
            downGrade();
        if(life <= 0) {
            life = 0;
        }
    }

    /**
     * Destroys the plant it finds on its way
     * @param plant The poor plant
     */
    public void destroy(Plant plant) {
        try {
            if(gamePlayer.isNotMuted())
                ThreadPool.execute(new SoundPlayer(path, 500, false));
            Thread.sleep(1000);
            plant.injure(destructionPower);
        } catch (InterruptedException ignore) { }
    }

    /**
     * Only reverses the appearance to that of a normal zombie
     */
    public void downGrade() {
        setAppearance(new ImageIcon(normalZombieImage).getImage());
    }

    /**
     * Moves this zombie on the board
     */
    public void move() {
        --xLocation;
        try {
            Thread.sleep(movingSpeed);
        } catch (InterruptedException ignore) { }
    }

    /**
     * Finishes the game player this zombie belongs to
     */
    public void finishTheGame() {
        gamePlayer.setGameFinished(true);
    }

    /**
     * Called when this zombie is in exposure of a cherry bomb explosion
     */
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
                width = 50;
                height = 120;
                setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\burntZombie.gif").getImage());
                Thread.sleep(3000);
                die();
                return;
            } catch (InterruptedException ignore) { }
        if(life == 0)
            try {
                width = 78;
                height = 125;
                setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\zombie_normal_dying.gif").getImage());
                if(this instanceof FootballZombie)
                    setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\zombie_football_dying.gif").getImage());
                Thread.sleep(1000);
                die();
            } catch (InterruptedException ignore) { }
    }
}
