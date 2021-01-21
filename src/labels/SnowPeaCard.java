package labels;

import enums.GameDifficulty;
import javax.swing.*;
import java.awt.*;

public class SnowPeaCard extends Card {

    private final int rechargeTime;
    private static SnowPeaCard snowPeaCard = null;

    private SnowPeaCard(GameDifficulty gameDifficulty) {
        super(new ImageIcon("Game accessories\\images\\Cards\\card_freezepeashooter.png").getImage());
        if(gameDifficulty == GameDifficulty.HARD)
            rechargeTime = 30000;
        else rechargeTime = 7500;
    }

    public static SnowPeaCard getInstance(GameDifficulty gameDifficulty) {
        if(snowPeaCard == null)
            snowPeaCard = new SnowPeaCard(gameDifficulty);
        return snowPeaCard;
    }

    @Override
    public void setCardImage(Image cardImage) {
        super.setCardImage(cardImage);
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
    public void run() { }
}
