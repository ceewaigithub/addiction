package blackjack;

import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class BlackJackCLITest {
    public static void main(String[] args) {
        BlackJack blackJack = new BlackJack();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to BlackJack CLI Test!");
        System.out.println("This is just a test run of the logic of the game.");
        System.out.println("You will be playing against only one computer player.");
        System.out.println("Let's get started!");
        System.out.println("");
        System.out.print("Please enter your name: ");
        String name = scanner.nextLine();
        Player player = new Player(name);
        blackJack.addPlayer(player);

        Player dealer = new Player("Dealer AI");
        blackJack.addPlayer(dealer);

        // Start the game
        blackJack.startGame();

        // print player's hand
        System.out.println("Your hand: ");
        player.printHand();
        
        boolean keepHitting = true;
        while (keepHitting) {
            System.out.println("Do you want to hit? (Y/N)");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("Y")) {
                // Player wants to hit
                Card drawn_card = blackJack.getDeck().dealCard();
                player.addCard(drawn_card);
            } else if (input.equalsIgnoreCase("N")) {
                // Player doesn't want to hit
                keepHitting = false;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
            
            System.out.println("");

            // Print the player's hand
            System.out.println("Your hand: ");
            player.printHand();

            // Print the dealer's hand
            System.out.println("(You take a sly peek at the computer's hand): ");
            dealer.printHandSize();
            System.out.println("");
        }

        System.out.println("");

        // Dealer's turn
        boolean dealerKeepHitting = true;
        while (dealerKeepHitting) {
            if (dealer.getHandValue() < 17) {
                // Dealer wants to hit
                System.out.println("Dealer wants to hit, he draws a card!");
                Card drawn_card = blackJack.getDeck().dealCard();
                dealer.addCard(drawn_card);
            } else {
                // Dealer doesn't want to hit
                System.out.println("Dealer doesn't want to hit. He ends his turn.");
                dealerKeepHitting = false;
            }
            
            System.err.println();
            // Print the player's hand
            System.out.println("Your hand: ");
            player.printHand();

            // Print the dealer's hand
            System.out.println("(You take a sly peek at the computer's hand): ");
            dealer.printHandSize();
            System.out.println("");
        }
        
        // Determine the winner
        ArrayList<Player> winner = blackJack.determineWinners();

        // reveal everyone's hand
        blackJack.revealAllHands();

        // check if there is a winner
        if (winner == null) {
            System.out.println("No winner. Everyone busted.");
            System.out.println("Thanks for playing!");
            scanner.close();
            return;
        }

        // Print the winner
        System.out.println("The winner is: " + winner.get(0).getName());
        System.out.println("");
        System.out.println("Thanks for playing!");
        
        scanner.close();
    }
}
