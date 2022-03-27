package edu.dogfood.wordwarone;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import edu.dogfood.wordwarone.database.EnglishWordsRepository;
import edu.dogfood.wordwarone.database.HighScoreRepository;
import edu.dogfood.wordwarone.database.SavedGameRepository;
import edu.dogfood.wordwarone.database.entry.Highscore;
import edu.dogfood.wordwarone.database.entry.SavedGame;
import edu.dogfood.wordwarone.interfaces.GameHandoffListener;
import edu.dogfood.wordwarone.interfaces.SavedGameSelectionListener;
import edu.dogfood.wordwarone.interfaces.SubmissionListener;
import edu.dogfood.wordwarone.ui.GameProper;
import edu.dogfood.wordwarone.ui.LoadSavedGames;
import edu.dogfood.wordwarone.ui.Menu;
import edu.dogfood.wordwarone.ui.NamePrompt;
import edu.dogfood.wordwarone.ui.PlayerRanking;
import edu.dogfood.wordwarone.ui.SettingsMenu;
import edu.dogfood.wordwarone.ui.SplashScreen;


/**
 * Hello world!
 *
 */
public class Game {

    // Initialize logger
    private static Logger logger = Logger.getLogger(Game.class.getName());

    private final HighScoreRepository hsRepo;
    private final SavedGameRepository sgRepo;
    private final EnglishWordsRepository ewRepo;
    private final Settings settings;

    private final Menu menu = new Menu();
    private final SettingsMenu settingsMenu = new SettingsMenu();
    private final NamePrompt namePrompt = new NamePrompt();
    private final LoadSavedGames loadSavedGames = new LoadSavedGames();
    private final PlayerRanking playerRanking = new PlayerRanking();
    private GameProper gameProper;

    // Constructor
    public Game() {
        logger.setLevel(Level.INFO);
        logger.info("Game started");

        // Initialize settings
        settings = Settings.getInstance();

        // Initialize Audio
        MusicPlayer musicPlayer = MusicPlayer.getInstance();
        musicPlayer.setVolume(settings.getVolume());
        musicPlayer.setFile(getClass().getClassLoader().getResourceAsStream("bg.wav"));

        if(settings.isSoundEnabled()) {
            musicPlayer.play();
        }

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
        final SplashScreen splashScreen = new SplashScreen();

        hsRepo = HighScoreRepository.getInstance();
        splashScreen.changeProgress(33);
        sgRepo = SavedGameRepository.getInstance();
        splashScreen.changeProgress(67);
        ewRepo = EnglishWordsRepository.getInstance();
        splashScreen.changeProgress(99);

        // Menu EventListeners
        menu.newgameButton.addActionListener(e -> {
            logger.info("New game button clicked");
            namePrompt.setVisible(true); 
            menu.setVisible(false);
        });

        menu.settingsButton.addActionListener(e -> {
            settingsMenu.setVisible(true);
            menu.setVisible(false);
        });

        menu.loadgameButton.addActionListener(e -> {
            loadSavedGames.updateSaves();
            
            loadSavedGames.setVisible(true);
            menu.setVisible(false);
        });

        menu.rankingButton.addActionListener(e -> {
            playerRanking.update();
            playerRanking.setVisible(true);
            menu.setVisible(false);
        });

        menu.exitButton.addActionListener(e -> {
            close();
        });

        menu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        // Settings EventListeners
        settingsMenu.homeButton.addActionListener(e -> {
            menu.setVisible(true);
            settingsMenu.setVisible(false);
        });

        settingsMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        // NamePrompt EventListeners
        namePrompt.jButton_back.addActionListener(e -> {
            menu.setVisible(true);
            namePrompt.setVisible(false);
        });

        namePrompt.setSubmissionListener(new SubmissionListener() {
            @Override
            public void onSubmit(String name) {
                // TODO Auto-generated method stub
                SavedGame sg = new SavedGame(name);

                callGame(sg);

                namePrompt.setVisible(false);
            }
        });

        namePrompt.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menu.setVisible(true);
                namePrompt.setVisible(false);
            }
        });

        // LoadSavedGames EventListeners
        loadSavedGames.homeButton.addActionListener(e -> {
            menu.setVisible(true);
            loadSavedGames.setVisible(false);
        });

        loadSavedGames.setSavedGameSelectionListener(new SavedGameSelectionListener() {
            @Override
            public void onSavedGameSelected(SavedGame game) {
                // TODO Auto-generated method stub
                loadSavedGames.setVisible(false);
                callGame(game);
            }
        });

        loadSavedGames.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menu.setVisible(true);
                loadSavedGames.setVisible(false);
            }
        });
        
        // PlayerRanking EventListeners
        playerRanking.homeButton.addActionListener(e -> {
            menu.setVisible(true);
            playerRanking.setVisible(false);
        });

        playerRanking.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        splashScreen.changeProgress(100);

        // Delay for a second and half
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) { /* Ignored Exception */}

        // Close splash screen and show menu
        splashScreen.end();
        
        menu.setVisible(true);
    }
    
    public void close() {
        JFrame frame = new JFrame("Exit");
        if(JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit","Exit Word War I: Jumble Rumble?", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION){
            // Write settings
            logger.log(Level.INFO, "Writing settings");
            settings.close();
            
            // Write databases
            try {
                logger.log(Level.INFO, "Writing databases");
                hsRepo.close();
                sgRepo.close();
            } catch(Exception ex) {
                logger.log(Level.SEVERE, "Error closing databases", ex);
            }

            menu.dispose();
            settingsMenu.dispose();

            logger.info("Game close permitted");
            System.exit(0);
        }
    }

    private void callGame(SavedGame sg) {
        gameProper = new GameProper(sg);
        
        gameProper.setGameHandoffListener(new GameHandoffListener() {
            @Override
            public void onSave(SavedGame sg) {
                // TODO Auto-generated method stub
                // sgRepo.save(sg);

                // If ID is non-negative, update existing SavedGame
                if (sg.getId() >= 0) {
                    sgRepo.updateSavedGame(sg.getId(), sg);
                } else {
                    sgRepo.addSaveGame(sg);
                }

                gameProper.setVisible(false);
                menu.setVisible(true);

                gameProper.dispose();
            }

            @Override
            public void onForfeit(Highscore highscore) {
                // TODO Auto-generated method stub
                // hsRepo.save(highscore);

                if(highscore.getScore() > 0) {
                    hsRepo.addHighscore(highscore);
                }

                gameProper.setVisible(false);
                playerRanking.setVisible(true);

                gameProper.dispose();
            }
        });

        gameProper.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Game();
    }

}
