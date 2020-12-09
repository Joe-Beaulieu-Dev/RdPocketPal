package com.octrobi.rdpocketpal.util

import android.content.Context
import android.content.pm.ApplicationInfo
import com.facebook.stetho.Stetho

class StethoInitializer(private val mContext: Context) {

    fun initialize() {
        // initialize Stetho only if running a debug build
        if (mContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0) {
            Stetho.initializeWithDefaults(mContext)
        }
    }
}