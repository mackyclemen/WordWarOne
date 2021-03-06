package edu.dogfood.wordwarone.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.dogfood.wordwarone.database.SavedGameRepository;
import edu.dogfood.wordwarone.database.entry.SavedGame;
import edu.dogfood.wordwarone.interfaces.SavedGameSelectionListener;
/**
 *
 * @author janah
 */
public class LoadSavedGames extends JFrame {

    // Prepare logger
    private static Logger logger = Logger.getLogger(LoadSavedGames.class.getName());

    // Prepare Listener
    private SavedGameSelectionListener listener = null;

    public void setSavedGameSelectionListener(SavedGameSelectionListener listener) {
        this.listener = listener;
    }

    private class SavedListModel extends AbstractListModel<SavedGame> {
        SavedGameRepository sgr = SavedGameRepository.getInstance();
        private List<SavedGame> games = sgr.getSavedGames();

        @Override
        public int getSize() {
            return games.size();
        }

        @Override
        public SavedGame getElementAt(int index) {
            return games.get(index);
        }

        public void removeElement(int index) {
            // Get object to remove
            SavedGame game = games.get(index);

            // Remove game from database
            sgr.deleteSavedGame(game.getId());

            // Refresh list
            games = sgr.getSavedGames();

            // Update list
            fireContentsChanged(this, 0, games.size());
        }

        public void update() {
            // Refresh list
            games = sgr.getSavedGames();

            // Update list
            fireContentsChanged(this, 0, games.size());
        }
    }

    private SavedListModel savedGamesModel = new SavedListModel();

    public void updateSaves() {
        savedGamesModel.update();
    }

    public JButton homeButton;
    private JButton deleteBtn;
    private JButton selectBtn;
    private JLabel jLabel_pageTitle;
    private JList<SavedGame> jList1;
    private JPanel jPanel_background;
    private JScrollPane jScrollPane1;

    /**
     * Creates new form loadSavedGames
     */
    public LoadSavedGames() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {

        jPanel_background = new JPanel();
        jLabel_pageTitle = new JLabel();
        homeButton = new JButton();
        jScrollPane1 = new JScrollPane();
        jList1 = new JList<>();
        deleteBtn = new JButton();
        selectBtn = new JButton();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("World War I: Jumble Rumble");
        setResizable(false);

        jPanel_background.setBackground(new Color(255, 255, 255));
        jPanel_background.setPreferredSize(new Dimension(1280, 720));

        jLabel_pageTitle.setFont(new Font("Impact", 0, 62)); // NOI18N
        jLabel_pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_pageTitle.setText("Saved Games");

        homeButton.setBackground(new Color(0, 0, 0));
        homeButton.setForeground(new Color(255, 255, 255));
        homeButton.setText("????");
        homeButton.setBorder(null);
        homeButton.setBorderPainted(false);
        homeButton.setMaximumSize(new Dimension(50, 50));
        homeButton.setMinimumSize(new Dimension(50, 50));

        jList1.setModel(savedGamesModel);

        jList1.setCellRenderer(new ListCellRenderer<SavedGame>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends SavedGame> list, SavedGame value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                SavedGame sg = savedGamesModel.getElementAt(index);

                JPanel panel = new JPanel(new GridLayout(0, 4));

                JLabel jLabel1 = new JLabel();
                JLabel jLabel2 = new JLabel();
                JLabel jLabel3 = new JLabel();
                JLabel jLabel4 = new JLabel();

                
                // Create date string from format;
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String date = sdf.format(sg.getDate());
                jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
                jLabel1.setText(date);
                panel.add(jLabel1);
        
                jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
                jLabel2.setText(sg.getPlayerName());
                panel.add(jLabel2);
        
                jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
                jLabel3.setText("Lives: " + sg.getHealth() + ", Round: " + sg.getRound());
                panel.add(jLabel3);
        
                jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
                jLabel4.setText("Difficulty: " + (sg.getDifficulty() == 0 ? "Easy" : "Hard"));
                panel.add(jLabel4);

                panel.setBorder(
                    new CompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0x646464)),
                        BorderFactory.createEmptyBorder(12, 12, 12, 12)
                    )
                );

                if(cellHasFocus || isSelected) {
                    panel.setBackground(new Color(0x81c1d2));
                    panel.setForeground(new Color(0xa5a5a5));
                } else {
                    panel.setBackground(new Color(0xa5a5a5));
                    panel.setForeground(new Color(0x81c1d2));
                }

                return panel;
            }
        });

        jList1.setLayoutOrientation(JList.VERTICAL);

        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    deleteBtn.setEnabled(true);
                    selectBtn.setEnabled(true);

                    logger.log(Level.INFO, "Selected game: " + jList1.getSelectedValue());
                }
            }
        });

        jScrollPane1.setViewportView(jList1);

        deleteBtn.setForeground(new Color(255, 0, 51));
        deleteBtn.setText("Delete");
        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        selectBtn.setText("Select");
        selectBtn.setEnabled(false);
        selectBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                selectBtnActionPerformed(evt);
            }
        });

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
                        .addGap(37, 37, 37)
                        .addGroup(jPanel_backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 1229, GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_backgroundLayout.createSequentialGroup()
                                .addComponent(selectBtn, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(deleteBtn, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel_backgroundLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel_pageTitle)
                .addGap(473, 473, 473))
        );
        jPanel_backgroundLayout.setVerticalGroup(
            jPanel_backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(homeButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jLabel_pageTitle)
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel_backgroundLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteBtn, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectBtn, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_background, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1292, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel_background, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteBtnActionPerformed(ActionEvent evt) {
        // Prompt user
        SavedGame game = (SavedGame) jList1.getSelectedValue();
        String message = "Are you sure you want to delete this game? This action is irreversible!\nGame from: " + new SimpleDateFormat("MM/dd/YYY").format(game.getDate()) + "\nPlayer name: " + game.getPlayerName();

        int result = JOptionPane.showConfirmDialog(this, message, "Delete game", JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION) {
            savedGamesModel.removeElement(jList1.getSelectedIndex());
        }
    }

    private void selectBtnActionPerformed(ActionEvent evt) {
        // Prompt user
        SavedGame game = (SavedGame) jList1.getSelectedValue();
        String message = "Are you sure you want to load this game?\nGame from: " + new SimpleDateFormat("MM/dd/YYY").format(game.getDate()) + "\nPlayer name: " + game.getPlayerName();

        int result = JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION) {
            if(listener != null) listener.onSavedGameSelected(game);
        }
    }
}
