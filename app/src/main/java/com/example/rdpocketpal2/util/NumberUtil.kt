package com.example.rdpocketpal2.util

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.model.UserPreferences
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

object NumberUtil {

    //region Parsing
    @JvmStatic
    fun parseDouble(liveData: MutableLiveData<String>): Double {
        val data: String? = liveData.value
        return data!!.toDouble()
    }

    @JvmStatic
    fun parseInt(liveData: MutableLiveData<String>): Int {
        val data: String? = liveData.value
        return data!!.toInt()
    }
    //endregion

    //region Type checks
    @JvmStatic
    fun isDouble(liveData: MutableLiveData<String>): Boolean {
        val data: String? = liveData.value
        return isDouble(data)
    }

    @JvmStatic
    fun isDouble(string: String?): Boolean {
        try {
            string!!.toDouble()
            return true
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        return false
    }
    //endregion

    //region Manipulation
    @JvmStatic
    fun roundOrTruncate(context: Context, prefs: UserPreferences?, num: Double): String {
        // prefs is null or not properly set, return raw Double as String
        return if (prefs?.reductionMethod == null || prefs.numericScale == null) {
            num.toString()
        } else {
            when (prefs.reductionMethod!!) {
                // round
                context.getString(R.string.key_rounding) -> round(num, prefs.numericScale!!)
                // truncate
                else -> truncate(num, prefs.numericScale!!)
            }
        }
    }

    @JvmStatic
    fun round(num: Double, scale: Int): String {
        // throw exception for invalid input
        if (scale < 0) {
            throw IllegalArgumentException(
                    "'places' parameter must be greater than 0. Actual value: $scale")
        }

        // round value
        var output = BigDecimal(num.toString())
        output = output.setScale(scale, RoundingMode.HALF_UP)

        // convert back to double and return
        return trim(output.toDouble())
    }

    @JvmStatic
    fun truncate(num: Double, scale: Int): String {
        // start pattern
        val builder: StringBuilder = StringBuilder("#")

        // append decimal places to pattern if applicable
        if (scale > 0 ) {
            builder.append(".")
            for (i in 0 until scale) {
                builder.append("#")
            }
        }

        // truncate
        val formatter = DecimalFormat(builder.toString())
        formatter.roundingMode = RoundingMode.DOWN

        // convert to double and return
        return trim(formatter.format(num).toDouble())
    }

    /**
     * Trims the ".0" off a double iff there are no digits afterwards.
     *
     * @param num the [Double] to potentially be trimmed
     */
    private fun trim(num: Double): String {
        // get decimal point index
        val numString = num.toString()
        val decimalIndex = numString.indexOf(".", 0)

        // return substring which trims the decimal places
        return if (decimalIndex >= 1
                && decimalIndex == numString.length - 2
                && numString[decimalIndex + 1].equals('0', false)) {
            numString.substring(0, decimalIndex)
        } else {
            numString
        }
    }
    //endregion
}