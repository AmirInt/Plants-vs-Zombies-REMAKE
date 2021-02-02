package entities.bullets;

import entities.zombies.Zombie;
import managers.GamePlayer;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Represents them radical peas (the ones that move orthogonally across the rows along
 * with their ordinary move)
 */
public class RadicalPea extends Bullet {

    private final int direction;

    /**
     * Instantiates this class
     * @param xLocation The initial x location
     * @param yLocation The initial y location
     * @param gamePlayer The owning game player
     */
    public RadicalPea(int xLocation, int yLocation, GamePlayer gamePlayer) {
        super(xLocation, yLocation, 30, gamePlayer);
        //    random generates a random number to determine the direction of this radical pea
        Random random = new Random();
        direction = 10 - random.nextInt(20);
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        setAppearance(new ImageIcon("Game accessories\\images\\pea.png").getImage());
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
    public void move() {
        super.move();
        yLocation += direction;
    }

    @Override
    public void hit(Zombie zombie) {
        super.hit(zombie);
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
