package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import world.GamePanel;
import java.io.File;
import java.io.FileReader;

/**
 * The TileManager class is responsible for managing the tiles in the game.
 * It loads the tile images from a tile sheet and loads the map from a text file.
 * It also handles drawing the tiles on the screen.
 */
public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    /**
     * Constructs a TileManager object.
     * 
     * @param gp The GamePanel object associated with the TileManager.
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[256];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
        getTileImage();
        loadMap();
    }

    /**
     * Loads the tile images from a tile sheet.
     * Each index of the 16x16 image is loaded as a tile.
     */
    public void getTileImage() {
        try {
            String currentDirectory = new File("").getAbsolutePath();

            // load from tile sheet
            // load each index of 16x16 image as a tile
            try {
                String tileSheetPath = currentDirectory + "/res/tiles/tileSheet.png";
                BufferedImage tileSheet = ImageIO.read(new File(tileSheetPath));

                int numTiles = 256; // Assuming there are 256 tiles in the tile sheet
                tile = new Tile[numTiles];

                int tileWidth = 16;
                int tileHeight = 16;

                int tileIndex = 0;
                for (int y = 0; y < tileSheet.getHeight(); y += tileHeight) {
                    for (int x = 0; x < tileSheet.getWidth(); x += tileWidth) {
                        tile[tileIndex] = new Tile();
                        tile[tileIndex].image = tileSheet.getSubimage(x, y, tileWidth, tileHeight);
                        if (tileIndex >= 144 && tileIndex <= 203) {
                            tile[tileIndex].collision = true;
                        }
                        tileIndex++;
                    }
                }

            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            
            
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Loads the map from a text file.
     */
    public void loadMap() {
        try {
            String currentDirectory = new File("").getAbsolutePath();
            File mapFile = new File(currentDirectory + "/res/maps/map03.txt");
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

    /**
     * Draws the tiles on the screen.
     * 
     * @param g2 The Graphics2D object used for drawing.
     */
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