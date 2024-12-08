package exceptions;

public class NoGameInProgressException extends RuntimeException {
    public NoGameInProgressException() {
        super("No game currently in progress.");
    }
}
