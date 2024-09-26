/*
 * Name : Rahul Ganeshwar Patil
 * Date : 24-09-2024
 * Description : [ This file is used to implement Number System CLI. ]
 **/
import java.text.DecimalFormat;
import java.util.Scanner;

public class NumberSystemConverterCLI {
	static DecimalFormat df = new DecimalFormat("#.#####");
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
                System.out.println(Constant.RED + Constant.ERROR + e.getMessage() + Constant.CLOSE_COLOR);
            }
        }
        scanner.close();
    }

    //This function process the input line and divide them according to operation and conversion
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
    
    //This function handles arithmetic operations on String
    //Input -> Array element
    private static void handleArithmetic(String[] parts) {
        String operation = parts[0].toLowerCase();
        String inputBase1 = parts[1].toLowerCase();
        String number1 = parts[2].toUpperCase();
        String inputBase2 = parts[4].toLowerCase();
        String number2 = parts[5].toUpperCase();

        double decimalValue1 = Double.parseDouble(convertToDecimal(inputBase1, number1));
        double decimalValue2 = Double.parseDouble(convertToDecimal(inputBase2, number2));;

        double result;

        switch (operation) {
            case "add":
                result = decimalValue1 + decimalValue2;
                break;
            case "subtract":
                result = decimalValue1 - decimalValue2;
                break;
            case "multiply":
                result = decimalValue1 * decimalValue2;
                break;
            case "divide":
                if (decimalValue2 == 0) {
                    throw new IllegalArgumentException(Constant.BY_ZERO);
                }
                result = decimalValue1 / decimalValue2;
                break;
            default:
                throw new IllegalArgumentException(Constant.INVALID_OPERATIONS + operation);
        }
        System.out.println(Constant.GREEN + Constant.ARITHMETIC_RESULT + result + Constant.CLOSE_COLOR);
    }
    //This function handles conversion on number
    //Input -> Array element
    private static void handleConversion(String[] parts) {
        String inputBase = parts[1].toLowerCase();
        String number = parts[2].toUpperCase();
        String outputBase = parts[4].toLowerCase();

        String decimalValue = convertToDecimal(inputBase, number);
        double decimalValue1 = Double.parseDouble(decimalValue)
;        String result = convertFromDecimal(outputBase, decimalValue1);

        System.out.println(Constant.GREEN + Constant.CONVERSION_RESULT + result + Constant.CLOSE_COLOR);
    }
    //If input is in other base and we want to convert it to decimal
    //Input -> base, Number
    //Output -> Decimal Number
    private static String convertToDecimal(String inputBase, String number) {
        switch (inputBase) {
            case Constant.BINARY:
                return df.format(NumberSystemConverter.binaryToDecimal(number));
            case Constant.OCTAL:
                return df.format(NumberSystemConverter.octalToDecimal(number));
            case Constant.DECIMAL:
                return df.format(Double.parseDouble(number));
            case Constant.HEXADECIMAL:
                return df.format(NumberSystemConverter.hexaDecimalToDecimal(number));
            default:
                throw new IllegalArgumentException(Constant.UNSUPPORTED_BASE + inputBase);
        }
    }

    //If input is in decimal and we want to convert it to other base
    //Input -> base, Number
    //Output -> Number according to base
    private static String convertFromDecimal(String outputBase, double decimalValue) {
        switch (outputBase) {
            case "binary":
                return NumberSystemConverter.decimalToBinary(decimalValue);
            case "octal":
                return NumberSystemConverter.decimalToOctal(decimalValue);
            case "decimal":
                return String.valueOf(decimalValue);
            case "hexadecimal":
                return NumberSystemConverter.decimalToHexadecimal(decimalValue);
            default:
                throw new IllegalArgumentException(Constant.UNSUPPORTED_BASE + outputBase);
        }
    }
}
