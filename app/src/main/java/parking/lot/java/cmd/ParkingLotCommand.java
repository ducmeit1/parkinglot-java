package parking.lot.java.cmd;

import java.util.Scanner;

import parking.lot.java.constant.CommandLineConstant;
import parking.lot.java.constant.MessageConstant;
import parking.lot.java.exception.CommandLineException;
import parking.lot.java.exception.ParkingException;
import parking.lot.java.service.ParkingLotService;
import parking.lot.java.util.InputUtil;

public class ParkingLotCommand {
    private Scanner scanner;
    private ParkingLotService parkingLotService = null;

    public ParkingLotCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            try {
                String command = scanner.next().trim();
                if (!command.equals(CommandLineConstant.CREATE_COMMAND) && !ParkingLotService.isInitialized()) {
                    throw new CommandLineException("Parking Lot wasn't created yet");
                }
                
                switch (command) {
                    case CommandLineConstant.CREATE_COMMAND:
                        int capacity = InputUtil.getInteger(scanner, "capacity", 1);
                        this.parkingLotService = ParkingLotService.getInstance(capacity);
                        System.out.println(String.format(MessageConstant.CREATED_PARKING_LOT_WITH_SLOTS, capacity));
                        break;
                    case CommandLineConstant.PARK_COMMAND:
                        String parkRegistrationNumber = InputUtil.getString(scanner, "registration-number");
                        System.out.println(String.format(MessageConstant.ALLOCATED_SLOT_NUMBER, this.parkingLotService.park(parkRegistrationNumber)));
                        break;
                    case CommandLineConstant.LEAVE_COMMAND:
                        String leaveRegistrationNumber = InputUtil.getString(scanner, "registration-number");
                        int duration = InputUtil.getInteger(scanner, "duration", 0);
                        int result [] = this.parkingLotService.leave(leaveRegistrationNumber, duration);
                        System.out.println(String.format(MessageConstant.LEAVE_PARKING_LOT_WITH_CHARGE, leaveRegistrationNumber, result[0], result[1]));
                        break;
                    case CommandLineConstant.STATUS_COMMAND:
                        System.out.println(this.parkingLotService.status());
                        break;
                    case CommandLineConstant.EXIT_COMMAND:
                        System.exit(0);
                        break;
                    default:
                        throw new CommandLineException(String.format("Command: %s is not found", command));
                    }
            } catch (CommandLineException | ParkingException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
