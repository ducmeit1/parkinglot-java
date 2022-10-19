package parking.lot.java.domain;

import parking.lot.java.constant.TicketPricingConstant;

public class ParkingTicket {
    private Car car;
    private Integer spotNumber;
    private Integer hour;
    private Integer charge;

    public ParkingTicket(Car car, Integer spotNumber) {
        this.car = car;
        this.spotNumber = spotNumber;
    }

    public Integer calculateChargeAmount(Integer hour) {
        this.hour = hour;
        Integer charge = 0;
        if (hour <= TicketPricingConstant.BASE_CHARGE_DURATION_HOUR) {
            charge = TicketPricingConstant.BASE_CHARGE_PRICE;
        } else {
            charge = TicketPricingConstant.BASE_CHARGE_PRICE + ((hour - TicketPricingConstant.BASE_CHARGE_DURATION_HOUR) * TicketPricingConstant.ADDITION_CHARGE_EVERY_HOUR);
        }
        this.charge = charge;
        return charge;
    }

    public Integer getSpotNumber() {
        return spotNumber;
    }

    public Car getCar() {
        return car;
    }

    public Integer getHour() {
        return hour;
    }

    public Integer getCharge() {
        return charge;
    }
}
