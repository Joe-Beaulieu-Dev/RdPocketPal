package com.josephbeaulieu.rdpocketpal.home;

import android.content.Intent;
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
import com.josephbeaulieu.rdpocketpal.model.CoroutineCallbackListener;
import com.josephbeaulieu.rdpocketpal.model.PreferenceRepository;
import com.josephbeaulieu.rdpocketpal.model.QueryResult;
import com.josephbeaulieu.rdpocketpal.settings.SettingsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity implements
        HomeButtonAdapter.HomeButtonListener, CoroutineCallbackListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // query prefs to see if the Disclaimer should be shown, and show if necessary
        queryIfShowDisclaimer();
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

    private void queryIfShowDisclaimer() {
        PreferenceRepository repo = new PreferenceRepository();
        repo.getUserThroughDisclaimer(this, this);
    }

    private void showDisclaimerIfNecessary(boolean isUserThroughDisclaimer) {
        if (!isUserThroughDisclaimer && !isDisclaimerShowing()) {
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

    private void setUpRecyclerView() {
        View root = findViewById(android.R.id.content);

        RecyclerView homeButtons = root.findViewById(R.id.home_buttons_recyclerView);
        homeButtons.setLayoutManager(new GridLayoutManager(this, 2));
        homeButtons.setAdapter(new HomeButtonAdapter(this));
    }

    private void setUpBannerAd() {
        BannerAd bannerAd = new BannerAd(this
                , (FrameLayout) findViewById(R.id.home_banner_ad_container));
        getLifecycle().addObserver(bannerAd);
        bannerAd.loadBanner();
    }

    @Override
    public void onClick(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    @Override
    public <T> void onCoroutineFinished(QueryResult<T> result) {
        if (result instanceof QueryResult.Success
                && (((QueryResult.Success<T>) result).getData() instanceof Boolean) ) {
            boolean isUserThroughDisclaimer = (Boolean) ((QueryResult.Success<T>) result).getData();
            showDisclaimerIfNecessary(isUserThroughDisclaimer);
        } else {
            // if there was an issue retrieving the disclaimer pref, set
            // the flag to false and show the disclaimer anyways
            PreferenceRepository repo = new PreferenceRepository();
            repo.setUserThroughDisclaimer(this, false);
            showDisclaimer();
        }
    }
}
