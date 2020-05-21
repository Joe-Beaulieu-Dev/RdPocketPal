package com.example.rdpocketpal2.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.example.rdpocketpal2.BuildConfig;
import com.facebook.stetho.Stetho;

public class StethoInitializer {
    private final Context mContext;

    public StethoInitializer(Context context) {
        mContext = context;
    }

    public void initialize() {
        // initialize Stetho only if running a debug build
        if ((mContext.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            Stetho.initializeWithDefaults(mContext);
        }
    }
}
