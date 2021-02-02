package cards;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the card for sunflowers
 */
public class SunflowerCard extends Card {

    private final int rechargeTime;
    private static SunflowerCard sunflowerCard = null;

    /**
     * Instantiates this class
     * @param xLocation The fixed x position of this card
     * @param yLocation The fixed y position of this card
     */
    private SunflowerCard(int xLocation, int yLocation) {
        super(new ImageIcon("Game accessories\\images\\Cards\\card_sunflower.png").getImage(),
                xLocation, yLocation, 50);
        rechargeTime = 7500;
    }

    /**
     * This is a singleton model
     * @param xLocation The fixed x position of this card
     * @param yLocation The fixed y position of this card
     * @return The only object of this class
     */
    public static SunflowerCard getInstance(int xLocation, int yLocation) {
        if(sunflowerCard == null)
            sunflowerCard = new SunflowerCard(xLocation, yLocation);
        return sunflowerCard;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    @Override
    public void setCardImage(Image cardImage) {
        super.setCardImage(cardImage);
    }

    @Override
    public Image getCardImage() {
        return super.getCardImage();
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
    public int getXLocation() {
        return super.getXLocation();
    }

    @Override
    public int getYLocation() {
        return super.getYLocation();
    }

    @Override
    public int getRequiredEnergy() {
        return super.getRequiredEnergy();
    }

    @Override
    public boolean getEnabled() {
        return super.getEnabled();
    }

    @Override
    public void use() {
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\card_sunflower_used.png").getImage());
        setEnabled(false);
        try {
            Thread.sleep(rechargeTime);
        } catch (InterruptedException ignore) { }
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\card_sunflower.png").getImage());
        setEnabled(true);
    }

    @Override
    public void run() {
        use();
    }
}
