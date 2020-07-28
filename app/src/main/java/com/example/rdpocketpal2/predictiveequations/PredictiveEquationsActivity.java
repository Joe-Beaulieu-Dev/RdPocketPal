package com.example.rdpocketpal2.predictiveequations;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.databinding.ActivityPredictiveEquationsBinding;
import com.example.rdpocketpal2.predictiveequations.PredictiveEquationsViewModel.Equations;
import com.example.rdpocketpal2.settings.SettingsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

public class PredictiveEquationsActivity extends AppCompatActivity {
    private PredictiveEquationsViewModel mViewModel;
    private ActivityPredictiveEquationsBinding mBinding;

    private static final String KEY_ORIENTATION_CHANGED = "orientationChanged";
    private boolean mConfigurationChanged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create ViewModel with SavedStateHandle
        mViewModel = new ViewModelProvider(this
                , new SavedStateViewModelFactory(this.getApplication(), this))
                .get(PredictiveEquationsViewModel.class);

        // set up DataBinding
        // inflate Activity layout with data binding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_predictive_equations);
        // set the ViewModel instance
        mBinding.setViewModel(mViewModel);
        // set binding's lifecycle owner so LiveData works properly
        mBinding.setLifecycleOwner(this);

        // add ViewModel as a LifecycleObserver
        getLifecycle().addObserver(mViewModel);

