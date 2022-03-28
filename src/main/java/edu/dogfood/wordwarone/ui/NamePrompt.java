package edu.dogfood.wordwarone.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import edu.dogfood.wordwarone.interfaces.SubmissionListener;

/**
 *
 * @author janah
 */
public class NamePrompt extends JFrame {

    // Initialize logger
    private static Logger logger = Logger.getLogger(NamePrompt.class.getName());
    
    private SubmissionListener listener = null;

    public JButton jButton_back;
    private JButton jButton_submitName;
    private JLabel jLabel_promptText;
    private JPanel jPanel_background;
    private JTextField jTextField_playerName;

    /**
     * Creates new form namePrompt
     */
    public NamePrompt() {
        initComponents();
        setLocationRelativeTo(null); //centers window
    }

    private void initComponents() {

        jPanel_background = new JPanel();
        jLabel_promptText = new JLabel();
        jTextField_playerName = new JTextField();
        jButton_back = new JButton();
        jButton_submitName = new JButton();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("World War I: Jumble Rumble");
        setResizable(false);

        jPanel_background.setBackground(new Color(255, 255, 255));
        jPanel_background.setPreferredSize(new Dimension(1280, 720));
        jPanel_background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel_promptText.setFont(new Font("Impact", 0, 62)); // NOI18N
        jLabel_promptText.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_promptText.setText("State your name, Soldier.");
        jPanel_background.add(jLabel_promptText, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, -1, -1));

        jTextField_playerName.setFont(new Font("Segoe UI", 2, 24)); // NOI18N
        jTextField_playerName.setForeground(new Color(153, 153, 153));
        jTextField_playerName.setHorizontalAlignment(JTextField.CENTER);
        jTextField_playerName.setText("Who art thou?");
        jTextField_playerName.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(204, 204, 204)));
        jTextField_playerName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTextField_playerName.getText().equals("Who art thou?")) {
                    jTextField_playerName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextField_playerName.getText().equals("")) {
                    jTextField_playerName.setText("Who art thou?");
                }
            }
        });

        jPanel_background.add(jTextField_playerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, 685, 57));

        jButton_back.setBackground(new Color(0, 0, 0));
        jButton_back.setFont(new Font("Segoe Print", 0, 18)); // NOI18N
        jButton_back.setForeground(new Color(255, 255, 255));
        jButton_back.setText("Go Back");
        jButton_back.setBorder(null);
        jButton_back.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        jPanel_background.add(jButton_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 630, 180, 50));

        jButton_submitName.setBackground(new Color(0, 0, 0));
        jButton_submitName.setFont(new Font("Segoe Print", 0, 18)); // NOI18N
        jButton_submitName.setForeground(new Color(255, 255, 255));
        jButton_submitName.setText("Join War");
        jButton_submitName.setBorder(null);
        jButton_submitName.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        jButton_submitName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton_submitActionPerformed(evt);
            }
        });
        jPanel_background.add(jButton_submitName, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 470, 340, 50));

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

    private void jButton_submitActionPerformed(ActionEvent evt) {
        // Log
        logger.info("NamePrompt: jButton_submitActionPerformed()");

        if(jTextField_playerName.getText().isEmpty() || jTextField_playerName.getText().equalsIgnoreCase("Who art thou?")){
            logger.info("NamePrompt: jButton_submitActionPerformed(): Name is empty");
            JOptionPane.showMessageDialog(this, "State your name.", "Player Name", JOptionPane.ERROR_MESSAGE);
        } else {
            if(jTextField_playerName.getText().equalsIgnoreCase("your name")) {
                JOptionPane.showMessageDialog(this, "\"Your Name\"? Really???", "Really...?", JOptionPane.INFORMATION_MESSAGE);
            }

            logger.info("NamePrompt: jButton_submitActionPerformed(): Submitting name...");
            if(listener != null) listener.onSubmit(jTextField_playerName.getText());
        }
    }

    public void setSubmissionListener(SubmissionListener listener) {
        this.listener = listener;
    }
}
