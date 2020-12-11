package com.octrobi.rdpocketpal.util

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
}