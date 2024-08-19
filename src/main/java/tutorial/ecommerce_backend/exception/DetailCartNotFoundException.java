package tutorial.ecommerce_backend.exception;


public class DetailCartNotFoundException extends RuntimeException {
    public DetailCartNotFoundException(String message) {
        super(message);
    }
}
