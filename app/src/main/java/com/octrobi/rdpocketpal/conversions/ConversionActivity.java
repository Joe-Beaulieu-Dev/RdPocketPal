package com.octrobi.rdpocketpal.conversions;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.octrobi.rdpocketpal.R;
import com.octrobi.rdpocketpal.adapter.NoFilterAdapter;
import com.octrobi.rdpocketpal.databinding.ActivityConversionNewBinding;
import com.octrobi.rdpocketpal.disclaimer.DisclaimerActivity;
import com.octrobi.rdpocketpal.settings.SettingsActivity;
import com.octrobi.rdpocketpal.util.UiUtil;

import java.util.Arrays;

public class ConversionActivity extends AppCompatActivity {
    public ConversionViewModel mViewModel;
    private ActivityConversionNewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up the ViewModel
        mViewModel = new ViewModelProvider(this
                , new SavedStateViewModelFactory(this.getApplication(), this))
                .get(ConversionViewModel.class);

        // set up DataBinding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_conversion_new);
        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(mViewModel);

        // observe LiveData
        observeLiveData();

        // set up UI elements
        setUpUi();
    }

    private void observeLiveData() {
        observeConversionTypeData();
    }

    private void setUpUi() {
        setUpConversionSpinner();
        setUpAllBtnRipples();
    }

    private void observeConversionTypeData() {
        mViewModel.getConversionTypeData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // update the unit labels for the input/output fields
                // when the conversion type changes
                mViewModel.updateFieldLabelData();
                // clear input/output fields and errors when User changes conversion type
                mViewModel.clearAllFieldsAndErrors();
            }
        });
    }

    private void setUpConversionSpinner() {
        NoFilterAdapter<String> adapter = new NoFilterAdapter<>(
                this,
                Arrays.asList(getResources().getStringArray(R.array.conversion_list))
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mBinding.convConversionSelectionTextView.setAdapter(adapter);
    }

    private void setUpAllBtnRipples() {
        UiUtil.setUpBtnRippleRectangle(getResources(), getTheme(), mBinding.convClearBtn);
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // save the state of the ViewModel to deal with system initiated process death
        mViewModel.saveState();
        super.onSaveInstanceState(outState);
    }
}
