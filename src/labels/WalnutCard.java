package labels;

import javax.swing.*;
import java.awt.*;

public class WalnutCard extends Card {

    private final int rechargeTime;
    private static WalnutCard walnutCard = null;

    private WalnutCard() {
        super(new ImageIcon("Game accessories\\images\\Cards\\card_wallnut.png").getImage());
        rechargeTime = 30000;
    }

    public static WalnutCard getInstance() {
        if(walnutCard == null)
            walnutCard = new WalnutCard();
        return walnutCard;
    }

    @Override
    public void setCardImage(Image cardImage) {
        super.setCardImage(cardImage);
    }

    @Override
    public void use() {
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\card_wallnut_used.png").getImage());
        setEnabled(false);
        try {
            Thread.sleep(rechargeTime);
        } catch (InterruptedException ignore) { }
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\card_wallnut.png").getImage());
        setEnabled(true);
    }

    @Override
    public void run() { }
}
