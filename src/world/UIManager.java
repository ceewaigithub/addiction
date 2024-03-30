package world;

import java.awt.Graphics2D;

import main.UI;


/**
 * The UIManager class manages the user interface for the game.
 * It handles drawing the UI components on the screen.
 */
public class UIManager {
    private GamePanel gp;
    private UI ui;

    /**
     * Constructs a UIManager object with the specified GamePanel.
     * Initializes the UI object.
     *
     * @param gp The GamePanel object to associate with the UIManager.
     */
    public UIManager(GamePanel gp) {
        this.gp = gp;
        this.ui = new UI(gp);
    }

    /**
     * Draws the UI components on the screen.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2) {
        ui.draw(g2);
    }

    /**
     * Returns the UI object associated with the UIManager.
     *
     * @return The UI object.
     */
    public UI getUI() {
        return ui;
    }
}