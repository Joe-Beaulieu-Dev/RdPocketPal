package com.example.rdpocketpal2.util

object ConversionUtilKotlin {

    @JvmStatic
    fun poundsToKilograms(pounds: Double): Double {
        return pounds * 0.45359237
    }
}