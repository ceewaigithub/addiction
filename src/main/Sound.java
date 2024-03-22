package main;

import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

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

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    public void loop() {
        if (clip != null && !clip.isRunning()) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void setVolume(float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    public void fadeBackgroundMusic() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float originalVolume = gainControl.getValue();

        // Lower the volume by half
        float targetVolume = originalVolume - 6.0f; // Adjust the value as needed
        float fadeDuration = 2000.0f; // Adjust the duration as needed
        float fadeStep = (targetVolume - originalVolume) / fadeDuration;

        // Gradually decrease the volume
        while (gainControl.getValue() > targetVolume) {
            float currentVolume = gainControl.getValue();
            gainControl.setValue(currentVolume + fadeStep);
            try {
                Thread.sleep(10); // Adjust the sleep duration as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Restore the original volume
        gainControl.setValue(originalVolume);
    }
}
