package com.example.rdpocketpal2.predictiveequations;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.databinding.ActivityPredictiveEquationsBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

public class PredictiveEquationsActivity extends AppCompatActivity {
    private static final String LOG_TAG = "PredictiveEqActivity";

    private PredictiveEquationsViewModel mViewModel;
    private ActivityPredictiveEquationsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create the ViewModel
//        mViewModel = new ViewModelProvider(this,
//                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
//                .get(PredictiveEquationsViewModel.class);

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

        // set up UI elements
        setUpEquationSpinner();
        setUpButtonRipple();

        // observe data
        observeData();
    }

    private void setUpEquationSpinner() {
        // style Spinner and set Adapter
        Spinner equationSpinner = findViewById(R.id.spinner_equation);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.predictive_equations, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        equationSpinner.setAdapter(adapter);

//        // set tags on each view so we can data bind it to the xml so the ViewModel
//        // can read it to compare in order to know which equation to use
//        String [] choices = getResources().getStringArray(R.array.predictive_equations);
//        for (int i = 0; i < adapter.getCount(); i++) {
//            adapter.getView(i, null, equationSpinner).setTag(choices[i]);
//        }
    }

    private void setUpButtonRipple() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Male button
            Button sexMale = findViewById(R.id.sex_male);
            sexMale.setForeground(getResources()
                    .getDrawable(R.drawable.ripple_oval, getTheme()));

            // Female button
            Button sexFemale = findViewById(R.id.sex_female);
            sexFemale.setForeground(getResources()
                    .getDrawable(R.drawable.ripple_oval, getTheme()));

            // Clear button
            Button clear = findViewById(R.id.btn_parenteral_nutrition_clear);
            clear.setForeground(getResources()
                    .getDrawable(R.drawable.ripple_rectangle, getTheme()));

            // Calculate button
            Button calculate = findViewById(R.id.btn_parenteral_nutrition_calculate);
            calculate.setForeground(getResources()
                    .getDrawable(R.drawable.ripple_rectangle, getTheme()));
        }
    }

    private void observeData() {
        // observe equation spinner and set view visibility of variable views accordingly
        mViewModel.getSelectedEquation().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // Doing findViewById() here instead of data binding because I need to grab
                // string resources in order to determine visibility states, so that
                // needs to be done on this side. If done in the ViewModel, and the user changes the
                // locale while the screen is up, the ViewModel will be holding onto (and displaying) a stale string
                // in the previous locale, which is bad practice. Since the strings are the determining factor for visibility calculation,
                // that calculation needs to be done here. Since it's done here, using data binding
                // would be pointless as I would end up needing to call the ViewModel from the View
                // in order to update the data bound values, which is bad practice, because the View
                // shouldn't call the ViewModel unless it's necessary, such as for observing data.
                // Therefore, it's best just to do it the old fashioned way.
                if (s.equals(getString(R.string.mifflin_st_jear))
                        || s.equals(getString(R.string.harris_benedict))) {
                    // set visibilities
                    setVisibilities(View.GONE, View.GONE, View.GONE);
                } else if (s.equals(getString(R.string.penn_state_2003b))
                        || s.equals(getString(R.string.penn_state_2010))) {
                    // set visibilities
                    setVisibilities(View.VISIBLE, View.GONE, View.VISIBLE);

                    // change layout constraints to eliminate gaps
                    ConstraintLayout layout = findViewById(R.id.data_entry_layout);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(layout);
                    constraintSet.connect(R.id.text_ve, ConstraintSet.START, R.id.text_height, ConstraintSet.START);
                    constraintSet.connect(R.id.text_ve, ConstraintSet.TOP, R.id.text_height, ConstraintSet.BOTTOM);
                    constraintSet.applyTo((ConstraintLayout) findViewById(R.id.data_entry_layout));
                } else if (s.equals(getString(R.string.brandi))) {
                    // set visibilities
                    setVisibilities(View.GONE, View.VISIBLE, View.VISIBLE);

                    // change layout constraints to eliminate gaps
                    ConstraintLayout layout = findViewById(R.id.data_entry_layout);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(layout);
                    constraintSet.connect(R.id.text_heart_rate, ConstraintSet.START, R.id.text_weight, ConstraintSet.START);
                    constraintSet.connect(R.id.text_heart_rate, ConstraintSet.TOP, R.id.text_weight, ConstraintSet.BOTTOM);
                    constraintSet.connect(R.id.text_ve, ConstraintSet.START, R.id.text_height, ConstraintSet.START);
                    constraintSet.connect(R.id.text_ve, ConstraintSet.TOP, R.id.text_height, ConstraintSet.BOTTOM);
                    constraintSet.applyTo((ConstraintLayout) findViewById(R.id.data_entry_layout));
                }
            }
        });
    }

    private void setVisibilities(int tmaxVisibility, int hrVisibility, int veVisibility) {
        findViewById(R.id.label_tmax).setVisibility(tmaxVisibility);
        findViewById(R.id.text_tmax).setVisibility(tmaxVisibility);
        findViewById(R.id.label_heart_rate).setVisibility(hrVisibility);
        findViewById(R.id.text_heart_rate).setVisibility(hrVisibility);
        findViewById(R.id.label_ve).setVisibility(veVisibility);
        findViewById(R.id.text_ve).setVisibility(veVisibility);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // save the state of the ViewModel to deal with system initiated process death
        mViewModel.saveState();
        super.onSaveInstanceState(outState);
    }
}
