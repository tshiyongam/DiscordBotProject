package exceptions;

public class CategoryNotValidException extends RuntimeException {
    public CategoryNotValidException(String message) {
        super(message + " is not a valid category.");
    }
}
