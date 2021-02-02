package menus;

import graphics.GameFrame;
import managers.GameManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

/**
 * LaunchingMenu is used at the beginning of the game, for sign-in
 * or sign-up
 */
public class LaunchingMenu extends Menu {

    private final Font font;
    private JPanel signUpPanel;
    private JPanel signInPanel;
    private JTextField signUpUsername;
    private JTextField signInUsername;
    private JPasswordField signUpPassword;
    private JPasswordField signUpRepeatPassword;
    private JPasswordField signInPassword;
    private JLabel signIn;
    private JLabel signUp;
    private final JLabel errorLabel;
    private final JLabel label01;
    private final JLabel label02;
    private final JLabel label03;
    private final JLabel label04;
    private final JLabel label05;
    private final JLabel exit;
    private final MouseHandler mouseHandler;
    private final ActionHandler actionHandler;

    /**
     * Instantiates this class
     * @param gameManager The supervising game manager
     * @param gameFrame The game frame displaying this menu
     */
    public LaunchingMenu(GameManager gameManager, GameFrame gameFrame) {
        super(gameManager, gameFrame,
                new ImageIcon("Game accessories\\images\\81l+GSScs7L._AC_SL1500_.jpg").getImage(),
                new BorderLayout());

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitPanel.setOpaque(false);
        mouseHandler = new MouseHandler(signUpPanel);
        actionHandler = new ActionHandler();
        font = new Font("", Font.BOLD, 16);

        label01 = new JLabel("Username:");
        label01.setForeground(Color.WHITE);
        label01.setFont(font);
        label02 = new JLabel("Password:");
        label02.setForeground(Color.WHITE);
        label02.setFont(font);
        label03 = new JLabel("Repeat Password:");
        label03.setFont(font);
        label03.setForeground(Color.WHITE);
        label04 = new JLabel("Username:");
        label04.setForeground(Color.WHITE);
        label04.setFont(font);
        label05 = new JLabel("Password:");
        label05.setForeground(Color.WHITE);
        label05.setFont(font);
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.YELLOW);
        exit = new JLabel("Exit");
        exit.setFont(unselectedItemFont);
        exit.addMouseListener(mouseHandler);
        exitPanel.add(exit);
        add(exitPanel, BorderLayout.SOUTH);
        constraints.gridy = 0;

