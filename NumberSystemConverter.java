/*
 * Name : Rahul Ganeshwar Patil
 * Date : 24-09-2024
 * Description : [This file is used to convert number system from one base to other. ]
 **/

public class NumberSystemConverter {

    public static double binaryToDecimal(String binary) {
        return baseToDecimal(binary, 2);
    }

    public static double octalToDecimal(String octal) {
        return baseToDecimal(octal, 8);
    }

    public static double hexaDecimalToDecimal(String hexString) {
        return baseToDecimal(hexString, 16);
    }

    private static double baseToDecimal(String input, int base) {
        String[] parts = input.trim().split(Constant.SPLIT_BASE);
        if (parts.length > 2) {
            System.err.println(Constant.INVALID_INPUT);
            return 0;
        }

        double decimalValue = 0.0;
        int startIndex = (parts[0].startsWith("0x") || parts[0].startsWith("0X")) ? 2 : 0;

        decimalValue += convertIntegerPart(parts[0], base, startIndex);
        if (parts.length > 1) {
            decimalValue += convertFractionalPart(parts[1], base);
        }

        return decimalValue;
    }

    private static double convertIntegerPart(String integerPart, int base, int startIndex) {
        double value = 0.0;
        int length = integerPart.length();
        for (int i = length - 1; i >= startIndex; i--) {
            char digit = integerPart.charAt(i);
            int numValue = getNumericValue(digit, base);
            if (numValue < 0) {
                System.err.println(Constant.INVALID_CHARACTER + digit);
                return 0;
            }
            value += numValue * Power(base, length - 1 - i);
        }
        return value;
    }

    private static double convertFractionalPart(String fractionalPart, int base) {
        double value = 0.0;
        for (int i = 0; i < fractionalPart.length(); i++) {
            char digit = fractionalPart.charAt(i);
            int numValue = getNumericValue(digit, base);
            if (numValue < 0) {
                System.err.println(Constant.INVALID_CHARACTER + digit);
                return 0;
            }
            value += numValue * Power(base, -(i + 1));
        }
        return value;
    }

    private static int getNumericValue(char digit, int base) {
        int numValue = digit - '0';
        if (numValue < 0 || (numValue >= base && base <= 10)) {
            return -1;
        }
        if (base > 10 && digit >= 'A' && digit <= 'F') {
            return 10 + (digit - 'A');
        }
        return numValue;
    }

    private static double Power(int base, int exponent) {
        double result = 1.0;
        for (int i = 0; i < ((exponent < 0) ? -exponent : exponent); i++) {
            result *= base;
        }
        return (exponent < 0) ? 1 / result : result;
    }

    public static String decimalToBinary(double decimal) {
        return decimalToBase(decimal, 2);
    }

    public static String decimalToOctal(double decimal) {
        return decimalToBase(decimal, 8);
    }

    public static String decimalToHexadecimal(double decimal) {
        return decimalToBase(decimal, 16);
    }

    private static String decimalToBase(double decimal, int base) {
        if (decimal == 0) return Constant.BINARY_0;

        char[] result = new char[64];
        int index = 0;
        int integerPart = (int) decimal;
        double fractionalPart = decimal - integerPart;
        if (integerPart == 0) {
            result[index++] = '0';
        } else {
            while (integerPart > 0) {
                int remainder = integerPart % base;
                result[index++] = getCharForValue(remainder);
                integerPart /= base;
            }
        }
        reverseArray(result, index);
        if (fractionalPart > 0) {
            result[index++] = '.';
            for (int i = 0; i < 5 && fractionalPart > 0; i++) {
                fractionalPart *= base;
                int bit = (int) fractionalPart;
                result[index++] = getCharForValue(bit);
                fractionalPart -= bit;
            }
        }
        return new String(result, 0, index);
    }

    private static char getCharForValue(int value) {
        if (value < 10) {
            return (char) ('0' + value);
        } else {
            return (char) ('A' + (value - 10));
        }
    }

    private static void reverseArray(char[] array, int length) {
        for (int i = 0; i < length / 2; i++) {
            char temp = array[i];
            array[i] = array[length - 1 - i];
            array[length - 1 - i] = temp;
        }
    }

    public static String binaryToOctal(String binary) {
        double decimal = binaryToDecimal(binary);
        return decimalToOctal(decimal);
    }

    public static String binaryToHexadecimal(String binary) {
        double decimal = binaryToDecimal(binary);
        return decimalToHexadecimal(decimal);
    }

    public static String octalToBinary(String octal) {
        double decimal = octalToDecimal(octal);
        return decimalToBinary(decimal);
    }

    public static String octalToHexadecimal(String octal) {
        double decimal = octalToDecimal(octal);
        return decimalToHexadecimal(decimal);
    }

    public static String hexadecimalToBinary(String hex) {
        double decimal = hexaDecimalToDecimal(hex);
        return decimalToBinary(decimal);
    }

    public static String hexadecimalToOctal(String hex) {
        double decimal = hexaDecimalToDecimal(hex);
        return decimalToOctal(decimal);
    }
    
}
