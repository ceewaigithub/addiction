import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.util.random.*;
import javax.swing.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        BlackJack blackJack = new BlackJack();
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");

        blackJack.addPlayer(player);
        blackJack.addPlayer(dealer);

        blackJack.startGame();

        // Set up GUI
        int boardWidth = 800;
        int boardHeight = boardWidth;

        int cardWidth = 73;
        int cardHeight = 97;

        JFrame frame = new JFrame("BlackJack");
        JPanel gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                try {
                    // Draw dealer's hand
                    for (int i = 0; i < dealer.getHand().size(); i++) {
                        //Image cardImg = new ImageIcon(getClass().getResource(dealer.getHand().get(i).getImagePath())).getImage();
                        Image hiddenCardImg = new ImageIcon(getClass().getResource("./cards/b.gif")).getImage();
                        g.drawImage(hiddenCardImg, 20 + (i * 100), 20, cardWidth, cardHeight, null);
                    }

                    // Draw player's hand
                    for (int i = 0; i < player.getHand().size(); i++) {
                        Image cardImg = new ImageIcon(getClass().getResource(player.getHand().get(i).getImagePath())).getImage();
                        g.drawImage(cardImg, 20 + (i * 100), getHeight() - cardHeight - 20, cardWidth, cardHeight, null);
                    }
                     
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        JPanel buttonPanel = new JPanel();
        JButton hitButton = new JButton("Hit");
        JButton stayPanel = new JButton("Stay");


        frame.setSize(boardWidth, boardHeight);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up game panel
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(255, 90, 01));
        frame.add(gamePanel);
        // Update frame color immediately
        gamePanel.revalidate();
        gamePanel.repaint();

        // Set up button panel
        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayPanel.setFocusable(false);
        buttonPanel.add(stayPanel);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }
}
