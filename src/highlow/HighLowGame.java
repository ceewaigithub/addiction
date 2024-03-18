package highlow;

import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;


class HighLowGame {

    private HighLowGUI gui;
    private Deck deck;
    private Card currCard;
    private Card nextCard;
    private int correctGuesses;
    private List<Player> players;

    public HighLowGame(HighLowGUI gui) {
        this.gui = gui;
        players = new ArrayList<>();
        // player = new Player("Player");

        // Add action listeners
        gui.getHigherButton().addActionListener(e -> handleGuess("H"));
        gui.getLowerButton().addActionListener(e -> handleGuess("L"));
        gui.getExitButton().addActionListener(e -> System.exit(0));
        // gui.getNextGameButton().addActionListener(e -> restartGame());

        // Start the game
        startGame();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    // Creating deck and shuffling it, then showcasing first card
    private void startGame(){
        deck = new Deck();
        correctGuesses = 0;
        currCard = deck.dealCard();

        // Add the first card to the player's hand
        // player.addCard(currCard);

        gui.getMessageLabel().setText("The first card is the " + currCard);

        // Display the first card
        gui.displayCard(currCard, gui.getRow2());

        // gui.getGamePanel().repaint();
    }

    // Checking if guess is correct or incorrect
    private void handleGuess(String guess) {
        nextCard = deck.dealCard();
        // player.addCard(currCard);
        gui.getMessageLabel().setText("The next card is " + nextCard);

        // Display the next card
        gui.displayCard(nextCard, gui.getRow2());
        // gui.getGamePanel().repaint();
        

        // if (nextCard.getHighLowValue() == currCard.getHighLowValue()) {
        if (nextCard.compareTo(currCard) == 0) {
            gui.getMessageLabel().setText("The value is the same as the previous card.\nYou lose on ties. Sorry!");
            endGame();
            return;
        }

        // if ((nextCard.getHighLowValue() > currCard.getHighLowValue() && guess.equals("H")) || (nextCard.getHighLowValue() < currCard.getHighLowValue() && guess.equals("L"))) {
        if ((nextCard.compareTo(currCard) > 0 && guess.equals("H")) || (nextCard.compareTo(currCard) < 0 && guess.equals("L"))) {
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

    public List<Player> getPlayers(){
        return players;
    }
    
    // To display card in GUI
    private void displayCard(Card card, JPanel panel) {
        // ImageIcon icon = new ImageIcon(card.getImagePath());
        // JLabel label = new JLabel(icon);
        Image image = card.getImage();
        JLabel label = new JLabel(new ImageIcon(image));
        panel.removeAll();
        panel.add(label);
        panel.revalidate();
        panel.repaint();
    }
}