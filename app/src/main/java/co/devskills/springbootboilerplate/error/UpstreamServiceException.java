package co.devskills.springbootboilerplate.error;

public class UpstreamServiceException extends RuntimeException {

    public UpstreamServiceException(String message) {
        super(message);
    }
}