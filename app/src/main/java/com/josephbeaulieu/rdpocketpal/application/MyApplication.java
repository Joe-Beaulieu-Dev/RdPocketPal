package com.josephbeaulieu.rdpocketpal.application;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.josephbeaulieu.rdpocketpal.model.PreferenceRepository;
import com.josephbeaulieu.rdpocketpal.util.StethoInitializer;

import androidx.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // initialize Stetho
        new StethoInitializer(this).initialize();
        // initialize AdMob
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        // reset preference flag for if the User has passed through the disclaimer this session
        resetUserThroughDisclaimerPref();
    }

    public void resetUserThroughDisclaimerPref() {
        PreferenceRepository repo = new PreferenceRepository();
        repo.setUserThroughDisclaimer(this, false);
    }
}
