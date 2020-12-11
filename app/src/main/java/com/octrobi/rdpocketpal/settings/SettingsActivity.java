package com.octrobi.rdpocketpal.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.octrobi.rdpocketpal.R;
import com.octrobi.rdpocketpal.disclaimer.DisclaimerActivity;
import com.octrobi.rdpocketpal.util.UiUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
            case R.id.main_menu_disclaimer:
                startActivity(new Intent(this, DisclaimerActivity.class));
                return true;
            case R.id.main_menu_settings:
                UiUtil.showToast(this
                        , R.string.text_already_viewing_settings, Toast.LENGTH_SHORT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
