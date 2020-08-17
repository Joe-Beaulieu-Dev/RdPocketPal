package com.example.rdpocketpal2.anthropometrics

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.databinding.ActivityAnthropometricsBinding
import com.example.rdpocketpal2.settings.SettingsActivity

class AnthropometricsActivity : AppCompatActivity() {
    private lateinit var mViewModel: AnthropometricsViewModel
    private lateinit var mBinding: ActivityAnthropometricsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set up ViewModel
        mViewModel = ViewModelProvider(this).get(AnthropometricsViewModel::class.java)

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setUpBtnRipple(R.id.anthro_clear_btn)
            setUpBtnRipple(R.id.anthro_calculate_btn)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpBtnRipple(@IdRes btnId: Int) {
        findViewById<Button>(btnId).foreground =
                ResourcesCompat.getDrawable(resources, R.drawable.ripple_rectangle, theme)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.main_menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}