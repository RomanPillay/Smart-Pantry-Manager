package com.example.smartpantrymanager;

import java.util.List;

public class Recipe {
    private String id;
    private String name;
    private String description;
    private List<String> requiredIngredients; // Normalized keys for matching
    private List<String> displayIngredients;  // User-facing ingredient list with quantities
    private String instructions;

    public Recipe(String id, String name, String description, List<String> requiredIngredients, List<String> displayIngredients, String instructions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requiredIngredients = requiredIngredients;
        this.displayIngredients = displayIngredients;
        this.instructions = instructions;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<String> getRequiredIngredients() { return requiredIngredients; }
    public List<String> getDisplayIngredients() { return displayIngredients; }
    public String getInstructions() { return instructions; }
}
