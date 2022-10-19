package parking.lot.java.util;

import java.util.Scanner;

import parking.lot.java.constant.MessageConstant;
import parking.lot.java.exception.CommandLineException;

public class InputUtil {
    public static int getInteger(Scanner scanner, String name, int min) throws CommandLineException {
        try {
            int value = scanner.nextInt();
            if (value < min) {
                throw new IllegalArgumentException();
            }
            return value;
        } catch (Exception e) {
            throw new CommandLineException(String.format(MessageConstant.INVALID_INPUT_INTEGER, name, min));
        }
    }

    public static String getString(Scanner scanner, String name) {
        String value = scanner.next();
        if (value.trim().isEmpty()) {
            throw new CommandLineException(String.format(MessageConstant.INVALID_INPUT_STRING, name));
        }
        return value;
    }
}
