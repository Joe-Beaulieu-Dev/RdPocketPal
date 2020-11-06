package com.josephbeaulieu.rdpocketpal.model

import android.content.Context
import androidx.preference.PreferenceManager
import com.josephbeaulieu.rdpocketpal.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class PreferenceRepository {

    //region Java Usage
    /**
     * Get Decimal Reduction Method and Numeric Scale preferences. Intended for usage in Java code.
     * Launches a new coroutine on [Dispatchers.Main] and utilizes a callback to return value.
     *
     * @param context  The [Context] of the desired preferences
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

    fun getUserThroughDisclaimer(context: Context, listener: CoroutineCallbackListener) {
        CoroutineScope(Dispatchers.Main).launch {
            listener.onCoroutineFinished(getUserThroughDisclaimer(context))
        }
    }
    //endregion

    //region Kotlin Usage
    /**
     * Get Decimal Reduction Method and Numeric Scale preferences, which are returned in a
     * [QueryResult.Success<UserPreferences>] object. If any preference cannot be retrieved, then
     * this method will return a [QueryResult.Failure] object containing an [Exception].
     *
     * @param context The [Context] of the desired preferences
     */
    suspend fun getAllNumericSettings(context: Context): QueryResult<UserPreferences> {
        return withContext(Dispatchers.IO) {
            val decimalReductionMethod = getDecimalReductionMethod(context)
            val scale = getNumericScale(context)

            // return a UserPreferences object if pref retrieval was successful
            return@withContext if (decimalReductionMethod is QueryResult.Success
                    && scale is QueryResult.Success) {
                val pref = UserPreferences(decimalReductionMethod.data, scale.data)
                QueryResult.Success(pref)
            } else {
                QueryResult.Failure(IOException("getAllNumericSettings() failed to query"))
            }
        }
    }

    /**
     * Get Decimal Reduction Method preference. Defaults to rounding if pref not found. Intended
     * for usage in Kotlin code.
     *
     * @param context  The context of the desired preferences
     */
    private suspend fun getDecimalReductionMethod(context: Context): QueryResult<String>? {
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
    private suspend fun getNumericScale(context: Context): QueryResult<Int>? {
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

    private suspend fun getUserThroughDisclaimer(context: Context): QueryResult<Boolean>? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                // get pref
                val sharedPrefs = context.getSharedPreferences(
                        context.getString(R.string.key_disclaimer_pref_file), Context.MODE_PRIVATE)
                val pref = sharedPrefs.getBoolean(
                        context.getString(R.string.key_user_through_disclaimer), false)

                //return pref
                QueryResult.Success(pref)
            } catch (e: ClassCastException) {
                e.printStackTrace()
                QueryResult.Failure(e)
            } catch (e: NullPointerException) {
                e.printStackTrace()
                QueryResult.Failure(e)
            }
        }
    }

    fun setUserThroughDisclaimer(context: Context, isUserThroughDisclaimer: Boolean) {
        val sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_disclaimer_pref_file)
                , Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(context.getString(R.string.key_user_through_disclaimer)
                    , isUserThroughDisclaimer)
            apply()
        }
    }
}