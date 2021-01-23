package entities.others;

import entities.Entity;

import javax.swing.*;

public class Sun extends Entity {

    int movingSpeed;
    int reflection;
    int yDestination;

    public Sun(int life, int xLocation, int yLocation, int yDestination) {
        super(life, xLocation, yLocation, 50, 48,
                new ImageIcon("Game accessories\\images\\Gifs\\sun.gif").getImage());
        this.yDestination = yDestination;
        movingSpeed = 0;
        reflection = yDestination - (yDestination - yLocation) / 5;
    }

    public void fall() {
        while(yLocation < yDestination) {
            try {
                Thread.sleep(30);
                yLocation += movingSpeed;
                xLocation += 5;
                movingSpeed += 2;
            } catch (InterruptedException ignore) { }
        }
        while (yLocation > reflection) {
            try {
                Thread.sleep(30);
                yLocation -= movingSpeed;
                xLocation += 5;
                movingSpeed -= 10;
            } catch (InterruptedException ignore) { }
        }
        while(yLocation < yDestination) {
            try {
                Thread.sleep(30);
                yLocation += movingSpeed;
                xLocation += 5;
                movingSpeed += 2;
            } catch (InterruptedException ignore) { }
        }
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(30);
                xLocation += 10 - i;
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
