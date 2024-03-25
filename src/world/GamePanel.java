package world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.CollisionChecker;
import entity.User;
import main.AssetSetter;
import main.Config;
import main.NewGameException;
import main.Sound;
import main.UI;
import object.OBJ_Door;
import object.SuperObject;
import shop.ShopManager;
import tile.TileManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    public final int originalTileSize = 16; // 16x16 tile size
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile size
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768
    public final int screenHeight = tileSize * maxScreenRow; // 576

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public JFrame frame;

    // Game Settings
    int fps = 60;

    public TileManager tm = new TileManager(this);
    public ShopManager sm;
    KeyHandler keyH = new KeyHandler(this);

    public boolean musicEnabled = false;

    public Sound music = new Sound();
    Sound se = new Sound();

    public CollisionChecker cc = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int titleState = 4;
    public final int gameOverState = 5;

    public User user = new User(this, keyH);
    public SuperObject obj[] = new SuperObject[10];

    public Config config = new Config(this);
    
    public GamePanel(JFrame frame) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.frame = frame;
        sm = new ShopManager(this);

        aSetter.setObject();
        try {
            config.loadGameConfig();
            sm.loadShopItems(); // Load shop items after loading the game configuration
            System.out.println("Game loaded from save file successfully");
        } catch (NewGameException e) {
            System.out.println(e.getMessage());
        }
        
        if (sm.isSoundPurchased()) {
            musicEnabled = true;
        }
        openAllOpenedDoors();

    }

    public void startGame() {

        if (sm.isSoundPurchased()) {
            setBackgroundMusic();
        }
        
        gameState = titleState;
        if (user.money <= 0) {
            gameState = gameOverState;
        }

    }

    public void restartGame() {
        config.restartGame();
        user.setDefaultValues();
        user.getPlayerImage();
        gameState = titleState;
        if (sm.isSoundPurchased()) {
            setBackgroundMusic();
        } else {
            stopMusic();
        }
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
                // System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            user.update();
        }
        if (gameState == pauseState) {
            // Do nothing
        }
        if (gameState == dialogueState) {
            // Do nothing
        }
        if (gameState == titleState) {
            // Do nothing
        }
        if (gameState == gameOverState) {
            // Do nothing
        }
        if (user.money < 0) {
            gameState = gameOverState;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (gameState != titleState || gameState != gameOverState) {
            tm.draw(g2);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }
            user.draw(g2);
        }
        ui.draw(g2);

        g2.dispose();
    }

    public void openAllOpenedDoors() {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null && obj[i] instanceof OBJ_Door) {
                if (!((OBJ_Door) obj[i]).isLocked()) {
                    ((OBJ_Door) obj[i]).setOpen(true);
                }
            }
        }
    }

    public void setCurrentSprite(String sprite) {
        user.sprite = sprite;
    }

    public void playMusic(int i) {
        if (sm.isSoundPurchased()) {
            music.setFile(i);
            music.play();
            music.loop();
        }
    }

    public void setBackgroundMusic() {
        if (sm.isSoundPurchased()) {
            music.setFile(0);
            music.play();
            music.loop();
            music.setVolume(0.25f);
        }
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        if (sm.isSoundPurchased()) {
            se.setFile(i);
            se.setVolume(1.5f);
            se.play();
        }
    }

}
