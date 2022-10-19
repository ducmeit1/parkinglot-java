package parking.lot.java.domain;

public class Car {
    private String registrationNumber;
    private ParkingTicket parkingTicket;

    public Car(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public ParkingTicket getParkingTicket() {
        return parkingTicket;
    }

    public void assignParkingTicket(ParkingTicket parkingTicket) {
        this.parkingTicket = parkingTicket;
    }    
}
