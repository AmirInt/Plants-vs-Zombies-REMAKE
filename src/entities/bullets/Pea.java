package entities.bullets;

import entities.zombies.Zombie;

import javax.swing.*;

public class Pea extends Bullet {

    public Pea(int life, int xLocation, int yLocation) {
        super(life, xLocation, yLocation,
                new ImageIcon("Game accessories\\images\\pea.png").getImage(), 30);
    }

    @Override
    public void hit(Zombie zombie) {
        zombie.injure(destructionPower);
    }
}
