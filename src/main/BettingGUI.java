package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BettingGUI {
    private final String[] chipPaths = { "1", "2", "5", "10", "50" };
    private JPanel bettingPanel;
    private JPanel textPanel;
    private JPanel nestedPanel;
    private JButton[] chipButtons;
    private JButton placeBetButton;
    private JLabel betLabel;
    private JButton clearBetButton;
    private JLabel balanceLabel;
    private BettingSystem bettingSystem;
    private int width = 64;
    private int height = 64;

    public BettingGUI() {
//        pass in betting system, player
//        or initialize betting system upon creation player
        bettingSystem = new BettingSystem(1000); // Set initial balance for betting system

        clearBetButton = new JButton("Clear bet"); //Clear bet button
        clearBetButton.setPreferredSize(new Dimension(120,20));
        clearBetButton.addActionListener(e->{
            bettingSystem.resetBet();
            balanceLabel.setText("Balance: " + bettingSystem.getPlayerBalance());
            betLabel.setText("Bet: " + bettingSystem.getPlayerBet());
        });

        placeBetButton = new JButton("Place bet");//button to confirm bet
        placeBetButton.setPreferredSize(new Dimension(120, 20));
        placeBetButton.addActionListener(e->{
            //
        });

        bettingPanel = new JPanel(); //panel for chips
        bettingPanel.setBackground(new Color(0, 116, 3));
        bettingPanel.setLayout(new GridLayout(0, 5));

        chipButtons = new JButton[] { //chip buttons
                new JButton(),
                new JButton(),
                new JButton(),
                new JButton(),
                new JButton()
        };

//        Border emptyborder = BorderFactory.createEmptyBorder();

        for (int i = 0; i < chipPaths.length; i++) { //adding image and actionlistener to button and to panel
            String imagePath = "chips/" + chipPaths[i] + ".png";
            System.out.println(imagePath);
            Image icon = new ImageIcon(imagePath).getImage();
            Image scaledImage = icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            chipButtons[i].setIcon(new ImageIcon(scaledImage));
            chipButtons[i].setPreferredSize(new Dimension(width + 10, height + 10));
            chipButtons[i].setOpaque(false);
            chipButtons[i].setBorder(null);
            chipButtons[i].setContentAreaFilled(false);
            chipButtons[i].setUI(new BasicButtonUI());
            chipButtons[i].setBorderPainted(false);
            int finalI = i;
            chipButtons[i].addActionListener(e -> {
                bettingSystem.placeBet(Integer.parseInt(chipPaths[finalI]));
                balanceLabel.setText("Balance: " + bettingSystem.getPlayerBalance());
                betLabel.setText("Bet: " + bettingSystem.getPlayerBet());
            });
            bettingPanel.add(chipButtons[i]);
        }

        betLabel = new JLabel("Bet: " + bettingSystem.getPlayerBet());
        balanceLabel = new JLabel("Balance: " + bettingSystem.getPlayerBalance());

        textPanel = new JPanel();
        textPanel.add(balanceLabel);
        textPanel.add(betLabel);
        textPanel.add(placeBetButton);
        textPanel.add(clearBetButton);

        nestedPanel = new JPanel(); //nested panel for formatting text and bet panel
        nestedPanel.setLayout(new BoxLayout(nestedPanel, BoxLayout.Y_AXIS));
        nestedPanel.add(bettingPanel);
        nestedPanel.add(textPanel);
    }

    public JPanel getBettingPanel() {
        return nestedPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BettingGUI();
            }
        });
    }
}
