package com.example.rdpocketpal2.util

import org.junit.Assert.assertEquals
import org.junit.Test

class NumberUtilTest {
    // rounding values
    private val ROUND_INPUT: Double = 12.90154
    private val ROUND_ZERO_DIGITS = "13"
    private val ROUND_ONE_DIGIT = "12.9"
    private val ROUND_TWO_DIGITS = "12.9"
    private val ROUND_THREE_DIGITS = "12.902"
    private val ROUND_FOUR_DIGITS = "12.9015"
    private val ROUND_FIVE_DIGITS = "12.90154"

    // truncate values
    private val TRUNCATE_INPUT = 12.34
    private val TRUNCATE_ZERO_DIGITS = "12"
    private val TRUNCATE_ONE_DIGIT = "12.3"
    private val TRUNCATE_TWO_DIGITS = "12.34"
    private val TRUNCATE_THREE_DIGITS = "12.34"

    @Test
    fun round_isCorrect() {
        assertEquals(ROUND_ZERO_DIGITS, NumberUtil.round(ROUND_INPUT, 0))
        assertEquals(ROUND_ONE_DIGIT, NumberUtil.round(ROUND_INPUT, 1))
        assertEquals(ROUND_TWO_DIGITS, NumberUtil.round(ROUND_INPUT, 2))
        assertEquals(ROUND_THREE_DIGITS, NumberUtil.round(ROUND_INPUT, 3))
        assertEquals(ROUND_FOUR_DIGITS, NumberUtil.round(ROUND_INPUT, 4))
        assertEquals(ROUND_FIVE_DIGITS, NumberUtil.round(ROUND_INPUT, 5))
    }

    @Test
    fun truncate_isCorrect() {
        assertEquals(TRUNCATE_ZERO_DIGITS, NumberUtil.truncate(TRUNCATE_INPUT, 0))
        assertEquals(TRUNCATE_ONE_DIGIT, NumberUtil.truncate(TRUNCATE_INPUT, 1))
        assertEquals(TRUNCATE_TWO_DIGITS, NumberUtil.truncate(TRUNCATE_INPUT, 2))
        assertEquals(TRUNCATE_THREE_DIGITS, NumberUtil.truncate(TRUNCATE_INPUT, 3))
    }
}