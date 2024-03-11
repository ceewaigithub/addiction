package highlow;

import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

class HighLowGame {

    private HighLowGUI gui;
    private Deck deck;
    private Card currCard;
    private Card nextCard;
    private int correctGuesses;

    public HighLowGame(HighLowGUI gui) {
        this.gui = gui;

        // Add action listeners
        gui.getHigherButton().addActionListener(e -> handleGuess("H"));
        gui.getLowerButton().addActionListener(e -> handleGuess("L"));
        gui.getExitButton().addActionListener(e -> System.exit(0));

        // Start the game
        startGame();
    }

    // Creating deck and shuffling it, then showcasing first card
    private void startGame(){
        deck = new Deck();
        deck.shuffle();
        correctGuesses = 0;
        currCard = deck.dealCard();
        gui.getMessageLabel().setText("The first card is the " + currCard);

        // Display the first card
        displayCard(currCard, gui.getBottomPanel());
    }

    // Checking if guess is correct or incorrect
    private void handleGuess(String guess) {
        nextCard = deck.dealCard();
        gui.getMessageLabel().setText("The next card is " + nextCard);

        // Display the next card
        displayCard(nextCard, gui.getBottomPanel());

        if (nextCard.getValue() == currCard.getValue()) {
            gui.getMessageLabel().setText("The value is the same as the previous card.\nYou lose on ties. Sorry!");
            endGame();
            return;
        }

        if ((nextCard.getValue() > currCard.getValue() && guess.equals("H")) ||
        (nextCard.getValue() < currCard.getValue() && guess.equals("L"))) {
            gui.getMessageLabel().setText("Your prediction was correct!\n The next card is " + nextCard);
            correctGuesses++;
        } else {
            gui.getMessageLabel().setText("Your predictions was incorrect.");
            endGame();
            return;
        }

        currCard = nextCard;
    }

    // To end a game when an incorrect guess is made
    private void endGame() {
        gui.getMessageLabel().setText("The game is over. You made " + correctGuesses + " correct guesses!");
        gui.getHigherButton().setEnabled(false);
        gui.getLowerButton().setEnabled(false);
    }
    
    // To display card in GUI
    private void displayCard(Card card, JPanel panel) {
        ImageIcon icon = new ImageIcon(card.getImagePath());
        JLabel label = new JLabel(icon);
        panel.removeAll();
        panel.add(label);
        panel.revalidate();
        panel.repaint();
    }
}