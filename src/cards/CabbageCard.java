package cards;

import enums.GameDifficulty;

import javax.swing.*;
import java.awt.*;

public class CabbageCard extends Card {

    private final int rechargeTime;
    private static CabbageCard cabbageCard = null;

    private CabbageCard(GameDifficulty gameDifficulty, int xLocation, int yLocation) {
        super(new ImageIcon("Game accessories\\images\\Cards\\CabbageCard.png").getImage(),
                xLocation, yLocation, 275);
        if(gameDifficulty == GameDifficulty.HARD)
            rechargeTime = 20000;
        else rechargeTime = 14000;
    }

    public static CabbageCard getInstance(GameDifficulty gameDifficulty, int xLocation, int yLocation) {
        if(cabbageCard == null)
            cabbageCard = new CabbageCard(gameDifficulty, xLocation, yLocation);
        return cabbageCard;
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
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\CabbageCard-used.png").getImage());
        setEnabled(false);
        try {
            Thread.sleep(rechargeTime);
        } catch (InterruptedException ignore) { }
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\CabbageCard.png").getImage());
        setEnabled(true);
    }

    @Override
    public void run() {
        use();
    }
}
