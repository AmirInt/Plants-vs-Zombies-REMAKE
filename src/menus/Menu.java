package menus;

import manager.GameManager;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel {

    private final GameManager gameManager;
    private final JFrame gameFrame;
    private final Image backgroundImage;
    private final Border selectedItemBorder;
    private final Font unselectedItemFont;
    private final Font selectedItemFont;
    private final Color selectedItemColour;
    private final Color unselectedItemColour;
    private final JPanel mainMenu;
    private final JPanel rankingMenu;
    private final JPanel settingsMenu;
    private JLabel newGame, loadGame, ranking, settings, exitGame;
    private final GridBagConstraints constraints;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    public Menu(GameManager gameManager, JFrame gameFrame) {
        super(new GridBagLayout());
        this.gameManager = gameManager;
        this.gameFrame = gameFrame;
        backgroundImage = new ImageIcon("Game accessories\\images\\PvZStreet_1440x900.0.jpeg").getImage();
        constraints = new GridBagConstraints();

        selectedItemBorder = BorderFactory.createMatteBorder(0, 2, 10, 0, Color.YELLOW);
        unselectedItemFont = new Font("unselected", Font.BOLD, 40);
        selectedItemFont = new Font("selected", Font.BOLD, 45);
        selectedItemColour = new Color(180, 100, 0);
        unselectedItemColour = new Color(80, 10, 0);

        mainMenu = new JPanel(new GridBagLayout());
        mainMenu.setOpaque(false);
        rankingMenu = new JPanel();
        settingsMenu = new JPanel();

        keyHandler = new KeyHandler(mainMenu);
        mouseHandler = new MouseHandler(mainMenu);

        setMainMenuComponents();
        getMainMenuListenersReady();
        setMainMenu();

        gameFrame.addKeyListener(keyHandler);
    }

    private void setMainMenuComponents() {
        newGame = new JLabel("New Game");
        newGame.setFont(unselectedItemFont);
        newGame.setForeground(unselectedItemColour);
        newGame.addMouseListener(mouseHandler);
        loadGame = new JLabel("Load Game");
        loadGame.setFont(unselectedItemFont);
        loadGame.setForeground(unselectedItemColour);
        loadGame.addMouseListener(mouseHandler);
        ranking = new JLabel("Ranking");
        ranking.setFont(unselectedItemFont);
        ranking.setForeground(unselectedItemColour);
        ranking.addMouseListener(mouseHandler);
        settings = new JLabel("Settings");
        settings.setFont(unselectedItemFont);
        settings.setForeground(unselectedItemColour);
        settings.addMouseListener(mouseHandler);
        exitGame = new JLabel("Exit Game");
        exitGame.setFont(unselectedItemFont);
        exitGame.setForeground(unselectedItemColour);
        exitGame.addMouseListener(mouseHandler);
    }

    public void getMainMenuListenersReady() {
        newGame.addMouseListener(mouseHandler);
        loadGame.addMouseListener(mouseHandler);
        ranking.addMouseListener(mouseHandler);
        settings.addMouseListener(mouseHandler);
        exitGame.addMouseListener(mouseHandler);
    }

    private void setMainMenu() {
        constraints.gridy = 0;
        mainMenu.add(newGame, constraints);
        constraints.gridy = 1;
        mainMenu.add(loadGame, constraints);
        constraints.gridy = 2;
        mainMenu.add(ranking, constraints);
        constraints.gridy = 3;
        mainMenu.add(settings, constraints);
        constraints.gridy = 4;
        mainMenu.add(exitGame, constraints);
        constraints.gridy = 0;
        add(mainMenu);
    }

    private void setFocusedItem(JLabel unfocusedItem, JLabel focusGainingItem) {
        if(unfocusedItem != null) {
            unfocusedItem.setBorder(null);
            unfocusedItem.setFont(unselectedItemFont);
            unfocusedItem.setForeground(unselectedItemColour);
        }
        if(focusGainingItem != null) {
            focusGainingItem.setFont(selectedItemFont);
            focusGainingItem.setForeground(selectedItemColour);
            focusGainingItem.setBorder(selectedItemBorder);
        }
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }

    private class KeyHandler extends KeyAdapter {

        JPanel panel;

        public KeyHandler(JPanel panel) {
            this.panel = panel;
        }

        public void setPanel(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                if (loadGame.getForeground() == selectedItemColour)
                    setFocusedItem(loadGame, newGame);
                else if (ranking.getForeground() == selectedItemColour)
                    setFocusedItem(ranking, loadGame);
                else if (settings.getForeground() == selectedItemColour)
                    setFocusedItem(settings, ranking);
                else if (exitGame.getForeground() == selectedItemColour)
                    setFocusedItem(exitGame, settings);
                else setFocusedItem(newGame, exitGame);
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (newGame.getForeground() == selectedItemColour)
                    setFocusedItem(newGame, loadGame);
                else if (loadGame.getForeground() == selectedItemColour)
                    setFocusedItem(loadGame, ranking);
                else if (ranking.getForeground() == selectedItemColour)
                    setFocusedItem(ranking, settings);
                else if (settings.getForeground() == selectedItemColour)
                    setFocusedItem(settings, exitGame);
                else setFocusedItem(exitGame, newGame);
            } else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (newGame.getForeground() == selectedItemColour) {
                    gameFrame.removeKeyListener(keyHandler);
                    gameFrame.removeMouseListener(mouseHandler);
                    gameManager.play();
                }
                else if (loadGame.getForeground() == selectedItemColour) { }
                else if (ranking.getForeground() == selectedItemColour) { }
                else if (settings.getForeground() == selectedItemColour) { }
                else if(exitGame.getForeground() == selectedItemColour)
                    gameFrame.dispose();
            }
            panel.revalidate();
            gameFrame.revalidate();
        }
    }
    private class MouseHandler extends MouseAdapter {

        JPanel panel;

        public MouseHandler(JPanel panel) {
            this.panel = panel;
        }

        public void setPanel(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() == newGame) {
                gameFrame.removeKeyListener(keyHandler);
                gameFrame.removeMouseListener(mouseHandler);
                gameManager.play();
            }
            else if(e.getSource() == loadGame) { }
            else if(e.getSource() == ranking) { }
            else if(e.getSource() == settings) { }
            else if(e.getSource() == exitGame)
                gameFrame.dispose();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            newGame.setFont(unselectedItemFont);
            newGame.setForeground(unselectedItemColour);
            newGame.setBorder(null);
            loadGame.setFont(unselectedItemFont);
            loadGame.setForeground(unselectedItemColour);
            loadGame.setBorder(null);
            ranking.setFont(unselectedItemFont);
            ranking.setForeground(unselectedItemColour);
            ranking.setBorder(null);
            settings.setFont(unselectedItemFont);
            settings.setForeground(unselectedItemColour);
            settings.setBorder(null);
            exitGame.setFont(unselectedItemFont);
            exitGame.setForeground(unselectedItemColour);
            exitGame.setBorder(null);
            JLabel hoveredLabel = (JLabel) e.getSource();
            hoveredLabel.setFont(selectedItemFont);
            hoveredLabel.setForeground(selectedItemColour);
            hoveredLabel.setBorder(selectedItemBorder);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource() instanceof JLabel) {
                JLabel releasedLabel = (JLabel) e.getSource();
                releasedLabel.setBorder(null);
                releasedLabel.setForeground(unselectedItemColour);
                releasedLabel.setFont(unselectedItemFont);
            }
        }
    }
}
