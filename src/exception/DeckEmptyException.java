package exception;

public class DeckEmptyException extends RuntimeException {
    public DeckEmptyException(String message) {
        super(message);
    }
}