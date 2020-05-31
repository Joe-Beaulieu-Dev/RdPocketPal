package com.example.rdpocketpal2.util

import androidx.lifecycle.MutableLiveData

object NumberUtilKotlin {

    @JvmStatic
    fun parseDouble(liveData: MutableLiveData<String>): Double {
        val data: String? = liveData.value
        return data!!.toDouble()
    }

    @JvmStatic
    fun isDouble(liveData: MutableLiveData<String>): Boolean {
        val data: String? = liveData.value
        try {
            data!!.toDouble()
            return true
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: KotlinNullPointerException) {
            e.printStackTrace()
        }
        return false
    }
}