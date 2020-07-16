package converter;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static String parseIntPart(int sourceRadix, String num, int destRadix) {
        String res = new String();
        if (num.contains(".")) {
            num = num.split("\\.")[0];
        }

        if (sourceRadix == 1) {
            num = String.valueOf(num.length());
            sourceRadix = 10;
        }

        if (destRadix == 1) {
            res = "1".repeat(Integer.parseInt(num, 10));
        } else {
            res = Integer.toString(Integer.parseInt(num, sourceRadix), destRadix);
        }

        return res;
    }

    private static String parseFractionalPart(int sourceRadix, String num, int destRadix) {
        String res = new String();

        if (!num.contains(".")) {
            return "";
        }
        num = num.split("\\.")[1];

        double fractionInDecimal = 0.0;
        if (sourceRadix != 10) {
            fractionInDecimal = convertFractionToDecimal(num, sourceRadix);
        } else {
            fractionInDecimal = Double.parseDouble("." + num);
        }

        int intPart = 0;
        StringBuilder fraction = new StringBuilder();
        while (fraction.length() < 5) {
            fractionInDecimal *= destRadix;
            int digit = (int) fractionInDecimal;
            fraction.append(Integer.toString(digit, destRadix));
            fractionInDecimal -= digit;
            if (fractionInDecimal == 0.0) {
                break;
            }
        }

        return Integer.toString(intPart) + "." + fraction.toString();
    }

    private static double convertFractionToDecimal(String num, int sourceRadix) {
        double number = 0;
        for (int i = 0; i < num.length(); ++i) {
            number += Integer.parseInt(num.substring(i, i + 1), sourceRadix) / Math.pow(sourceRadix, i + 1);
        }

        return number;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            int sourceRadix = scanner.nextInt();
            String num = scanner.next();
            int destRadix = scanner.nextInt();
            if (destRadix < 1) {
                System.out.println("Error. Destination radix should be Greater than Zero");
                return;
            }
            if (destRadix > 36) {
                System.out.println("Error. Destination radix should be Smaller than " + Character.MIN_RADIX + 1);
                return;
            }

            String intPart = parseIntPart(sourceRadix, num, destRadix);
            String fractionalPart = parseFractionalPart(sourceRadix, num, destRadix);
            if (fractionalPart.contains(".")) {
                fractionalPart = fractionalPart.split("\\.")[1];
            }

            System.out.println(intPart + "." + fractionalPart);
        } catch (InputMismatchException e) {
            System.out.println("Error. Please enter integer:" + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Error. Not enough data:" + e.getMessage());
        }
    }
}
