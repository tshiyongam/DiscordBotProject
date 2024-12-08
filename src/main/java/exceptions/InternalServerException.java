package exceptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super("Internal server error: " + message);
    }
}
