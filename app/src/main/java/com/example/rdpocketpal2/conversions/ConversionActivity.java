package com.example.rdpocketpal2.conversions;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.databinding.ActivityConversionBinding;
import com.example.rdpocketpal2.settings.SettingsActivity;
import com.example.rdpocketpal2.util.UiUtil;

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
        observeElementData();
    }

    private void setUpUi() {
        setUpConversionSpinner();
        setUpAllBtnRipples();
    }

    private void observeConversionTypeData() {
        mViewModel.getConversionTypeData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals(getResources().getString(R.string.text_gm_to_meq))
                        || s.equals(getResources().getString(R.string.text_mg_to_meq))) {
                    setElementSpinnerToMeqSet();
                    setElementSpinnerVisibility(View.VISIBLE);
                } else if (s.equals(getResources().getString(R.string.text_gm_to_mmol))
                        || s.equals(getResources().getString(R.string.text_mg_to_mmol))) {
                    setElementSpinnerToMmolSet();
                    setElementSpinnerVisibility(View.VISIBLE);
                } else {
                    setElementSpinnerVisibility(View.GONE);
                }
                // update the unit labels for the input/output fields
                // when the conversion type changes
                mViewModel.updateFieldLabelData();
                // clear input/output fields and errors when User changes conversion type
                mViewModel.clearAllFieldsAndErrors();
            }
        });
    }

    private void observeElementData() {
        mViewModel.getElementData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // clear input/output fields and errors when User changes element type
                mViewModel.clearAllFieldsAndErrors();
            }
        });
    }

    private void setUpConversionSpinner() {
        // initialize and assign ArrayAdapter to Spinner
        Spinner spinner = mBinding.convConversionSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.conversion_list, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setElementSpinnerToMeqSet() {
        Spinner spinner = mBinding.convElementSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.element_list_meq, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setElementSpinnerToMmolSet() {
        Spinner spinner = mBinding.convElementSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.element_list_mmol, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setUpAllBtnRipples() {
        UiUtil.setUpBtnRippleRectangle(getResources(), getTheme(), mBinding.convClearBtn);
    }

    private void setElementSpinnerVisibility(int visibility) {
        // @View.Visibility is hidden, so have to check validity
        if (visibility != View.VISIBLE
                && visibility != View.INVISIBLE
                && visibility != View.GONE) {
            return;
        }

        // set visibility
        mBinding.convElementSpinner.setVisibility(visibility);
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
