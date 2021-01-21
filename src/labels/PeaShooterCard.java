package labels;

import javax.swing.*;
import java.awt.*;

public class PeaShooterCard extends Card {

    private final int rechargeTime;
    private static PeaShooterCard peaShooterCard = null;

    private PeaShooterCard() {
        super(new ImageIcon("Game accessories\\images\\Cards\\card_peashooter.png").getImage());
        rechargeTime = 7500;
    }

    public static PeaShooterCard getInstance() {
        if(peaShooterCard == null)
            peaShooterCard = new PeaShooterCard();
        return peaShooterCard;
    }

    @Override
    public void setCardImage(Image cardImage) {
        super.setCardImage(cardImage);
    }

    @Override
    public void use() {
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\card_peashooter_used.png").getImage());
        setEnabled(false);
        try {
            Thread.sleep(rechargeTime);
        } catch (InterruptedException ignore) { }
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\card_peashooter.png").getImage());
        setEnabled(true);
    }

    @Override
    public void run() { }
}
