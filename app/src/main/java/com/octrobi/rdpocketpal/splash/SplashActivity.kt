package com.octrobi.rdpocketpal.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.databinding.ActivitySplashBinding

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
}