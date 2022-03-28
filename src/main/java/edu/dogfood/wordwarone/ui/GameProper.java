package edu.dogfood.wordwarone.ui;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.plaf.ColorUIResource;

import edu.dogfood.wordwarone.Settings;
import edu.dogfood.wordwarone.Settings.DiffConfig;
import edu.dogfood.wordwarone.data.LevelBank;
import edu.dogfood.wordwarone.database.EnglishWordsRepository;
import edu.dogfood.wordwarone.database.entry.Highscore;
import edu.dogfood.wordwarone.database.entry.SavedGame;
import edu.dogfood.wordwarone.interfaces.GameHandoffListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tristan L. Bonus
 */
public class GameProper extends JFrame {

    // Initialize logger
    private static Logger logger = Logger.getLogger(GameProper.class.getName());

    // Initialize GameHandoffListener
    private GameHandoffListener gameHandoffListener = null;

    public void setGameHandoffListener(GameHandoffListener listener) {
        this.gameHandoffListener = listener;
    }

    private class WordListModel extends AbstractListModel<String> {
        List<String> words = Arrays.asList();

        public void clearWords() {
            words.clear();
            fireContentsChanged(this, 0, 0);
        }

        public void setWords(List<String> words) {
            this.words = words;
            fireContentsChanged(this, 0, words.size());
        }

        @Override
        public int getSize() {
            return words.size();
        }

        @Override
        public String getElementAt(int index) {
            return words.get(index);
        }
    }

    private final String playerName;
    private Integer diffOverride = null;

    private LevelBank levelBank;
    private SavedGame sg;

    private Map<String, Boolean> mainWords = new HashMap<>();
    private List<String> secondaryWords;
    private List<String> secondaryEnteredWords = new ArrayList<>();

    private WordListModel wlm;

    private int livesVal;
    private int scoreVal;
    private int roundVal = 0;

    private int wordsEntered = 0;

    public JButton homeButton;
    private JList<String> jList1;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTextField jTextField_currWord;
    private JLabel jumbledword;
    private JLabel livesLabel;
    private JLabel lives_var;
    private JLabel round;
    private JLabel score;
    private JLabel score_var;
    private JSeparator separator;
    private JButton submitButton;
    private JLabel wordsleft;
    private JLabel wordsleft_var;

