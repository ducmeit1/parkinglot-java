package parking.lot.java.service;

import java.util.Optional;
import java.util.TreeMap;

import parking.lot.java.constant.MessageConstant;
import parking.lot.java.domain.Car;
import parking.lot.java.domain.ParkingTicket;
import parking.lot.java.domain.strategy.NearestParkingSpotStrategy;
import parking.lot.java.exception.ParkingException;

public class ParkingLotService {
    // Using to find nearest first parking slot
    private NearestParkingSpotStrategy nearestParkingSpotStrategy;
    private TreeMap<Integer, Optional<ParkingTicket>> parkingSpotTickets;

    private static ParkingLotService parkingLot = null;

    private ParkingLotService(int capacity) {
        this.nearestParkingSpotStrategy = new NearestParkingSpotStrategy(capacity);
        this.parkingSpotTickets = new TreeMap<>();
        for (int i = 1; i <= capacity; i++) {
            this.parkingSpotTickets.put(i, Optional.empty());
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

    public int park(String registrationNumber) throws ParkingException {
        try {
            Car car = new Car(registrationNumber);
            int spotNumber = this.nearestParkingSpotStrategy.park();
            ParkingTicket ticket = new ParkingTicket(car, spotNumber);
            this.parkingSpotTickets.put(spotNumber, Optional.of(ticket));
            return spotNumber;
        } catch (ParkingException e) {
            throw e;
        }
    }

    public int[] leave(String registrationNumber, int duration) throws ParkingException {
        try {
            ParkingTicket parkingTicket = this.findParkingTicketByRegistrationNumber(registrationNumber);
            final int spotNumber = parkingTicket.getSpotNumber();
            this.parkingSpotTickets.put(spotNumber, Optional.empty());
            this.nearestParkingSpotStrategy.leave(spotNumber);
            final int totalChargeAmount = parkingTicket.calculateTotalChargeAmount(duration);
            final int[] result = { spotNumber, totalChargeAmount };
            return result;
        } catch (ParkingException e) {
            throw e;
        }
    }

    public String status() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Slot No.\tRegistration No.\n");
        parkingSpotTickets.keySet().forEach(
                k -> {
                    if (parkingSpotTickets.get(k).isPresent()) {
                        stringBuilder.append(String.format("%d\t\t%s\n", k,
                                parkingSpotTickets.get(k).get().getCar().getRegistrationNumber()));
                    } else {
                        stringBuilder.append(String.format("%d\n", k));
                    }
                });
        return stringBuilder.toString().trim();
    }

    private ParkingTicket findParkingTicketByRegistrationNumber(String registrationNumber) throws ParkingException {
        for (Optional<ParkingTicket> parkingTicket : parkingSpotTickets.values()) {
            if (parkingTicket.isPresent()
                    && parkingTicket.get().getCar().getRegistrationNumber().equalsIgnoreCase(registrationNumber)) {
                return parkingTicket.get();
            }
        }
        throw new ParkingException(String.format(MessageConstant.REGISTRATION_NUMBER_NOT_FOUND, registrationNumber));
    }
}
