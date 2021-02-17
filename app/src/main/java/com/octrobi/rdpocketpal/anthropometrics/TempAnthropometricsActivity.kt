package com.octrobi.rdpocketpal.anthropometrics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.octrobi.rdpocketpal.R

class TempAnthropometricsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp_anthropometrics)
    }

    override fun onStart() {
        super.onStart()
//        findViewById<TextInputLayout>(R.id.anthro_height_layout).error =
//                getString(R.string.error_enter_a_number)
    }
}