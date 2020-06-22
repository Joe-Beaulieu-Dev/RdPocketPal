package com.example.rdpocketpal2.quickmethod;

import android.os.Build;
import android.os.Bundle;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.databinding.ActivityQuickMethodBinding;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

public class QuickMethodActivity extends AppCompatActivity {
    private QuickMethodViewModel mViewModel;
    private ActivityQuickMethodBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_method);

        // set up the ViewModel
        mViewModel = new ViewModelProvider(this
                , new SavedStateViewModelFactory(this.getApplication(), this))
                .get(QuickMethodViewModel.class);

        // set up DataBinding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_quick_method);
        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(mViewModel);

        // set up UI elements
        setUpAllBtnRipples();
    }

    private void setUpAllBtnRipples() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Protein Clear button
            setUpBtnRipple(R.id.qm_protein_clear_btn);
            // Protein Calculate button
            setUpBtnRipple(R.id.qm_protein_calculate_btn);

            // Fluid Clear button
            setUpBtnRipple(R.id.qm_fluid_clear_btn);
            // Fluid Calculate button
            setUpBtnRipple(R.id.qm_fluid_calculate_btn);

            // Calorie Clear button
            setUpBtnRipple(R.id.qm_calorie_clear_btn);
            // Calorie Calculate button
            setUpBtnRipple(R.id.qm_calorie_calculate_btn);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setUpBtnRipple(int btnId) {
        findViewById(btnId).setForeground(getResources()
                .getDrawable(R.drawable.ripple_rectangle, getTheme()));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // save the state of the ViewModel to deal with system initiated process death
        mViewModel.saveState();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register ViewModel as OnSharedPreferenceChangeListener
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(mViewModel);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // unregister ViewModel as OnSharedPreferenceChangeListener
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(mViewModel);
    }
}
