package com.example.rdpocketpal2.quickmethod;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.databinding.ActivityQuickMethodBinding;
import com.example.rdpocketpal2.settings.SettingsActivity;
import com.example.rdpocketpal2.util.UiUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

public class QuickMethodActivity extends AppCompatActivity {
    private QuickMethodViewModel mViewModel;
    private ActivityQuickMethodBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up the ViewModel
        mViewModel = new ViewModelProvider(this
                , new SavedStateViewModelFactory(this.getApplication(), this))
                .get(QuickMethodViewModel.class);

        // set up DataBinding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_quick_method);
        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(mViewModel);

        // add ViewModel as a LifeCycleObserver
        getLifecycle().addObserver(mViewModel);

        // set up UI elements
        setUpAllBtnRipples();
        setUpKeyboardNextBtn();
    }

    private void setUpAllBtnRipples() {
        Resources res = getResources();
        Resources.Theme theme = getTheme();

        UiUtil.setUpBtnRippleRectangle(res, theme, mBinding.qmProteinClearBtn);
        UiUtil.setUpBtnRippleRectangle(res, theme, mBinding.qmProteinCalculateBtn);
        UiUtil.setUpBtnRippleRectangle(res, theme, mBinding.qmFluidClearBtn);
        UiUtil.setUpBtnRippleRectangle(res, theme, mBinding.qmFluidCalculateBtn);
        UiUtil.setUpBtnRippleRectangle(res, theme, mBinding.qmCalorieClearBtn);
        UiUtil.setUpBtnRippleRectangle(res, theme, mBinding.qmCalorieCalculateBtn);
    }

    private void setUpKeyboardNextBtn() {
        UiUtil.setNextBtnBehaviorForEditText(mBinding.qmCalorieKcalPerKgMin
                , mBinding.qmCalorieKcalPerKgMax);
        UiUtil.setNextBtnBehaviorForEditText(mBinding.qmProteinGramsPerKgMin
                , mBinding.qmProteinGramsPerKgMax);
        UiUtil.setNextBtnBehaviorForEditText(mBinding.qmFluidMlPerKgMin
                , mBinding.qmFluidMlPerKgMax);
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
