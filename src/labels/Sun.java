package labels;

import entities.Entity;

import javax.swing.*;

public class Sun extends Entity {

    int movingSpeed;
    int reflection;
    int yDestination;

    public Sun(int life, int xLocation, int yLocation, int yDestination) {
        super(life, xLocation, yLocation,
                new ImageIcon("Game accessories\\images\\Gifs\\sun.gif").getImage());
        this.yDestination = yDestination;
        movingSpeed = 40;
        reflection = yDestination - (yDestination - yLocation) / 5;
    }

    public void fall() {
        while(yLocation < yDestination) {
            try {
                Thread.sleep(30);
                yLocation += movingSpeed;
                xLocation += 5;
                movingSpeed += 20;
            } catch (InterruptedException ignore) { }
        }
        while (yLocation > reflection) {
            try {
                Thread.sleep(30);
                yLocation -= movingSpeed;
                xLocation += 5;
                movingSpeed -= 20;
            } catch (InterruptedException ignore) { }
        }
        while(yLocation < yDestination) {
            try {
                Thread.sleep(30);
                yLocation += movingSpeed;
                xLocation += 5;
                movingSpeed += 20;
            } catch (InterruptedException ignore) { }
        }
        for (int i = 0; i < 4; i++) {
            try {
                Thread.sleep(30);
                xLocation += 5;
            } catch (InterruptedException ignore) { }
        }
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void run() {
        fall();
    }
}
