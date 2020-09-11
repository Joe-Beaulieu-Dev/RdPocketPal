package com.example.rdpocketpal2.util

import com.example.rdpocketpal2.conversions.Element

object ConversionUtil {

    //region Weight
    @JvmStatic
    fun poundsToKilograms(pounds: Double): Double {
        return pounds * 0.45359237
    }

    @JvmStatic
    fun kilogramsToPounds(kilograms: Double): Double {
        return kilograms / 0.45359237
    }
    //endregion

    //region Length
    @JvmStatic
    fun inchesToCentimeters(inches: Double): Double {
        return inches * 2.54
    }

    @JvmStatic
    fun centimetersToInches(centimeters: Double): Double {
        return centimeters / 2.54
    }
    //endregion

    //region Temperature
    @JvmStatic
    fun fahrenheitToCelsius(tempFahrenheit: Double): Double {
        return (tempFahrenheit - 32) * (5.0 / 9.0)
    }

    @JvmStatic
    fun celsiusToFahrenheit(tempCelsius: Double): Double {
        return (tempCelsius * (9.0 / 5.0)) + 32
    }
    //endregion

    //region Fluid
    @JvmStatic
    fun gallonsToLiters(gallons: Double): Double {
        return gallons * 3.78541
    }

    @JvmStatic
    fun litersToGallons(liters: Double): Double {
        // sources online say litres * 0.26417, but why not
        // just reverse the universally accepted G -> L?
        return liters / 3.78541
    }
    //endregion

    //region Chemistry
    @JvmStatic
    fun gramsToMilliequivalents(element: Element, grams: Double): Double {
        // convert grams to milligrams and defer to milligram equation
        return milligramsToMilliequivalents(element, grams * 1000)
    }

    @JvmStatic
    fun milliequivalentsToGrams(element: Element, milliequivalents: Double): Double {
        // defer calculation to milligram equation and convert result to grams
        return milliequivalentsToMilligrams(element, milliequivalents) / 1000

    }

    @JvmStatic
    fun milligramsToMilliequivalents(element: Element, milligrams: Double): Double {
        // check for division by zero
        return if (element.atomicWeight == 0.0) {
            0.0
        } else {
            (milligrams * element.valence) / element.atomicWeight
        }

    }

    @JvmStatic
    fun milliequivalentsToMilligrams(element: Element, milliequivalents: Double): Double {
        // check for division by zero
        return if (element.valence == 0) {
            0.0
        } else {
            (milliequivalents * element.atomicWeight) / element.valence
        }
    }

    @JvmStatic
    fun gramsToMillimoles(element: Element, grams: Double): Double {
        // convert grams to milligrams and defer to milligram equation
        return milligramsToMillimoles(element, grams * 1000)
    }

    @JvmStatic
    fun millimolesToGrams(element: Element, millimoles: Double): Double {
        // defer calculation to milligram equation and convert result to grams
        return millimolesToMilligrams(element, millimoles) / 1000
    }

    @JvmStatic
    fun milligramsToMillimoles(element: Element, milligrams: Double): Double {
        // check for division by zero
        return if (element.atomicWeight == 0.0) 0.0 else milligrams / element.atomicWeight
    }

    @JvmStatic
    fun millimolesToMilligrams(element: Element, millimoles: Double): Double {
        return millimoles * element.atomicWeight
    }
    //endregion
}