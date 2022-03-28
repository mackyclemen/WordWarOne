package edu.dogfood.wordwarone.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author Tristan L. Bonus
 */
public class Menu extends JFrame {

    // Logger
    private static Logger logger = Logger.getLogger(Menu.class.getName());

    public JButton exitButton;
    private JLabel heading;
    private JLabel jLabel1;
    private JPanel jPanel1;
    public JButton loadgameButton;
    public JButton newgameButton;
    public JButton rankingButton;
    public JButton settingsButton;
    private JLabel subtitle;

    /**
     * Creates new form menu
     */
    public Menu() {
        initComponents();

        setLocationRelativeTo(null); //centers window
    }

    private void initComponents() {

        jPanel1 = new JPanel();
        exitButton = new JButton();
        rankingButton = new JButton();
        loadgameButton = new JButton();
        settingsButton = new JButton();
        newgameButton = new JButton();
        subtitle = new JLabel();
        heading = new JLabel();
        jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Word War I: Jumble Rumble");
        setBackground(new Color(204, 255, 204));
        setForeground(new Color(204, 255, 204));
        setResizable(false);

        jPanel1.setBackground(new Color(255, 255, 255));

        exitButton.setBackground(new Color(0, 0, 0));
        exitButton.setFont(new Font("Segoe Print", 0, 24)); // NOI18N
        exitButton.setForeground(new Color(255, 255, 255));
        exitButton.setText("Exit");
        exitButton.setBorder(null);

        rankingButton.setBackground(new Color(0, 0, 0));
        rankingButton.setFont(new Font("Segoe Print", 0, 24)); // NOI18N
        rankingButton.setForeground(new Color(255, 255, 255));
        rankingButton.setText("Ranking");
        rankingButton.setBorder(null);

        loadgameButton.setBackground(new Color(0, 0, 0));
        loadgameButton.setFont(new Font("Segoe Print", 0, 24)); // NOI18N
        loadgameButton.setForeground(new Color(255, 255, 255));
        loadgameButton.setText("Load Game");
        loadgameButton.setBorder(null);

        settingsButton.setBackground(new Color(0, 0, 0));
        settingsButton.setFont(new Font("Segoe Print", 0, 24)); // NOI18N
        settingsButton.setForeground(new Color(255, 255, 255));
        settingsButton.setText("Settings");
        settingsButton.setAutoscrolls(true);
        settingsButton.setBorder(null);

        newgameButton.setBackground(new Color(0, 0, 0));
        newgameButton.setFont(new Font("Segoe Print", 0, 24)); // NOI18N
        newgameButton.setForeground(new Color(255, 255, 255));
        newgameButton.setText("New Game");
        newgameButton.setBorder(null);

        subtitle.setFont(new Font("Impact", 0, 48)); // NOI18N
        subtitle.setText("JUMBLE RUMBLE");

        heading.setFont(new Font("Impact", 0, 36)); // NOI18N
        heading.setText("WORD WAR I: ");

        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            jLabel1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("logo.png")));
        } catch(Exception e) {
            logger.warning("Could not load logo.png");
        }

        // jLabel1.setText("LOGO");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(heading)
                            .addComponent(subtitle)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE)))
                .addGap(80, 80, 80)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 623, GroupLayout.PREFERRED_SIZE)
                    .addComponent(rankingButton, GroupLayout.PREFERRED_SIZE, 623, GroupLayout.PREFERRED_SIZE)
                    .addComponent(settingsButton, GroupLayout.PREFERRED_SIZE, 623, GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadgameButton, GroupLayout.PREFERRED_SIZE, 623, GroupLayout.PREFERRED_SIZE)
                    .addComponent(newgameButton, GroupLayout.PREFERRED_SIZE, 623, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addComponent(newgameButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(loadgameButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(settingsButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(rankingButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(heading)
                .addGap(18, 18, 18)
                .addComponent(subtitle)
                .addGap(33, 33, 33)
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    }
}
