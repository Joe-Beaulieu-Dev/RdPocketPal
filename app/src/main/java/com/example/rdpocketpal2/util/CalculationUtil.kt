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
                    , ve)
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateBmrPennState2010(@Unit unit: Int, mifflinBmr: Double, tmax: Double, ve: Double): Double {
        return when (unit) {
            METRIC -> MetricEquationUtil.calculateBmrPennState2010(mifflinBmr, tmax, ve)
            STANDARD -> MetricEquationUtil.calculateBmrPennState2010(mifflinBmr
                    , ConversionUtil.fahrenheitToCelsius(tmax)
                    , ve)
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateBmrBrandi(benedictBmr: Double, heartRate: Double, ve: Double): Double {
        return MetricEquationUtil.calculateBmrBrandi(benedictBmr, heartRate, ve)
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

    //region Anthropometrics
    @JvmStatic
    fun calculateBmi(unit: UnitK, weight: Double, height: Double): Double {
        return when (unit) {
            is UnitK.Metric -> MetricEquationUtil.calculateBmi(weight, height)
            is UnitK.Standard -> MetricEquationUtil.calculateBmi(
                    ConversionUtil.poundsToKilograms(weight)
                    , ConversionUtil.inchesToCentimeters(height))
        }
    }

    @JvmStatic
    fun calculateIbwHamwi(unit: UnitK, sex: SexK, height: Double): Double {
        return when (unit) {
            is UnitK.Metric -> ConversionUtil.poundsToKilograms(
                    MetricEquationUtil.calculateIbwHamwi(sex
                            , ConversionUtil.centimetersToInches(height)))
            is UnitK.Standard -> MetricEquationUtil.calculateIbwHamwi(sex, height)
        }
    }

    @JvmStatic
    fun calculatePercentIbw(unit: UnitK, sex: SexK, weight: Double, height: Double): Double {
        // unit doesn't actually matter here, except for calculating the base IBW
        return MetricEquationUtil.calculatePercentIbw(calculateIbwHamwi(unit, sex, height), weight)
    }

    @JvmStatic
    fun calculateAdjustedIbw(unit: UnitK, sex: SexK, weight: Double, height: Double): Double {
        return when (unit) {
            UnitK.Metric -> MetricEquationUtil.calculateAdjustedIbw(
                    calculateIbwHamwi(unit, sex, height)
                    , weight)
            UnitK.Standard -> ConversionUtil.kilogramsToPounds(
                    MetricEquationUtil.calculateAdjustedIbw(ConversionUtil.poundsToKilograms(
                            calculateIbwHamwi(unit, sex, height))
                            , ConversionUtil.poundsToKilograms(weight)))
        }
    }
    //endregion
}