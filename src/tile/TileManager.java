package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import world.GamePanel;
import java.io.File;
import java.io.FileReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[256];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            String currentDirectory = new File("").getAbsolutePath();
            // test tile
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File(currentDirectory + "/res/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File(currentDirectory + "/res/tiles/grass_top_left.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File(currentDirectory + "/res/tiles/grass_top_middle.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(new File(currentDirectory + "/res/tiles/grass_top_right.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(new File(currentDirectory + "/res/tiles/grass_edge_bottom_right.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(new File(currentDirectory + "/res/tiles/grass_edge_bottom_left.png"));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(new File(currentDirectory + "/res/tiles/grass_middle_left.png"));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(new File(currentDirectory + "/res/tiles/dirt.png"));

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(new File(currentDirectory + "/res/tiles/grass_edge_top_right.png"));

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(new File(currentDirectory + "/res/tiles/grass_edge_top_left.png"));

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(new File(currentDirectory + "/res/tiles/grass_bottom_left.png"));

            tile[14] = new Tile();
            tile[14].image = ImageIO.read(new File(currentDirectory + "/res/tiles/grass_bottom_middle.png"));

            tile[15] = new Tile();
            tile[15].image = ImageIO.read(new File(currentDirectory + "/res/tiles/grass_bottom_right.png"));

            // tile[16] = new Tile();
            // tile[16].image = ImageIO.read(new File(currentDirectory + "/res/tiles/chest.png"));
            // tile[16].collision = true;

            tile[255] = new Tile();
            tile[255].image = ImageIO.read(new File(currentDirectory + "/res/tiles/water.png"));
            tile[255].collision = true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void loadMap() {
        try {
            String currentDirectory = new File("").getAbsolutePath();
            File mapFile = new File(currentDirectory + "/res/maps/map01.txt");
            BufferedReader br = new BufferedReader(new FileReader(mapFile));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                String[] tokens = line.split("[ \t]");

                while (col < gp.maxWorldCol) {
                    mapTileNum[row][col] = Integer.parseInt(tokens[col]);
                    col++;
                }
                col = 0;
                row++;
            }
            br.close();

        } catch (Exception e) {
            System.out.println("Error getting map: " + e);
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        while (worldRow < gp.maxWorldRow && worldCol < gp.maxWorldCol) {

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
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

}
