package menus;

import graphics.GameFrame;
import managers.GameManager;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.Serializable;

/**
 * Class Menu extending JPanel is for creating and displaying various
 * menus used during the game
 * @author Amir Fazlollahi
 * @since The dawn of time
 * @version 0.1
 */
public class Menu extends JPanel implements Serializable {

//    The common attributes of the menus
    protected final GameManager gameManager;
    protected final GameFrame gameFrame;
    protected final Image backgroundImage;
    protected final Border selectedItemBorder;
    protected final Font unselectedItemFont;
    protected final Font selectedItemFont;
    protected final Color selectedItemColour;
    protected final Color unselectedItemColour;
    protected final GridBagConstraints constraints;

    /**
     * Instantiates this class, initialising the common attributes and fields
     * @param gameManager The supervising game manager
     * @param gameFrame The JFrame displaying this menu
     * @param backgroundImage This menu's background image
     * @param layoutManager This menu's layout manager
     */
    public Menu(GameManager gameManager, GameFrame gameFrame, Image backgroundImage, LayoutManager layoutManager) {

        super(layoutManager);
        this.gameManager = gameManager;
        this.gameFrame = gameFrame;
        this.backgroundImage = backgroundImage;
        constraints = new GridBagConstraints();

        selectedItemBorder = BorderFactory.createMatteBorder(0, 2, 10, 0, Color.YELLOW);
        unselectedItemFont = new Font("unselected", Font.BOLD, 40);
        selectedItemFont = new Font("selected", Font.BOLD, 45);
        selectedItemColour = new Color(180, 100, 0);
        unselectedItemColour = new Color(80, 10, 0);

    }

    /**
     * Activates this class' listeners
     */
    public void getListenersReady() { }

    /**
     * Updates info about this menu's attributes
     */
    public void update() { }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }

}
