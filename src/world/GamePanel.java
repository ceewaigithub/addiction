package world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import entity.CollisionChecker;
import entity.User;
import main.AssetSetter;
import main.Config;
import main.NewGameException;
import main.UI;
import object.OBJ_Door;
import object.SuperObject;
import shop.ShopManager;
import tile.TileManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The GamePanel class represents the main panel of the game. It extends the JPanel class and implements the Runnable interface.
 * It contains the game logic, screen settings, world settings, game settings, and various methods for managing the game state.
 */
public class GamePanel extends JPanel implements Runnable {

    public ScreenSettings screenSettings = new ScreenSettings();
    public GameLogic gameLogic = new GameLogic(this);
    public MusicManager musicManager = new MusicManager(this);

    public JFrame frame;

    // Game Settings
    int fps = 60;

    KeyHandler keyH = new KeyHandler(this);
    public User user = new User(this, keyH);
    public Config config = new Config(this);
    public TileManager tileManager = new TileManager(this);
    public ShopManager shopManager = new ShopManager(this);

    public boolean musicEnabled = false;

    public CollisionChecker cc = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    public SuperObject obj[] = new SuperObject[10];

    /**
     * Constructs a GamePanel object.
     * 
     * @param frame the JFrame object associated with the GamePanel
     */
    public GamePanel(JFrame frame) {
        this.setPreferredSize(new Dimension(screenSettings.screenWidth, screenSettings.screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.frame = frame;

        aSetter.setObject();
        try {
            config.loadGameConfig();
            shopManager.loadShopItems();
            System.out.println("Game loaded from save file successfully");
        } catch (NewGameException e) {
            System.out.println(e.getMessage());
        }

        if (shopManager.isSoundPurchased()) {
            musicEnabled = true;
        }
        openAllOpenedDoors();
    }

    /**
     * Starts the game thread.
     */
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

    /**
     * Updates the game state.
     */
    public void update() {
        gameLogic.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (gameLogic.getGameState() != gameLogic.titleState || gameLogic.getGameState() != gameLogic.gameOverState) {
            tileManager.draw(g2);
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

    /**
     * Opens all unlocked doors in the game.
     */
    public void openAllOpenedDoors() {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null && obj[i] instanceof OBJ_Door) {
                if (!((OBJ_Door) obj[i]).isLocked()) {
                    ((OBJ_Door) obj[i]).setOpen(true);
                }
            }
        }
    }

    /**
     * Gets the current game state.
     * 
     * @return the current game state
     */
    public int getGameState() {
        return gameLogic.getGameState();
    }

    /**
     * Sets the game state.
     * 
     * @param gameState the new game state to set
     */
    public void setGameState(int gameState) {
        gameLogic.setGameState(gameState);
    }

}
