package entities.zombies;

import entities.Entity;
import enums.GameDifficulty;
import javax.swing.*;
import java.awt.*;

public class BucketHeadZombie extends Zombie{

    public BucketHeadZombie(GameDifficulty gameDifficulty, int xLocation, int yLocation) {
        super(560, xLocation, yLocation, 0);
//        Setting the appearance
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\bucketheadzombie.gif").getImage());
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
    public void run() {

    }
}
