package exception;

/**
 * This class represents a custom exception that is thrown when a deck is empty.
 */
public class DeckEmptyException extends RuntimeException {
    public DeckEmptyException(String message) {
        super(message);
    }
}