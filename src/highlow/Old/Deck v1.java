// package highlow.Old;

// import highlow.Card;

// public class Deck {
//     private Card[] deck;
//     private int cardUsed;

//     public Deck() {
//         this(false);
//     }

//     public Deck(boolean includeJokers) {

//         // If includeJokers is true, make deck with 52 + 2 jokers = 54
//         if (includeJokers) {
//             deck = new Card[54];
//         } else {
//             deck = new Card[52];
//         }

//         int cardCount = 0;

//         // Creating the deck
//         for (int suit = 0; suit <= 3; suit++) {
//             for (int value = 1; value <= 13; value++) {
//                 deck[cardCount++] = new Card(value, suit);
//                 // System.out.println(deck[cardCount]);
//             }
//         }

//         // Adding jokers if needed
//         if (includeJokers) {
//             deck[52] = new Card(1, Card.JOKER);
//             deck[53] = new Card(2, Card.JOKER);
//         }

//         cardUsed = 0;

//     }

//     // shuffle deck of cards
//     public void shuffle() {
//         for (int i = deck.length - 1; i > 0; i--) {
//             int rand = (int) (Math.random() * (i + 1));
//             // System.out.println(deck[i]);
//             Card temp = deck[i];
//             deck[i] = deck[rand];
//             deck[rand] = temp;
//         }

//         // reset cards used
//         cardUsed = 0;
//     }

//     // how many cards are left
//     public int cardsLeft() {
//         return deck.length - cardUsed;
//     }

//     // remove next card from deck and return it
//     public Card dealCard() {
//         if (cardsLeft() == 0) {
//             System.out.println("Dealcard Error");
//             throw new IllegalStateException("No cards are left in the deck.");
//         }

//         return deck[cardUsed++];
//     }

//     // does this deck have jokers?
//     public boolean hasJokers() {
//         return (deck.length == 54);
//     }
// }
