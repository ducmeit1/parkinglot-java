package parking.lot.java.domain.strategy;

import java.util.TreeSet;

import parking.lot.java.constant.MessageConstant;
import parking.lot.java.exception.ParkingException;

public class NearestParkingSpotStrategy implements IParkingSpotStrategy {
    private TreeSet<Integer> availableParkingSpots;

    public NearestParkingSpotStrategy(int capacity) {
        this.availableParkingSpots = new TreeSet<Integer>();
        for (int i = 1; i <= capacity; i++) {
            this.availableParkingSpots.add(i);
        }
    }

    @Override
    public int park() throws ParkingException {
        if (this.availableParkingSpots.isEmpty()) {
            throw new ParkingException(MessageConstant.PARKING_LOT_IS_FULL);
        }
        int nearestSpot = this.availableParkingSpots.first();
        this.availableParkingSpots.remove(nearestSpot);
        return nearestSpot;
    }

    @Override
    public void leave(int spotNumber) throws ParkingException {
        this.availableParkingSpots.add(spotNumber);
    }
}
