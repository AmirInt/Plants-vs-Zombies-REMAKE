package entities.plants;

import entities.Entity;
import java.awt.*;

public abstract class Plant extends Entity {

    public Plant(int life, int xLocation, int yLocation, Image appearance) {
        super(life, xLocation, yLocation, appearance);
    }

    public void getReadyForSabotage() { }

    @Override
    public void setAppearance(Image appearance) {
        super.setAppearance(appearance);
    }

    @Override
    public void die() {
        super.die();
    }
}
