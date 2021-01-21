package menus;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Menu extends JPanel {

    private final Image backgroundImage;
    private final Border selectedItemBorder = BorderFactory.createMatteBorder(0, 2, 10, 0, Color.YELLOW);
    private final Font notSelectedItemFont = new Font("notSelected", Font.BOLD, 40);
    private final Font selectedItemFont = new Font("selected", Font.BOLD, 60);
    private final ArrayList<JLabel> mainMenuLabels;
    private final ArrayList<JLabel> rankingMenulabels;
    private final ArrayList<JComponent> settingsMenuComponents;
    private final JFrame gameFrame;
    private JLabel focusedItem, newGame, loadGame, ranking, settings, quitGame;

    public Menu(JFrame gameFrame) {
        this.gameFrame = gameFrame;
        backgroundImage = new ImageIcon("Game accessories\\images\\PvZStreet_1440x900.0.jpeg").getImage();
        setComponents();
        mainMenuLabels = new ArrayList<>();
        mainMenuLabels.add(newGame);
        mainMenuLabels.add(loadGame);
        mainMenuLabels.add(ranking);
        mainMenuLabels.add(settings);
        mainMenuLabels.add(quitGame);
        addComponents(mainMenuLabels);
        rankingMenulabels = new ArrayList<>();
        settingsMenuComponents = new ArrayList<>();
        focusedItem = newGame;
        addKeyListener(new KeyHandler());
    }

    private void setComponents() {
        newGame = new JLabel("New Game");
        newGame.setLocation(200, 300);
        newGame.setFont(selectedItemFont);
        newGame.setBorder(selectedItemBorder);
        loadGame = new JLabel("Load Game");
        loadGame.setLocation(250, 350);
        loadGame.setFont(notSelectedItemFont);
        ranking = new JLabel("Ranking");
        ranking.setLocation(300, 400);
        ranking.setFont(notSelectedItemFont);
        settings = new JLabel("New Game");
        settings.setLocation(350, 450);
        settings.setFont(notSelectedItemFont);
        quitGame = new JLabel("New Game");
        quitGame.setLocation(400, 500);
        quitGame.setFont(notSelectedItemFont);
    }

    private void addComponents(ArrayList<JComponent> components) {
        for (JComponent component:
             components) {
            add(component);
        }
    }

    private void addComponents(ArrayList<JLabel> labels) {
        for (JLabel label:
                labels) {
            add(label);
        }
    }

    private void removeComponents(ArrayList<JComponent> components) {
        for (JComponent component:
             components) {
            remove(component);
        }
    }

    private void removeComponents(ArrayList<JLabel> labels) {
        for (JLabel label:
                labels) {
            remove(label);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }

    private class KeyHandler extends KeyAdapter {

        @Override
        public void keyTyped(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                if (loadGame.equals(focusedItem))
                    setFocusedItem(loadGame, newGame);
                else if (ranking.equals(focusedItem))
                    setFocusedItem(ranking, loadGame);
                else if (settings.equals(focusedItem))
                    setFocusedItem(settings, ranking);
                else if (quitGame.equals(focusedItem))
                    setFocusedItem(quitGame, settings);
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (newGame.equals(focusedItem))
                    setFocusedItem(newGame, loadGame);
                else if (loadGame.equals(focusedItem))
                    setFocusedItem(loadGame, ranking);
                else if (ranking.equals(focusedItem))
                    setFocusedItem(ranking, settings);
                else if (settings.equals(focusedItem))
                    setFocusedItem(settings, quitGame);
            } else if(e.getKeyCode() == KeyEvent.VK_ENTER) { }

        }

        private void setFocusedItem(JLabel unfocusedItem, JLabel focusGainingItem) {
            unfocusedItem.setBorder(null);
            unfocusedItem.setFont(notSelectedItemFont);
            focusedItem = focusGainingItem;
        }
    }

}
