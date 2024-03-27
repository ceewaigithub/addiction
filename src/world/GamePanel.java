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

/**
 * The GamePanel class represents the main panel of the game. It extends the JPanel class and implements the Runnable interface.
 * It contains the game logic, screen settings, world settings, game settings, and various methods for managing the game state.
 */
public class GamePanel extends JPanel implements Runnable {

    public ScreenSettings screenSettings = new ScreenSettings();
    public GameLogic gameLogic = new GameLogic(this);

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


    public User user = new User(this, keyH);
    public SuperObject obj[] = new SuperObject[10];

    public Config config = new Config(this);

    public GamePanel(JFrame frame) {
        this.setPreferredSize(new Dimension(screenSettings.screenWidth, screenSettings.screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.frame = frame;
        sm = new ShopManager(this);
    
        aSetter.setObject();
        try {
            config.loadGameConfig();
            sm.loadShopItems();
            System.out.println("Game loaded from save file successfully");
        } catch (NewGameException e) {
            System.out.println(e.getMessage());
        }
    
        if (sm.isSoundPurchased()) {
            musicEnabled = true;
        }
        openAllOpenedDoors();
    }
    /**
     * Starts the game by setting the game state to titleState or gameOverState.
     * If the player has no money, the game state is set to gameOverState.
     */
    public void startGame() {
        if (sm.isSoundPurchased()) {
            setBackgroundMusic();
        }
        
        gameLogic.setGameState(gameLogic.titleState);
        if (user.getBalance() <= 0) {
            gameLogic.setGameState(gameLogic.gameOverState);
        }

    }

    /**
     * Restarts the game by resetting the game configuration, user values, and game state.
     * Sets the background music if sound is purchased, otherwise stops the music.
     */
    public void restartGame() {
        config.restartGame();
        user.setDefaultValues();
        user.getPlayerImage();
        gameLogic.setGameState(gameLogic.titleState);
        if (sm.isSoundPurchased()) {
            setBackgroundMusic();
        } else {
            stopMusic();
        }
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


    public void update() {
        gameLogic.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (gameLogic.getGameState() != gameLogic.titleState || gameLogic.getGameState() != gameLogic.gameOverState) {
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
     * Sets the current sprite for the user.
     * @param sprite The sprite to set for the user.
     */
    public void setCurrentSprite(String sprite) {
        user.sprite = sprite;
    }

    /**
     * Plays the specified music file if sound is purchased.
     * @param i The index of the music file to play.
     */
    public void playMusic(int i) {
        if (sm.isSoundPurchased()) {
            music.setFile(i);
            music.play();
            music.loop();
        }
    }

    /**
     * Sets the background music if sound is purchased.
     */
    public void setBackgroundMusic() {
        if (sm.isSoundPurchased()) {
            music.setFile(0);
            music.play();
            music.loop();
            music.setVolume(0.25f);
        }
    }

    /**
     * Stops the currently playing music.
     */
    public void stopMusic() {
        music.stop();
    }

    /**
     * Plays the specified sound effect if sound is purchased.
     * @param i The index of the sound effect to play.
     */
    public void playSE(int i) {
        if (sm.isSoundPurchased()) {
            se.setFile(i);
            se.setVolume(1.5f);
            se.play();
        }
    }

    public int getGameState() {
        return gameLogic.getGameState();
    }
    
    public void setGameState(int gameState) {
        gameLogic.setGameState(gameState);
    }

}