        setUpSignUpPanel();
        setUpSignInPanel();
        putSignUpOn();
    }

    /**
     * Instantiates the sign-up panel
     */
    private void setUpSignUpPanel() {
        signUpPanel = new JPanel(new GridBagLayout());
        signUpPanel.setOpaque(false);
        signUpUsername = new JTextField();
        signUpUsername.setOpaque(false);
        signUpUsername.setBorder(selectedItemBorder);
        signUpUsername.setFont(font);
        signUpUsername.setForeground(Color.WHITE);
        signUpUsername.setPreferredSize(new Dimension(100, 50));
        signUpUsername.addActionListener(actionHandler);
        signUpPassword = new JPasswordField();
        signUpPassword.setFont(font);
        signUpPassword.setForeground(Color.WHITE);
        signUpPassword.setOpaque(false);
        signUpPassword.setBorder(selectedItemBorder);
        signUpPassword.setPreferredSize(new Dimension(100, 50));
        signUpPassword.addActionListener(actionHandler);
        signUpRepeatPassword = new JPasswordField();
        signUpRepeatPassword.setOpaque(false);
        signUpRepeatPassword.setBorder(selectedItemBorder);
        signUpRepeatPassword.setFont(font);
        signUpRepeatPassword.setForeground(Color.WHITE);
        signUpRepeatPassword.setPreferredSize(new Dimension(100, 50));
        signUpRepeatPassword.addActionListener(actionHandler);
        signIn = new JLabel("Sign In");
        signIn.setOpaque(false);
        signIn.setFont(unselectedItemFont);
        signIn.setForeground(unselectedItemColour);
        signIn.addMouseListener(mouseHandler);
        signUpPanel.add(label01, constraints);
        constraints.gridx = 1;
        signUpPanel.add(signUpUsername, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        signUpPanel.add(label02, constraints);
        constraints.gridx = 1;
        signUpPanel.add(signUpPassword, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        signUpPanel.add(label03, constraints);
        constraints.gridx = 1;
        signUpPanel.add(signUpRepeatPassword, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        signUpPanel.add(signIn, constraints);
        constraints.gridx = 0;
        constraints.gridy = 0;
    }

    /**
     * Instantiates the sign-in panel
     */
    private void setUpSignInPanel() {
        signInPanel = new JPanel(new GridBagLayout());
        signInPanel.setOpaque(false);
        signInUsername = new JTextField();
        signInUsername.setOpaque(false);
        signInUsername.setBorder(selectedItemBorder);
        signInUsername.setFont(font);
        signInUsername.setForeground(Color.WHITE);
        signInUsername.setPreferredSize(new Dimension(100, 50));
        signInUsername.addActionListener(actionHandler);
        signInPassword = new JPasswordField();
        signInPassword.setOpaque(false);
        signInPassword.setBorder(selectedItemBorder);
        signInPassword.setFont(font);
        signInPassword.setForeground(Color.WHITE);
        signInPassword.setPreferredSize(new Dimension(100, 50));
        signInPassword.addActionListener(actionHandler);
        signUp = new JLabel("Sign Up");
        signUp.setOpaque(false);
        signUp.setFont(unselectedItemFont);
        signUp.setForeground(unselectedItemColour);
        signUp.addMouseListener(mouseHandler);
        signInPanel.add(label04, constraints);
        constraints.gridx = 1;
        signInPanel.add(signInUsername, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        signInPanel.add(label05, constraints);
        constraints.gridx = 1;
        signInPanel.add(signInPassword, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        signInPanel.add(signUp, constraints);
        constraints.gridx = 0;
        constraints.gridy = 0;
    }

    /**
     * Applies the sign-up panel as the first page
     */
    public void putSignUpOn() {
        mouseHandler.setPanel(signUpPanel);
        errorLabel.setText("");
        signUpUsername.setText("");
        signUpPassword.setText("");
        signUpRepeatPassword.setText("");
        constraints.gridy = 8;
        signUpPanel.add(errorLabel, constraints);
        constraints.gridy = 0;
        add(signUpPanel, BorderLayout.CENTER);
    }

    /**
     * Applies the sign-in panel as the first page
     */
    public void putSignInOn() {
        mouseHandler.setPanel(signInPanel);
        errorLabel.setText("");
        signInUsername.setText("");
        signInPassword.setText("");
        constraints.gridy = 5;
        signInPanel.add(errorLabel, constraints);
        constraints.gridy = 0;
        add(signInPanel, BorderLayout.CENTER);
    }

    @Override
    public void getListenersReady() {
        super.getListenersReady();
    }

    /**
     * This menu's mouse listener
     */
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
            if(e.getSource() == exit)
                System.exit(0);
            if(e.getSource() == signIn) {
                remove(signUpPanel);
                putSignInOn();
                gameFrame.revalidate();
                gameFrame.repaint();
            }
            else if(e.getSource() == signUp) {
                remove(signInPanel);
                putSignUpOn();
                gameFrame.revalidate();
                gameFrame.repaint();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel hoveredLabel = (JLabel) e.getSource();
            hoveredLabel.setFont(selectedItemFont);
            hoveredLabel.setForeground(selectedItemColour);
            hoveredLabel.setBorder(selectedItemBorder);
            panel.revalidate();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel releasedLabel = (JLabel) e.getSource();
            releasedLabel.setBorder(null);
            releasedLabel.setForeground(unselectedItemColour);
            releasedLabel.setFont(unselectedItemFont);
            panel.revalidate();
            gameFrame.revalidate();
        }
    }

    /**
     * This menu's action listener
     */
    private class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == signUpUsername
                    || e.getSource() == signUpPassword
                    || e.getSource() == signUpRepeatPassword) {
                if(signUpUsername.getText().length() == 0
                        || signUpPassword.getPassword().length == 0) {
                    errorLabel.setText("Insufficient credentials");
                }
                else if(!Arrays.equals(signUpPassword.getPassword(), signUpRepeatPassword.getPassword())) {
                    errorLabel.setText("Passwords conflict");
                }
                else {
                    int operationSuccess = gameManager.addUser(signUpUsername.getText(),
                            new String(signUpPassword.getPassword()));
                    errorLabel.setText(switch (operationSuccess) {
                        case 1 -> "Something went wrong, try reopening the game";
                        case 2 -> "Username already exists";
                        case 3 -> "Can't connect to the server, check ye connection";
                        default -> "";
                    });
                }
            }
            else if(e.getSource() == signInUsername
                    || e.getSource() == signInPassword) {
                if(signInUsername.getText().length() == 0
                        || signInPassword.getPassword().length == 0)
                    errorLabel.setText("Insufficient credentials");
                else {
                    int operationSuccess = gameManager.getUser(signInUsername.getText(),
                            new String(signInPassword.getPassword()));
                    errorLabel.setText(switch (operationSuccess) {
                        case 1 -> "Invalid username or password";
                        case 2 -> "Something went wrong, try reopening the game";
                        case 3 -> "Can't connect to the server, check ye connection";
                        default -> "";
                    });
                }
            }
        }
    }
}
