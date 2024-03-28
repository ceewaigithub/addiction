package main;

import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import javax.sound.sampled.FloatControl;

/**
 * The Sound class represents a sound player that can play, loop, stop, and adjust the volume of audio clips.
 * It provides methods to load sound files and control the playback of the clips.
 */
public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

    /**
     * Constructs a Sound object and initializes the sound URLs.
     * The sound URLs are loaded from the specified file paths.
     */
    public Sound() {
        String currentDirectory = new File("").getAbsolutePath();
        try {
            soundURL[0] = new File(currentDirectory + "/res/sound/casinopukimans.wav").toURI().toURL();
            soundURL[1] = new File(currentDirectory + "/res/sound/Click.wav").toURI().toURL();
            soundURL[2] = new File(currentDirectory + "/res/sound/Pause.wav").toURI().toURL();
            soundURL[3] = new File(currentDirectory + "/res/sound/Cancel.wav").toURI().toURL();
            soundURL[4] = new File(currentDirectory + "/res/sound/Jump.wav").toURI().toURL();
            soundURL[5] = new File(currentDirectory + "/res/sound/door.wav").toURI().toURL();
            soundURL[6] = new File(currentDirectory + "/res/sound/Menu_In.wav").toURI().toURL();
            soundURL[7] = new File(currentDirectory + "/res/sound/Menu_Out.wav").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the audio file to be played based on the specified index.
     * The index corresponds to the position of the sound URL in the array.
     *
     * @param i the index of the sound URL
     */
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the audio clip if it is not already running.
     */
    public void play() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    /**
     * Loops the audio clip continuously if it is not already running.
     */
    public void loop() {
        if (clip != null && !clip.isRunning()) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Stops the audio clip if it is running.
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Sets the volume of the audio clip.
     * The volume is specified as a float value between 0.0 and 1.0.
     *
     * @param volume the volume level
     */
    public void setVolume(float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
