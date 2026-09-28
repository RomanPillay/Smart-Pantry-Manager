package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    private TextView textViewTitle, textViewDescription, textViewIngredients, textViewInstructions;
    private Button buttonCook;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewIngredients = findViewById(R.id.textViewIngredients);
        textViewInstructions = findViewById(R.id.textViewInstructions);
        buttonCook = findViewById(R.id.buttonCookRecipe);

        String recipeId = getIntent().getStringExtra(EXTRA_RECIPE_ID);
        if (recipeId != null) {
            recipe = RecipeRepository.getRecipeById(recipeId);
        }

        if (recipe == null) {
            Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setTitle(recipe.getName());
        textViewTitle.setText(recipe.getName());
        textViewDescription.setText(recipe.getDescription());

        List<String> ingredients = recipe.getDisplayIngredients();
        StringBuilder ingBuilder = new StringBuilder();
        for (String ing : ingredients) {
            ingBuilder.append("• ").append(ing).append("\n");
        }
        textViewIngredients.setText(ingBuilder.toString().trim());

        textViewInstructions.setText(recipe.getInstructions());

        buttonCook.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Cook " + recipe.getName() + "?")
                .setMessage("Would you like to mark this recipe as cooked?")
                .setPositiveButton("Yes, Cook!", (dialog, which) -> {
                    deductIngredientsFromPantry();
                    Toast.makeText(RecipeDetailActivity.this, "Bon appétit! Ingredients updated in pantry.", Toast.LENGTH_LONG).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });
    }

    private void deductIngredientsFromPantry() {
        PantryManager.loadItems(this);
        for (String req : recipe.getRequiredIngredients()) {
            String normReq = IngredientNormalizer.normalize(req);
            for (PantryItem item : PantryManager.items) {
                String normPantry = IngredientNormalizer.normalize(item.getName());
                if (normPantry.contains(normReq) || normReq.contains(normPantry)) {
                    if (item.getQuantity() > 1) {
                        PantryManager.updateItem(this, new PantryItem(
                            item.getId(), item.getName(), item.getQuantity() - 1, item.getUnit(), item.getExpiryDate()
                        ));
                    } else {
                        PantryManager.deleteItem(this, item.getId());
                    }
                    break;
                }
            }
        }
    }
}
