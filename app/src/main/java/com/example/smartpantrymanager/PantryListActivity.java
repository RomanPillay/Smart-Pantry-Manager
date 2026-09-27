package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class PantryListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);
        
        findViewById(R.id.fabAddIngredient).setOnClickListener(v -> {
            startActivity(new Intent(PantryListActivity.this, AddEditIngredientActivity.class));
        });
        
        setupBottomNav();
    }
    
    private void setupBottomNav() {
        // Bottom Navigation setup here
    }
}
