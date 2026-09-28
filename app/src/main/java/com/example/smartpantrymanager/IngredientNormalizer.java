package com.example.smartpantrymanager;

public class IngredientNormalizer {
    public static String normalize(String name) {
        if (name == null) return "";
        String normalized = name.trim().toLowerCase();
        // Handle basic plurals
        if (normalized.endsWith("es")) {
            if (normalized.equals("tomatoes") || normalized.equals("potatoes")) {
                normalized = normalized.substring(0, normalized.length() - 2);
            }
        } else if (normalized.endsWith("s") && !normalized.endsWith("ss")) {
            normalized = normalized.substring(0, normalized.length() - 1);
        }
        return normalized;
    }
}