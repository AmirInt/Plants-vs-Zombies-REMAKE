package menus;

import graphics.GameFrame;

import javax.swing.*;
import java.awt.*;

public class FinishingMenu extends Menu {

    public FinishingMenu(int score, GameFrame gameFrame) {
        super(null, gameFrame,
                new ImageIcon("Game accessories\\images\\gameOver.jpg").getImage(), new BorderLayout());
        JLabel label1, label2, label3;
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label1.setForeground(Color.WHITE);
        label2.setForeground(Color.WHITE);
        label3.setForeground(Color.WHITE);
        label1.setFont(unselectedItemFont);
        label2.setFont(unselectedItemFont);
        label3.setFont(unselectedItemFont);
        if(score > 0) {
            label1.setText("  You won but...");
            label2.setText("  Lucky chap");
        } else if(score < 0){
            label1.setText("  You lost and...");
            label2.setText("  Poor lame lad");
        }
        label3.setText("     " + score);
//        label1.setMinimumSize(new Dimension(50, 200));
//        label2.setMinimumSize(new Dimension(50, 200));
//        label3.setMinimumSize(new Dimension(50, 200));
        add(label1, BorderLayout.WEST);
        add(label1, BorderLayout.EAST);
        add(label1, BorderLayout.SOUTH);
        revalidate();
        repaint();
        gameFrame.revalidate();
    }
}
