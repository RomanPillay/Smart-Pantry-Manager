package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {
    private List<PantryItem> items;

    public PantryAdapter(List<PantryItem> items) {
        this.items = items;
    }

    public void updateData(List<PantryItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry_ingredient, parent, false);
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = items.get(position);
        holder.textViewItemName.setText(item.getName());
        holder.textViewItemQuantity.setText(item.getQuantity() + " " + item.getUnit());
        
        if (item.getExpiryDate() != null && !item.getExpiryDate().isEmpty()) {
            holder.textViewItemExpiry.setText("Exp: " + item.getExpiryDate());
            holder.textViewItemExpiry.setVisibility(View.VISIBLE);
        } else {
            holder.textViewItemExpiry.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName, textViewItemQuantity, textViewItemExpiry;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemQuantity = itemView.findViewById(R.id.textViewItemQuantity);
            textViewItemExpiry = itemView.findViewById(R.id.textViewItemExpiry);
        }
    }
}