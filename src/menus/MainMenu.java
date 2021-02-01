package menus;

import enums.AvailablePlants;
import enums.AvailableZombies;
import enums.GameDifficulty;
import graphics.GameFrame;
import managers.GameManager;
import managers.GamePlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends Menu {

    private final JPanel mainMenu, rankingMenu, settingsMenu, loadGameMenu;
    private JLabel signOut, user;
    private JLabel newGame, loadGame, ranking, settings, exitGame;
    private final JLabel back, errorLabel;
    private JList<String> playersList;
    private JScrollPane scoreBoard;
    private JLabel Hard, Medium;
    private JTextField username;
    private JPasswordField passwordField, repeatPasswordField;
    private JCheckBox cabbage, chomper, galting, wintermelon, balloon, catapult, creepy, doorShield, football, yeti;

    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final ActionHandler actionHandler;

    public MainMenu(GameManager gameManager, GameFrame gameFrame) {
        super(gameManager, gameFrame,
                new ImageIcon("Game accessories\\images\\PvZStreet_1440x900.0.jpeg").getImage(),
                new BorderLayout());

        mainMenu = new JPanel(new GridBagLayout());
        mainMenu.setOpaque(false);
        rankingMenu = new JPanel(new GridBagLayout());
        settingsMenu = new JPanel(new GridBagLayout());
        loadGameMenu = new JPanel(new GridBagLayout());
        loadGameMenu.setOpaque(false);
        back = new JLabel("Back");
        back.setFont(unselectedItemFont);
        back.setForeground(unselectedItemColour);
        errorLabel = new JLabel();
        errorLabel.setPreferredSize(new Dimension(600, 80));
        errorLabel.setFont(new Font("", Font.PLAIN, 16));
        errorLabel.setForeground(Color.YELLOW);

        keyHandler = new KeyHandler(mainMenu);
        mouseHandler = new MouseHandler();
        actionHandler = new ActionHandler();

        setRankingMenuComponents();
        setSettingsMenuComponents();
        setSignOutPanel();
        setMainMenuComponents();
        setMainMenu();

        username.addActionListener(actionHandler);
        passwordField.addActionListener(actionHandler);
        repeatPasswordField.addActionListener(actionHandler);

    }

    @Override
    public void update() {
        remove(mainMenu);
        remove(settingsMenu);
        remove(rankingMenu);
        remove(loadGameMenu);
        add(mainMenu, BorderLayout.CENTER);
        setFocusedItem(signOut, exitGame);
        setFocusedItem(exitGame, settings);
        setFocusedItem(settings, ranking);
        setFocusedItem(ranking, loadGame);
        setFocusedItem(loadGame, newGame);
        setFocusedItem(newGame, back);
        setFocusedItem(back, Hard);
        setFocusedItem(Hard, Medium);
        Medium.setFont(unselectedItemFont);
        Medium.setForeground(unselectedItemColour);
        Medium.setBorder(null);
        user.setText(gameManager.getUser() + ": " + gameManager.getScore());
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

    private void setRankingMenuComponents() {
        rankingMenu.setOpaque(false);
        playersList = new JList<>();
        playersList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        playersList.setFixedCellHeight(50);
        playersList.setFixedCellWidth(500);
        playersList.setFont(new Font("", Font.BOLD, 16));
        playersList.setSelectionBackground(new Color(190, 180, 110));
        playersList.setSelectionForeground(selectedItemColour);
        playersList.setOpaque(false);
        scoreBoard = new JScrollPane(playersList);
        scoreBoard.getViewport().setOpaque(false);
        scoreBoard.setOpaque(false);
        scoreBoard.setPreferredSize(new Dimension(800, 300));
    }

    public void setRankingMenu() {
        constraints.gridy = 0;
        rankingMenu.add(back, constraints);
        constraints.gridy = 1;
        rankingMenu.add(scoreBoard, constraints);
        constraints.gridy = 2;
        rankingMenu.add(errorLabel, constraints);
        constraints.gridy = 0;
        playersList.clearSelection();
        playersList.removeMouseListener(mouseHandler);
        errorLabel.setText("Connecting to the server...");
        remove(mainMenu);
        add(rankingMenu, BorderLayout.CENTER);
        rankingMenu.repaint();
        gameFrame.revalidate();
        gameFrame.repaint();
        errorLabel.setText("");
        if(!gameManager.updateUser(gameManager.getUser(), "")) {
            errorLabel.setText("Something went wrong, try again later");
        }
        else {
            String[] usersList = gameManager.getPlayers();
            if(usersList == null) {
                errorLabel.setText("Something went wrong, try again later");
            }
            else {
                playersList.setListData(usersList);
            }
        }
        errorLabel.revalidate();
        scoreBoard.revalidate();
        rankingMenu.revalidate();
        rankingMenu.repaint();
        gameFrame.revalidate();
    }

    public void setSettingsMenuComponents() {
        JPanel settingsMenu1, settingsMenu2, settingsMenu3, inclusivePanel;
        Menu cabbageF, chomperF, galtingF, wintermelonF, balloonF, catapultF, creepyF, doorShieldF, footballF, yetiF;
        settingsMenu.setOpaque(false);
        settingsMenu1 = new JPanel(new GridBagLayout());
        settingsMenu1.setOpaque(false);
        settingsMenu3 = new JPanel(new GridBagLayout());
        settingsMenu3.setOpaque(false);
        Hard = new JLabel("Hard");
        Hard.setFont(unselectedItemFont);
        Hard.setForeground(unselectedItemColour);
        Medium = new JLabel("Medium");
        Medium.setForeground(unselectedItemColour);
        Medium.setFont(unselectedItemFont);
        username = new JTextField();
        username.setForeground(Color.WHITE);
        username.setFont(new Font("", Font.PLAIN, 16));
        username.setPreferredSize(new Dimension(200, 40));
        username.setOpaque(false);
        username.setBorder(selectedItemBorder);
        passwordField = new JPasswordField();
        passwordField.setForeground(Color.WHITE);
        passwordField.setPreferredSize(new Dimension(200, 40));
        passwordField.setOpaque(false);
        passwordField.setBorder(selectedItemBorder);
        repeatPasswordField = new JPasswordField();
        repeatPasswordField.setForeground(Color.WHITE);
        repeatPasswordField.setPreferredSize(new Dimension(200, 40));
        repeatPasswordField.setOpaque(false);
        repeatPasswordField.setBorder(selectedItemBorder);
        errorLabel.setText("");
        JLabel gameDifficulty = new JLabel("Game Difficulty");
        gameDifficulty.setFont(unselectedItemFont);
        JLabel label01 = new JLabel("New Username:");
        label01.setFont(new Font("", Font.PLAIN, 16));
        label01.setForeground(Color.WHITE);
        JLabel label02 = new JLabel("New Password:");
        label02.setFont(new Font("", Font.PLAIN, 16));
        label02.setForeground(Color.WHITE);
        JLabel label03 = new JLabel("Repeat Password:");
        label03.setFont(new Font("", Font.PLAIN, 16));
        label03.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        settingsMenu1.add(gameDifficulty, constraints);
        constraints.gridy = 1;
        settingsMenu1.add(Medium, constraints);
        constraints.gridx = 1;
        settingsMenu1.add(Hard, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        settingsMenu1.add(label01, constraints);
        constraints.gridx = 1;
        settingsMenu1.add(username, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        settingsMenu1.add(label02, constraints);
        constraints.gridx = 1;
        settingsMenu1.add(passwordField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        settingsMenu1.add(label03, constraints);
        constraints.gridx = 1;
        settingsMenu1.add(repeatPasswordField, constraints);
        constraints.gridy = 0;
        constraints.gridx = 0;

        cabbage = new JCheckBox();
        cabbage.setSelected(gameManager.getAvailablePlants().contains(AvailablePlants.CABBAGE));
        cabbageF = new Menu(null, null,
                new ImageIcon("Game Accessories\\Images\\cabbage.png").getImage(), null);
        cabbage.setOpaque(false);
        cabbageF.setPreferredSize(new Dimension(110, 110));
        chomper = new JCheckBox();
        chomper.setSelected(gameManager.getAvailablePlants().contains(AvailablePlants.CHOMPER));
        chomperF = new Menu(null, null,
                new ImageIcon("Game Accessories\\Images\\chomper.png").getImage(), null);
        chomper.setOpaque(false);
        chomperF.setPreferredSize(new Dimension(110, 110));
        galting = new JCheckBox();
        galting.setSelected(gameManager.getAvailablePlants().contains(AvailablePlants.GALTING_SHOOTER));
        galtingF = new Menu(null, null,
                new ImageIcon("Game Accessories\\Images\\galting.png").getImage(), null);
        galting.setOpaque(false);
        galtingF.setPreferredSize(new Dimension(110, 110));
        wintermelon = new JCheckBox();
        wintermelon.setSelected(gameManager.getAvailablePlants().contains(AvailablePlants.WINTERMELON));
        wintermelonF = new Menu(null, null,
                new ImageIcon("Game Accessories\\Images\\wintermelon.png").getImage(), null);
        wintermelon.setOpaque(false);
        wintermelonF.setPreferredSize(new Dimension(110, 110));
        balloon = new JCheckBox();
        balloon.setSelected(gameManager.getAvailableZombies().contains(AvailableZombies.BalloonZombie));
        balloonF = new Menu(null, null,
                new ImageIcon("Game Accessories\\Images\\balloon.png").getImage(), null);
        balloon.setOpaque(false);
        balloonF.setPreferredSize(new Dimension(110, 110));
        catapult = new JCheckBox();
        catapult.setSelected(gameManager.getAvailableZombies().contains(AvailableZombies.CatapultZombie));
        catapultF = new Menu(null, null,
                new ImageIcon("Game Accessories\\Images\\catapult.png").getImage(), null);
        catapult.setOpaque(false);
        catapultF.setPreferredSize(new Dimension(110, 110));
        creepy = new JCheckBox();
        creepy.setSelected(gameManager.getAvailableZombies().contains(AvailableZombies.CreepyZombie));
        creepyF = new Menu(null, null,
                new ImageIcon("Game Accessories\\Images\\creepy.png").getImage(), null);
        creepy.setOpaque(false);
        creepyF.setPreferredSize(new Dimension(110, 110));
        doorShield = new JCheckBox();
        doorShield.setSelected(gameManager.getAvailableZombies().contains(AvailableZombies.DoorShieldZombie));
        doorShieldF = new Menu(null, null,
                new ImageIcon("Game Accessories\\Images\\doorShield.png").getImage(), null);
        doorShield.setOpaque(false);
        doorShieldF.setPreferredSize(new Dimension(110, 110));
        football = new JCheckBox();
        football.setSelected(gameManager.getAvailableZombies().contains(AvailableZombies.FootballZombie));
        footballF = new Menu(null, null,
                new ImageIcon("Game Accessories\\Images\\football.png").getImage(), null);
        football.setOpaque(false);
        footballF.setPreferredSize(new Dimension(110, 110));
        yeti = new JCheckBox();
        yeti.setSelected(gameManager.getAvailableZombies().contains(AvailableZombies.YetiZombie));
        yetiF = new Menu(null, null,
                new ImageIcon("Game Accessories\\Images\\yeti.png").getImage(), null);
        yeti.setOpaque(false);
        yetiF.setPreferredSize(new Dimension(110, 110));
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 130, 10));
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 130, 10));
        panel1.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        panel4.setOpaque(false);

        panel1.add(balloonF);
        panel1.add(catapultF);
        panel1.add(creepyF);
        panel1.add(doorShieldF);
        panel1.add(footballF);
        panel1.add(yetiF);
        panel2.add(balloon);
        panel2.add(catapult);
        panel2.add(creepy);
        panel2.add(doorShield);
        panel2.add(football);
        panel2.add(yeti);
        panel3.add(cabbageF);
        panel3.add(chomperF);
        panel3.add(galtingF);
        panel3.add(wintermelonF);
        panel4.add(cabbage);
        panel4.add(chomper);
        panel4.add(galting);
        panel4.add(wintermelon);

        cabbage.addActionListener(actionHandler);
        chomper.addActionListener(actionHandler);
        galting.addActionListener(actionHandler);
        wintermelon.addActionListener(actionHandler);
        balloon.addActionListener(actionHandler);
        catapult.addActionListener(actionHandler);
        creepy.addActionListener(actionHandler);
        doorShield.addActionListener(actionHandler);
        football.addActionListener(actionHandler);
        yeti.addActionListener(actionHandler);

        inclusivePanel = new JPanel(new GridBagLayout());
        inclusivePanel.setOpaque(false);
        inclusivePanel.add(settingsMenu1, constraints);
        constraints.gridy = 1;
        inclusivePanel.add(panel1, constraints);
        constraints.gridy = 2;
        inclusivePanel.add(panel2, constraints);
        constraints.gridy = 3;
        inclusivePanel.add(panel3, constraints);
        constraints.gridy = 4;
        inclusivePanel.add(panel4, constraints);
        JScrollPane settingsMenuPane = new JScrollPane(inclusivePanel);
        settingsMenuPane.setPreferredSize(new Dimension(1200, 400));
        settingsMenuPane.setBorder(null);
        settingsMenuPane.getViewport().setOpaque(false);
        settingsMenuPane.setOpaque(false);
        constraints.gridy = 0;
        settingsMenu.add(settingsMenuPane, constraints);
    }

    public void setSettingsMenu() {
        errorLabel.setText("");
        username.setText("");
        passwordField.setText("");
        repeatPasswordField.setText("");
        if(gameManager.getGameDifficulty() == GameDifficulty.MEDIUM) {
            Medium.setText("* Medium");
        } else Hard.setText("* Hard");
        constraints.gridy = 1;
        settingsMenu.add(back, constraints);
        constraints.gridy = 2;
        settingsMenu.add(errorLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 0;
        remove(mainMenu);
        add(settingsMenu, BorderLayout.CENTER);
        revalidate();
        repaint();
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    public void setLoadGameMenu() {
        constraints.gridy = 0;
        loadGameMenu.add(back, constraints);
        constraints.gridy = 1;
        loadGameMenu.add(scoreBoard, constraints);
        constraints.gridy = 2;
        loadGameMenu.add(errorLabel, constraints);
        constraints.gridy = 0;
        playersList.clearSelection();
        playersList.setListData(new String[0]);
        playersList.addMouseListener(mouseHandler);
        errorLabel.setText("Connecting to the server");
        remove(mainMenu);
        add(loadGameMenu, BorderLayout.CENTER);
        loadGameMenu.revalidate();
        loadGameMenu.repaint();
        gameFrame.revalidate();

        String[] loadedGames = gameManager.getLoadedGames();
        errorLabel.setText("");
        if(loadedGames == null) {
            errorLabel.setText("Something went wrong, try again later");
        }
        else if(loadedGames.length == 0){
            errorLabel.setText("Nothing to show");
        }
        else {
            playersList.setListData(loadedGames);
        }
        errorLabel.revalidate();
        scoreBoard.revalidate();
        loadGameMenu.revalidate();
        loadGameMenu.repaint();
        gameFrame.revalidate();
    }

    private void setSignOutPanel() {
        JPanel signOutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        signOutPanel.setOpaque(false);
        signOut = new JLabel("Sign Out");
        signOut.setFont(unselectedItemFont);
        signOut.setForeground(unselectedItemColour);
        user = new JLabel(gameManager.getUser() + ": " + gameManager.getScore());
        user.setFont(unselectedItemFont);
        user.setForeground(Color.WHITE);
        signOutPanel.add(signOut);
        signOutPanel.add(user);
        add(signOutPanel, BorderLayout.SOUTH);
    }

    public void getListenersReady() {
        gameFrame.addKeyListener(keyHandler);
        newGame.addMouseListener(mouseHandler);
        loadGame.addMouseListener(mouseHandler);
        ranking.addMouseListener(mouseHandler);
        settings.addMouseListener(mouseHandler);
        exitGame.addMouseListener(mouseHandler);
        back.addMouseListener(mouseHandler);
        signOut.addMouseListener(mouseHandler);
        Hard.addMouseListener(mouseHandler);
        Medium.addMouseListener(mouseHandler);
    }

    public void removeListeners() {
        gameFrame.removeKeyListener(keyHandler);
        back.removeMouseListener(mouseHandler);
        newGame.removeMouseListener(mouseHandler);
        loadGame.removeMouseListener(mouseHandler);
        ranking.removeMouseListener(mouseHandler);
        settings.removeMouseListener(mouseHandler);
        exitGame.removeMouseListener(mouseHandler);
        signOut.removeMouseListener(mouseHandler);
        Hard.removeMouseListener(mouseHandler);
        Medium.removeMouseListener(mouseHandler);
    }

    public void updateUsername() {
        user.setText(gameManager.getUser() + ": " + gameManager.getScore());
        user.revalidate();
        user.repaint();
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

        JPanel panel;

        public KeyHandler(JPanel panel) {
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
                    removeListeners();
                    gameManager.play(new GamePlayer(gameManager.getGameDifficulty(), gameManager.getAvailableZombies(),
                            gameManager.getAvailablePlants()));
                }
                else if (loadGame.getForeground() == selectedItemColour) {
                    setLoadGameMenu();
                }
                else if (ranking.getForeground() == selectedItemColour) {
                    setRankingMenu();
                }
                else if (settings.getForeground() == selectedItemColour) {
                    setSettingsMenu();
                }
                else if(exitGame.getForeground() == selectedItemColour) {
                    System.exit(0);
                }
            }
            panel.revalidate();
            gameFrame.revalidate();
        }
    }
    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() == newGame) {
                removeListeners();
                gameManager.play(new GamePlayer(gameManager.getGameDifficulty(), gameManager.getAvailableZombies(),
                        gameManager.getAvailablePlants()));
            }
            else if(e.getSource() == loadGame) {
                setLoadGameMenu();
            }
            else if(e.getSource() == ranking) {
                playersList.removeMouseListener(mouseHandler);
                setRankingMenu();
            }
            else if(e.getSource() == settings) {
                setSettingsMenu();
            }
            else if(e.getSource() == exitGame) {
                System.exit(0);
            }
            else if(e.getSource() == signOut) {
                removeListeners();
                gameManager.signOut();
            }
            else if(e.getSource() == back) {
                username.removeActionListener(actionHandler);
                passwordField.removeActionListener(actionHandler);
                repeatPasswordField.removeActionListener(actionHandler);
                remove(settingsMenu);
                remove(rankingMenu);
                remove(loadGameMenu);
                add(mainMenu, BorderLayout.CENTER);
                gameFrame.revalidate();
                gameFrame.repaint();
            }
            else if(e.getSource() == Hard) {
                Medium.setText("Medium");
                Hard.setText("* Hard");
                settingsMenu.revalidate();
                settingsMenu.repaint();
                gameFrame.revalidate();
                gameFrame.repaint();
                gameManager.setGameDifficulty(GameDifficulty.HARD);
                gameManager.store();
            }
            else if(e.getSource() == Medium) {
                Medium.setText("* Medium");
                Hard.setText("Hard");
                settingsMenu.revalidate();
                settingsMenu.repaint();
                gameFrame.revalidate();
                gameFrame.repaint();
                gameManager.setGameDifficulty(GameDifficulty.MEDIUM);
                gameManager.store();
            }
            else if(e.getSource() == playersList) {
                GamePlayer gamePlayer = gameManager.getGame(playersList.getSelectedValue());
                if(gamePlayer == null) {
                    errorLabel.setText("Game not loaded properly");
                } else {
                    update();
                    revalidate();
                    repaint();
                    gameFrame.revalidate();
                    removeListeners();
                    gameManager.play(gamePlayer);
                    gameFrame.requestFocus();
                }
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
            try {
                JLabel hoveredLabel = (JLabel) e.getSource();
                hoveredLabel.setFont(selectedItemFont);
                hoveredLabel.setForeground(selectedItemColour);
                hoveredLabel.setBorder(selectedItemBorder);
            } catch (ClassCastException ignore) { }
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
    private class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == username
                    || e.getSource() == passwordField
                    || e.getSource() == repeatPasswordField) {
                String password = new String(passwordField.getPassword());
                String repPassword = new String(repeatPasswordField.getPassword());
                if(!password.equals(repPassword))
                    errorLabel.setText("Conflicting passwords");
                else {
                    String newUsername = username.getText().length() == 0 ? gameManager.getUser() : username.getText();
                    if(gameManager.updateUser(newUsername, password)) {
                        errorLabel.setText("Successfully saved");
                        gameManager.store();
                    } else {
                        errorLabel.setText("Username already exists");
                        errorLabel.revalidate();
                    }
                }
                errorLabel.revalidate();
            }
            else if(e.getSource() instanceof JCheckBox) {
                errorLabel.setText("");
                JCheckBox actedBox = (JCheckBox) e.getSource();
                if(!actedBox.isSelected()) {
                    if (cabbage == (actedBox)) {
                        if (!wintermelon.isSelected() && balloon.isSelected()) {
                            errorLabel.setText("Impossible");
                            actedBox.setSelected(true);
                        }
                        else gameManager.removeAvailablePlant(AvailablePlants.CABBAGE);
                    } else if (wintermelon == (actedBox)) {
                        if (!cabbage.isSelected() && balloon.isSelected()) {
                            errorLabel.setText("Impossible");
                            actedBox.setSelected(true);
                        }
                        else gameManager.removeAvailablePlant(AvailablePlants.WINTERMELON);
                    } else if (galting == (actedBox)) {
                        gameManager.removeAvailablePlant(AvailablePlants.GALTING_SHOOTER);
                    } else if (chomper == (actedBox)) {
                        gameManager.removeAvailablePlant(AvailablePlants.CHOMPER);
                    } else if (balloon == (actedBox)) {
                        gameManager.removeAvailableZombie(AvailableZombies.BalloonZombie);
                    } else if (catapult == (actedBox)) {
                        gameManager.removeAvailableZombie(AvailableZombies.CatapultZombie);
                    } else if (creepy == (actedBox)) {
                        gameManager.removeAvailableZombie(AvailableZombies.CreepyZombie);
                    } else if (doorShield == (actedBox)) {
                        gameManager.removeAvailableZombie(AvailableZombies.DoorShieldZombie);
                    } else if (football == (actedBox)) {
                        gameManager.removeAvailableZombie(AvailableZombies.FootballZombie);
                    } else if (yeti == (actedBox)) {
                        gameManager.removeAvailableZombie(AvailableZombies.YetiZombie);
                    }
                }
                else {
                    if (cabbage == (actedBox)) {
                        if (!wintermelon.isSelected() && balloon.isSelected())
                            errorLabel.setText("Impossible");
                        else gameManager.addToAvailablePlants(AvailablePlants.CABBAGE);
                    } else if (wintermelon == (actedBox)) {
                        if (!cabbage.isSelected() && balloon.isSelected())
                            errorLabel.setText("Impossible");
                        else gameManager.addToAvailablePlants(AvailablePlants.WINTERMELON);
                    } else if (galting == (actedBox)) {
                        gameManager.addToAvailablePlants(AvailablePlants.GALTING_SHOOTER);
                    } else if (chomper == (actedBox)) {
                        gameManager.addToAvailablePlants(AvailablePlants.CHOMPER);
                    } else if (balloon == (actedBox)) {
                        gameManager.addToAvailableZombies(AvailableZombies.BalloonZombie);
                    } else if (catapult == (actedBox)) {
                        gameManager.addToAvailableZombies(AvailableZombies.CatapultZombie);
                    } else if (creepy == (actedBox)) {
                        gameManager.addToAvailableZombies(AvailableZombies.CreepyZombie);
                    } else if (doorShield == (actedBox)) {
                        gameManager.addToAvailableZombies(AvailableZombies.DoorShieldZombie);
                    } else if (football == (actedBox)) {
                        gameManager.addToAvailableZombies(AvailableZombies.FootballZombie);
                    } else if (yeti == (actedBox)) {
                        gameManager.addToAvailableZombies(AvailableZombies.YetiZombie);
                    }
                }
                gameManager.store();
            }
        }
    }
}
