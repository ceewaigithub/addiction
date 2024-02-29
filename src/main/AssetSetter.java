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
        gp.obj[0].x = 17 * gp.tileSize;
        gp.obj[0].y = 23 * gp.tileSize;

        gp.obj[1] = new OBJ_Chest();
        gp.obj[1].x = 33 * gp.tileSize;
        gp.obj[1].y = 23 * gp.tileSize;
    }
}
