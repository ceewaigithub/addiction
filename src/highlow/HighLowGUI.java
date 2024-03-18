package highlow;

import java.awt.*;
import java.util.List;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;

public class HighLowGUI {
    private JFrame frame;
    private JPanel gamePanel, buttonPanel, topPanel, bottomPanel, centerPanel, row1, row2, row3;
    private JLabel topLabel, bottomLabel, messageLabel;
    private JButton higherButton, lowerButton, exitButton, nextGameButton;
    private List<Player> players;

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
        // topPanel.setBackground(Color.GREEN);
        topLabel = new JLabel("HIGHER OR LOWER?");
        topLabel.setForeground(Color.WHITE);
        topLabel.setFont(new Font("Arial", Font.BOLD, 25));
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(topLabel);

        // Bottom Panel
        bottomPanel = new JPanel();
        bottomPanel.setBackground(gamePanel.getBackground());
        bottomLabel = new JLabel("Player (You)");
        bottomLabel.setForeground(Color.WHITE);
        bottomLabel.setFont(new Font("Arial", Font.BOLD, 15));
        bottomPanel.add(bottomLabel);

        // Center Panel
        centerPanel = new JPanel(new GridLayout(3,1));
        centerPanel.setBackground(gamePanel.getBackground());
        // centerPanel.setBackground(Color.BLUE);

        // Center Panel row 1
        row1 = new JPanel(new BorderLayout());
        row2 = new JPanel();
        row3 = new JPanel();
        row1.setBackground(gamePanel.getBackground());
        row2.setBackground(gamePanel.getBackground());
        row3.setBackground(gamePanel.getBackground());

        row1.add(topLabel, BorderLayout.CENTER);
        // row1.setBackground(Color.BLACK);
        // row2.setBackground(Color.BLUE);
        // row3.setBackground(Color.RED);
        // row1.setLayout(new BorderLayout());
        

        // Add Top, Bottom and Center panels to the gamePanel
        gamePanel.add(topPanel, BorderLayout.NORTH);
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add messageLabel to the gamePanel
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        row3.add(messageLabel);

        centerPanel.add(row1);
        centerPanel.add(row2);
        centerPanel.add(row3);

        gamePanel.add(centerPanel, BorderLayout.CENTER);

        // Set up buttons and add to the buttonPanel
        higherButton = new JButton("Higher");
        higherButton.setFocusable(false);
        lowerButton = new JButton("Lower");
        lowerButton.setFocusable(false);
        exitButton = new JButton("Exit");
        exitButton.setFocusable(false);
        // nextGameButton = new JButton("Next Game");
        // nextGameButton.setFocusable(false);
        // nextGameButton.setVisible(false);

        // Add buttons to the buttonPanel
        buttonPanel.add(higherButton);
        buttonPanel.add(lowerButton);
        buttonPanel.add(exitButton);
        // buttonPanel.add(nextGameButton);

        // Add buttonPanel to the frame's SOUTH position
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add gamePanel to the frame
        frame.add(gamePanel);

        // Make frame visible
        frame.setVisible(true);
    }

    // To display card in GUI
    public void displayCard(Card card, JPanel panel) {
        Image image = card.getImage();
        JLabel label = new JLabel(new ImageIcon(image));
        label.setLayout(new BorderLayout());
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

    // public JButton getNextGameButton() {
    //     return nextGameButton;
    // }

    public JButton getExitButton() {
        return exitButton;
    }

    public JPanel getCenterPanel(){
        return centerPanel;
    }

    public JPanel getRow2(){
        return row2;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }
}
