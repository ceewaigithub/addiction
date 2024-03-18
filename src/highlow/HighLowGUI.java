package highlow;

import java.awt.*;
// import java.util.Scanner;
import javax.swing.*;

public class HighLowGUI {
    private JFrame frame;
    private JPanel gamePanel, buttonPanel, topPanel, bottomPanel;
    private JLabel topLabel, bottomLabel, messageLabel;
    private JButton higherButton, lowerButton, exitButton;

    public HighLowGUI() {
        frame = new JFrame("High Low");
        gamePanel = new JPanel();
        buttonPanel = new JPanel();

        // Set up frame
        int boardWidth = 800;
        int boardHeight = 540;
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up gamePanel
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(0, 116, 3));

        // Top Panel
        topPanel = new JPanel();
        topPanel.setBackground(gamePanel.getBackground());
        topLabel = new JLabel("HIGHER OR LOWER?");
        topLabel.setForeground(Color.WHITE);
        topLabel.setFont(new Font("Arial", Font.BOLD, 15));
        topPanel.add(topLabel);

        // Bottom Panel
        bottomPanel = new JPanel();
        bottomPanel.setBackground(gamePanel.getBackground());
        bottomLabel = new JLabel("Player (You)");
        bottomLabel.setForeground(Color.WHITE);
        bottomLabel.setFont(new Font("Arial", Font.BOLD, 15));
        bottomPanel.add(bottomLabel);

        // Add Top and Bottom panels to the gamePanel
        gamePanel.add(topPanel, BorderLayout.NORTH);
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add messageLabel to the gamePanel
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gamePanel.add(messageLabel, BorderLayout.CENTER);

        // Set up buttons and add to the buttonPanel
        higherButton = new JButton("Higher");
        higherButton.setFocusable(false);
        lowerButton = new JButton("Lower");
        lowerButton.setFocusable(false);
        exitButton = new JButton("Exit");
        exitButton.setFocusable(false);

        // Add buttons to the buttonPanel
        buttonPanel.add(higherButton);
        buttonPanel.add(lowerButton);
        buttonPanel.add(exitButton);

        // Add buttonPanel to the frame's SOUTH position
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add gamePanel to the frame
        frame.add(gamePanel);

        // Make frame visible
        frame.setVisible(true);
    }

    // To display card in GUI
    public void displayCard(Card card, JPanel panel) {
        // ImageIcon icon = new ImageIcon(card.getImagePath());
        // JLabel label = new JLabel(icon);
        Image image = card.getImage();
        JLabel label = new JLabel(new ImageIcon(image));
        panel.removeAll();
        panel.add(label);
        panel.revalidate();
        panel.repaint();
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }

    public JButton getHigherButton() {
        return higherButton;
    }

    public JButton getLowerButton() {
        return lowerButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }
}
