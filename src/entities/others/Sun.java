package entities.others;

import entities.Entity;
import manager.GamePlayer;

import javax.swing.*;

public class Sun extends Entity {

    int movingSpeed;
    int reflection;
    int yDestination;

    public Sun(int xLocation, int yLocation, int yDestination, GamePlayer gamePlayer) {
        super(10, xLocation, yLocation, 50, 48,
                new ImageIcon("Game accessories\\images\\Gifs\\sun.gif").getImage(), gamePlayer);
        this.yDestination = yDestination;
        movingSpeed = 0;
        reflection = yDestination - (yDestination - yLocation) / 10;
    }

    public void fall() {
        while(yLocation < yDestination) {
            try {
                Thread.sleep(30);
                yLocation += movingSpeed;
                xLocation += 5;
                movingSpeed += 1;
            } catch (InterruptedException ignore) { }
        }
        while (movingSpeed != 0) {
            try {
                Thread.sleep(30);
                yLocation -= movingSpeed;
                xLocation += 5;
                movingSpeed -= 15;
                movingSpeed = Math.max(movingSpeed, 0);
            } catch (InterruptedException ignore) { }
        }
        while(yLocation < yDestination) {
            try {
                Thread.sleep(30);
                yLocation += movingSpeed;
                xLocation += 5;
                movingSpeed += 1;
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
