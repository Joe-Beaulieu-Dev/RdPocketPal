package com.example.rdpocketpal2.util

object CalculationUtil {

    //region Predictive Equations
    @JvmStatic
    fun calculateBmrMifflin(@Unit unit: Int, @Sex sex: Int,
                            weight: Double, height: Double, age: Int): Double {
        return when (unit) {
            METRIC -> MetricEquationUtil.calculateBmrMifflin(sex, weight, height, age)
            STANDARD -> MetricEquationUtil.calculateBmrMifflin(sex
                    , ConversionUtil.poundsToKilograms(weight)
                    , ConversionUtil.inchesToCentimeters(height)
                    , age)
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateBmrBenedict(@Unit unit: Int, @Sex sex: Int,
                            weight: Double, height: Double, age: Int): Double {
        return when (unit) {
            METRIC -> MetricEquationUtil.calculateBmrBenedict(sex, weight, height, age)
            STANDARD -> MetricEquationUtil.calculateBmrBenedict(sex
                    , ConversionUtil.poundsToKilograms(weight)
                    , ConversionUtil.inchesToCentimeters(height)
                    , age)
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateBmrPennState2003b(@Unit unit: Int, mifflinBmr: Double, tmax: Double, ve: Double): Double {
        return when (unit) {
            METRIC -> MetricEquationUtil.calculateBmrPennState2003b(mifflinBmr, tmax, ve)
            STANDARD -> MetricEquationUtil.calculateBmrPennState2003b(mifflinBmr
                    , ConversionUtil.fahrenheitToCelsius(tmax)
                    , ConversionUtil.gallonsToLiters(ve))
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateBmrPennState2010(@Unit unit: Int, mifflinBmr: Double, tmax: Double, ve: Double): Double {
        return when (unit) {
            METRIC -> MetricEquationUtil.calculateBmrPennState2010(mifflinBmr, tmax, ve)
            STANDARD -> MetricEquationUtil.calculateBmrPennState2010(mifflinBmr
                    , ConversionUtil.fahrenheitToCelsius(tmax)
                    , ConversionUtil.gallonsToLiters(ve))
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateBmrBrandi(@Unit unit: Int, benedictBmr: Double, heartRate: Double, ve: Double): Double {
        return when (unit) {
            METRIC -> MetricEquationUtil.calculateBmrBrandi(benedictBmr, heartRate, ve)
            STANDARD -> MetricEquationUtil.calculateBmrBrandi(benedictBmr
                    , heartRate
                    , ConversionUtil.gallonsToLiters(ve))
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
            METRIC -> MetricEquationUtil.calculateQuickMethod(weight, factor)
            STANDARD -> MetricEquationUtil.calculateQuickMethod(
                    ConversionUtil.poundsToKilograms(weight), factor)
            else -> 0.0
        }
    }
    //endregion
}