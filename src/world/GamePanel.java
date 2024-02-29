package world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import entity.CollisionChecker;
import entity.User;
import main.AssetSetter;
import object.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    
    // Screen Settings
    public final int originalTileSize = 16; // 16x16 tile size
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile size
    public final int maxScreenCol  = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768
    public final int screenHeight = tileSize * maxScreenRow; // 576

    // World Settings
    public final int maxWorldCol = 52;
    public final int maxWorldRow = 52;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // Game Settings
    int fps = 60;

    public TileManager tm = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cc = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public User user = new User(this, keyH);
    public SuperObject obj[] = new SuperObject[10];


    // Set player's default postiion
    int playerX = worldWidth / 2 - tileSize / 2;
    int playerY = worldHeight / 2 - tileSize / 2;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGame() {
        aSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta < 1) {
                continue;
            } else {
                // Update game state
                update();
                // Draw game state to screen
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                // If you want to see the FPS uncomment this line
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        user.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tm.draw(g2);
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        user.draw(g2);
        g2.dispose();
    }

}
