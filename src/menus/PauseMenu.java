package menus;

import graphics.GameFrame;
import graphics.GameState;
import managers.GameManager;
import managers.GamePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PauseMenu extends Menu {

    private final GameState gameState;
    private final JLabel resumeGame, saveGame, exitToMainMenu, errorLabel;
    private final MouseHandler mouseHandler;
    private final KeyHandler keyHandler;

    public PauseMenu(GameManager gameManager, GameFrame gameFrame, GameState gameState) {
        super(gameManager, gameFrame,
                new ImageIcon("Game accessories\\images\\pauseMenu.jpg").getImage(), new GridBagLayout());
        this.gameState = gameState;
        resumeGame = new JLabel("Go on to Game");
        resumeGame.setFont(unselectedItemFont);
        resumeGame.setForeground(unselectedItemColour);
        saveGame = new JLabel("Save Game");
        saveGame.setFont(unselectedItemFont);
        saveGame.setForeground(unselectedItemColour);
        exitToMainMenu = new JLabel("Exit to Main Menu");
        exitToMainMenu.setFont(unselectedItemFont);
        exitToMainMenu.setForeground(unselectedItemColour);
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.YELLOW);
        errorLabel.setFont(unselectedItemFont);
        GridBagConstraints constraints = new GridBagConstraints();

        add(resumeGame, constraints);
        constraints.gridy = 1;
        add(saveGame, constraints);
        constraints.gridy = 2;
        add(exitToMainMenu, constraints);
        constraints.gridy = 3;
        add(errorLabel, constraints);

        mouseHandler = new MouseHandler();
        keyHandler = new KeyHandler();
    }

    @Override
    public void update() {
        errorLabel.setText("");
        setFocusedItem(exitToMainMenu, saveGame);
        setFocusedItem(saveGame, resumeGame);
        setFocusedItem(resumeGame, null);
    }

    @Override
    public void getListenersReady() {
        gameFrame.addKeyListener(keyHandler);
        resumeGame.addMouseListener(mouseHandler);
        saveGame.addMouseListener(mouseHandler);
        exitToMainMenu.addMouseListener(mouseHandler);
    }

    public void removeListeners() {
        gameFrame.removeKeyListener(keyHandler);
        resumeGame.removeMouseListener(mouseHandler);
        saveGame.removeMouseListener(mouseHandler);
        exitToMainMenu.removeMouseListener(mouseHandler);
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

    private class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                if (saveGame.getForeground() == selectedItemColour)
                    setFocusedItem(saveGame, resumeGame);
                else if (exitToMainMenu.getForeground() == selectedItemColour)
                    setFocusedItem(exitToMainMenu, saveGame);
                else setFocusedItem(resumeGame, exitToMainMenu);
            }
            else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (resumeGame.getForeground() == selectedItemColour)
                    setFocusedItem(resumeGame, saveGame);
                else if (saveGame.getForeground() == selectedItemColour)
                    setFocusedItem(saveGame, exitToMainMenu);
                else setFocusedItem(exitToMainMenu, resumeGame);
            }
            else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (resumeGame.getForeground() == selectedItemColour) {
                    removeListeners();
                    gameState.unpauseGame();
                }
                else if (saveGame.getForeground() == selectedItemColour) {
                    if(gameState.saveGame())
                        errorLabel.setText("Successfully saved");
                    else errorLabel.setText("Something went wrong, try again in a few moments");
                    errorLabel.revalidate();
                    gameFrame.revalidate();
                }
                else if (exitToMainMenu.getForeground() == selectedItemColour) {
                    removeListeners();
                    gameState.killGame();
                }
            }
            revalidate();
            gameFrame.revalidate();
        }
    }
    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() == resumeGame) {
                removeListeners();
                gameState.unpauseGame();
            }
            else if(e.getSource() == saveGame) {
                if(gameState.saveGame())
                    errorLabel.setText("Successfully saved");
                else errorLabel.setText("Something went wrong, try again in a few moments");
                errorLabel.revalidate();
                gameFrame.revalidate();
            }
            else if(e.getSource() == exitToMainMenu) {
                removeListeners();
                gameState.killGame();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            saveGame.setFont(unselectedItemFont);
            resumeGame.setForeground(unselectedItemColour);
            exitToMainMenu.setBorder(null);
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