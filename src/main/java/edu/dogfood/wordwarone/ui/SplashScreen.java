package edu.dogfood.wordwarone.ui;

import java.util.logging.Logger;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.netbeans.lib.awtextra.AbsoluteLayout;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
/**
 *
 * @author janah
 */
public class SplashScreen extends JFrame {

    // Initialize logger
    private static Logger logger = Logger.getLogger(SplashScreen.class.getName());

    private final Thread sentenceThread;
    private boolean loaded = false;

    // Constants declaration
    private final List<String> words = Arrays.asList(
        "Preparing Firearms...",
        "Loading word bullets...",
        "Discussing war tactics...",
        "Deploying soldiers...",
        "Preparing for battle...",
        "Preparing for war...",
        "Getting the dictionaries ready...",
        "Compiling backup plans...",
        "Are you ready for the war?"
    );

    // Variables declaration
    private JLabel jLabel_gameTitle;
    private JLabel jLabel_loading;
    private JLabel jLabel_loadingValue;
    private JLabel jLabel_logo;
    private JLabel jLabel_tagline;
    private JPanel jPanel_background;
    private JProgressBar jProgressBar_loadingBar;

    /**
     * Creates new form SplashScreen
     */
    public SplashScreen() {
        initComponents();

        setLocationRelativeTo(null); //centers window
        setVisible(true);

        // Create a thread that randomizes load text each second
        sentenceThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int prevNum = 0;
                int rand;

                while (!loaded) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        logger.log(Level.SEVERE, "Thread interrupted.", ex);
                    }

                    // Get a random sentence
                    
                    do {
                        rand = (int) (Math.random() * words.size());
                    } while(rand == prevNum);

                    prevNum = rand;

                    jLabel_loading.setText(words.get(rand));
                }
            }
        });

        sentenceThread.start();
    }

    public void changeProgress(int progress) {
        // Make sure the value is between 0 and 100
        if (progress < 0) {
            progress = 0;
        } else if (progress > 100) {
            progress = 100;
        }
        
        jProgressBar_loadingBar.setValue(progress);
        jLabel_loadingValue.setText(progress + "%");
    }

    public void end() {
        loaded = true;

        // Hide window, then dispose
        setVisible(false);
        dispose();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel_background = new JPanel();
        jLabel_tagline = new JLabel();
        jLabel_gameTitle = new JLabel();
        jLabel_loading = new JLabel();
        jLabel_loadingValue = new JLabel();
        jProgressBar_loadingBar = new JProgressBar();
        jLabel_logo = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("World War I: Jumble Rumble");
        setResizable(false);

        jPanel_background.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_background.setPreferredSize(new java.awt.Dimension(1280, 720));
        jPanel_background.setLayout(new AbsoluteLayout());

        jLabel_tagline.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel_tagline.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_tagline.setText("\"If we don't end war, war will end us\"");
        jPanel_background.add(jLabel_tagline, new AbsoluteConstraints(400, 460, 500, -1));

        jLabel_gameTitle.setFont(new java.awt.Font("Impact", 0, 64)); // NOI18N
        jLabel_gameTitle.setText("WORD WAR I: JUMBLE RUMBLE");
        jPanel_background.add(jLabel_gameTitle, new AbsoluteConstraints(271, 502, -1, -1));

        jLabel_loading.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel_loading.setText("Loading...");
        jPanel_background.add(jLabel_loading, new AbsoluteConstraints(22, 666, -1, -1));

        jLabel_loadingValue.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel_loadingValue.setText("0%");
        jPanel_background.add(jLabel_loadingValue,
                new AbsoluteConstraints(1238, 666, -1, -1));

        jProgressBar_loadingBar.setBorderPainted(false);
        jPanel_background.add(jProgressBar_loadingBar,
                new AbsoluteConstraints(0, 693, 1280, 27));

        jLabel_logo.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_logo.setText("LOGO");
        jPanel_background.add(jLabel_logo, new AbsoluteConstraints(390, 50, 510, 390));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel_background, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel_background, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));

        pack();
    }
}
