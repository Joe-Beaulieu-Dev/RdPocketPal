package com.example.rdpocketpal2.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.settings.SettingsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity implements
        HomeButtonAdapter.HomeButtonListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // set up RecyclerView
        setUpRecyclerView();
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

    private void setUpRecyclerView() {
        View root = findViewById(android.R.id.content);

        RecyclerView homeButtons = root.findViewById(R.id.home_buttons_recyclerView);
        homeButtons.setLayoutManager(new GridLayoutManager(this, 2));
        homeButtons.setAdapter(new HomeButtonAdapter(this));
    }

    @Override
    public void onClick(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}
