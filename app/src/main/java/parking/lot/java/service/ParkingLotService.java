package parking.lot.java.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;

import parking.lot.java.constant.MessageConstant;
import parking.lot.java.domain.Car;
import parking.lot.java.domain.ParkingTicket;
import parking.lot.java.exception.ParkingException;

public class ParkingLotService {
    private final int maxCapacity;
    // Using to find nearest first parking slot
    private TreeSet<Integer> spots;
    private Map<Integer, Optional<Car>> parkingSpots;

    private static ParkingLotService parkingLot = null;

    private ParkingLotService(int capacity) {
        this.maxCapacity = capacity;
        this.spots = new TreeSet<>();
        this.parkingSpots = new HashMap<>();
        for (int i = 1; i <= capacity; i++) {
            this.spots.add(i);
            this.parkingSpots.put(i, Optional.empty());
        }
    }

    public static ParkingLotService getInstance(int capacity) {
        if (parkingLot == null) {
            parkingLot = new ParkingLotService(capacity);
        }
        return parkingLot;
    }

    public static boolean isInitialized() {
        return parkingLot != null;
    }

    public String park(String registrationNumber) throws ParkingException {
        if (this.spots.isEmpty()) {
            throw new ParkingException(MessageConstant.PARKING_LOT_IS_FULL);
        }

        int nearestFirstSpot = this.spots.first();
        Car car = new Car(registrationNumber);
        ParkingTicket ticket = new ParkingTicket(car, nearestFirstSpot);
        car.assignParkingTicket(ticket);
        this.parkingSpots.put(nearestFirstSpot, Optional.of(car));
        this.spots.remove(nearestFirstSpot);
        return String.format(MessageConstant.ALLOCATED_SLOT_NUMBER, nearestFirstSpot);
    }

    public String leave(String registrationNumber, int duration) {
        int foundSpotNumber = getSpotNumberByRegistrationNumber(registrationNumber);
        if (foundSpotNumber == -1) {
            throw new ParkingException(String.format(MessageConstant.REGISTRATION_NUMBER_NOT_FOUND, registrationNumber));
        }

        Car car = parkingSpots.get(foundSpotNumber).get();
        ParkingTicket ticket = car.getParkingTicket();
        int charge = ticket.calculateChargeAmount(duration);
        this.parkingSpots.put(foundSpotNumber, Optional.empty());
        this.spots.add(foundSpotNumber);

        return String.format(MessageConstant.LEAVE_PARKING_LOT_WITH_CHARGE, registrationNumber, foundSpotNumber, charge);
    }

    public String status() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Slot No.\tRegistration No.\n");
        for (int i = 1; i <= this.maxCapacity; i++) {
            Optional<Car> car = this.parkingSpots.get(i);
            if (car.isPresent()) {
                stringBuilder.append(String.format("%d\t\t%s\n", i, car.get().getRegistrationNumber()));
            } else {
                stringBuilder.append(String.format("%d\n", i));
            }
        }
        return stringBuilder.toString().trim();
    }

    private int getSpotNumberByRegistrationNumber(String registrationNumber) {
        for (int i = 1; i <= this.maxCapacity; i++) {
            Optional<Car> car = this.parkingSpots.get(i);
            if (car.isPresent() && car.get().getRegistrationNumber().equalsIgnoreCase(registrationNumber)) {
                return i;
            }
        }
        return -1;
    }
}
