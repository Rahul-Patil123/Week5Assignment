/*
 * Name : Rahul Ganeshwar Patil
 * Date : 24-09-2024
 * Description : [ This file is used to implement Number System CLI. ]
 **/
import java.util.Scanner;

public class NumberSystemConverterCLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(Constant.WELCOME_MESSAGE);

        while (true) {
            System.out.println(Constant.PROMPT_COMMAND);
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase(Constant.EXIT_COMMAND)) {
                System.out.println(Constant.EXIT_MESSAGE);
                break;
            }

            try {
                processInput(input);
            } catch (Exception e) {
                System.out.println("\033[1;31m" + Constant.ERROR + e.getMessage() + "\033[0m");
            }
        }
        scanner.close();
    }

    private static void processInput(String input) {
        String[] parts = input.split(" ");

        if (parts.length == 5 && parts[0].equalsIgnoreCase(Constant.KEYWORD_CONVERT) && parts[3].equalsIgnoreCase(Constant.KEYWORD_TO)) {
            handleConversion(parts);
        } 
        else if (parts.length == 6 && 
                 (parts[0].equalsIgnoreCase(Constant.ADD) || parts[0].equalsIgnoreCase(Constant.SUBTRACT) ||
                  parts[0].equalsIgnoreCase(Constant.MULTIPLY) || parts[0].equalsIgnoreCase(Constant.DIVIDE))) {
            handleArithmetic(parts);
        } 
        else {
            throw new IllegalArgumentException(Constant.INVALID_FORMAT);
        }
    }
    
    private static void handleArithmetic(String[] parts) {
        String operation = parts[0].toLowerCase();
        String inputBase1 = parts[1].toLowerCase();
        String number1 = parts[2];
        String inputBase2 = parts[4].toLowerCase();
        String number2 = parts[5];

        double decimalValue1 = convertToDecimal(inputBase1, number1);
        double decimalValue2 = convertToDecimal(inputBase2, number2);

        double result;

        switch (operation) {
            case Constant.ADD:
                result = decimalValue1 + decimalValue2;
                break;
            case Constant.SUBTRACT:
                result = decimalValue1 - decimalValue2;
                break;
            case Constant.MULTIPLY:
                result = decimalValue1 * decimalValue2;
                break;
            case Constant.DIVIDE:
                if (decimalValue2 == 0) {
                    throw new IllegalArgumentException(Constant.BY_ZERO);
                }
                result = decimalValue1 / decimalValue2;
                break;
            default:
                throw new IllegalArgumentException(Constant.INVALID_OPERATIONS + operation);
        }
        System.out.println("\033[1;32m" + Constant.ARITHMETIC_RESULT + result + "\033[0m");
    }
    private static void handleConversion(String[] parts) {
        String inputBase = parts[1].toLowerCase();
        String number = parts[2];
        String outputBase = parts[4].toLowerCase();

        double decimalValue = convertToDecimal(inputBase, number);
        String result = convertFromDecimal(outputBase, decimalValue);

        System.out.println("\033[1;32m" + Constant.CONVERSION_RESULT + result + "\033[0m");
    }
    private static double convertToDecimal(String inputBase, String number) {
        switch (inputBase) {
            case Constant.BINARY:
                return NumberSystemConverter.binaryToDecimal(number);
            case Constant.OCTAL:
                return NumberSystemConverter.octalToDecimal(number);
            case Constant.DECIMAL:
                return Double.parseDouble(number);
            case Constant.HEXADECIMAL:
                return NumberSystemConverter.hexaDecimalToDecimal(number);
            default:
                throw new IllegalArgumentException(Constant.UNSUPPORTED_BASE + inputBase);
        }
    }

    private static String convertFromDecimal(String outputBase, double decimalValue) {
        switch (outputBase) {
            case Constant.BINARY:
                return NumberSystemConverter.decimalToBinary(decimalValue);
            case Constant.OCTAL:
                return NumberSystemConverter.decimalToOctal(decimalValue);
            case Constant.DECIMAL:
                return String.valueOf(decimalValue);
            case Constant.HEXADECIMAL:
                return NumberSystemConverter.decimalToHexadecimal(decimalValue);
            default:
                throw new IllegalArgumentException(Constant.UNSUPPORTED_BASE + outputBase);
        }
    }
}
