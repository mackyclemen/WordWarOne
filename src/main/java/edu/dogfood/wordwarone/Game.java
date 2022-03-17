package edu.dogfood.wordwarone;

import java.util.logging.Logger;
import java.util.logging.Level;

import javax.swing.UIManager;

import edu.dogfood.wordwarone.ui.SplashScreen;


/**
 * Hello world!
 *
 */
public class Game {

    // Initialize logger
    private static Logger logger = Logger.getLogger(Game.class.getName());

    // Constructor
    public Game() {
        logger.setLevel(Level.INFO);
        logger.info("Game started");

        // Initialize settings
        Settings settings = Settings.getInstance();

        // Initialize Audio
        MusicPlayer musicPlayer = MusicPlayer.getInstance();
        musicPlayer.setVolume(15);
        musicPlayer.play(getClass().getClassLoader().getResource("bg.wav").getPath());
        musicPlayer.setLoop(true);

        // Set UI settings
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        // Show splash screen
        final SplashScreen splashScreen = new SplashScreen(settings);

        // Load databases

        
    }
    
    public static void main(String[] args) {
        new Game();
    }

}
