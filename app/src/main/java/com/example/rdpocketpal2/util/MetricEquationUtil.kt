package com.example.rdpocketpal2.util

import kotlin.math.pow

object MetricEquationUtil {

    //region Predictive Equations
    @JvmStatic
    fun calculateBmrMifflin(@Sex sex: Int, weight: Double, height: Double, age: Int): Double {
        return when (sex) {
            MALE -> (10 * weight) + (6.25 * height) - (5 * age) + 5
            FEMALE -> (10 * weight) + (6.25 * height) - (5 * age) - 161
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateBmrBenedict(@Sex sex: Int, weight: Double, height: Double, age: Int): Double {
        return when (sex) {
            MALE -> 66 + (13.7 * weight) + (5 * height) - (6.8 * age)
            FEMALE -> 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age)
            else -> 0.0
        }
    }

    @JvmStatic
    fun calculateBmrPennState2003b(mifflinBmr: Double, tmax: Double, ve: Double): Double {
        return (mifflinBmr * 0.96) + (tmax * 167) + (ve * 31) - 6212
    }

    @JvmStatic
    fun calculateBmrPennState2010(mifflinBmr: Double, tmax: Double, ve: Double): Double {
        return (mifflinBmr * 0.71) + (tmax * 85) + (ve * 64) - 3085
    }

    @JvmStatic
    fun calculateBmrBrandi(benedictBmr: Double, heartRate: Double, ve: Double): Double {
        return (benedictBmr * 0.96) + (heartRate * 7) + (ve * 48) - 702
    }
    //endregion

    //region Quick Method
    @JvmStatic
    fun calculateQuickMethod(weight: Double, factor: Double): Double {
        return weight * factor
    }
    //endregion

    //region Anthropometrics
    @JvmStatic
    fun calculateBmi(weight: Double, height: Double): Double {
        return weight / (height / 100).pow(2.0)
    }

    @JvmStatic
    fun calculateIbwHamwi(sex: SexK, height: Double): Double {
        return when (sex) {
            SexK.Male -> calculateIbwMale(height)
            SexK.Female -> calculateIbwFemale(height)
        }
    }

    @JvmStatic
    private fun calculateIbwMale(height: Double): Double {
        return when {
            height >= 60 -> 106 + 6 * (height - 60)
            else -> 106 - (6 * (60 - height) / 2)
        }
    }

    @JvmStatic
    private fun calculateIbwFemale(height: Double): Double {
        return when {
            height >= 60 -> 100 + 5 * (height - 60)
            else -> 100 - (5 * (60 - height) / 2)
        }
    }

    @JvmStatic
    fun calculatePercentIbw(ibw: Double, weight: Double): Double {
        // check to avoid division by zero
        return if (ibw == 0.0) 0.0 else (weight / ibw) * 100
    }

    @JvmStatic
    fun calculateAdjustedIbw(ibw: Double, weight: Double): Double {
        return ((weight - ibw) / 4) + ibw
    }
    //endregion
}