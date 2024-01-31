package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import world.GamePanel;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[256];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            // test tile
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResource("./tiles/brick_1.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResource("./tiles/wall_1.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResource("./tiles/carpet_1.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResource("./tiles/carpet_2.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResource("./tiles/machine_1.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResource("./tiles/carpet_3.png"));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void loadMap() {
        try {
            InputStream in = getClass().getResourceAsStream("./maps/map01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int col = 0;
            int row = 0;

            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                String[] tokens = line.split(" ");

                while (col < gp.maxWorldCol) {
                    mapTileNum[row][col] = Integer.parseInt(tokens[col]);
                    col++;
                }
                col = 0;
                row++;
            }
            br.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        while (worldRow < gp.maxWorldRow) {
            while (worldCol < gp.maxWorldCol) {
                int tileNum = mapTileNum[worldRow][worldCol];

                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.user.worldX + gp.user.screenX;
                int screenY = worldY - gp.user.worldY + gp.user.screenY;

                if (worldX + gp.tileSize > gp.user.worldX - gp.user.screenX
                        && worldX < gp.user.worldX - gp.user.screenX + gp.screenWidth
                        && worldY + gp.tileSize > gp.user.worldY - gp.user.screenY
                        && worldY < gp.user.worldY - gp.user.screenY + gp.screenHeight) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
                worldCol++;
            }
            worldCol = 0;
            worldRow++;
        }
    }

}
