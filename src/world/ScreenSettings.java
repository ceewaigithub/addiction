package world;

public class ScreenSettings {
    // The size of each tile in pixels
    public final int originalTileSize = 16;
    
    // The scaling factor for the tiles
    public final int scale = 3;
    
    // The size of each scaled tile in pixels
    public final int tileSize = originalTileSize * scale;
    
    // The maximum number of columns on the screen
    public final int maxScreenCol = 16;
    
    // The maximum number of rows on the screen
    public final int maxScreenRow = 12;
    
    // The width of the screen in pixels
    public final int screenWidth = tileSize * maxScreenCol;
    
    // The height of the screen in pixels
    public final int screenHeight = tileSize * maxScreenRow;
    
    // The maximum number of columns in the world
    public final int maxWorldCol = 50;
    
    // The maximum number of rows in the world
    public final int maxWorldRow = 50;
    
    // The width of the world in pixels
    public final int worldWidth = tileSize * maxWorldCol;
    
    // The height of the world in pixels
    public final int worldHeight = tileSize * maxWorldRow;
}