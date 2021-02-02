package menus;

import graphics.GameFrame;
import javax.swing.*;
import java.awt.*;

/**
 * FinishingMenu is used only after a game finishes, thus displays the
 * players score
 */
public class FinishingMenu extends Menu {
    /**
     * Instantiates this menu
     * @param score The score of the game
     * @param gameFrame The game frame displaying this menu
     */
    public FinishingMenu(int score, GameFrame gameFrame) {
        super(null, gameFrame,
                new ImageIcon("Game accessories\\images\\gameOver.jpg").getImage(), new BorderLayout());
        JLabel label1, label2, label3;
        JPanel panel1, panel2, panel3;
        panel1 = new JPanel(new GridBagLayout());
        panel2 = new JPanel(new GridBagLayout());
        panel3 = new JPanel(new GridBagLayout());
        panel1.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label1.setForeground(Color.WHITE);
        label2.setForeground(Color.WHITE);
        label3.setForeground(Color.WHITE);
        label1.setFont(selectedItemFont);
        label2.setFont(selectedItemFont);
        label3.setFont(selectedItemFont);
        if(score > 0) {
            label1.setText("    You won but...");
            label2.setText("Lucky chap...    ");
        } else if(score < 0){
            label1.setText("    You lost and...");
            label2.setText("Poor lame lad...    ");
        }
        label3.setText("     " + score);
        panel1.add(label1);
        panel2.add(label2);
        panel3.add(label3);
        add(panel1, BorderLayout.WEST);
        add(panel2, BorderLayout.EAST);
        add(panel3, BorderLayout.SOUTH);
        revalidate();
        repaint();
        gameFrame.revalidate();
    }
}
