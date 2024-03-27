package world;

import java.awt.Graphics2D;

import main.UI;

public class UIManager {
    private GamePanel gp;
    private UI ui;

    public UIManager(GamePanel gp) {
        this.gp = gp;
        this.ui = new UI(gp);
    }

    public void draw(Graphics2D g2) {
        ui.draw(g2);
    }

    public UI getUI() {
        return ui;
    }

}