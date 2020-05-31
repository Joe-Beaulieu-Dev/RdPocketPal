package com.example.rdpocketpal2.util

object CalculationUtilKotlin {

    //region Predictive Equations
    @JvmStatic
    fun calculateBmrMifflin(@Unit unit: Int, @Sex sex: Int,
                            weight: Double, height: Double, age: Int): Double {
        return when (unit) {
            METRIC -> MetricEquationUtilKotlin.calculateBmrMifflin(sex, weight, height, age)
            STANDARD -> MetricEquationUtilKotlin.calculateBmrMifflin(sex
                    , ConversionUtilKotlin.poundsToKilograms(weight)
                    , ConversionUtilKotlin.inchesToCentimeters(height)
                    , age)
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateBmrBenedict(@Unit unit: Int, @Sex sex: Int,
                            weight: Double, height: Double, age: Int): Double {
        return when (unit) {
            METRIC -> MetricEquationUtilKotlin.calculateBmrBenedict(sex, weight, height, age)
            STANDARD -> MetricEquationUtilKotlin.calculateBmrBenedict(sex
                    , ConversionUtilKotlin.poundsToKilograms(weight)
                    , ConversionUtilKotlin.inchesToCentimeters(height)
                    , age)
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateBmrPennState2003b(@Unit unit: Int, mifflinBmr: Double, tmax: Double, ve: Double): Double {
        return when (unit) {
            METRIC -> MetricEquationUtilKotlin.calculateBmrPennState2003b(mifflinBmr, tmax, ve)
            STANDARD -> MetricEquationUtilKotlin.calculateBmrPennState2003b(mifflinBmr
                    , ConversionUtilKotlin.fahrenheitToCelsius(tmax)
                    , ConversionUtilKotlin.gallonsToLiters(ve))
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateBmrPennState2010(@Unit unit: Int, mifflinBmr: Double, tmax: Double, ve: Double): Double {
        return when (unit) {
            METRIC -> MetricEquationUtilKotlin.calculateBmrPennState2010(mifflinBmr, tmax, ve)
            STANDARD -> MetricEquationUtilKotlin.calculateBmrPennState2010(mifflinBmr
                    , ConversionUtilKotlin.fahrenheitToCelsius(tmax)
                    , ConversionUtilKotlin.gallonsToLiters(ve))
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateBmrBrandi(@Unit unit: Int, benedictBmr: Double, heartRate: Double, ve: Double): Double {
        return when (unit) {
            METRIC -> MetricEquationUtilKotlin.calculateBmrBrandi(benedictBmr, heartRate, ve)
            STANDARD -> MetricEquationUtilKotlin.calculateBmrBrandi(benedictBmr
                    , heartRate
                    , ConversionUtilKotlin.gallonsToLiters(ve))
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateCalorieMin(bmr: Double, activityFactorMin: Double): Double {
        return bmr * activityFactorMin
    }

    @JvmStatic
    fun calculateCalorieMax(bmr: Double, activityFactorMax: Double): Double {
        return bmr * activityFactorMax
    }
    //endregion

    //region Quick Method
    @JvmStatic
    fun calculateQuickMethod(@Unit unit: Int, weight: Double, factor: Double): Double {
        return when (unit) {
            METRIC -> MetricEquationUtilKotlin.calculateQuickMethod(weight, factor)
            STANDARD -> MetricEquationUtilKotlin.calculateQuickMethod(
                    ConversionUtilKotlin.poundsToKilograms(weight), factor)
            else -> 0.0
        }
    }
    //endregion
}