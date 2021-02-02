package cards;

import enums.GameDifficulty;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the card for the chomper
 */
public class ChomperCard extends Card {

    private final int rechargeTime;
    private static ChomperCard chomperCard = null;


    /**
     * Instantiates this class
     * @param gameDifficulty The difficulty of the game, affecting the recharge time
     * @param xLocation The fixed x position of this card
     * @param yLocation The fixed y position of this card
     */
    private ChomperCard(GameDifficulty gameDifficulty, int xLocation, int yLocation) {
        super(new ImageIcon("Game accessories\\images\\Cards\\ChomperCard.png").getImage(),
                xLocation, yLocation, 425);
        if(gameDifficulty == GameDifficulty.HARD)
            rechargeTime = 30000;
        else rechargeTime = 250000;
    }

    /**
     * This is a singleton model
     * @param gameDifficulty The difficulty of the game
     * @param xLocation The fixed x position of this card
     * @param yLocation The fixed y position of this card
     * @return The only object of this class
     */
    public static ChomperCard getInstance(GameDifficulty gameDifficulty, int xLocation, int yLocation) {
        if(chomperCard == null)
            chomperCard = new ChomperCard(gameDifficulty, xLocation, yLocation);
        return chomperCard;
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
    public int getWidth() {
        return super.getWidth();
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }

    @Override
    public boolean getEnabled() {
        return super.getEnabled();
    }

    @Override
    public void use() {
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\ChomperCard-used.png").getImage());
        setEnabled(false);
        try {
            Thread.sleep(rechargeTime);
        } catch (InterruptedException ignore) { }
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\ChomperCard.png").getImage());
        setEnabled(true);
    }

    @Override
    public void run() {
        use();
    }
}
