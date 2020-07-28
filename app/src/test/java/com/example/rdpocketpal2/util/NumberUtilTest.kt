package com.example.rdpocketpal2.util

import androidx.lifecycle.MutableLiveData
import org.junit.Assert.*
import org.junit.Test

class NumberUtilTest {
    //region Test data
    // general values
    private val DELTA = 0.001

    // Parsing values
    private val PARSE_DOUBLE_INPUT: MutableLiveData<String> = MutableLiveData("1.2")
    private val PARSE_DOUBLE_OUTPUT = 1.2
    private val PARSE_INT_INPUT: MutableLiveData<String> = MutableLiveData("1")
    private val PARSE_INT_OUTPUT = 1

    // Type check values
    private val IS_DOUBLE_LIVEDATA_VALID_INPUT: MutableLiveData<String> = MutableLiveData("1.2")
    private val IS_DOUBLE_LIVEDATA_INVALID_INPUT_NOT_NULL: MutableLiveData<String> = MutableLiveData(".")
    private val IS_DOUBLE_LIVEDATA_INVALID_INPUT_NULL: MutableLiveData<String> = MutableLiveData()
    private val IS_DOUBLE_STRING_VALID_INPUT = "1.2"
    private val IS_DOUBLE_STRING_INVALID_INPUT_NOT_NULL = "."
    private val IS_DOUBLE_STRING_INVALID_INPUT_NULL: String? = null

    // Rounding values
    private val ROUND_INPUT = 12.90154
    private val ROUND_ZERO_DIGITS = "13"
    private val ROUND_ONE_DIGIT = "12.9"
    private val ROUND_TWO_DIGITS = "12.9"
    private val ROUND_THREE_DIGITS = "12.902"
    private val ROUND_FOUR_DIGITS = "12.9015"
    private val ROUND_FIVE_DIGITS = "12.90154"

    // Truncation values
    private val TRUNCATE_INPUT = 12.34
    private val TRUNCATE_ZERO_DIGITS = "12"
    private val TRUNCATE_ONE_DIGIT = "12.3"
    private val TRUNCATE_TWO_DIGITS = "12.34"
    private val TRUNCATE_THREE_DIGITS = "12.34"
    //endregion

    //region Parsing
    @Test
    fun parseDouble_validDouble_isCorrect() {
        assertEquals(PARSE_DOUBLE_OUTPUT, NumberUtil.parseDouble(PARSE_DOUBLE_INPUT), DELTA)
    }

    @Test
    fun parseInt_validInt_isCorrect() {
        assertEquals(PARSE_INT_OUTPUT, NumberUtil.parseInt(PARSE_INT_INPUT))
    }
    //endregion

    //region Type checks
    @Test
    fun isDouble_validDouble_LiveData_isCorrect() {
        assertTrue(NumberUtil.isDouble(IS_DOUBLE_LIVEDATA_VALID_INPUT))
    }

    @Test
    fun isDouble_invalidDouble_notNull_LiveData_isCorrect() {
        assertFalse(NumberUtil.isDouble(IS_DOUBLE_LIVEDATA_INVALID_INPUT_NOT_NULL))
    }

    @Test
    fun isDouble_invalidDouble_nullString_LiveData_isCorrect() {
        assertFalse(NumberUtil.isDouble(IS_DOUBLE_LIVEDATA_INVALID_INPUT_NULL))
    }

    @Test
    fun isDouble_validDouble_String_isCorrect() {
        assertTrue(NumberUtil.isDouble(IS_DOUBLE_STRING_VALID_INPUT))
    }

    @Test
    fun isDouble_invalidDouble_notNull_String_isCorrect() {
        assertFalse(NumberUtil.isDouble(IS_DOUBLE_STRING_INVALID_INPUT_NOT_NULL))
    }

    @Test
    fun isDouble_invalidDouble_nullString_String_isCorrect() {
        assertFalse(NumberUtil.isDouble(IS_DOUBLE_STRING_INVALID_INPUT_NULL))
    }
    //endregion

    //region Manipulation
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
    //endregion
}