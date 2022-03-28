package edu.dogfood.wordwarone;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

public class MusicPlayer {

    private static MusicPlayer INSTANCE = null;

    private float vol = -80.0f;

    private InputStream file = null;
    private Clip clip = null;
    private FloatControl fc = null;
    
    // Initialize logger
    private static Logger logger = Logger.getLogger(MusicPlayer.class.getName());

    public static MusicPlayer getInstance() {
        if (INSTANCE == null) {
            logger.log(Level.INFO, "Creating new instance of MusicPlayer");
            INSTANCE = new MusicPlayer();
        } else {
            logger.log(Level.INFO, "Returning existing instance of MusicPlayer");
        }
        return INSTANCE;
    }

    private MusicPlayer() {
        try {
            logger.log(Level.INFO, "Loading MusicPlayer");
            clip = AudioSystem.getClip();
        } catch(LineUnavailableException ex) {
            logger.log(Level.WARNING, "AudioSystem threw an exception", ex);
        }
    }

    public void setFile(InputStream file) {
        this.file = file;

        try {
            clip.open(AudioSystem.getAudioInputStream(new BufferedInputStream(this.file)));
        } catch(Exception ex) {
            logger.log(Level.WARNING, "AudioSystem threw an exception", ex);
        }
    }

    public void play() {
        if(file != null) {
            logger.log(Level.INFO, "Playing set file...");
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(vol);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else throw new IllegalStateException("No file has been set");
    }

    public void stop() {
        logger.log(Level.INFO, "Stopping");
        clip.stop();
    }

    public void setVolume(int vol) {
        // Map vol 0 to -80.0f and 20 to 6.0f
        this.vol = map(vol, 0, 20, -80.0f, 6.0f);

        if(fc != null) fc.setValue(this.vol);
    }

    public void setLoop(boolean loop) {
        logger.log(Level.INFO, "Looping");
        clip.loop(loop ? Clip.LOOP_CONTINUOUSLY : 0);
    }

    float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (float) (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
