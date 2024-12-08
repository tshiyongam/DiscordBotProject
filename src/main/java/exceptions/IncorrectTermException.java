package exceptions;

public class IncorrectTermException extends RuntimeException {
    public IncorrectTermException(String term) {
        super("The term " + term + " is not correct.");
    }
}
