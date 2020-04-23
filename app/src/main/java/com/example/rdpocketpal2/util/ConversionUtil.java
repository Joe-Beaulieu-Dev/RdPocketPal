package com.example.rdpocketpal2.util;

public class ConversionUtil {
    public static double poundsToKilograms(double pounds) {
        return pounds * 0.45359237;
    }

    public static double inchesToCentimeters(double inches) {
        return inches * 2.54;
    }

    public static double fahrenheitToCelsius(double tempFahrenheit) {
        return (tempFahrenheit - 32) * (5.0/9.0);
    }

    public static double gallonsToLiters(double gallons) {
        return gallons * 3.78541;
    }
}
