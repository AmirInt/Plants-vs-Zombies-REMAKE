package labels;

import enums.GameDifficulty;
import javax.swing.*;
import java.awt.*;

public class CherryBombCard extends Card {

    private final int rechargeTime;
    private static CherryBombCard cherryBombCard = null;

    private CherryBombCard(GameDifficulty gameDifficulty) {
        super(new ImageIcon("Game accessories\\images\\Cards\\card_cherrybomb.png").getImage());
        if(gameDifficulty == GameDifficulty.HARD)
            rechargeTime = 45000;
        else rechargeTime = 30000;
    }

    public static CherryBombCard getInstance(GameDifficulty gameDifficulty) {
        if(cherryBombCard == null)
            cherryBombCard = new CherryBombCard(gameDifficulty);
        return cherryBombCard;
    }

    @Override
    public void setCardImage(Image cardImage) {
        super.setCardImage(cardImage);
    }

    @Override
    public void use() {
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\card_cherrybomb_used.png").getImage());
        setEnabled(false);
        try {
            Thread.sleep(rechargeTime);
        } catch (InterruptedException ignore) { }
        setCardImage(new ImageIcon("Game accessories\\images\\Cards\\card_cherrybomb.png").getImage());
        setEnabled(true);
    }

    @Override
    public void run() { }
}
