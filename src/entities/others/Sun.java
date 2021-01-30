package entities.others;

import entities.Entity;
import managers.GamePlayer;

import javax.print.attribute.standard.Finishings;
import javax.swing.*;

public class Sun extends Entity {

    int movingSpeed;
    int reflection;
    int yDestination;

    public Sun(int xLocation, int yLocation, int yDestination, GamePlayer gamePlayer) {
        super(10, xLocation, yLocation, 50, 48, gamePlayer);
        this.yDestination = yDestination;
        movingSpeed = 0;
        reflection = yDestination - (yDestination - yLocation) / 10;
    }

    @Override
    public void initialise(GamePlayer gamePlayer) {
        super.initialise(gamePlayer);
        setAppearance(new ImageIcon("Game accessories\\images\\Gifs\\sun.gif").getImage());
    }

    public void fall() {
        while(yLocation < yDestination) {
            if(gamePlayer.isGamePaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                try {
                    Thread.sleep(30);
                    yLocation += movingSpeed;
                    xLocation += 5;
                    movingSpeed += 1;
                } catch (InterruptedException ignore) {
                }
            }
        }
        while (movingSpeed != 0) {
            if(gamePlayer.isGamePaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                try {
                    Thread.sleep(30);
                    yLocation -= movingSpeed;
                    xLocation += 5;
                    movingSpeed -= 15;
                    movingSpeed = Math.max(movingSpeed, 0);
                } catch (InterruptedException ignore) {
                }
            }
        }
        while(yLocation < yDestination) {
            if(gamePlayer.isGamePaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                try {
                    Thread.sleep(30);
                    yLocation += movingSpeed;
                    xLocation += 5;
                    movingSpeed += 1;
                } catch (InterruptedException ignore) {
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            if(gamePlayer.isGamePaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                try {
                    Thread.sleep(30);
                    xLocation += 10 - i;
                } catch (InterruptedException ignore) {
                }
            }
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
