package com.example.rdpocketpal2.conversions;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.databinding.ActivityConversionBinding;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

    private void setUpUi() {
        setUpConversionSpinner();
        setUpElementSpinner();
        setUpAllBtnRipples();
    }

    private void setUpConversionSpinner() {
        // initialize and assign ArrayAdapter to Spinner
        Spinner spinner = findViewById(R.id.conv_conversion_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.conversion_list, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setUpElementSpinner() {
        // initialize and assign ArrayAdapter to Spinner
        Spinner spinner = findViewById(R.id.conv_element_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.element_list, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void observeLiveData() {
        observeConversionTypeData();
        observeElementData();
    }

    private void observeConversionTypeData() {
        mViewModel.getConversionTypeData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals(getResources().getString(R.string.text_gm_to_meq))
                        || s.equals(getResources().getString(R.string.text_mg_to_meq))) {
                    setElementSpinnerVisibility(View.VISIBLE);
                } else {
                    setElementSpinnerVisibility(View.GONE);
                }
                // update the unit labels for the input/output fields
                // when the conversion type changes
                mViewModel.updateFieldLabelData();
                // clear input/output fields when User changes conversion type
                mViewModel.clearAllFields();
            }
        });
    }

    private void observeElementData() {
        mViewModel.getElementData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // clear input/output fields when User changes element type
                mViewModel.clearAllFields();
            }
        });
    }

    private void setElementSpinnerVisibility(int visibility) {
        // @View.Visibility is hidden, so have to check validity
        if (visibility != View.VISIBLE
                && visibility != View.INVISIBLE
                && visibility != View.GONE) {
            return;
        }

        // set visibility
        findViewById(R.id.conv_element_spinner).setVisibility(visibility);
    }

    private void setUpAllBtnRipples() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Clear Button
            setUpBtnRipple(R.id.conv_clear_btn);
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
}
