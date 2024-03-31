package exception;

/**
 * This exception is used to indicate an error when starting a new game.
 * It allows the program to handle specific errors related to starting a new game.
 */
public class NewGameException extends Exception{
    public NewGameException(String message){
        super(message);
    }
}
