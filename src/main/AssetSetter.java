package main;

import object.OBJ_Chest;
import world.GamePanel;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    public void setObject() {
        gp.obj[0] = new OBJ_Chest();
        gp.obj[0].x = 28 * gp.tileSize;
        gp.obj[0].y = 27 * gp.tileSize;

        gp.obj[1] = new OBJ_Chest();
        gp.obj[1].x = 17 * gp.tileSize;
        gp.obj[1].y = 26 * gp.tileSize;
    }
}
