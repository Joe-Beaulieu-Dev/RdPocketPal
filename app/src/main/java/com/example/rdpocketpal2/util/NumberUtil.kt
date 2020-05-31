package com.example.rdpocketpal2.util

import androidx.lifecycle.MutableLiveData

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
}