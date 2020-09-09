package com.example.rdpocketpal2.util

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
    fun gramsToMilliequivalents(@Element element: Int, grams: Double): Double {
        // call lower level conversion method based on element
        return when (element) {
            CALCIUM ->
                gramsToMilliequivalents(CALCIUM_ATOMIC_WEIGHT, CALCIUM_VALENCE, grams)
            CHLORINE ->
                gramsToMilliequivalents(CHLORINE_ATOMIC_WEIGHT, CHLORINE_VALENCE, grams)
            MAGNESIUM ->
                gramsToMilliequivalents(MAGNESIUM_ATOMIC_WEIGHT, MAGNESIUM_VALENCE, grams)
            POTASSIUM ->
                gramsToMilliequivalents(POTASSIUM_ATOMIC_WEIGHT, POTASSIUM_VALENCE, grams)
            SODIUM ->
                gramsToMilliequivalents(SODIUM_ATOMIC_WEIGHT, SODIUM_VALENCE, grams)
            else -> 0.0
        }
    }

    @JvmStatic
    fun milliequivalentsToGrams(@Element element: Int, milliequivalents: Double): Double {
        // call lower level conversion method based on element
        return when (element) {
            CALCIUM ->
                milliequivalentsToGrams(CALCIUM_ATOMIC_WEIGHT, CALCIUM_VALENCE, milliequivalents)
            CHLORINE ->
                milliequivalentsToGrams(CHLORINE_ATOMIC_WEIGHT, CHLORINE_VALENCE, milliequivalents)
            MAGNESIUM ->
                milliequivalentsToGrams(MAGNESIUM_ATOMIC_WEIGHT, MAGNESIUM_VALENCE, milliequivalents)
            POTASSIUM ->
                milliequivalentsToGrams(POTASSIUM_ATOMIC_WEIGHT, POTASSIUM_VALENCE, milliequivalents)
            SODIUM ->
                milliequivalentsToGrams(SODIUM_ATOMIC_WEIGHT, SODIUM_VALENCE, milliequivalents)
            else -> 0.0
        }
    }

    @JvmStatic
    fun milligramsToMilliequivalents(@Element element: Int, milligrams: Double): Double {
        // call lower level conversion method based on element
        return when (element) {
            CALCIUM ->
                milligramsToMilliequivalents(CALCIUM_ATOMIC_WEIGHT, CALCIUM_VALENCE, milligrams)
            CHLORINE ->
                milligramsToMilliequivalents(CHLORINE_ATOMIC_WEIGHT, CHLORINE_VALENCE, milligrams)
            MAGNESIUM ->
                milligramsToMilliequivalents(MAGNESIUM_ATOMIC_WEIGHT, MAGNESIUM_VALENCE, milligrams)
            POTASSIUM ->
                milligramsToMilliequivalents(POTASSIUM_ATOMIC_WEIGHT, POTASSIUM_VALENCE, milligrams)
            SODIUM ->
                milligramsToMilliequivalents(SODIUM_ATOMIC_WEIGHT, SODIUM_VALENCE, milligrams)
            else -> 0.0
        }
    }

    @JvmStatic
    fun milliequivalentsToMilligrams(@Element element: Int, milliequivalents: Double): Double {
        // call lower level conversion method based on element
        return when (element) {
            CALCIUM ->
                milliequivalentsToMilligrams(CALCIUM_ATOMIC_WEIGHT, CALCIUM_VALENCE, milliequivalents)
            CHLORINE ->
                milliequivalentsToMilligrams(CHLORINE_ATOMIC_WEIGHT, CHLORINE_VALENCE, milliequivalents)
            MAGNESIUM ->
                milliequivalentsToMilligrams(MAGNESIUM_ATOMIC_WEIGHT, MAGNESIUM_VALENCE, milliequivalents)
            POTASSIUM ->
                milliequivalentsToMilligrams(POTASSIUM_ATOMIC_WEIGHT, POTASSIUM_VALENCE, milliequivalents)
            SODIUM ->
                milliequivalentsToMilligrams(SODIUM_ATOMIC_WEIGHT, SODIUM_VALENCE, milliequivalents)
            else -> 0.0
        }
    }

    private fun gramsToMilliequivalents(atomicWeight: Double, valence: Int, grams: Double): Double {
        // convert grams to milligrams and defer to milligram equation
        return milligramsToMilliequivalents(atomicWeight, valence, grams * 1000)
    }

    private fun milliequivalentsToGrams(atomicWeight: Double,
                                        valence: Int,
                                        milliequivalents: Double): Double {
        // defer calculation to milligram equation and convert result to grams
        return milliequivalentsToMilligrams(atomicWeight, valence, milliequivalents) / 1000

    }

    private fun milligramsToMilliequivalents(atomicWeight: Double,
                                             valence: Int,
                                             milligrams: Double): Double {
        // check for division by zero
        return if (atomicWeight == 0.0) 0.0 else (milligrams * valence) / atomicWeight

    }

    private fun milliequivalentsToMilligrams(atomicWeight: Double,
                                             valence: Int,
                                             milliequivalents: Double): Double {
        // check for division by zero
        return if (valence == 0) 0.0 else (milliequivalents * atomicWeight) / valence
    }

    @JvmStatic
    fun gramsToMillimoles(@Element element: Int, grams: Double): Double {
        return when(element) {
            PHOSPHORUS -> gramsToMillimoles(PHOSPHORUS_ATOMIC_WEIGHT, grams)
            else -> 0.0
        }
    }

    @JvmStatic
    fun millimolesToGrams(@Element element: Int, millimoles: Double): Double {
        return when(element) {
            PHOSPHORUS -> millimolesToGrams(PHOSPHORUS_ATOMIC_WEIGHT, millimoles)
            else -> 0.0
        }
    }

    @JvmStatic
    fun milligramsToMillimoles(@Element element: Int, milligrams: Double): Double {
        return when(element) {
            PHOSPHORUS -> milligramsToMillimoles(PHOSPHORUS_ATOMIC_WEIGHT, milligrams)
            else -> 0.0
        }
    }

    @JvmStatic
    fun millimolesToMilligrams(@Element element: Int, millimoles: Double): Double {
        return when(element) {
            PHOSPHORUS -> millimolesToMilligrams(PHOSPHORUS_ATOMIC_WEIGHT, millimoles)
            else -> 0.0
        }
    }

    private fun gramsToMillimoles(atomicWeight: Double, grams: Double): Double {
        // convert grams to milligrams and defer to milligram equation
        return milligramsToMillimoles(atomicWeight, grams * 1000)
    }

    private fun millimolesToGrams(atomicWeight: Double, millimoles: Double): Double {
        // defer calculation to milligram equation and convert result to grams
        return millimolesToMilligrams(atomicWeight, millimoles) / 1000
    }

    private fun milligramsToMillimoles(atomicWeight: Double, milligrams: Double): Double {
        // check for division by zero
        return if (atomicWeight == 0.0) 0.0 else milligrams / atomicWeight
    }

    private fun millimolesToMilligrams(atomicWeight: Double, millimoles: Double): Double {
        return millimoles * atomicWeight
    }
    //endregion
}