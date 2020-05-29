package com.example.rdpocketpal2.util

object CalculationUtilKotlin {

    @JvmStatic
    fun calculateQuickMethod(@Unit unit: Int, weight: Double, factor: Double): Double {
        return when (unit) {
            METRIC -> MetricEquationUtilKotlin.calculateQuickMethod(weight, factor)
            STANDARD -> MetricEquationUtilKotlin.calculateQuickMethod(
                    ConversionUtilKotlin.poundsToKilograms(weight), factor)
            else -> 0.0
        }
    }
}