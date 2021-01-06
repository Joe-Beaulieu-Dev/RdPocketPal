package com.octrobi.rdpocketpal.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.databinding.ActivitySplashBinding
import com.octrobi.rdpocketpal.getstarted.GetStartedActivity
import com.octrobi.rdpocketpal.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var mViewModel: SplashViewModel
    private lateinit var mBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set up ViewModel
        mViewModel = ViewModelProvider(this
                , ViewModelProvider.AndroidViewModelFactory.getInstance(this.application))
                .get(SplashViewModel::class.java)

        // set up DataBinding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        setUpDisclaimerRegulation()
    }

    private fun setUpDisclaimerRegulation() {
        observeDisclaimerStatus()
        lifecycle.addObserver(mViewModel)
    }

    private fun observeDisclaimerStatus() {
        mViewModel.getIfDisclaimerAccepted().observe(this, {
            if (it == true) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                startActivity(Intent(this, GetStartedActivity::class.java))
            }
        })
    }
}