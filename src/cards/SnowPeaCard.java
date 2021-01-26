package cards;

import enums.GameDifficulty;
import javax.swing.*;
import java.awt.*;

public class SnowPeaCard extends Card {

    private final int rechargeTime;
    private static SnowPeaCard snowPeaCard = null;

    private SnowPeaCard(GameDifficulty gameDifficulty, int xLocation, int yLocation) {
        super(new ImageIcon("Game accessories\\images\\Cards\\card_freezepeashooter.png").getImage(),
                xLocation, yLocation, 175);
        if(gameDifficulty == GameDifficulty.HARD)
            rechargeTime = 30000;
        else rechargeTime = 7500;
    }

    public static SnowPeaCard getInstance(GameDifficulty gameDifficulty, int xLocation, int yLocation) {
        if(snowPeaCard == null)
            snowPeaCard = new SnowPeaCard(gameDifficulty, xLocation, yLocation);
        return snowPeaCard;
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
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\card_freezepeashooter_used.png").getImage());
        setEnabled(false);
        try {
            Thread.sleep(rechargeTime);
        } catch (InterruptedException ignore) { }
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\card_freezepeashooter.png").getImage());
        setEnabled(true);
    }

    @Override
    public void run() {
        use();
    }
}
