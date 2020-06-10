package com.example.rdpocketpal2.application;

import android.app.Application;

import com.example.rdpocketpal2.util.StethoInitializer;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // initialize Stetho
        new StethoInitializer(this).initialize();
    }
}
