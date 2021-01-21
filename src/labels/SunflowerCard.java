package labels;

import entities.plants.Sunflower;

import javax.swing.*;
import java.awt.*;

public class SunflowerCard extends Card {

    private final int rechargeTime;
    private static SunflowerCard sunflowerCard = null;

    private SunflowerCard() {
        super(new ImageIcon("Game accessories\\images\\Cards\\card_sunflower.png").getImage());
        rechargeTime = 7500;
    }

    public static SunflowerCard getInstance() {
        if(sunflowerCard == null)
            sunflowerCard = new SunflowerCard();
        return sunflowerCard;
    }

    @Override
    public void setCardImage(Image cardImage) {
        super.setCardImage(cardImage);
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
    public void run() { }
}
