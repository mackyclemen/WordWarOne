package edu.dogfood.wordwarone;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * Settings
 */
public class Settings {    
    // Initialize logger
    private static Logger logger = Logger.getLogger(Game.class.getName());

    // Settings constants
    private boolean soundEnabled;
    private int volume;
    private int difficulty;

    // Configuration constants
    public class DiffConfig {
        public static final int DIFFICULTY_EASY = 0;
        public static final int DIFFICULTY_HARD = 1;

        private DiffConfig() {}
    }

    static final String SETTINGS_FILE = "config.properties";
    public static Settings INSTANCE;

    private final Configurations configs = new Configurations();
    private FileBasedConfigurationBuilder<PropertiesConfiguration> configBuilder;
    private Configuration config;

    private Settings() {
        logger.setLevel(Level.INFO);
        logger.info("Loading settings");

        // Create file object, and check if it exists
        File settingsObj = new File(SETTINGS_FILE);
        if(!settingsObj.exists()) {
            logger.info("Settings file not found, creating new one");
            try {
                settingsObj.createNewFile();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to create settings file", e);
            }
        }

        // Load settings
        try {
            // Create configuration object
            configBuilder = configs.propertiesBuilder(SETTINGS_FILE);
            config = configBuilder.getConfiguration();

            // Check if configurations exist
            if(!config.containsKey("soundEnabled")) {
                logger.info("Sound enabled not found, creating new one");
                config.addProperty("soundEnabled", true);
            }

            if(!config.containsKey("volume")) {
                logger.info("Volume not found, creating new one");
                config.addProperty("volume", 20);
            }

            if(!config.containsKey("difficulty")) {
                logger.info("Difficulty not found, creating new one");
                config.addProperty("difficulty", DiffConfig.DIFFICULTY_EASY);
            }

            // Load settings
            soundEnabled = config.getBoolean("soundEnabled");
            volume = config.getInt("volume");
            difficulty = config.getInt("difficulty");

            logger.info("Settings loaded");
            
            // Save settings
            configBuilder.save();
        } catch (ConfigurationException e) {
            logger.log(Level.SEVERE, "Failed to load settings", e);
        }
    }

    // Singleton Boilerplate
    public static Settings getInstance() {
        if (INSTANCE == null) {
            // Log
            logger.info("Creating new settings instance");
            INSTANCE = new Settings();
        } else {
            // Log
            logger.info("Returning existing settings instance");
        }
        return INSTANCE;
    }

    // Getters
    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public int getVolume() {
        return volume;
    }

    public int getDifficulty() {
        return difficulty;
    }

    // Setters
    public void setSoundEnabled(boolean soundEnabled) {
        // Log sound setting change
        logger.info("Sound setting changed to " + soundEnabled);
        
        this.soundEnabled = soundEnabled;

        // Temporarily access MusicPlayer instance
        MusicPlayer mpInstance = MusicPlayer.getInstance();
        
        if(!soundEnabled) mpInstance.stop();
        else mpInstance.play();
        
        // Set config to file
        config.setProperty("soundEnabled", soundEnabled);
    }

    public void setVolume(int volume) {
        // Limit values to 0 - 20
        if (volume < 0) {
            volume = 0;
        } else if (volume > 20) {
            volume = 20;
        }
        
        // Log volume
        logger.log(Level.INFO, "Volume set to " + volume);

        this.volume = volume;

        // Temporarily access MusicPlayer instance
        MusicPlayer.getInstance().setVolume(this.volume);

        // Set config to file
        config.setProperty("volume", volume);
    }

    public void setDifficulty(int difficulty) {
        // Log difficulty
        logger.log(Level.INFO, "Setting difficulty to " + difficulty);

        this.difficulty = difficulty;

        // Set config to file
        config.setProperty("difficulty", difficulty);
    }

    public void close() {
        // Save settings
        logger.info("Saving settings");
        try {
            configBuilder.save();
            logger.info("Save successful");
        } catch (ConfigurationException e) {
            logger.log(Level.SEVERE, "Failed to save settings", e);
        }
    }
}