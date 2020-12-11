package com.octrobi.rdpocketpal.util

object CalculationUtil {

    //region Predictive Equations
    @JvmStatic
    fun calculateBmrMifflin(unit: Unit, sex: Sex,
                            weight: Double, height: Double, age: Int): Double {
        return when (unit) {
            Unit.METRIC -> MetricEquationUtil.calculateBmrMifflin(sex, weight, height, age)
            Unit.STANDARD -> MetricEquationUtil.calculateBmrMifflin(sex
                    , ConversionUtil.poundsToKilograms(weight)
                    , ConversionUtil.inchesToCentimeters(height)
                    , age)
        }
    }

    @JvmStatic
    fun calculateBmrBenedict(unit: Unit, sex: Sex,
                             weight: Double, height: Double, age: Int): Double {
        return when (unit) {
            Unit.METRIC -> MetricEquationUtil.calculateBmrBenedict(sex, weight, height, age)
            Unit.STANDARD -> MetricEquationUtil.calculateBmrBenedict(sex
                    , ConversionUtil.poundsToKilograms(weight)
                    , ConversionUtil.inchesToCentimeters(height)
                    , age)
        }
    }

    @JvmStatic
    fun calculateBmrPennState2003b(unit: Unit, mifflinBmr: Double, tmax: Double, ve: Double): Double {
        return when (unit) {
            Unit.METRIC -> MetricEquationUtil.calculateBmrPennState2003b(mifflinBmr, tmax, ve)
            Unit.STANDARD -> MetricEquationUtil.calculateBmrPennState2003b(mifflinBmr
                    , ConversionUtil.fahrenheitToCelsius(tmax)
                    , ve)
        }
    }

    @JvmStatic
    fun calculateBmrPennState2010(unit: Unit, mifflinBmr: Double, tmax: Double, ve: Double): Double {
        return when (unit) {
            Unit.METRIC -> MetricEquationUtil.calculateBmrPennState2010(mifflinBmr, tmax, ve)
            Unit.STANDARD -> MetricEquationUtil.calculateBmrPennState2010(mifflinBmr
                    , ConversionUtil.fahrenheitToCelsius(tmax)
                    , ve)
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
    fun calculateQuickMethod(unit: Unit, weight: Double, factor: Double): Double {
        return when (unit) {
            Unit.METRIC -> MetricEquationUtil.calculateQuickMethod(weight, factor)
            Unit.STANDARD -> MetricEquationUtil.calculateQuickMethod(
                    ConversionUtil.poundsToKilograms(weight), factor)
        }
    }
    //endregion

    //region Anthropometrics
    @JvmStatic
    fun calculateBmi(unit: Unit, weight: Double, height: Double): Double {
        return when (unit) {
            Unit.METRIC -> MetricEquationUtil.calculateBmi(weight, height)
            Unit.STANDARD -> MetricEquationUtil.calculateBmi(
                    ConversionUtil.poundsToKilograms(weight)
                    , ConversionUtil.inchesToCentimeters(height))
        }
    }

    @JvmStatic
    fun calculateIbwHamwi(unit: Unit, sex: Sex, height: Double): Double {
        return when (unit) {
            Unit.METRIC -> ConversionUtil.poundsToKilograms(
                    MetricEquationUtil.calculateIbwHamwi(sex
                            , ConversionUtil.centimetersToInches(height)))
            Unit.STANDARD -> MetricEquationUtil.calculateIbwHamwi(sex, height)
        }
    }

    @JvmStatic
    fun calculatePercentIbw(unit: Unit, sex: Sex, weight: Double, height: Double): Double {
        // unit doesn't actually matter here, except for calculating the base IBW
        return MetricEquationUtil.calculatePercentIbw(calculateIbwHamwi(unit, sex, height), weight)
    }

    @JvmStatic
    fun calculateAdjustedIbw(unit: Unit, sex: Sex, weight: Double, height: Double): Double {
        return when (unit) {
            Unit.METRIC -> MetricEquationUtil.calculateAdjustedIbw(
                    calculateIbwHamwi(unit, sex, height)
                    , weight)
            Unit.STANDARD -> ConversionUtil.kilogramsToPounds(
                    MetricEquationUtil.calculateAdjustedIbw(ConversionUtil.poundsToKilograms(
                            calculateIbwHamwi(unit, sex, height))
                            , ConversionUtil.poundsToKilograms(weight)))
        }
    }
    //endregion
}