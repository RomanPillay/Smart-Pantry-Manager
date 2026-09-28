package com.example.smartpantrymanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Calendar;
import java.util.Locale;

public class AddEditIngredientActivity extends AppCompatActivity {
    
    public static final String EXTRA_ITEM_ID = "extra_item_id";
    
    private TextInputEditText editTextName, editTextQuantity, editTextExpiry;
    private TextInputLayout layoutName, layoutQuantity, layoutExpiry;
    private Spinner spinnerUnit;
    private Button buttonSave, buttonDelete;
    
    private String[] units = new String[]{"g", "kg", "ml", "L", "pcs", "tsp", "tbsp", "cups"};
    private boolean isEditMode = false;
    private long editingItemId = -1;
    
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
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDeleteIngredient);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        spinnerUnit.setAdapter(adapter);
        
        layoutExpiry.setEndIconOnClickListener(v -> showDatePicker());
        
        if (getIntent().hasExtra(EXTRA_ITEM_ID)) {
            editingItemId = getIntent().getLongExtra(EXTRA_ITEM_ID, -1);
            if (editingItemId != -1) {
                isEditMode = true;
                setTitle("Edit Ingredient");
                buttonSave.setText("Update Ingredient");
                buttonDelete.setVisibility(View.VISIBLE);
                loadItemData(editingItemId);
            }
        } else {
            setTitle("Add Ingredient");
        }
        
        buttonSave.setOnClickListener(v -> validateAndSave());
        
        buttonDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Delete Ingredient")
                .setMessage("Are you sure you want to delete this ingredient?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    PantryManager.deleteItem(this, editingItemId);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });
    }
    
    private void loadItemData(long itemId) {
        PantryItem item = PantryManager.getItemById(itemId);
        if (item != null) {
            editTextName.setText(item.getName());
            
            double q = item.getQuantity();
            String qStr = (q == (long) q) ? String.format(Locale.getDefault(), "%d", (long) q) : String.format(Locale.getDefault(), "%.1f", q);
            editTextQuantity.setText(qStr);
            
            if (item.getExpiryDate() != null) {
                editTextExpiry.setText(item.getExpiryDate());
            }
            
            for (int i = 0; i < units.length; i++) {
                if (units[i].equalsIgnoreCase(item.getUnit())) {
                    spinnerUnit.setSelection(i);
                    break;
                }
            }
        }
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
        
        String name = editTextName.getText() != null ? editTextName.getText().toString().trim() : "";
        if (TextUtils.isEmpty(name)) {
            layoutName.setError("Ingredient name is required");
            isValid = false;
        } else {
            layoutName.setError(null);
        }
        
        String quantityStr = editTextQuantity.getText() != null ? editTextQuantity.getText().toString().trim() : "";
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
            String unit = spinnerUnit.getSelectedItem() != null ? spinnerUnit.getSelectedItem().toString() : "pcs";
            String expiry = editTextExpiry.getText() != null ? editTextExpiry.getText().toString().trim() : "";
            double quantity = Double.parseDouble(quantityStr);
            
            if (isEditMode) {
                PantryManager.updateItem(this, new PantryItem(editingItemId, name, quantity, unit, expiry));
            } else {
                long id = System.currentTimeMillis();
                PantryManager.addItem(this, new PantryItem(id, name, quantity, unit, expiry));
            }
            
            finish();
        }
    }
}
