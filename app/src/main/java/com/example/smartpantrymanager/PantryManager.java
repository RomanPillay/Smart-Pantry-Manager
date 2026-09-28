package com.example.smartpantrymanager;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class PantryManager {
    public static List<PantryItem> items = new ArrayList<>();
    private static final String PREFS_NAME = "SmartPantryData";
    private static final String KEY_ITEMS = "pantry_items";
    private static final String KEY_INITIALIZED = "has_initialized_defaults";

    public static List<PantryItem> loadItems(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean initialized = prefs.getBoolean(KEY_INITIALIZED, false);
        items.clear();

        if (!initialized) {
            // Seed initial sample ingredients for immediate demo & functionality
            items.add(new PantryItem(1, "Eggs", 6, "pcs", "15/12/2026"));
            items.add(new PantryItem(2, "Whole Milk", 1, "L", "20/12/2026"));
            items.add(new PantryItem(3, "Tomatoes", 4, "pcs", "10/12/2026"));
            items.add(new PantryItem(4, "Pasta (Spaghetti)", 500, "g", "30/12/2026"));
            items.add(new PantryItem(5, "Butter", 200, "g", "25/12/2026"));
            items.add(new PantryItem(6, "Cheddar Cheese", 250, "g", "18/12/2026"));
            items.add(new PantryItem(7, "Garlic", 3, "pcs", "30/12/2026"));
            items.add(new PantryItem(8, "White Bread", 1, "pcs", "12/12/2026"));

            saveItems(context);
            prefs.edit().putBoolean(KEY_INITIALIZED, true).apply();
            return items;
        }

        String jsonStr = prefs.getString(KEY_ITEMS, "[]");
        try {
            JSONArray array = new JSONArray(jsonStr);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                long id = obj.optLong("id", System.currentTimeMillis());
                String name = obj.optString("name", "");
                double quantity = obj.optDouble("quantity", 0.0);
                String unit = obj.optString("unit", "");
                String expiryDate = obj.optString("expiryDate", "");
                items.add(new PantryItem(id, name, quantity, unit, expiryDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void saveItems(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        JSONArray array = new JSONArray();
        try {
            for (PantryItem item : items) {
                JSONObject obj = new JSONObject();
                obj.put("id", item.getId());
                obj.put("name", item.getName());
                obj.put("quantity", item.getQuantity());
                obj.put("unit", item.getUnit());
                obj.put("expiryDate", item.getExpiryDate());
                array.put(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        prefs.edit().putString(KEY_ITEMS, array.toString()).apply();
    }

    public static void addItem(Context context, PantryItem item) {
        items.add(item);
        saveItems(context);
    }

    public static void updateItem(Context context, PantryItem updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == updatedItem.getId()) {
                items.set(i, updatedItem);
                break;
            }
        }
        saveItems(context);
    }

    public static void deleteItem(Context context, long id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
                break;
            }
        }
        saveItems(context);
    }

    public static PantryItem getItemById(long id) {
        for (PantryItem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public static void clearAll(Context context) {
        items.clear();
        saveItems(context);
    }

    public static void resetDefaults(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_INITIALIZED, false).apply();
        loadItems(context);
    }
}
