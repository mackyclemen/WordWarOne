package edu.dogfood.wordwarone.ui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import edu.dogfood.wordwarone.Settings;
import edu.dogfood.wordwarone.Settings.DiffConfig;

/**
 *
 * @author Tristan L. Bonus
 */
public class SettingsMenu extends JFrame {

    private final Settings settingsInstance;
        
    private JLabel difficulty;
    private JButton easyButton;
    private JButton hardButton;
    public JButton homeButton;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JLabel settings;
    private JButton soundButton;
    private JLabel sounds;
    private JButton volPlus;
    private JButton volMinus;
    private JLabel volume;
    private JLabel volumevalue;

    /**
     * Creates new form gamesettings
     */
    public SettingsMenu() {
        settingsInstance = Settings.getInstance();

        initComponents();
        setLocationRelativeTo(null); //centers window
        
        // Set text according to settings
        if(settingsInstance.isSoundEnabled()) {
            soundButton.setText("Sound: ON");
        } else {
            soundButton.setText("Sound: OFF");
        }

        volumevalue.setText(Integer.toString(settingsInstance.getVolume()));

        easyButton.setBorder(settingsInstance.getDifficulty() == DiffConfig.DIFFICULTY_EASY ? BorderFactory.createLineBorder(Color.GREEN, 2) : null);
        hardButton.setBorder(settingsInstance.getDifficulty() == DiffConfig.DIFFICULTY_HARD ? BorderFactory.createLineBorder(Color.GREEN, 2) : null);
    }

    private void initComponents() {

        jLabel4 = new JLabel();
        jPanel1 = new JPanel();
        difficulty = new JLabel();
        settings = new JLabel();
        sounds = new JLabel();
        volume = new JLabel();
        hardButton = new JButton();
        easyButton = new JButton();
        volPlus = new JButton();
        volMinus = new JButton();
        volumevalue = new JLabel();
        soundButton = new JButton();
        homeButton = new JButton();

        jLabel4.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel4.setText("ROUND 1");

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Word War I: Jumble Rumble");
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 720));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1280, 720));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));

        difficulty.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        difficulty.setText("Difficulty");

        settings.setFont(new java.awt.Font("Impact", 0, 62)); // NOI18N
        settings.setText("SETTINGS");

        sounds.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        sounds.setText("Sounds");

        volume.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        volume.setText("Volume");

        hardButton.setBackground(new java.awt.Color(0, 0, 0));
        hardButton.setFont(new java.awt.Font("Segoe Print", 0, 24)); // NOI18N
        hardButton.setForeground(new java.awt.Color(255, 255, 255));
        hardButton.setText("HARD");
        hardButton.setBorder(null);
        hardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                hardButtonActionPerformed(evt);
            }
        });

        easyButton.setBackground(new java.awt.Color(0, 0, 0));
        easyButton.setFont(new java.awt.Font("Segoe Print", 0, 24)); // NOI18N
        easyButton.setForeground(new java.awt.Color(255, 255, 255));
        easyButton.setText("EASY");
        easyButton.setBorder(null);
        easyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                easyButtonActionPerformed(evt);
            }
        });

        volPlus.setBackground(new java.awt.Color(0, 0, 0));
        volPlus.setFont(new java.awt.Font("Segoe Print", 0, 24)); // NOI18N
        volPlus.setForeground(new java.awt.Color(255, 255, 255));
        volPlus.setText("+");
        volPlus.setBorder(null);
        volPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                volplusButtonActionPerformed(evt);
            }
        });

        volMinus.setBackground(new java.awt.Color(0, 0, 0));
        volMinus.setFont(new java.awt.Font("Segoe Print", 0, 24)); // NOI18N
        volMinus.setForeground(new java.awt.Color(255, 255, 255));
        volMinus.setText("-");
        volMinus.setBorder(null);
        volMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                volminusButtonActionPerformed(evt);
            }
        });

        volumevalue.setFont(new java.awt.Font("Segoe Print", 0, 24)); // NOI18N
        volumevalue.setHorizontalAlignment(SwingConstants.CENTER);
        volumevalue.setText("20");

        soundButton.setBackground(new java.awt.Color(0, 0, 0));
        soundButton.setFont(new java.awt.Font("Segoe Print", 0, 24)); // NOI18N
        soundButton.setForeground(new java.awt.Color(255, 255, 255));
        soundButton.setText("ON / OFF");
        soundButton.setBorder(null);
        soundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                soundButtonActionPerformed(evt);
            }
        });

        homeButton.setBackground(new java.awt.Color(0, 0, 0));
        homeButton.setForeground(new java.awt.Color(255, 255, 255));
        homeButton.setText("🏠");
        homeButton.setBorder(null);
        homeButton.setBorderPainted(false);
        homeButton.setMaximumSize(new java.awt.Dimension(50, 50));
        homeButton.setMinimumSize(new java.awt.Dimension(50, 50));

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(settings)
                .addGap(502, 502, 502))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(difficulty)
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(sounds, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
                                .addComponent(volume, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)))
                        .addGap(123, 123, 123)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(volMinus, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(volumevalue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(volPlus, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                            .addComponent(soundButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(easyButton, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(hardButton, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(homeButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(236, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(homeButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(settings)
                .addGap(117, 117, 117)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(sounds, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                    .addComponent(soundButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(volPlus, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
                            .addComponent(volMinus, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
                            .addComponent(volumevalue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(volume, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(easyButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                            .addComponent(hardButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
                        .addGap(173, 173, 173))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(difficulty)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void easyButtonActionPerformed(ActionEvent evt) {
        if(!easyButton.isSelected() && settingsInstance.getDifficulty() != DiffConfig.DIFFICULTY_EASY) {
            settingsInstance.setDifficulty(DiffConfig.DIFFICULTY_EASY);

            easyButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            hardButton.setBorder(null);
        }
    }

    private void volminusButtonActionPerformed(ActionEvent evt) {
        if(settingsInstance.getVolume() > 0) {
            settingsInstance.setVolume(settingsInstance.getVolume() - 1);
            volumevalue.setText(Integer.toString(settingsInstance.getVolume()));
        }
    } 

    private void volplusButtonActionPerformed(ActionEvent evt) {
        if(settingsInstance.getVolume() < 20) {
            settingsInstance.setVolume(settingsInstance.getVolume() + 1);
            volumevalue.setText(Integer.toString(settingsInstance.getVolume()));
        }
    } 
    
    private void hardButtonActionPerformed(ActionEvent evt) {
        if(settingsInstance.getDifficulty() != DiffConfig.DIFFICULTY_HARD) {
            settingsInstance.setDifficulty(DiffConfig.DIFFICULTY_HARD);
            
            hardButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            easyButton.setBorder(null);
        }
    }
    
    private void soundButtonActionPerformed(ActionEvent evt) {
        settingsInstance.setSoundEnabled(!settingsInstance.isSoundEnabled());
        
        if(settingsInstance.isSoundEnabled()) {
            soundButton.setText("Sound: ON");
        } else {
            soundButton.setText("Sound: OFF");
        }
    }

}
