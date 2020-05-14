package com.example.rdpocketpal2.application;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import com.example.rdpocketpal2.util.StethoInitializer;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // initialize stetho
        new StethoInitializer(this).initialize();
    }
}
