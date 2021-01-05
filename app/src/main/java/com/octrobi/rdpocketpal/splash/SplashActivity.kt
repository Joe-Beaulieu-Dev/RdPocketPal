package com.octrobi.rdpocketpal.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.databinding.ActivitySplashBinding
import com.octrobi.rdpocketpal.getstarted.GetStartedActivity

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

        // add ViewModel as LifecycleObserver
        lifecycle.addObserver(mViewModel)
    }

    override fun onResume() {
        super.onResume()
        // temporary implementation to work on Get Started screen
        TEMP_openGetStarted_timed()
    }

    private fun TEMP_openGetStarted_timed() {
        Handler(Looper.getMainLooper())
                .postDelayed(
                        { startActivity(Intent(this, GetStartedActivity::class.java)) }
                        , 2000)
    }
}