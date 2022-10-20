package parking.lot.java.domain;

import parking.lot.java.constant.TicketPricingConstant;

public class ParkingTicket {
    private Car car;
    private Integer spotNumber;
    private Integer totalChargeAmount;

    public ParkingTicket(Car car, Integer spotNumber) {
        this.car = car;
        this.spotNumber = spotNumber;
    }

    public Integer calculateTotalChargeAmount(Integer totalDuration) {
        if (totalDuration <= TicketPricingConstant.BASE_CHARGE_DURATION_HOUR) {
            this.totalChargeAmount = TicketPricingConstant.BASE_CHARGE_PRICE;
        } else {
            this.totalChargeAmount = TicketPricingConstant.BASE_CHARGE_PRICE
                    + ((totalDuration - TicketPricingConstant.BASE_CHARGE_DURATION_HOUR)
                            * TicketPricingConstant.ADDITION_CHARGE_EVERY_HOUR);
        }
        return this.totalChargeAmount;
    }

    public Integer getSpotNumber() {
        return spotNumber;
    }

    public Car getCar() {
        return car;
    }
}
