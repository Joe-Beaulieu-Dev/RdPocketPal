package com.octrobi.rdpocketpal.anthropometrics

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.databinding.ActivityAnthropometricsBinding
import com.octrobi.rdpocketpal.disclaimer.DisclaimerActivity
import com.octrobi.rdpocketpal.settings.SettingsActivity
import com.octrobi.rdpocketpal.util.setUpBtnRippleOval
import com.octrobi.rdpocketpal.util.setUpBtnRippleRectangle

class AnthropometricsActivity : AppCompatActivity() {
    private lateinit var mViewModel: AnthropometricsViewModel
    private lateinit var mBinding: ActivityAnthropometricsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set up ViewModel
        mViewModel = ViewModelProvider(this
                , SavedStateViewModelFactory(application, this))
                .get(AnthropometricsViewModel::class.java)

        // set up DataBinding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_anthropometrics)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        // add ViewModel as a LifeCycleObserver
        lifecycle.addObserver(mViewModel)

        // set up Button ripples
        setUpAllBtnRipples()
    }

    private fun setUpAllBtnRipples() {
        setUpBtnRippleOval(resources, theme, mBinding.anthroSexMale)
        setUpBtnRippleOval(resources, theme, mBinding.anthroSexFemale)
        setUpBtnRippleRectangle(resources, theme, mBinding.anthroClearBtn)
        setUpBtnRippleRectangle(resources, theme, mBinding.anthroCalculateBtn)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.main_menu_disclaimer -> {
                startActivity(Intent(this, DisclaimerActivity::class.java))
                return true
            }
            R.id.main_menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // save the state of the ViewModel to deal with system initiated process death
        mViewModel.saveState()
        super.onSaveInstanceState(outState)
    }
}