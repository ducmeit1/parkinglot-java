package parking.lot.java.domain.strategy;

import parking.lot.java.exception.ParkingException;

public interface IParkingSpotStrategy {
    public int park() throws ParkingException;
    public void leave(int spotNumber) throws ParkingException;
}
