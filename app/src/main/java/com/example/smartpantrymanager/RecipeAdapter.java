package com.example.smartpantrymanager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    public interface OnRecipeClickListener {
        void onRecipeClick(RecipeRepository.RecipeMatch match);
    }

    private List<RecipeRepository.RecipeMatch> matches;
    private OnRecipeClickListener listener;

    public RecipeAdapter(List<RecipeRepository.RecipeMatch> matches, OnRecipeClickListener listener) {
        this.matches = matches;
        this.listener = listener;
    }

    public void updateData(List<RecipeRepository.RecipeMatch> newMatches) {
        this.matches = newMatches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        RecipeRepository.RecipeMatch match = matches.get(position);
        Recipe recipe = match.getRecipe();

        holder.textViewRecipeName.setText(recipe.getName());

        String matchText = "Match: " + match.getMatchPercentage() + "% (" + match.getMatchCount() + "/" + match.getTotalCount() + " ingredients in pantry)";
        holder.textViewRecipeMatchInfo.setText(matchText);

        String desc = recipe.getDescription();
        if (!match.getMissingIngredients().isEmpty()) {
            desc += "\nMissing: " + TextUtils.join(", ", match.getMissingIngredients());
        }
        holder.textViewRecipePreview.setText(desc);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRecipeClick(match);
            }
        });
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRecipeName, textViewRecipeMatchInfo, textViewRecipePreview;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRecipeName = itemView.findViewById(R.id.textViewRecipeName);
            textViewRecipeMatchInfo = itemView.findViewById(R.id.textViewRecipeMatchInfo);
            textViewRecipePreview = itemView.findViewById(R.id.textViewRecipePreview);
        }
    }
}
