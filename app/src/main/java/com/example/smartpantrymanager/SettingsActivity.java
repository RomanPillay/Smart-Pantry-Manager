package com.example.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {
    
    public static final String PREFS_NAME = "SmartPantryPrefs";
    public static final String PREF_DARK_MODE = "dark_mode";
    public static final String PREF_ALERTS = "expiry_alerts";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        SwitchMaterial switchDarkMode = findViewById(R.id.switchDarkMode);
        SwitchMaterial switchAlerts = findViewById(R.id.switchAlerts);
        Button buttonResetSampleData = findViewById(R.id.buttonResetSampleData);
        Button buttonClearAllPantry = findViewById(R.id.buttonClearAllPantry);
        
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDark = prefs.getBoolean(PREF_DARK_MODE, false);
        boolean alertsEnabled = prefs.getBoolean(PREF_ALERTS, true);
        
        switchDarkMode.setChecked(isDark);
        switchAlerts.setChecked(alertsEnabled);
        
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(PREF_DARK_MODE, isChecked).apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        switchAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(PREF_ALERTS, isChecked).apply();
            String msg = isChecked ? "Expiring-soon alerts enabled" : "Expiring-soon alerts disabled";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        buttonResetSampleData.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Reset Pantry Data")
                .setMessage("This will restore default sample ingredients. Continue?")
                .setPositiveButton("Reset", (dialog, which) -> {
                    PantryManager.resetDefaults(this);
                    Toast.makeText(this, "Sample pantry items restored!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });

        buttonClearAllPantry.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Clear All Pantry Items")
                .setMessage("Are you sure you want to remove ALL items from your pantry?")
                .setPositiveButton("Clear All", (dialog, which) -> {
                    PantryManager.clearAll(this);
                    Toast.makeText(this, "Pantry cleared!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });
        
        setupBottomNav();
    }
    
    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_settings);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_pantry) {
                startActivity(new Intent(this, PantryListActivity.class));
                overridePendingTransition(0,0);
                finish();
                return true;
            } else if (id == R.id.nav_recipes) {
                startActivity(new Intent(this, SuggestedRecipesActivity.class));
                overridePendingTransition(0,0);
                finish();
                return true;
            } else if (id == R.id.nav_settings) {
                return true;
            }
            return false;
        });
    }
}
