package cards;

import java.awt.*;

/**
 * Class Card is used to display cards of the different plants during the play-
 * game.
 */
public abstract class Card implements Runnable {

    transient private Image cardImage;
    private final int xLocation;
    private final int yLocation;
//    The required energy to enter the corresponding plant onto the yard
    private final int requiredEnergy;
    private boolean enabled;

    /**
     * Instantiates this class
     * @param cardImage The image of the card
     * @param xLocation The x position on the frame
     * @param yLocation The f position on the frame
     * @param requiredEnergy The required energy for the corresponding plant
     */
    protected Card(Image cardImage, int xLocation, int yLocation, int requiredEnergy) {
        this.cardImage = cardImage;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.requiredEnergy = requiredEnergy;
        enabled = true;
    }

    /**
     * Sets the image for this card
     * @param cardImage The image
     */
    public void setCardImage(Image cardImage) {
        this.cardImage = cardImage;
    }

    /**
     * Sets the accessibility of this card
     * @param enabled The accessibility status
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return This card image
     */
    public Image getCardImage() {
        return cardImage;
    }

    /**
     * @return This card's fixed x position
     */
    public int getXLocation() {
        return xLocation;
    }

    /**
     * @return This card's fixed y position
     */
    public int getYLocation() {
        return yLocation;
    }

    /**
     * @return This card's image width
     */
    public int getWidth() {
        return 64;
    }

    /**
     * @return This card's image height
     */
    public int getHeight() {
        return 90;
    }

    /**
     * @return This card's corresponding plant's required energy
     */
    public int getRequiredEnergy() {
        return requiredEnergy;
    }

    /**
     * @return The accessibility status of this card
     */
    public synchronized boolean getEnabled() {
        return enabled;
    }

    /**
     * Uses this card, called during the game to make this card
     * temporarily unavailable
     */
    public void use() { }
}
