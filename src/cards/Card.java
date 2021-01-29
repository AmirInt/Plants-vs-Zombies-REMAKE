package cards;

import java.awt.*;

public abstract class Card implements Runnable {

    transient private Image cardImage;
    private final int xLocation;
    private final int yLocation;
    private final int requiredEnergy;
    private boolean enabled;

    protected Card(Image cardImage, int xLocation, int yLocation, int requiredEnergy) {
        this.cardImage = cardImage;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.requiredEnergy = requiredEnergy;
        enabled = true;
    }

    public void setCardImage(Image cardImage) {
        this.cardImage = cardImage;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Image getCardImage() {
        return cardImage;
    }

    public int getXLocation() {
        return xLocation;
    }

    public int getYLocation() {
        return yLocation;
    }

    public int getWidth() {
        return 64;
    }

    public int getHeight() {
        return 90;
    }

    public int getRequiredEnergy() {
        return requiredEnergy;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void use() { }
}
