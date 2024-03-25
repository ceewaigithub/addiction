package main;

import object.*;
import world.GamePanel;
import object.OBJ_Door;

/**
 * The AssetSetter class is responsible for setting up the game assets and manipulating them.
 */
public class AssetSetter {
    GamePanel gp;

    /**
     * Constructs an AssetSetter object with the specified GamePanel.
     * 
     * @param gp the GamePanel object
     */
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Sets up the game objects and their positions.
     */
    public void setObject() {
        gp.obj[0] = new OBJ_BlackJack();
        gp.obj[0].x = 28 * gp.tileSize;
        gp.obj[0].y = 27 * gp.tileSize;

        gp.obj[1] = new OBJ_BlackJack();
        gp.obj[1].x = 17 * gp.tileSize;
        gp.obj[1].y = 26 * gp.tileSize;

        gp.obj[2] = new OBJ_Door();
        gp.obj[2].x = 22 * gp.tileSize;
        gp.obj[2].y = 24 * gp.tileSize;

        gp.obj[3] = new OBJ_Door();
        gp.obj[3].x = 22 * gp.tileSize;
        gp.obj[3].y = 15 * gp.tileSize;

        gp.obj[4] = new OBJ_Baccarat();
        gp.obj[4].x = 19 * gp.tileSize;
        gp.obj[4].y = 17 * gp.tileSize;

        gp.obj[5] = new OBJ_Baccarat();
        gp.obj[5].x = 14 * gp.tileSize;
        gp.obj[5].y = 17 * gp.tileSize;

        gp.obj[6] = new OBJ_HighLow();
        gp.obj[6].x = 20 * gp.tileSize;
        gp.obj[6].y = 12 * gp.tileSize;

        gp.obj[7] = new OBJ_HighLow();
        gp.obj[7].x = 29 * gp.tileSize;
        gp.obj[7].y = 13 * gp.tileSize;
    }

    /**
     * Opens the doors based on the loaded configuration.
     */
    public void openDoors() {
        // Declare the obj variable
        Object[] obj = gp.obj;
        // Set the opened state of the doors based on the loaded configuration
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] instanceof OBJ_Door) {
                boolean isOpen = ((OBJ_Door) obj[i]).isOpen();
                if (isOpen) {
                    ((OBJ_Door) obj[i]).setOpen(isOpen);
                }
            }
        }
    }
}
