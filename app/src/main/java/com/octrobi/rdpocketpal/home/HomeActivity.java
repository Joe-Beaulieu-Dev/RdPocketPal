package com.octrobi.rdpocketpal.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.octrobi.rdpocketpal.R;
import com.octrobi.rdpocketpal.ad.BannerAd;
import com.octrobi.rdpocketpal.disclaimer.DisclaimerActivity;
import com.octrobi.rdpocketpal.settings.SettingsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity implements
        HomeButtonAdapter.HomeButtonListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUpUi();
    }

    public void setUpUi() {
        setUpBannerAd();
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
            case R.id.main_menu_disclaimer:
                startActivity(new Intent(this, DisclaimerActivity.class));
                return true;
            case R.id.main_menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpBannerAd() {
        BannerAd bannerAd = new BannerAd(this
                , (FrameLayout) findViewById(R.id.home_banner_ad_container));
        getLifecycle().addObserver(bannerAd);
        bannerAd.loadBanner();
    }

    private void setUpRecyclerView() {
        View root = findViewById(android.R.id.content);

        RecyclerView homeButtons = root.findViewById(R.id.home_buttons_recyclerView);
        homeButtons.setLayoutManager(new GridLayoutManager(this, 2));
        homeButtons.setAdapter(new HomeButtonAdapter(this));
    }

    @Override
    public void onClick(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}
