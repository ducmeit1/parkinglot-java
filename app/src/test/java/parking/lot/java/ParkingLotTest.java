package parking.lot.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeNoException;

import org.junit.Test;

import parking.lot.java.constant.MessageConstant;
import parking.lot.java.exception.ParkingException;
import parking.lot.java.service.ParkingLotService;

public class ParkingLotTest {
    private ParkingLotService setup(int capacity) {
        return ParkingLotService.getInstance(capacity);
    }

    @Test public void TestParkingCar_ValidArguments() {
        try {
        // Given
        ParkingLotService parkingLotService = setup(2);
        
        // When
        String actual1 = parkingLotService.park("KA-01-HH-1234");
        String actual2 = parkingLotService.park("KA-01-HH-9999");
        // Then
        assertEquals(String.format(MessageConstant.ALLOCATED_SLOT_NUMBER, 1), actual1);
        assertEquals(String.format(MessageConstant.ALLOCATED_SLOT_NUMBER, 2), actual2);
        } catch (ParkingException e) {
            assumeNoException(e);
        }
    }

    @Test public void TestParkingCar_InvalidArguments() {
        try {
            // Given
            ParkingLotService parkingLotService = setup(1);
            
            // When
            String actual1 = parkingLotService.park("KA-01-HH-1234");
            String actual2 = parkingLotService.park("KA-01-HH-9999");

            // Then
            assertEquals(String.format(MessageConstant.ALLOCATED_SLOT_NUMBER, 1), actual1);
        } catch (ParkingException e) {
            assertEquals(MessageConstant.PARKING_LOT_IS_FULL, e.getMessage());
        }
    }

    @Test public void TestLeaveParkingCar_ValidArguments() {
        try {
            // Given
            ParkingLotService parkingLotService = setup(1);
            
            // When
            String actual1 = parkingLotService.park("KA-01-HH-1234");
            String actual2 = parkingLotService.leave("KA-01-HH-1234", 2);
            // Then
            assertEquals(String.format(MessageConstant.ALLOCATED_SLOT_NUMBER, 1), actual1);
            assertEquals(String.format(MessageConstant.LEAVE_PARKING_LOT_WITH_CHARGE, "KA-01-HH-1234", 1, 10), actual2);
        } catch (ParkingException e) {
            assumeNoException(e);
        }
    }

    @Test public void TestLeaveParkingCar_InvalidArguments() {
        try {
            // Given
            ParkingLotService parkingLotService = setup(1);
            
            // When
            String actual1 = parkingLotService.park("KA-01-HH-1234");
            String actual2 = parkingLotService.leave("KA-01-HH-1235", 2);
            // Then
            assertEquals(String.format(MessageConstant.ALLOCATED_SLOT_NUMBER, 1), actual1);
        } catch (ParkingException e) {
            assertEquals(String.format(MessageConstant.REGISTRATION_NUMBER_NOT_FOUND, "KA-01-HH-1235"), e.getMessage());
        }
    }
}
