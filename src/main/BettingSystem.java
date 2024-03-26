package main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import entity.User;

public class BettingSystem {
    private final String[] chipPaths = { "1", "2", "5", "10", "50" };
    private JPanel bettingPanel, textPanel, nestedPanel;
    private JButton[] chipButtons;
    private JButton placeBetButton, clearBetButton, allInButton;
    private JLabel betLabel, balanceLabel;
    private int width = 64;
    private int height = 64;
    private int currentBet, tempBalance;
    private User user;

    public BettingSystem(User user) {
        this.user = user;
        tempBalance = user.getBalance();
        currentBet = 0;

        clearBetButton = new JButton("Clear bet"); //Clear bet button
        clearBetButton.setPreferredSize(new Dimension(120, 20));
        clearBetButton.addActionListener(e -> {
            resetBet();
        });
        allInButton = new JButton("ALL IN");
        allInButton.setPreferredSize(new Dimension(120, 20));
        allInButton.addActionListener(e -> {
            allInBet();
        });
        placeBetButton = new JButton("Place bet");//button to confirm bet
        placeBetButton.setPreferredSize(new Dimension(120, 20));
        bettingPanel = new JPanel(); //panel for chips
        bettingPanel.setBackground(new Color(0, 116, 3));
//        bettingPanel.setOpaque(false);
        bettingPanel.setLayout(new GridLayout(0, 5));

        chipButtons = new JButton[] { //chip buttons
                new JButton(),
                new JButton(),
                new JButton(),
                new JButton(),
                new JButton()
        };

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

        betLabel = new JLabel("Bet: " + currentBet);
        balanceLabel = new JLabel("Balance: " + tempBalance);

        textPanel = new JPanel();
        textPanel.add(balanceLabel);
        textPanel.add(betLabel);
        textPanel.add(placeBetButton);
        textPanel.add(allInButton);
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
        if (amount > 0 && amount <= tempBalance) {
            currentBet += amount;
            tempBalance -= amount;

        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void winBet(int multiplier) {
        user.addMoney(currentBet * (1 + multiplier));
        tempBalance = user.getBalance();
        currentBet = 0;
        updateBettingPanel();
    }
    public void loseBet() {
        tempBalance = user.getBalance();
        currentBet = 0;
        updateBettingPanel();
    }
    public void pushBet() {
        user.addMoney(currentBet);
        tempBalance = user.getBalance();
        currentBet = 0;
        updateBettingPanel();
        System.out.println("Push");
    }
    public void resetBet(){
        tempBalance += currentBet;
        currentBet = 0;
        updateBettingPanel();
        System.out.println("Cleared");
    }
    public void allInBet() {
        if (tempBalance > 0) {
            currentBet += tempBalance;
            tempBalance = 0;
            updateBettingPanel();
        } else {
            System.out.println("ALREADY ALL IN");
        }
    }
    public JPanel getBettingPanel() {
        return nestedPanel;
    }
    public void updateBettingPanel(){
        betLabel.setText("Bet: " + currentBet);
        balanceLabel.setText("Balance: " + tempBalance);
    }
    public void confirmBet() {
        user.setBalance(tempBalance);
    }
    public void checkBankrupt(){
        if(user.getBalance() == 0){
            user.setBalance(-1);
        }
    }
    public JButton getPlaceBetButton(){ return placeBetButton;}
}