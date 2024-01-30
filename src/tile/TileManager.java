package tile;

import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import world.GamePanel;

public class TileManager {
    GamePanel gp;
    Tile[] tile;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[256];
        getTileImage();
    }

    public void getTileImage() {
        try {
            // test tile
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResource("./tiles/brick_1.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResource("./tiles/carpet_1.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResource("./tiles/wall_1.png"));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < tile.length; i++) {
            if (tile[i] != null) {
                g2.drawImage(tile[i].image, 0, 0, gp.tileSize, gp.tileSize, null);
            }
        }
    }

}
