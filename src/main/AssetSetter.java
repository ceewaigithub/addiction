package main;

import object.*;
import world.GamePanel;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
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

        gp.obj[4] = new OBJ_Bacarrat();
        gp.obj[4].x = 19 * gp.tileSize;
        gp.obj[4].y = 17 * gp.tileSize;

        gp.obj[5] = new OBJ_Bacarrat();
        gp.obj[5].x = 14 * gp.tileSize;
        gp.obj[5].y = 17 * gp.tileSize;

        gp.obj[6] = new OBJ_HighLow();
        gp.obj[6].x = 20 * gp.tileSize;
        gp.obj[6].y = 12 * gp.tileSize;

        gp.obj[7] = new OBJ_HighLow();
        gp.obj[7].x = 29 * gp.tileSize;
        gp.obj[7].y = 13 * gp.tileSize;
    }
}
