package com.josephbeaulieu.rdpocketpal.disclaimer

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.josephbeaulieu.rdpocketpal.R
import com.josephbeaulieu.rdpocketpal.settings.SettingsActivity
import com.josephbeaulieu.rdpocketpal.util.showToast

class DisclaimerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disclaimer)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.main_menu_disclaimer -> {
                showToast(this, R.string.text_already_viewing_disclaimer, Toast.LENGTH_SHORT)
                return true
            }
            R.id.main_menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}