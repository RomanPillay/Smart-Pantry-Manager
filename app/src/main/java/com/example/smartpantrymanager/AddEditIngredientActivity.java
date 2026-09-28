package com.example.smartpantrymanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Calendar;
import java.util.Locale;

public class AddEditIngredientActivity extends AppCompatActivity {
    
    private TextInputEditText editTextName, editTextQuantity, editTextExpiry;
    private TextInputLayout layoutName, layoutQuantity, layoutExpiry;
    private Spinner spinnerUnit;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);
        
        editTextName = findViewById(R.id.editTextName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextExpiry = findViewById(R.id.editTextExpiry);
        layoutName = findViewById(R.id.layoutName);
        layoutQuantity = findViewById(R.id.layoutQuantity);
        layoutExpiry = findViewById(R.id.layoutExpiry);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        
        // Metric units as standard
        String[] units = new String[]{"g", "kg", "ml", "L", "pcs", "tsp", "tbsp", "cups"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        spinnerUnit.setAdapter(adapter);
        
        layoutExpiry.setEndIconOnClickListener(v -> showDatePicker());
        
        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(v -> validateAndSave());
    }
    
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
            (view, selectedYear, selectedMonth, selectedDay) -> {
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                editTextExpiry.setText(formattedDate);
            }, year, month, day);
        datePickerDialog.show();
    }
    
    private void validateAndSave() {
        boolean isValid = true;
        
        String name = editTextName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            layoutName.setError("Ingredient name is required");
            isValid = false;
        } else {
            layoutName.setError(null);
        }
        
        String quantityStr = editTextQuantity.getText().toString();
        if (TextUtils.isEmpty(quantityStr)) {
            layoutQuantity.setError("Quantity is required");
            isValid = false;
        } else {
            try {
                double quantity = Double.parseDouble(quantityStr);
                if (quantity < 0) {
                    layoutQuantity.setError("Quantity cannot be negative");
                    isValid = false;
                } else {
                    layoutQuantity.setError(null);
                }
            } catch (NumberFormatException e) {
                layoutQuantity.setError("Invalid number");
                isValid = false;
            }
        }
        
        if (isValid) {
            String unit = spinnerUnit.getSelectedItem().toString();
            String expiry = editTextExpiry.getText().toString();
            double quantity = Double.parseDouble(quantityStr);
            
            // Temporarily store in static array
            long id = System.currentTimeMillis();
            PantryManager.items.add(new PantryItem(id, name, quantity, unit, expiry));
            
            finish();
        }
    }
}