package menus;

import manager.GameManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends Menu {

    private final JPanel mainMenu;
    private final JPanel rankingMenu;
    private final JPanel settingsMenu;
    private JLabel signOut, user;
    private JLabel newGame, loadGame, ranking, settings, exitGame;

    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    public MainMenu(GameManager gameManager, JFrame gameFrame) {
        super(gameManager, gameFrame,
                new ImageIcon("Game accessories\\images\\PvZStreet_1440x900.0.jpeg").getImage(),
                new BorderLayout());

        mainMenu = new JPanel(new GridBagLayout());
        mainMenu.setOpaque(false);
        rankingMenu = new JPanel();
        settingsMenu = new JPanel();

        keyHandler = new KeyHandler(mainMenu);
        mouseHandler = new MouseHandler(mainMenu);

        setMainMenuComponents();
        setMainMenu();
        setSignOutPanel();
    }

    private void setMainMenuComponents() {
        newGame = new JLabel("New Game");
        newGame.setFont(unselectedItemFont);
        newGame.setForeground(unselectedItemColour);
        loadGame = new JLabel("Load Game");
        loadGame.setFont(unselectedItemFont);
        loadGame.setForeground(unselectedItemColour);
        ranking = new JLabel("Ranking");
        ranking.setFont(unselectedItemFont);
        ranking.setForeground(unselectedItemColour);
        settings = new JLabel("Settings");
        settings.setFont(unselectedItemFont);
        settings.setForeground(unselectedItemColour);
        exitGame = new JLabel("Exit Game");
        exitGame.setFont(unselectedItemFont);
        exitGame.setForeground(unselectedItemColour);
    }

    public void getListenersReady() {
        gameFrame.addKeyListener(keyHandler);
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
        add(mainMenu, BorderLayout.CENTER);
    }

    private void setSignOutPanel() {
        JPanel signOutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        signOutPanel.setOpaque(false);
        signOut = new JLabel("Sign Out");
        signOut.setFont(unselectedItemFont);
        signOut.setForeground(unselectedItemColour);
        signOut.addMouseListener(mouseHandler);
        user = new JLabel(gameManager.getUser() + ": " + gameManager.getScore());
        user.setFont(unselectedItemFont);
        user.setForeground(Color.WHITE);
        signOutPanel.add(signOut);
        signOutPanel.add(user);
        add(signOutPanel, BorderLayout.SOUTH);
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
            }
            else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (newGame.getForeground() == selectedItemColour)
                    setFocusedItem(newGame, loadGame);
                else if (loadGame.getForeground() == selectedItemColour)
                    setFocusedItem(loadGame, ranking);
                else if (ranking.getForeground() == selectedItemColour)
                    setFocusedItem(ranking, settings);
                else if (settings.getForeground() == selectedItemColour)
                    setFocusedItem(settings, exitGame);
                else setFocusedItem(exitGame, newGame);
            }
            else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (newGame.getForeground() == selectedItemColour) {
                    gameFrame.removeKeyListener(keyHandler);
                    newGame.removeMouseListener(mouseHandler);
                    loadGame.removeMouseListener(mouseHandler);
                    ranking.removeMouseListener(mouseHandler);
                    settings.removeMouseListener(mouseHandler);
                    exitGame.removeMouseListener(mouseHandler);
                    gameManager.play();
                }
                else if (loadGame.getForeground() == selectedItemColour) { }
                else if (ranking.getForeground() == selectedItemColour) { }
                else if (settings.getForeground() == selectedItemColour) { }
                else if(exitGame.getForeground() == selectedItemColour)
                    System.exit(0);
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
                newGame.removeMouseListener(mouseHandler);
                loadGame.removeMouseListener(mouseHandler);
                ranking.removeMouseListener(mouseHandler);
                settings.removeMouseListener(mouseHandler);
                exitGame.removeMouseListener(mouseHandler);
                gameManager.play();
            }
            else if(e.getSource() == loadGame) { }
            else if(e.getSource() == ranking) { }
            else if(e.getSource() == settings) { }
            else if(e.getSource() == exitGame)
                System.exit(0);
            else if(e.getSource() == signOut) {
                gameManager.signOut();
            }
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
