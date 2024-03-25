package main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import entity.User;

public class BettingSystem {
    private final String[] chipPaths = { "1", "2", "5", "10", "50" };
    private JPanel bettingPanel;
    private JPanel textPanel;
    private JPanel nestedPanel;
    private JButton[] chipButtons;
    private JButton placeBetButton;
    private JLabel betLabel;
    private JButton clearBetButton;
    private JLabel balanceLabel;
    private int width = 64;
    private int height = 64;
    private int currentBet;
    private User user;

    public BettingSystem(User user) {
        this.user = user;
        currentBet = 0;

        clearBetButton = new JButton("Clear bet"); //Clear bet button
        clearBetButton.setPreferredSize(new Dimension(120, 20));
        clearBetButton.addActionListener(e -> {
            resetBet();
            updateBettingPanel();
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
            String imagePath = "res/chips/" + chipPaths[i] + ".png";
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
                placeBet(Integer.parseInt(chipPaths[finalI]));
                updateBettingPanel();
            });
            bettingPanel.add(chipButtons[i]);
        }

        betLabel = new JLabel("Bet: " + getPlayerBet());
        balanceLabel = new JLabel("Balance: " + getPlayerBalance());

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

    public int getPlayerBalance() {
        return user.getBalance();
    }
    public int getPlayerBet(){
        return currentBet;
    }
    public void placeBet(int amount) {
        if (amount > 0 && amount <= user.getBalance()) {
            currentBet += amount;
            user.subtractMoney(amount);
//            playerBalance -= amount;
            System.out.println(currentBet);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void winBet(int multiplier) {
        user.addMoney(currentBet * (1 + multiplier));
        updateBettingPanel();
        currentBet = 0;
    }
    public void loseBet() {
        updateBettingPanel();
        currentBet = 0;
    }

    public void pushBet() {
        updateBettingPanel();
        currentBet = 0;
    }
    public void resetBet(){
        user.addMoney(currentBet); //clear be
        currentBet = 0;
        System.out.println("Cleared");
    }
    public JPanel getBettingPanel() {
        return nestedPanel;
    }
    public void updateBettingPanel(){
        betLabel.setText("Bet: " + getPlayerBet());
        balanceLabel.setText("Balance: " + getPlayerBalance());
    }
    public JButton getPlaceBetButton(){ return placeBetButton;}
}