        // set up UI elements
        setUpEquationSpinner();
        setUpButtonRipple();
    }

    private void setUpEquationSpinner() {
        // style Spinner and set Adapter
        Spinner equationSpinner = findViewById(R.id.pe_equation_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.predictive_equations, R.layout.spinner_item_dark_grey);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_dark_grey);
        equationSpinner.setAdapter(adapter);
    }

    private void setUpButtonRipple() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Male button
            Button sexMale = findViewById(R.id.pe_sex_male);
            sexMale.setForeground(getResources()
                    .getDrawable(R.drawable.ripple_oval, getTheme()));

            // Female button
            Button sexFemale = findViewById(R.id.pe_sex_female);
            sexFemale.setForeground(getResources()
                    .getDrawable(R.drawable.ripple_oval, getTheme()));

            // Clear button
            Button clear = findViewById(R.id.pe_clear_btn);
            clear.setForeground(getResources()
                    .getDrawable(R.drawable.ripple_rectangle, getTheme()));

            // Calculate button
            Button calculate = findViewById(R.id.pe_calculate_btn);
            calculate.setForeground(getResources()
                    .getDrawable(R.drawable.ripple_rectangle, getTheme()));
        }
    }

    // Observer's onChanged() is called not only when observed LiveData is changed, but also
    // on device configuration change, even if the LiveData's value is never changed. This is bad
    // because since this implementation of onChanged() clears the User's result data when the
    // selected equation is changed, if the configuration is changed then onChanged() will fire
    // as well. Therefore, we need to check whether onChanged() is being called due to an actual
    // change in the LiveData, or due to a configuration change. This will determine whether or not
    // the result data is cleared.
    //
    // This check is done using values set and read in onSaveInstanceState() and
    // onRestoreInstanceState(). If this method is implemented before onRestoreInstanceState() is
    // called, then we can't check if onChanged() was called due to an orientation change.
    // Therefore, this method MUST be implemented in a lifecycle method that gets called
    // AFTER onRestoreInstanceState().
    private void observeEquationSpinnerData() {
        // observe equation spinner and set view visibility of variable views accordingly
        mViewModel.getSelectedEquation().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // change view visibility based on selected equation,
                // then change layout constraints to eliminate gaps
                if (s.equals(getString(R.string.mifflin_st_jear))
                        || s.equals(getString(R.string.harris_benedict))) {
                    setVisibilities(View.GONE, View.GONE, View.GONE);
                    setConstraints(PredictiveEquationsViewModel.MIFFLIN);
                } else if (s.equals(getString(R.string.penn_state_2003b))
                        || s.equals(getString(R.string.penn_state_2010))) {
                    setVisibilities(View.VISIBLE, View.GONE, View.VISIBLE);
                    setConstraints(PredictiveEquationsViewModel.PENN2003B);
                } else if (s.equals(getString(R.string.brandi))) {
                    setVisibilities(View.GONE, View.VISIBLE, View.VISIBLE);
                    setConstraints(PredictiveEquationsViewModel.BRANDI);
                }

                // this is the method where the reason onChanged() was called is important
                clearDataIfNecessary();
            }
        });
    }

    private void clearDataIfNecessary() {
        // do nothing if this method's caller (Observer<T>.onChanged()) was called
        // due to a config change
        if (!mConfigurationChanged) {
            // clear result data when equation is changed and show Toast
            // this is done so results don't clash with user input/equation selection
            mViewModel.clearResultDataFromActivity();
        }
        // reset value as it being true is only relevant when onChanged() is initially
        // auto-called due to a config change
        mConfigurationChanged = false;
    }

    private void setConstraints(@Equations int equation) {
        // create ConstraintSet
        ConstraintLayout layout = findViewById(R.id.pe_input_first_three_rows);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        // create constraints
        switch (equation) {
            case PredictiveEquationsViewModel.MIFFLIN:
            case PredictiveEquationsViewModel.BENEDICT:
                break;
            case PredictiveEquationsViewModel.PENN2003B:
            case PredictiveEquationsViewModel.PENN2010:
                // changed constraint
                constraintSet.connect(R.id.pe_ve_editText, ConstraintSet.START, R.id.pe_age_editText, ConstraintSet.START);
                constraintSet.connect(R.id.pe_ve_editText, ConstraintSet.TOP, R.id.pe_age_editText, ConstraintSet.BOTTOM);
                break;
            case PredictiveEquationsViewModel.BRANDI:
                // changed constraints
                constraintSet.connect(R.id.pe_heart_rate_editText, ConstraintSet.START, R.id.pe_height_editText, ConstraintSet.START);
                constraintSet.connect(R.id.pe_heart_rate_editText, ConstraintSet.TOP, R.id.pe_height_editText, ConstraintSet.BOTTOM);
                constraintSet.connect(R.id.pe_ve_editText, ConstraintSet.START, R.id.pe_age_editText, ConstraintSet.START);
                constraintSet.connect(R.id.pe_ve_editText, ConstraintSet.TOP, R.id.pe_age_editText, ConstraintSet.BOTTOM);
                break;
        }

        // apply constraints
        constraintSet.applyTo(layout);
    }

    private void setVisibilities(int tmaxVisibility, int hrVisibility, int veVisibility) {
        findViewById(R.id.pe_tmax_textView).setVisibility(tmaxVisibility);
        findViewById(R.id.pe_tmax_editText).setVisibility(tmaxVisibility);
        findViewById(R.id.pe_tmax_unit_label).setVisibility(tmaxVisibility);
        findViewById(R.id.pe_heart_rate_textView).setVisibility(hrVisibility);
        findViewById(R.id.pe_heart_rate_editText).setVisibility(hrVisibility);
        findViewById(R.id.pe_heart_rate_unit_label).setVisibility(hrVisibility);
        findViewById(R.id.pe_ve_textView).setVisibility(veVisibility);
        findViewById(R.id.pe_ve_editText).setVisibility(veVisibility);
        findViewById(R.id.pe_ve_unit_label).setVisibility(veVisibility);
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
    protected void onResume() {
        observeEquationSpinnerData();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Activity was killed due to a configuration change and is going to be recreated
        // https://developer.android.com/guide/components/activities/activity-lifecycle#ondestroy
        if (!isFinishing()) {
            outState.putBoolean(KEY_ORIENTATION_CHANGED, true);
        }
        // save the state of the ViewModel to deal with system initiated process death
        mViewModel.saveState();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mConfigurationChanged = savedInstanceState
                .getBoolean(KEY_ORIENTATION_CHANGED, false);
    }
}
