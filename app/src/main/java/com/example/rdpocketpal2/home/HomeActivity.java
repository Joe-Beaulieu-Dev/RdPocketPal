package com.example.rdpocketpal2.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.settings.SettingsActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity implements
        HomeButtonAdapter.HomeButtonListener {

    private FrameLayout adContainerView;
    private AdSize adSize;
    private String TEST_BANNER_AD_ID = "ca-app-pub-3940256099942544/6300978111";
    private String PROD_BANNER_AD_ID = "ca-app-pub-3221996738700475/3715408836";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // set up Banner Ad
        setUpBannerAd();
        // set up RecyclerView
        setUpRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpRecyclerView() {
        View root = findViewById(android.R.id.content);

        RecyclerView homeButtons = root.findViewById(R.id.home_buttons_recyclerView);
        homeButtons.setLayoutManager(new GridLayoutManager(this, 2));
        homeButtons.setAdapter(new HomeButtonAdapter(this));
    }

    private void setUpBannerAd() {
        presizeBannerContainer();
        loadBanner();
    }

    private void presizeBannerContainer() {
        // get size of ad and pre-size container View
        adSize = getAdSize();
        adContainerView = findViewById(R.id.home_banner_ad_container);
        ConstraintLayout.LayoutParams params =
                (ConstraintLayout.LayoutParams) adContainerView.getLayoutParams();
        params.height = adSize.getHeightInPixels(this);
    }

    private void loadBanner() {
        AdView adView = new AdView(this);
        adView.setAdUnitId(TEST_BANNER_AD_ID);
        adView.setAdSize(adSize);

        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        Display display = checkApiAndGetDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        // getMetrics() is deprecated, but doesn't show
        display.getMetrics(outMetrics);

        float density = outMetrics.density;
        float adWidthPixels = outMetrics.widthPixels;
        int adWidth = (int) (adWidthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    private Display checkApiAndGetDisplay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return getDisplay();
        }
        return getWindowManager().getDefaultDisplay();
    }

    @Override
    public void onClick(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}
