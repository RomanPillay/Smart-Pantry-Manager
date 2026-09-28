package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeRepository {

    public static class RecipeMatch {
        private final Recipe recipe;
        private final int matchCount;
        private final int totalCount;
        private final List<String> missingIngredients;

        public RecipeMatch(Recipe recipe, int matchCount, int totalCount, List<String> missingIngredients) {
            this.recipe = recipe;
            this.matchCount = matchCount;
            this.totalCount = totalCount;
            this.missingIngredients = missingIngredients;
        }

        public Recipe getRecipe() { return recipe; }
        public int getMatchCount() { return matchCount; }
        public int getTotalCount() { return totalCount; }
        public List<String> getMissingIngredients() { return missingIngredients; }
        public int getMatchPercentage() {
            return totalCount == 0 ? 0 : (matchCount * 100) / totalCount;
        }
    }

    private static final List<Recipe> RECIPES = new ArrayList<>();

    static {
        // Recipe 1: Classic Omelette
        RECIPES.add(new Recipe(
            "1",
            "Classic Fluffy Omelette",
            "A quick and easy breakfast staple made with eggs, butter, and cheese.",
            Arrays.asList("egg", "butter", "cheese", "salt", "pepper"),
            Arrays.asList("2-3 Eggs", "1 tbsp Butter", "1/4 cup Shredded Cheese", "Pinch of Salt & Pepper"),
            "1. Whisk eggs in a bowl with a pinch of salt and pepper.\n" +
            "2. Melt butter in a non-stick skillet over medium heat.\n" +
            "3. Pour in eggs and tilt skillet to cover evenly.\n" +
            "4. As eggs set, sprinkle cheese over one half.\n" +
            "5. Fold omelette in half and serve warm."
        ));

        // Recipe 2: Tomato Basil Pasta
        RECIPES.add(new Recipe(
            "2",
            "Tomato Garlic Pasta",
            "Savory pasta tossed in fresh tomato sauce, garlic, and olive oil.",
            Arrays.asList("pasta", "tomato", "garlic", "olive oil", "salt", "cheese"),
            Arrays.asList("200g Pasta (Spaghetti or Penne)", "3 fresh Tomatoes or canned tomatoes", "2 cloves Garlic (minced)", "2 tbsp Olive Oil", "Grated Parmesan Cheese"),
            "1. Boil pasta in salted water until al dente.\n" +
            "2. Heat olive oil in a pan, sauté minced garlic until fragrant.\n" +
            "3. Add chopped tomatoes and simmer for 10 minutes until soft.\n" +
            "4. Toss cooked pasta into the sauce.\n" +
            "5. Garnish with cheese and serve immediately."
        ));

        // Recipe 3: Fluffy Pancakes
        RECIPES.add(new Recipe(
            "3",
            "Golden Pancakes",
            "Delicious fluffy pancakes perfect for weekend breakfasts.",
            Arrays.asList("flour", "milk", "egg", "butter", "sugar"),
            Arrays.asList("1 cup All-Purpose Flour", "1 cup Milk", "1 Egg", "2 tbsp Melted Butter", "2 tbsp Sugar"),
            "1. In a bowl, mix flour, sugar, egg, milk, and melted butter into a smooth batter.\n" +
            "2. Heat a greased pan over medium heat.\n" +
            "3. Pour 1/4 cup batter for each pancake.\n" +
            "4. Cook until bubbles form on top, then flip and cook until golden brown.\n" +
            "5. Serve with syrup, honey, or fresh fruit."
        ));

        // Recipe 4: French Toast
        RECIPES.add(new Recipe(
            "4",
            "Classic French Toast",
            "Golden brown bread dipped in a rich egg and cinnamon custard.",
            Arrays.asList("bread", "egg", "milk", "butter", "cinnamon"),
            Arrays.asList("4 slices Bread", "2 Eggs", "1/4 cup Milk", "1 tbsp Butter", "1/2 tsp Ground Cinnamon"),
            "1. Whisk eggs, milk, and cinnamon together in a shallow dish.\n" +
            "2. Melt butter in a skillet over medium heat.\n" +
            "3. Dip bread slices into the egg mixture, coating both sides.\n" +
            "4. Fry slices until golden on each side (about 2-3 mins per side).\n" +
            "5. Serve hot with maple syrup or powdered sugar."
        ));

        // Recipe 5: Quick Garlic Fried Rice
        RECIPES.add(new Recipe(
            "5",
            "Garlic Butter Fried Rice",
            "A flavorful garlic rice dish made with cooked rice and simple pantry staples.",
            Arrays.asList("rice", "garlic", "butter", "egg", "soy sauce"),
            Arrays.asList("2 cups Cooked Rice", "4 cloves Garlic (minced)", "2 tbsp Butter", "1 Egg", "1 tbsp Soy Sauce"),
            "1. Melt butter in a large skillet over medium heat.\n" +
            "2. Add minced garlic and cook until light golden brown.\n" +
            "3. Push garlic to side, scramble the egg in the pan.\n" +
            "4. Add cooked rice and soy sauce, stirring constantly to heat through.\n" +
            "5. Season to taste and serve hot."
        ));

        // Recipe 6: Fresh Garden Salad
        RECIPES.add(new Recipe(
            "6",
            "Fresh Pantry Salad",
            "A crisp, refreshing salad utilizing whatever fresh vegetables you have.",
            Arrays.asList("lettuce", "tomato", "cucumber", "olive oil", "lemon"),
            Arrays.asList("2 cups Chopped Lettuce or Greens", "1 Tomato (diced)", "1 Cucumber (sliced)", "2 tbsp Olive Oil", "1 tbsp Lemon Juice"),
            "1. Chop all fresh vegetables into bite-sized pieces.\n" +
            "2. Whisk olive oil, lemon juice, salt, and pepper in a small bowl.\n" +
            "3. Toss vegetables in salad bowl with dressing.\n" +
            "4. Serve fresh!"
        ));

        // Recipe 7: Creamy Potato Soup
        RECIPES.add(new Recipe(
            "7",
            "Comforting Potato Soup",
            "Rich and hearty potato soup made from simple root vegetables.",
            Arrays.asList("potato", "onion", "butter", "milk", "cheese"),
            Arrays.asList("4 large Potatoes (peeled & diced)", "1 Onion (chopped)", "2 tbsp Butter", "2 cups Milk or Broth", "Shredded Cheese for topping"),
            "1. Melt butter in a pot and sauté onions until soft.\n" +
            "2. Add diced potatoes and broth/water, simmer until potatoes are fork-tender (15 mins).\n" +
            "3. Mash half the potatoes for texture or blend until creamy.\n" +
            "4. Stir in milk, heat gently, season with salt and pepper.\n" +
            "5. Serve topped with cheese."
        ));

        // Recipe 8: Easy Guacamole
        RECIPES.add(new Recipe(
            "8",
            "Fresh Guacamole",
            "Zesty avocado dip made with lime, onion, and cilantro.",
            Arrays.asList("avocado", "lime", "onion", "tomato", "salt"),
            Arrays.asList("2 ripe Avocados", "1 tbsp Lime juice", "1/4 cup diced Onion", "1 small Tomato (diced)", "Pinch of Salt"),
            "1. Scoop avocado flesh into a bowl and mash with a fork.\n" +
            "2. Stir in lime juice, onions, diced tomatoes, and salt.\n" +
            "3. Taste and adjust seasoning as needed.\n" +
            "4. Serve immediately with tortilla chips or as a spread."
        ));

        // Recipe 9: Grilled Cheese Sandwich
        RECIPES.add(new Recipe(
            "9",
            "Crispy Grilled Cheese",
            "Golden, buttery toasted sandwich filled with gooey melted cheese.",
            Arrays.asList("bread", "cheese", "butter"),
            Arrays.asList("2 slices Bread", "2 slices Cheese (Cheddar or Swiss)", "1 tbsp Butter"),
            "1. Butter one side of each slice of bread.\n" +
            "2. Place one slice butter-side down on a pan over medium-low heat.\n" +
            "3. Top with cheese slices and remaining bread slice (butter side up).\n" +
            "4. Grill until bottom is golden brown, then flip and grill until cheese melts.\n" +
            "5. Cut diagonally and enjoy!"
        ));

        // Recipe 10: Banana Oat Smoothie
        RECIPES.add(new Recipe(
            "10",
            "Banana Oat Smoothie",
            "A healthy, energizing smoothie with bananas, milk, and oats.",
            Arrays.asList("banana", "milk", "oats", "honey"),
            Arrays.asList("1 ripe Banana", "1 cup Milk", "1/4 cup Rolled Oats", "1 tbsp Honey or Maple Syrup"),
            "1. Combine banana, milk, oats, and honey in a blender.\n" +
            "2. Blend on high speed for 1-2 minutes until smooth and creamy.\n" +
            "3. Pour into a glass and enjoy cold!"
        ));
    }

    public static List<Recipe> getAllRecipes() {
        return RECIPES;
    }

    public static Recipe getRecipeById(String id) {
        for (Recipe r : RECIPES) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public static List<RecipeMatch> findSuggestedRecipes(List<PantryItem> pantryItems) {
        Set<String> pantryKeys = new HashSet<>();
        for (PantryItem item : pantryItems) {
            if (item.getName() != null && !item.getName().trim().isEmpty()) {
                pantryKeys.add(IngredientNormalizer.normalize(item.getName()));
            }
        }

        List<RecipeMatch> matches = new ArrayList<>();

        for (Recipe recipe : RECIPES) {
            int matchCount = 0;
            List<String> missing = new ArrayList<>();

            for (String required : recipe.getRequiredIngredients()) {
                String reqNorm = IngredientNormalizer.normalize(required);
                boolean found = false;

                for (String pantryKey : pantryKeys) {
                    if (pantryKey.contains(reqNorm) || reqNorm.contains(pantryKey)) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    matchCount++;
                } else {
                    missing.add(required);
                }
            }

            if (matchCount > 0) {
                matches.add(new RecipeMatch(recipe, matchCount, recipe.getRequiredIngredients().size(), missing));
            }
        }

        // Sort matches by highest match percentage and match count
        Collections.sort(matches, (m1, m2) -> {
            int percentageCompare = Integer.compare(m2.getMatchPercentage(), m1.getMatchPercentage());
            if (percentageCompare != 0) return percentageCompare;
            return Integer.compare(m2.getMatchCount(), m1.getMatchCount());
        });

        return matches;
    }
}
