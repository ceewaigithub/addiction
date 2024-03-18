// package highlow;

// public class Card {

//     // Code for the 4 suits
//     public final static int SPADES = 0;
//     public final static int HEARTS = 1;
//     public final static int DIAMONDS = 2;
//     public final static int CLUBS = 3;
//     public final static int JOKER = 4; // plus joker

//     // Codes for the non-numeric cards
//     public final static int ACE = 1;
//     public final static int JACK = 11;
//     public final static int QUEEN = 12;
//     public final static int KING = 13;

//     private final int suit;
//     private final int value;
//     // private final String imagePath; // Path to GIF image

//     public Card() {
//         suit = JOKER;
//         value = 1;
//         // imagePath = "cards/j.gif";
//     }

//     public Card(int theValue, int theSuit){
//         if (theSuit != SPADES && theSuit != HEARTS && theSuit != DIAMONDS && theSuit != CLUBS && theSuit != JOKER){
//             throw new IllegalArgumentException("Illegal playing card suit");
//         }

//         if (theSuit != JOKER && (theValue < 1 || theValue > 13)){
//             throw new IllegalArgumentException("Illegal playing card value");
//         }

//         value = theValue;
//         suit = theSuit;
//         // this.imagePath
//     }

//     // returns suit of card
//     public int getSuit() {
//         return suit;
//     }

//     // returns value of card
//     public int getValue() {
//         return value;
//     }

//     // returns suit as String
//     public String getSuitAsString() {
//         switch (suit) {
//             case SPADES:
//                 return "Spades";
//             case HEARTS:
//                 return "Hearts";
//             case DIAMONDS:
//                 return "Diamonds";
//             case CLUBS:
//                 return "Clubs";
//             default:
//                 return "Joker";
//         }
//     }

//     // returns value as String
//     public String getValueAsString() {
//         if (suit == JOKER) {
//             return "" + value;
//         } else {
//             switch (value) {
//                 case 1:
//                     return "Ace";
//                 case 2:
//                     return "2";
//                 case 3:
//                     return "3";
//                 case 4:
//                     return "4";
//                 case 5:
//                     return "5";
//                 case 6:
//                     return "6";
//                 case 7:
//                     return "7";
//                 case 8:
//                     return "8";
//                 case 9:
//                     return "9";
//                 case 10:
//                     return "10";
//                 case 11:
//                     return "Jack";
//                 case 12:
//                     return "Queen";
//                 default:
//                     return "King";
//             }
//         }
//     }

//     // returns card object as String
//     public String toString() {
//         if (suit == JOKER){
//             if (value == 1) {
//                 return "Joker";
//             } else {
//                 return "Joker #" + value;
//             }
//         } else {
//             return getValueAsString() + " of " + getSuitAsString();
//         }
//     }


//     // returns image path of the card
//     public String getImagePath() {
//         String value;

//         // if 10, value = "t";
//         if (getValue() == 10) {
//             value = "t";
//         } else {
//             value = getValueAsString().substring(0,1).toLowerCase();
//         }
        
//         String suit = getSuitAsString().substring(0,1).toLowerCase();
//         // System.out.println("" + value + suit);
//         return "res/cards/" + value + suit + ".gif";
//     }
// }
