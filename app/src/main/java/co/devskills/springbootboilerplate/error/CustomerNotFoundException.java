package co.devskills.springbootboilerplate.error;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String userId) {
        super(String.format("Customer not found with ID: %s", userId));
    }
}