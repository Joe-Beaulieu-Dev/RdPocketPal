package com.josephbeaulieu.rdpocketpal.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.josephbeaulieu.rdpocketpal.R;
import com.josephbeaulieu.rdpocketpal.ad.BannerAd;
import com.josephbeaulieu.rdpocketpal.disclaimer.DisclaimerActivity;
import com.josephbeaulieu.rdpocketpal.disclaimer.DisclaimerDialogFragment;
import com.josephbeaulieu.rdpocketpal.settings.SettingsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity implements
        HomeButtonAdapter.HomeButtonListener {

    private HomeViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // set up ViewModel
        mViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(HomeViewModel.class);

        setUpUi();
    }

    public void setUpUi() {
        setUpDisclaimer();
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

    //region Disclaimer
    private void setUpDisclaimer() {
        getLifecycle().addObserver(mViewModel);
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.key_disclaimer_pref_file),
                Context.MODE_PRIVATE);
        prefs.registerOnSharedPreferenceChangeListener(mViewModel);
        observeDisclaimerStatus();
    }

    private void observeDisclaimerStatus() {
        mViewModel.getIfDisclaimerAccepted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean disclaimerAccepted) {
                showDisclaimerIfNecessary(disclaimerAccepted);
            }
        });
    }

    private void showDisclaimerIfNecessary(boolean disclaimerAccepted) {
        if (!disclaimerAccepted && !isDisclaimerShowing()) {
            showDisclaimer();
        }
    }

    private boolean isDisclaimerShowing() {
        DialogFragment frag = (DialogFragment) getSupportFragmentManager()
                .findFragmentByTag(DisclaimerDialogFragment.Companion.getTag());

        return frag != null
                && frag.getDialog() != null
                && frag.getDialog().isShowing()
                && !frag.isRemoving();
    }

    private void showDisclaimer() {
        DisclaimerDialogFragment.Companion.newInstance()
                .show(getSupportFragmentManager(), DisclaimerDialogFragment.Companion.getTag());
    }
    //endregion

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
