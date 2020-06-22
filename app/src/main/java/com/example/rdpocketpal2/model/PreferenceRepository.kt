package com.example.rdpocketpal2.model

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.util.CoroutineCallbackListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class PreferenceRepository() {

    //region Java Usage
    /**
     * Get Decimal Reduction Method and Numeric Scale preferences. Intended for usage in Java code.
     * Launches a new coroutine on [Dispatchers.Main] and utilizes a callback to return value.
     *
     * @param context  The context of the desired preferences
     * @param listener The listener whose callback will be invoked upon preference retrieval
     */
    fun getAllNumericSettings(context: Context, listener: CoroutineCallbackListener) {
        CoroutineScope(Dispatchers.Main).launch {
            // get preferences
            val decimalReductionMethod = getDecimalReductionMethod(context)
            val numericScale = getNumericScale(context)

            // prepare result
            val result = if (decimalReductionMethod is QueryResult.Success
                    && numericScale is QueryResult.Success) {
                // create pref object and set values
                val pref = UserPreferences().apply {
                    reductionMethod = decimalReductionMethod.data
                    this.numericScale = numericScale.data
                }

                // return Success with UserPreferences object
                QueryResult.Success(pref)
            } else {
                // return Failure with exception
                QueryResult.Failure(IOException("getAllNumericSettings() failed to query"))
            }

            // return results via callback
            listener.onCoroutineFinished(result)
        }
    }
    //endregion

    //region Kotlin Usage
    /**
     * Get Decimal Reduction Method preference. Defaults to rounding if pref not found. Intended
     * for usage in Kotlin code.
     *
     * @param context  The context of the desired preferences
     */
    suspend fun getDecimalReductionMethod(context: Context): QueryResult<String>? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                // get preference
                val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
                val pref = sharedPrefs!!.getString(
                        context.getString(R.string.key_decimal_reduction_method)
                        , context.getString(R.string.key_rounding))!!

                // assign QueryResult containing pref as try's return value
                QueryResult.Success(pref)
            } catch (e: ClassCastException) {
                // print stack trace, assign QueryResult containing exception as try's return value
                e.printStackTrace()
                QueryResult.Failure(e)
            } catch (e: NullPointerException) {
                // print stack trace, assign QueryResult containing exception as try's return value
                e.printStackTrace()
                QueryResult.Failure(e)
            }
        }
    }

    /**
     * Get Numeric Scale preference. Defaults to 2 if pref not found. Intended for usage in
     * Kotlin code.
     *
     * @param context  The context of the desired preferences
     */
    suspend fun getNumericScale(context: Context): QueryResult<Int>? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                // get preference
                val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
                val pref = sharedPrefs!!.getInt(
                        context.getString(R.string.key_numeric_scale)
                        , 2)

                // assign QueryResult containing pref as try's return value
                QueryResult.Success(pref)
            } catch (e: ClassCastException) {
                // print stack trace, assign QueryResult containing exception as try's return value
                e.printStackTrace()
                QueryResult.Failure(e)
            } catch (e: NullPointerException) {
                // print stack trace, assign QueryResult containing exception as try's return value
                e.printStackTrace()
                QueryResult.Failure(e)
            }
        }
    }
    //endregion
}