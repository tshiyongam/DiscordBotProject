package exceptions;

public class GameInProgressException extends RuntimeException {
    public GameInProgressException() {
        super("Game currently in progress.");
    }
}
