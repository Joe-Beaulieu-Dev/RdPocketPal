package com.josephbeaulieu.rdpocketpal.conversions;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.josephbeaulieu.rdpocketpal.R;
import com.josephbeaulieu.rdpocketpal.databinding.ActivityConversionBinding;
import com.josephbeaulieu.rdpocketpal.settings.SettingsActivity;
import com.josephbeaulieu.rdpocketpal.util.UiUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

public class ConversionActivity extends AppCompatActivity {
    private ConversionViewModel mViewModel;
    private ActivityConversionBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up the ViewModel
        mViewModel = new ViewModelProvider(this
                , new SavedStateViewModelFactory(this.getApplication(), this))
                .get(ConversionViewModel.class);

        // set up DataBinding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_conversion);
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
        Spinner spinner = mBinding.convConversionSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.conversion_list, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
