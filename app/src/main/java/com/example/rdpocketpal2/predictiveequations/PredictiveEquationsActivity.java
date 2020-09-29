package com.example.rdpocketpal2.predictiveequations;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.databinding.ActivityPredictiveEquationsBinding;
import com.example.rdpocketpal2.predictiveequations.PredictiveEquationsViewModel.Equations;
import com.example.rdpocketpal2.settings.SettingsActivity;
import com.example.rdpocketpal2.util.UiUtil;

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
        setUpUiElements();
    }

    private void setUpUiElements() {
        setUpEquationSpinner();
        setUpAllBtnRipples();
        setUpKeyboardNextBtn();
    }

    private void setUpEquationSpinner() {
        // style Spinner and set Adapter
        Spinner equationSpinner = mBinding.peEquationSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.predictive_equations, R.layout.spinner_item_dark_grey);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_dark_grey);
        equationSpinner.setAdapter(adapter);
    }

    private void setUpAllBtnRipples() {
        Resources res = getResources();
        Theme theme = getTheme();

        UiUtil.setUpBtnRippleOval(res, theme, mBinding.peSexMale);
        UiUtil.setUpBtnRippleOval(res, theme, mBinding.peSexFemale);
        UiUtil.setUpBtnRippleRectangle(res, theme, mBinding.peClearBtn);
        UiUtil.setUpBtnRippleRectangle(res, theme, mBinding.peCalculateBtn);
    }

    private void setUpKeyboardNextBtn() {
        UiUtil.setNextBtnBehaviorForEditText(mBinding.peWeightEditText, mBinding.peHeightEditText);
        UiUtil.setNextBtnBehaviorForEditText(mBinding.peHeightEditText, mBinding.peAgeEditText);
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
                if (s.equals(getString(R.string.mifflin_st_jeor))
                        || s.equals(getString(R.string.harris_benedict))) {
                    setEditTextAttributes(PredictiveEquationsViewModel.MIFFLIN);
                } else if (s.equals(getString(R.string.penn_state_2003b))
                        || s.equals(getString(R.string.penn_state_2010))) {
                    setEditTextAttributes(PredictiveEquationsViewModel.PENN2003B);
                } else if (s.equals(getString(R.string.brandi))) {
                    setEditTextAttributes(PredictiveEquationsViewModel.BRANDI);
                }

                // this is the method where the reason onChanged() was called is important
                clearOutputDataIfNecessary();
            }
        });
    }

    private void setEditTextAttributes(@Equations int equation) {
        switch (equation) {
            case PredictiveEquationsViewModel.MIFFLIN:
            case PredictiveEquationsViewModel.BENEDICT:
                setMifflinBenedictAttributes();
                break;
            case PredictiveEquationsViewModel.PENN2003B:
            case PredictiveEquationsViewModel.PENN2010:
                setPennStateAttributes();
                break;
            case PredictiveEquationsViewModel.BRANDI:
                setBrandiAttributes();
                break;
        }
    }

    public void setMifflinBenedictAttributes() {
        // hide and clear Tmax, Heart Rate, and Ve
        // define soft keyboard Next button behavior
        setVisibilities(View.GONE, View.GONE, View.GONE);
        setConstraints(PredictiveEquationsViewModel.MIFFLIN);
        UiUtil.setNextBtnBehaviorForEditText(mBinding.peAgeEditText
                , mBinding.peActivityFactorMinEditText);
        clearFieldsAndErrors(mBinding.peTmaxEditText
                , mBinding.peHeartRateEditText, mBinding.peVeEditText);
    }

    public void setPennStateAttributes() {
        // hide and clear Heart Rate, show Tmax and Ve
        // define soft keyboard Next button behavior
        setVisibilities(View.VISIBLE, View.GONE, View.VISIBLE);
        setConstraints(PredictiveEquationsViewModel.PENN2003B);
        UiUtil.setNextBtnBehaviorForEditText(mBinding.peAgeEditText, mBinding.peTmaxEditText);
        clearFieldsAndErrors(mBinding.peHeartRateEditText);
    }

    public void setBrandiAttributes() {
        // hide and clear Tmax, show Heart Rate and Ve
        // define soft keyboard Next button behavior
        setVisibilities(View.GONE, View.VISIBLE, View.VISIBLE);
        setConstraints(PredictiveEquationsViewModel.BRANDI);
        UiUtil.setNextBtnBehaviorForEditText(mBinding.peAgeEditText
                , mBinding.peHeartRateEditText);
        clearFieldsAndErrors(mBinding.peTmaxEditText);
    }

    private void setVisibilities(int tmaxVisibility, int hrVisibility, int veVisibility) {
        // Tmax
        mBinding.peTmaxTextView.setVisibility(tmaxVisibility);
        mBinding.peTmaxEditText.setVisibility(tmaxVisibility);
        mBinding.peTmaxUnitLabel.setVisibility(tmaxVisibility);
        // Heart rate
        mBinding.peHeartRateTextView.setVisibility(hrVisibility);
        mBinding.peHeartRateEditText.setVisibility(hrVisibility);
        mBinding.peHeartRateUnitLabel.setVisibility(hrVisibility);
        // Ve
        mBinding.peVeTextView.setVisibility(veVisibility);
        mBinding.peVeEditText.setVisibility(veVisibility);
        mBinding.peVeUnitLabel.setVisibility(veVisibility);
    }

    private void setConstraints(@Equations int equation) {
        // create ConstraintSet
        ConstraintLayout layout = mBinding.peInputFirstThreeRows;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        // create constraints
        switch (equation) {
            case PredictiveEquationsViewModel.MIFFLIN:
            case PredictiveEquationsViewModel.BENEDICT:
                break;
            case PredictiveEquationsViewModel.PENN2003B:
            case PredictiveEquationsViewModel.PENN2010:
                // change constraints
                constraintSet.connect(R.id.pe_ve_editText, ConstraintSet.START, R.id.pe_age_editText, ConstraintSet.START);
                constraintSet.connect(R.id.pe_ve_editText, ConstraintSet.TOP, R.id.pe_age_editText, ConstraintSet.BOTTOM);
                break;
            case PredictiveEquationsViewModel.BRANDI:
                // change constraints
                constraintSet.connect(R.id.pe_heart_rate_editText, ConstraintSet.START, R.id.pe_height_editText, ConstraintSet.START);
                constraintSet.connect(R.id.pe_heart_rate_editText, ConstraintSet.TOP, R.id.pe_height_editText, ConstraintSet.BOTTOM);
                constraintSet.connect(R.id.pe_ve_editText, ConstraintSet.START, R.id.pe_age_editText, ConstraintSet.START);
                constraintSet.connect(R.id.pe_ve_editText, ConstraintSet.TOP, R.id.pe_age_editText, ConstraintSet.BOTTOM);
                break;
        }

        // apply constraints
        constraintSet.applyTo(layout);
    }

    private void clearFieldsAndErrors(EditText... editTexts) {
        for (EditText editText: editTexts) {
            editText.setText(null);
            // Have to clear the errors like this because if it's done via setError()
            // then it won't trigger the binding adapter for the error field. This is because
            // this adapter is attached to a custom xml attribute, because there is no
            // xml attribute that exists for an EditText's error. Since the binding adapter's
            // onChange() fires via an OnFocusChangeListener, and not from some sort of non-existent
            // preexisting OnErrorChangeListener, direct calls to setError() will not cause any
            // activity in the EditText's binding adapters, and therefore the underlying bound data
            // will not change.
            switch (editText.getId()) {
                case R.id.pe_tmax_editText: mViewModel.clearTmaxError();
                case R.id.pe_heart_rate_editText: mViewModel.clearHeartRateError();
                case R.id.pe_ve_editText: mViewModel.clearVeError();
            }
        }
    }

    private void clearOutputDataIfNecessary() {
        // do nothing if this method's caller (Observer<T>.onChanged()) was called
        // due to a config change
        if (!mConfigurationChanged) {
            // clear result data when equation is changed and show Toast
            // this is done so results don't clash with user input/equation selection
            mViewModel.clearOutputDataFromActivity();
        }
        // reset value as it being true is only relevant when onChanged() is initially
        // auto-called due to a config change
        mConfigurationChanged = false;
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
