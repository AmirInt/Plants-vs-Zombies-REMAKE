package labels;

import javax.swing.*;
import java.awt.*;

public abstract class Card extends JLabel implements Runnable {

    Image cardImage;

    public Card(Image cardImage) {
        this.cardImage = cardImage;
    }

    public void setCardImage(Image cardImage) {
        this.cardImage = cardImage;
    }

    public void use() { }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(cardImage, 0, 0, null);
    }
}
