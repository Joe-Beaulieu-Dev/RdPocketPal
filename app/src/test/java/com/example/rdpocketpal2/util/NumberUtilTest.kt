package com.example.rdpocketpal2.util

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.model.UserPreferences
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

//region Test data
// general values
private const val DELTA = 0.001

// Parsing values
private val PARSE_DOUBLE_INPUT: MutableLiveData<String> = MutableLiveData("1.2")
private const val PARSE_DOUBLE_OUTPUT = 1.2
private val PARSE_INT_INPUT: MutableLiveData<String> = MutableLiveData("1")
private const val PARSE_INT_OUTPUT = 1

// Type check values
private val IS_DOUBLE_LIVEDATA_VALID_INPUT: MutableLiveData<String> = MutableLiveData("1.2")
private val IS_DOUBLE_LIVEDATA_INVALID_INPUT_NOT_NULL: MutableLiveData<String> = MutableLiveData(".")
private val IS_DOUBLE_LIVEDATA_INVALID_INPUT_NULL: MutableLiveData<String> = MutableLiveData()
private const val IS_DOUBLE_STRING_VALID_INPUT = "1.2"
private const val IS_DOUBLE_STRING_INVALID_INPUT_NOT_NULL = "."
private val IS_DOUBLE_STRING_INVALID_INPUT_NULL: String? = null

// Round/Truncate values
private const val ROUNDING_KEY = "Rounding"
private const val TRUNCATION_KEY = "Truncation"
private const val INVALID_REDUCTION_METHOD_KEY = "Roundation"
private const val INVALID_SCALE = -1
// Rounding values
private const val ROUND_INPUT = 12.90154
private const val ROUND_ZERO_DIGITS = "13"
private const val ROUND_ONE_DIGIT = "12.9"
private const val ROUND_TWO_DIGITS = "12.9"
private const val ROUND_THREE_DIGITS = "12.902"
private const val ROUND_FOUR_DIGITS = "12.9015"
private const val ROUND_FIVE_DIGITS = "12.90154"
// Truncation values
private const val TRUNCATE_INPUT = 12.34
private const val TRUNCATE_ZERO_DIGITS = "12"
private const val TRUNCATE_ONE_DIGIT = "12.3"
private const val TRUNCATE_TWO_DIGITS = "12.34"
private const val TRUNCATE_THREE_DIGITS = "12.34"
//endregion

@RunWith(MockitoJUnitRunner::class)
class NumberUtilTest {

    @Mock
    private val context = mock(Context::class.java)

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
    fun roundOrTruncate_round_isCorrect() {
        setUpContextMockSettings()
        val prefs = UserPreferences(ROUNDING_KEY, 0)

        // zero decimal places
        assertEquals(ROUND_ZERO_DIGITS, NumberUtil.roundOrTruncate(context, prefs, ROUND_INPUT))

        // one decimal place
        prefs.numericScale = 1
        assertEquals(ROUND_ONE_DIGIT, NumberUtil.roundOrTruncate(context, prefs, ROUND_INPUT))

        // two decimal places
        prefs.numericScale = 2
        assertEquals(ROUND_TWO_DIGITS, NumberUtil.roundOrTruncate(context, prefs, ROUND_INPUT))

        // three decimal places
        prefs.numericScale = 3
        assertEquals(ROUND_THREE_DIGITS, NumberUtil.roundOrTruncate(context, prefs, ROUND_INPUT))

        // four decimal places
        prefs.numericScale = 4
        assertEquals(ROUND_FOUR_DIGITS, NumberUtil.roundOrTruncate(context, prefs, ROUND_INPUT))

        // five decimal places
        prefs.numericScale = 5
        assertEquals(ROUND_FIVE_DIGITS, NumberUtil.roundOrTruncate(context, prefs, ROUND_INPUT))
    }

    @Test
    fun roundOrTruncate_truncate_isCorrect() {
        setUpContextMockSettings()
        val prefs = UserPreferences(TRUNCATION_KEY, 0)

        // zero decimal places
        assertEquals(TRUNCATE_ZERO_DIGITS, NumberUtil.roundOrTruncate(context, prefs, TRUNCATE_INPUT))

        // one decimal place
        prefs.numericScale = 1
        assertEquals(TRUNCATE_ONE_DIGIT, NumberUtil.roundOrTruncate(context, prefs, TRUNCATE_INPUT))

        // two decimal places
        prefs.numericScale = 2
        assertEquals(TRUNCATE_TWO_DIGITS, NumberUtil.roundOrTruncate(context, prefs, TRUNCATE_INPUT))

        // three decimal places
        prefs.numericScale = 3
        assertEquals(TRUNCATE_THREE_DIGITS, NumberUtil.roundOrTruncate(context, prefs, TRUNCATE_INPUT))
    }

    @Test
    fun roundOrTruncate_invalidReductionMethod_returnsRawDoubleToString() {
        setUpContextMockSettings()
        val prefs = UserPreferences(INVALID_REDUCTION_METHOD_KEY, INVALID_SCALE)

        // should return unaltered Double as a String
        assertEquals(ROUND_INPUT.toString()
                , NumberUtil.roundOrTruncate(context, prefs, ROUND_INPUT))
    }

    @Test
    fun roundOrTruncate_round_invalidScale_returnsRawDoubleToString() {
        setUpContextMockSettings()
        val prefs = UserPreferences(ROUNDING_KEY, INVALID_SCALE)

        // should return unaltered Double as a String
        assertEquals(ROUND_INPUT.toString()
                , NumberUtil.roundOrTruncate(context, prefs, ROUND_INPUT))
    }

    @Test
    fun roundOrTruncate_truncate_invalidScale_returnsRawDoubleToString() {
        setUpContextMockSettings()
        val prefs = UserPreferences(TRUNCATION_KEY, INVALID_SCALE)

        // should return unaltered Double as a String
        assertEquals(TRUNCATE_INPUT.toString()
                , NumberUtil.roundOrTruncate(context, prefs, TRUNCATE_INPUT))
    }

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
    fun round_invalidScale_returnsRawDoubleToString() {
        assertEquals(ROUND_INPUT.toString(), NumberUtil.round(ROUND_INPUT, INVALID_SCALE))
    }

    @Test
    fun truncate_isCorrect() {
        assertEquals(TRUNCATE_ZERO_DIGITS, NumberUtil.truncate(TRUNCATE_INPUT, 0))
        assertEquals(TRUNCATE_ONE_DIGIT, NumberUtil.truncate(TRUNCATE_INPUT, 1))
        assertEquals(TRUNCATE_TWO_DIGITS, NumberUtil.truncate(TRUNCATE_INPUT, 2))
        assertEquals(TRUNCATE_THREE_DIGITS, NumberUtil.truncate(TRUNCATE_INPUT, 3))
    }

    @Test
    fun truncate_invalidScale_returnsRawDoubleToString() {
        assertEquals(TRUNCATE_INPUT.toString(), NumberUtil.round(TRUNCATE_INPUT, INVALID_SCALE))
    }
    //endregion

    //region Helper methods
    private fun setUpContextMockSettings() {
        `when`(context.getString(R.string.key_rounding)).thenReturn(ROUNDING_KEY)
        `when`(context.getString(R.string.key_truncation)).thenReturn(TRUNCATION_KEY)
    }
    //endregion
}