package world;

import main.Sound;

public class MusicManager {
    private GamePanel gp;
    private Sound music;
    private Sound se;
    private boolean musicEnabled;

    public MusicManager(GamePanel gp) {
        this.gp = gp;
        this.music = new Sound();
        this.se = new Sound();
        this.musicEnabled = false;
    }

    /**
     * Plays the specified music file if sound is purchased.
     * @param i The index of the music file to play.
     */
    public void playMusic(int i) {
        if (gp.shopManager.isSoundPurchased()) {
            music.setFile(i);
            music.play();
            music.loop();
        }
    }

    /**
     * Sets the background music if sound is purchased.
     */
    public void setBackgroundMusic() {
        if (gp.shopManager.isSoundPurchased()) {
            music.setFile(0);
            music.play();
            music.loop();
            music.setVolume(0.25f);
        }
    }

    /**
     * Plays the specified sound effect if sound is purchased.
     * @param i The index of the sound effect to play.
     */
    public void playSE(int i) {
        if (gp.shopManager.isSoundPurchased()) {
            se.setFile(i);
            se.setVolume(1.5f);
            se.play();
        }
    }

    /**
     * Stops the currently playing music.
     */
    public void stopMusic() {
        music.stop();
    }


}