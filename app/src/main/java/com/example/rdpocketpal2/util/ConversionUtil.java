package com.example.rdpocketpal2.util;

import com.example.rdpocketpal2.util.Constants.Element;

public class ConversionUtil {
    public static double poundsToKilograms(double pounds) {
        return pounds * 0.45359237;
    }

    public static double kilogramsToPounds(double kilograms) {
        return kilograms / 0.45359237;
    }

    public static double inchesToCentimeters(double inches) {
        return inches * 2.54;
    }

    public static double centimetersToInches(double centimeters) {
        return centimeters / 2.54;
    }

    public static double fahrenheitToCelsius(double tempFahrenheit) {
        return (tempFahrenheit - 32) * (5.0/9.0);
    }

    public static double gallonsToLiters(double gallons) {
        return gallons * 3.78541;
    }

    public static double gramsToMilliequivalents(@Element int element, double grams) {
        switch (element) {
            case Constants.CALCIUM:
                return gramsToMilliequivalents(Constants.CALCIUM_ATOMIC_WEIGHT
                        , Constants.CALCIUM_VALENCE
                        , grams);
            case Constants.CHLORINE:
                return gramsToMilliequivalents(Constants.CHLORINE_ATOMIC_WEIGHT
                        , Constants.CHLORINE_VALENCE
                        , grams);
            case Constants.MAGNESIUM:
                return gramsToMilliequivalents(Constants.MAGNESIUM_ATOMIC_WEIGHT
                        , Constants.MAGNESIUM_VALENCE
                        , grams);
            case Constants.PHOSPHORUS:
                return gramsToMilliequivalents(Constants.PHOSPHORUS_ATOMIC_WEIGHT
                        , Constants.PHOSPHORUS_VALENCE
                        , grams);
            case Constants.POTASSIUM:
                return gramsToMilliequivalents(Constants.POTASSIUM_ATOMIC_WEIGHT
                        , Constants.POTASSIUM_VALENCE
                        , grams);
            case Constants.SODIUM:
                return gramsToMilliequivalents(Constants.SODIUM_ATOMIC_WEIGHT
                        , Constants.SODIUM_VALENCE
                        , grams);
            default:
                return 0.0;
        }
    }

    public static double milliequivalentsToGrams(@Element int element, double milliequivalents) {
        switch (element) {
            case Constants.CALCIUM:
                return milliequivalentsToGrams(Constants.CALCIUM_ATOMIC_WEIGHT
                        , Constants.CALCIUM_VALENCE
                        , milliequivalents);
            case Constants.CHLORINE:
                return milliequivalentsToGrams(Constants.CHLORINE_ATOMIC_WEIGHT
                        , Constants.CHLORINE_VALENCE
                        , milliequivalents);
            case Constants.MAGNESIUM:
                return milliequivalentsToGrams(Constants.MAGNESIUM_ATOMIC_WEIGHT
                        , Constants.MAGNESIUM_VALENCE
                        , milliequivalents);
            case Constants.PHOSPHORUS:
                return milliequivalentsToGrams(Constants.PHOSPHORUS_ATOMIC_WEIGHT
                        , Constants.PHOSPHORUS_VALENCE
                        , milliequivalents);
            case Constants.POTASSIUM:
                return milliequivalentsToGrams(Constants.POTASSIUM_ATOMIC_WEIGHT
                        , Constants.POTASSIUM_VALENCE
                        , milliequivalents);
            case Constants.SODIUM:
                return milliequivalentsToGrams(Constants.SODIUM_ATOMIC_WEIGHT
                        , Constants.SODIUM_VALENCE
                        , milliequivalents);
            default:
                return 0.0;
        }
    }

    public static double milligramsToMilliequivalents(@Element int element, double milligrams) {
        switch (element) {
            case Constants.CALCIUM:
                return milligramsToMilliequivalents(Constants.CALCIUM_ATOMIC_WEIGHT
                        , Constants.CALCIUM_VALENCE
                        , milligrams);
            case Constants.CHLORINE:
                return milligramsToMilliequivalents(Constants.CHLORINE_ATOMIC_WEIGHT
                        , Constants.CHLORINE_VALENCE
                        , milligrams);
            case Constants.MAGNESIUM:
                return milligramsToMilliequivalents(Constants.MAGNESIUM_ATOMIC_WEIGHT
                        , Constants.MAGNESIUM_VALENCE
                        , milligrams);
            case Constants.PHOSPHORUS:
                return milligramsToMilliequivalents(Constants.PHOSPHORUS_ATOMIC_WEIGHT
                        , Constants.PHOSPHORUS_VALENCE
                        , milligrams);
            case Constants.POTASSIUM:
                return milligramsToMilliequivalents(Constants.POTASSIUM_ATOMIC_WEIGHT
                        , Constants.POTASSIUM_VALENCE
                        , milligrams);
            case Constants.SODIUM:
                return milligramsToMilliequivalents(Constants.SODIUM_ATOMIC_WEIGHT
                        , Constants.SODIUM_VALENCE
                        , milligrams);
            default:
                return 0.0;
        }
    }

    public static double milliequivalentsToMilligrams(@Element int element,
                                                      double milliequivalents) {
        switch (element) {
            case Constants.CALCIUM:
                return milliequivalentsToMilligrams(Constants.CALCIUM_ATOMIC_WEIGHT
                        , Constants.CALCIUM_VALENCE
                        , milliequivalents);
            case Constants.CHLORINE:
                return milliequivalentsToMilligrams(Constants.CHLORINE_ATOMIC_WEIGHT
                        , Constants.CHLORINE_VALENCE
                        , milliequivalents);
            case Constants.MAGNESIUM:
                return milliequivalentsToMilligrams(Constants.MAGNESIUM_ATOMIC_WEIGHT
                        , Constants.MAGNESIUM_VALENCE
                        , milliequivalents);
            case Constants.PHOSPHORUS:
                return milliequivalentsToMilligrams(Constants.PHOSPHORUS_ATOMIC_WEIGHT
                        , Constants.PHOSPHORUS_VALENCE
                        , milliequivalents);
            case Constants.POTASSIUM:
                return milliequivalentsToMilligrams(Constants.POTASSIUM_ATOMIC_WEIGHT
                        , Constants.POTASSIUM_VALENCE
                        , milliequivalents);
            case Constants.SODIUM:
                return milliequivalentsToMilligrams(Constants.SODIUM_ATOMIC_WEIGHT
                        , Constants.SODIUM_VALENCE
                        , milliequivalents);
            default:
                return 0.0;
        }
    }

    private static double gramsToMilliequivalents(double atomicWeight,
                                                  int valence,
                                                  double grams) {
        return milligramsToMilliequivalents(atomicWeight, valence, grams * 1000);
    }

    private static double milliequivalentsToGrams(double atomicWeight,
                                                  int valence,
                                                  double milliequivalents) {
        return milliequivalentsToMilligrams(atomicWeight, valence, milliequivalents) / 1000;
    }

    private static double milligramsToMilliequivalents(double atomicWeight,
                                                       int valence,
                                                       double milligrams) {
        return (milligrams * valence) / atomicWeight;
    }

    private static double milliequivalentsToMilligrams(double atomicWeight,
                                                       int valence,
                                                       double milliequivalents) {
        return (milliequivalents * atomicWeight) / valence;
    }
}
