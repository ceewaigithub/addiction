package card;

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
    private int chipDimension = 64, buttonWidth = 120, buttonHeight = 20;
    private int currentBet, tempBalance;
    private User user;

    public BettingSystem(User user) {
        this.user = user;
        tempBalance = user.getBalance();
        currentBet = 0;

        clearBetButton = new JButton("Clear bet");
        clearBetButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        clearBetButton.addActionListener(e -> resetBet());

        allInButton = new JButton("ALL IN");
        allInButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        allInButton.addActionListener(e -> allInBet());

        placeBetButton = new JButton("Place bet");
        placeBetButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        bettingPanel = new JPanel();
        bettingPanel.setBackground(new Color(0, 116, 3));
        bettingPanel.setLayout(new GridLayout(0, 5));

        chipButtons = new JButton[chipPaths.length];
        for (int i = 0; i < chipPaths.length; i++) {
            String imagePath = "res/chips/" + chipPaths[i] + ".png";
            ImageIcon icon = new ImageIcon(imagePath);
            icon = new ImageIcon(icon.getImage().getScaledInstance(chipDimension, chipDimension, Image.SCALE_SMOOTH));
            chipButtons[i] = new JButton(icon);
            chipButtons[i].setPreferredSize(new Dimension(chipDimension + 10, chipDimension + 10));
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

        nestedPanel = new JPanel();
        nestedPanel.setLayout(new BoxLayout(nestedPanel, BoxLayout.Y_AXIS));
        nestedPanel.add(bettingPanel);
        nestedPanel.add(textPanel);
    }

    public int getPlayerBalance() {
        return user.getBalance();
    }

    public int getPlayerBet() {
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

    public void resetBet() {
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

    public void updateBettingPanel() {
        betLabel.setText("Bet: " + currentBet);
        balanceLabel.setText("Balance: " + tempBalance);
    }

    public void confirmBet() {
        user.setBalance(tempBalance);
    }

    public void checkBankrupt() {
        if (user.getBalance() == 0) {
            user.setBalance(-1);
        }
    }

    public JButton getPlaceBetButton() {
        return placeBetButton;
    }
}