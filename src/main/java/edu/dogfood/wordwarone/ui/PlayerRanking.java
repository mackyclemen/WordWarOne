package edu.dogfood.wordwarone.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;

import edu.dogfood.wordwarone.Settings.DiffConfig;
import edu.dogfood.wordwarone.database.HighScoreRepository;
import edu.dogfood.wordwarone.database.entry.Highscore;

/**
 *
 * @author janah
 */
public class PlayerRanking extends JFrame {

    // logger
    private static final Logger logger = Logger.getLogger(PlayerRanking.class.getName());

    private final HighScoreRepository hsRepo = HighScoreRepository.getInstance();

    private class HighscoreModel extends AbstractListModel<Highscore> {
        List<Highscore> highscores;

        public HighscoreModel(List<Highscore> highscores) {
            this.highscores = highscores;
        }

        @Override
        public int getSize() {
            // TODO Auto-generated method stub
            return highscores.size();
        }

        @Override
        public Highscore getElementAt(int index) {
            // TODO Auto-generated method stub
            return highscores.get(index);
        }

        public List<Highscore> getHighscores() {
            return highscores;
        }
    }

    private class HighscoreRenderer implements ListCellRenderer<Highscore> {
        final int[] colors = {0xc9a310, 0x262626, 0x944511};

        @Override
        public Component getListCellRendererComponent(JList<? extends Highscore> list, Highscore value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JPanel panel = new JPanel(new GridLayout(0, 4));

            JLabel jLabel1 = new JLabel();
            JLabel jLabel2 = new JLabel();
            JLabel jLabel3 = new JLabel();
            JLabel jLabel4 = new JLabel();

            jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel1.setText("Rank: " + (index + 1));
            jLabel1.setForeground(new Color(index < 3 ? colors[index] : 0x000000));

            // Set jlabel font style to bold
            jLabel1.setFont(new Font("Tahoma", Font.BOLD, 12));
            panel.add(jLabel1);
            
            jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel2.setText(value.getName());
            panel.add(jLabel2);
            
            // Create date string from format;
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String date = sdf.format(value.getDate());
            jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel3.setText(date);
            panel.add(jLabel3);
    
            jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel4.setText("Score: " + value.getScore());
            panel.add(jLabel4);

            panel.setBorder(
                new CompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0x646464)),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)
                )
            );

            panel.setBackground(new Color(0xbfffff));
            panel.setForeground(new Color(0xe3efe7));

            return panel;
        }
    }

    HighscoreModel easyModel;
    HighscoreModel hardModel;

    public JButton homeButton;
    private JLabel jLabel_promptText;
    private JList<Highscore> jList1;
    private JList<Highscore> jList2;
    private JPanel jPanel_background;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTabbedPane jTabbedPane1;

    /**
     * Creates new form playerRanking
     */
    public PlayerRanking() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                setModels();
            }
        });

        setModels();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        jPanel_background = new JPanel();
        jLabel_promptText = new JLabel();
        homeButton = new JButton();
        jTabbedPane1 = new JTabbedPane();
        jScrollPane1 = new JScrollPane();
        jList1 = new JList<Highscore>();
        jScrollPane2 = new JScrollPane();
        jList2 = new JList<Highscore>();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel_background.setBackground(new Color(255, 255, 255));
        jPanel_background.setPreferredSize(new Dimension(1280, 720));

        jLabel_promptText.setFont(new Font("Impact", 0, 62)); // NOI18N
        jLabel_promptText.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_promptText.setText("PLAYER RANKING RECORDS");

        homeButton.setBackground(new Color(0, 0, 0));
        homeButton.setForeground(new Color(255, 255, 255));
        homeButton.setText("????");
        homeButton.setBorder(null);
        homeButton.setBorderPainted(false);
        homeButton.setMaximumSize(new Dimension(50, 50));
        homeButton.setMinimumSize(new Dimension(50, 50));

        jList1.setModel(easyModel);
        jList1.setLayoutOrientation(JList.VERTICAL);
        jList1.setCellRenderer(new HighscoreRenderer());
        jScrollPane1.setViewportView(jList1);

        jTabbedPane1.addTab("Easy Difficulty", jScrollPane1);

        jList2.setModel(hardModel);
        jList2.setLayoutOrientation(JList.VERTICAL);
        jList2.setCellRenderer(new HighscoreRenderer());
        jScrollPane2.setViewportView(jList2);

        jTabbedPane1.addTab("Hard Difficulty", jScrollPane2);

        GroupLayout jPanel_backgroundLayout = new GroupLayout(jPanel_background);
        jPanel_background.setLayout(jPanel_backgroundLayout);
        jPanel_backgroundLayout.setHorizontalGroup(
            jPanel_backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_backgroundLayout.createSequentialGroup()
                .addGroup(jPanel_backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_backgroundLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(homeButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_backgroundLayout.createSequentialGroup()
                        .addGap(320, 320, 320)
                        .addComponent(jLabel_promptText))
                    .addGroup(jPanel_backgroundLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, 1217, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel_backgroundLayout.setVerticalGroup(
            jPanel_backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(homeButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel_promptText)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, 538, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_background, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_background, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }

    private void setModels() {
        easyModel = new HighscoreModel(
            hsRepo.getHighScores().stream()
            .filter(predicate -> predicate.getDifficulty() == DiffConfig.DIFFICULTY_EASY)
            .collect(Collectors.toList()));
        
        hardModel = new HighscoreModel(
            hsRepo.getHighScores().stream()
            .filter(predicate -> predicate.getDifficulty() == DiffConfig.DIFFICULTY_HARD)
            .collect(Collectors.toList()));
        
        // log
        logger.info("easyModel size: " + easyModel.getHighscores().size());
        logger.info("hardModel size: " + hardModel.getHighscores().size());

        // Only set models if the lists are not null
        if (jList1 != null && jList2 != null) {
            jList1.setModel(easyModel);
            jList2.setModel(hardModel);
        }
    }
}