    /**
     * Creates new form GameProper
     */
    public GameProper(SavedGame sg) {
        this.sg = sg;
        playerName = sg.getPlayerName();

        wlm = new WordListModel();

        initComponents();
        setLocationRelativeTo(null); // centers window

        // Log sg
        logger.log(Level.INFO, "sg: {0}", sg);

        prepareGame();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                prompt();               
            }
        });
    }

    private void initComponents() {

        jPanel1 = new JPanel();
        jumbledword = new JLabel();
        round = new JLabel();
        lives_var = new JLabel();
        livesLabel = new JLabel();
        wordsleft = new JLabel();
        submitButton = new JButton();
        separator = new JSeparator();
        homeButton = new JButton();
        jTextField_currWord = new JTextField();
        wordsleft_var = new JLabel();
        jScrollPane1 = new JScrollPane();
        jList1 = new JList<>();
        score = new JLabel();
        score_var = new JLabel();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setToolTipText("");
        jPanel1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        jumbledword.setFont(new Font("Segoe UI", 0, 72)); // NOI18N
        jumbledword.setForeground(new Color(0, 153, 153));
        jumbledword.setHorizontalAlignment(SwingConstants.CENTER);
        jumbledword.setText("ABCDEFRANDOMWORD");
        jumbledword.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        round.setFont(new Font("Impact", 0, 62)); // NOI18N
        round.setHorizontalAlignment(SwingConstants.CENTER);
        round.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        lives_var.setFont(new Font("Impact", 0, 24)); // NOI18N
        lives_var.setForeground(new Color(204, 0, 0));
        lives_var.setHorizontalAlignment(SwingConstants.RIGHT);
        lives_var.setText("5");

        livesLabel.setFont(new Font("Impact", 0, 24)); // NOI18N
        livesLabel.setText("LIVES LEFT");

        wordsleft.setFont(new Font("Impact", 0, 24)); // NOI18N
        wordsleft.setText("WORDS LEFT: ");

        submitButton.setBackground(new Color(0, 0, 0));
        submitButton.setFont(new Font("Segoe Print", 0, 24)); // NOI18N
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setText("SUBMIT");
        submitButton.setBorder(null);
        submitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        homeButton.setBackground(new Color(0, 0, 0));
        homeButton.setForeground(new Color(255, 255, 255));
        homeButton.setText("🏠");
        homeButton.setBorder(null);
        homeButton.setBorderPainted(false);
        homeButton.setMaximumSize(new Dimension(50, 50));
        homeButton.setMinimumSize(new Dimension(50, 50));
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                prompt();
            }
        });

        jTextField_currWord.setFont(new Font("Segoe UI", 2, 24)); // NOI18N
        jTextField_currWord.setForeground(new Color(153, 153, 153));
        jTextField_currWord.setHorizontalAlignment(JTextField.CENTER);
        jTextField_currWord.setText("Enter a word...");
        jTextField_currWord.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(204, 204, 204)));

        jTextField_currWord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        jTextField_currWord.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (jTextField_currWord.getText().equalsIgnoreCase("Enter a word...")) {
                    jTextField_currWord.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextField_currWord.getText().equals("")) {
                    jTextField_currWord.setText("Enter a word...");
                }
            }

        });

        wordsleft_var.setFont(new Font("Impact", 0, 24)); // NOI18N
        wordsleft_var.setForeground(new Color(204, 0, 0));

        jList1.setModel(wlm);

        jList1.setCellRenderer(new ListCellRenderer<String>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JPanel panel = new JPanel(new GridLayout(0, 1, 0, 0));
                panel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

                panel.setBackground(
                        (index >= mainWords.size() ? new ColorUIResource(0xb44300) : new ColorUIResource(0xf3a600)));

                JLabel label = new JLabel(value + (index >= mainWords.size() ? "*" : ""));
                label.setFont(new Font("Segoe UI", 2, 16));
                panel.add(label);

                return panel;
            }
        });

        jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList1.setLayoutOrientation(JList.VERTICAL_WRAP);
        jScrollPane1.setViewportView(jList1);
        jScrollPane1.setMaximumSize(new Dimension(933, 166));

        score.setFont(new Font("Impact", 0, 24)); // NOI18N
        score.setText("SCORE");

        score_var.setFont(new Font("Impact", 0, 24)); // NOI18N
        score_var.setForeground(new Color(204, 0, 0));
        score_var.setText("0");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(homeButton, GroupLayout.PREFERRED_SIZE, 40,
                                                        GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(41, 41, 41)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(wordsleft, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(score, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jScrollPane1)
                                                        .addComponent(separator, GroupLayout.Alignment.LEADING)
                                                        .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout
                                                                .createSequentialGroup()
                                                                .addComponent(jTextField_currWord,
                                                                        GroupLayout.PREFERRED_SIZE, 729,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(submitButton, GroupLayout.DEFAULT_SIZE,
                                                                        194, Short.MAX_VALUE))
                                                        .addComponent(jumbledword, GroupLayout.Alignment.LEADING,
                                                                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE))
                                                .addGap(0, 175, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(score_var, GroupLayout.PREFERRED_SIZE, 67,
                                                                GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(wordsleft_var, GroupLayout.PREFERRED_SIZE, 67,
                                                                GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lives_var, GroupLayout.PREFERRED_SIZE, 42,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(livesLabel, GroupLayout.PREFERRED_SIZE, 100,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addGap(36, 36, 36))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(375, 375, 375)
                                .addComponent(round, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(homeButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(round)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jumbledword)
                                .addGap(18, 18, 18)
                                .addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(submitButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(jTextField_currWord, GroupLayout.PREFERRED_SIZE, 57,
                                                GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(score_var, GroupLayout.PREFERRED_SIZE, 46,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(score, GroupLayout.PREFERRED_SIZE, 46,
                                                GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(wordsleft, GroupLayout.PREFERRED_SIZE, 46,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lives_var, GroupLayout.PREFERRED_SIZE, 46,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(wordsleft_var, GroupLayout.PREFERRED_SIZE, 46,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(livesLabel, GroupLayout.PREFERRED_SIZE, 46,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(ActionEvent evt) {// GEN-FIRST:event_submitButtonActionPerformed
        String word = jTextField_currWord.getText().toLowerCase();
        jTextField_currWord.setText("");

        float MAIN_WORD_MULTIPLIER = 1.5f;

        if (word.isEmpty() || word.equalsIgnoreCase("Enter a word...")) {
            JOptionPane.showMessageDialog(this, "Enter a word.", "Blank Submission", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (mainWords.containsKey(word)) {
            // Check if the user submitted the word before
            if (mainWords.get(word)) {
                JOptionPane.showMessageDialog(this, "You already submitted this word!", "Duplicate Submission",
                        JOptionPane.ERROR_MESSAGE);
                nextRoundPrep();
                return;
            } else {
                mainWords.put(word, true);
                scoreVal += EnglishWordsRepository.getScore(word) * MAIN_WORD_MULTIPLIER;
                score_var.setText(String.valueOf(scoreVal));
                wordsleft_var.setText(String.valueOf(mainWords.size() - ++wordsEntered));
                updateWords();
                nextRoundPrep();
                return;
            }
        }

        if (secondaryWords.contains(word)) {
            // Check if the user submitted the word before
            if (secondaryEnteredWords.contains(word)) {
                JOptionPane.showMessageDialog(this, "You already submitted this word!", "Duplicate Submission",
                        JOptionPane.ERROR_MESSAGE);
                nextRoundPrep();
                return;
            } else {
                secondaryEnteredWords.add(word);
                scoreVal += EnglishWordsRepository.getScore(word);
                score_var.setText(String.valueOf(scoreVal));
                wordsleft_var.setText(String.valueOf(mainWords.size() - ++wordsEntered));
                updateWords();
                nextRoundPrep();
                return;
            }
        }

        // At this point, the word is incorrect. Check if the word contains all the
        // letters in the jumbled word
        if (Pattern.matches("^[" + levelBank.getRandomWordShuffled().toLowerCase() + "]+$", word.toLowerCase())) {
            JOptionPane.showMessageDialog(this,
                    "The word you submitted does not contain all the letters in the jumbled word!",
                    "Incorrect Submission", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Word not found in local dictionary!", "Incorrect Submission",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Not in the list. Reduce health.
        lives_var.setText(String.valueOf(--livesVal));

        // Check if the user has lost
        if (livesVal <= 0) {
            JOptionPane.showMessageDialog(this, "You have lost!", "Game Over", JOptionPane.ERROR_MESSAGE);

            Highscore hs = new Highscore(sg.getPlayerName(), new Date(), scoreVal, sg.getDifficulty());
            if (gameHandoffListener != null)
                gameHandoffListener.onForfeit(hs);
        }

    }// GEN-LAST:event_submitButtonActionPerformed

    private void nextRoundPrep() {
        // Check if ready to go to the next "round"
        if (mainWords.size() == wordsEntered) {
            // Show a message
            JOptionPane.showMessageDialog(this, "You have completed the round!\n"
                    + "You will now be shown the next round of words.", "Round Complete",
                    JOptionPane.INFORMATION_MESSAGE);

            // Create new saved game
            sg = new SavedGame(playerName, roundVal + 1, scoreVal);
            prepareGame();
        }
    }

    private void updateWords() {

        List<String> outputWords = new ArrayList<>();

        for (Map.Entry<String, Boolean> entry : mainWords.entrySet()) {
            if (entry.getValue()) {
                outputWords.add(entry.getKey());
            } else {
                String blank = "";
                for (int i = 0; i < entry.getKey().length(); i++) {
                    blank += "_ ";
                }

                outputWords.add(blank);
            }
        }

        for (String word : secondaryEnteredWords) {
            outputWords.add(word);
        }

        wlm.setWords(outputWords);
    }

    private void prepareGame() {

        mainWords = new HashMap<>();
        secondaryWords = new ArrayList<>();
        secondaryEnteredWords = new ArrayList<>();

        // Check if ID is not negative
        if (sg.getId() < 0) {
            // A negative number means a new game. Create new instance of levelBank.
            // This is also called when the user is navigating to the next round.

            // Take note of the current difficulty - is the saved game different to the current settings?
            // If so, override the difficulty.
            levelBank = new LevelBank(diffOverride != null ? diffOverride : Settings.getInstance().getDifficulty());

            // Check if the difficulty is easy/hard, then set the health value to the appropriate value
            // 5 for easy, 3 for hard
            livesVal = (diffOverride == DiffConfig.DIFFICULTY_EASY ? 5 : 3);
            wordsEntered = 0;
        } else {
            // Otherwise, load the levelBank from the database
            diffOverride = sg.getDifficulty();
            levelBank = new LevelBank(diffOverride, sg.getSeed());

            livesVal = sg.getHealth();

            // Verify if the random word is the same in the database
            if (!levelBank.getRandomWord().equals(sg.getBaseWord())) {
                logger.log(Level.WARNING, "Random word in database does not match the one in the saved game!");
            }
        }

        scoreVal = sg.getScore();
        roundVal = sg.getRound();

        wlm.clearWords();

        // Get all main words
        levelBank.getCommonWords().forEach(word -> mainWords.put(word, false));

        // log
        logger.log(Level.INFO, "Main words: {0}", mainWords.keySet());

        // Get all secondary words
        secondaryWords = new ArrayList<>(levelBank.getBonusWords());

        if (sg.getId() >= 0) {
            for (String word : sg.getSubmittedWords()) {
                // Log word
                logger.log(Level.INFO, "Word: {0}", word);

                if (mainWords.containsKey(word)) {
                    mainWords.put(word, true);
                    logger.log(Level.INFO, "Main Word already submitted: {0}", word);
                } else {
                    secondaryEnteredWords.add(word);
                    logger.log(Level.INFO, "Bonus Word submitted: {0}", word);
                }

                wordsEntered++;
            }
        }

        lives_var.setText(livesVal + "");
        score_var.setText(scoreVal + "");
        wordsleft_var.setText(mainWords.size() - wordsEntered + "");
        round.setText("Round " + (roundVal));
        jumbledword.setText(levelBank.getRandomWordShuffled());

        updateWords();
    }

    private void prompt() {
        // Ask user if they want to save game, end game, or cancel
        int result = JOptionPane.showConfirmDialog(null,
                "Do you want to save your game?",
                "Save Game?",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            // Get all submitted words
            List<String> submitted = new ArrayList<>();

            for (String s : mainWords.keySet()) {
                if (mainWords.get(s)) {
                    submitted.add(s);
                }
            }

            submitted.addAll(secondaryEnteredWords);

            // Create new SavedGame object
            SavedGame lsg = new SavedGame(sg.getId(),
                    sg.getPlayerName(),
                    (diffOverride != null ? diffOverride : Settings.getInstance().getDifficulty()),
                    livesVal,
                    roundVal,
                    scoreVal,
                    levelBank.getSeed(),
                    levelBank.getRandomWord(),
                    submitted,
                    new Date());

            // Call GameHandoffListener
            if (gameHandoffListener != null)
                gameHandoffListener.onSave(lsg);
        } else if (result == JOptionPane.NO_OPTION) {
            // Call GameHandoffListener

            Highscore hs = new Highscore(sg.getPlayerName(), new Date(), scoreVal, sg.getDifficulty());

            if (gameHandoffListener != null)
                gameHandoffListener.onForfeit(hs);
        }
    }
}
