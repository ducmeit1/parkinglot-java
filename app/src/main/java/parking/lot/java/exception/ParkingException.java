package parking.lot.java.exception;

public class ParkingException extends RuntimeException {

    public ParkingException() {
    }

    public ParkingException(String message) {
        super(message);
    }
}
