package com.example.rdpocketpal2.application;

import android.app.Application;

import com.example.rdpocketpal2.util.StethoInitializerKotlin;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // initialize Stetho
        new StethoInitializerKotlin(this).initialize();
    }
}
