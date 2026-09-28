package com.example.smartpantrymanager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(PantryItem item);
        void onDeleteClick(PantryItem item);
    }

    private List<PantryItem> items;
    private OnItemClickListener listener;

    public PantryAdapter(List<PantryItem> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
        
        // Format quantity (remove trailing .0 if integer)
        double q = item.getQuantity();
        String qStr = (q == (long) q) ? String.format(Locale.getDefault(), "%d", (long) q) : String.format(Locale.getDefault(), "%.1f", q);
        holder.textViewItemQuantity.setText(qStr + " " + item.getUnit());

        if (item.getExpiryDate() != null && !item.getExpiryDate().trim().isEmpty()) {
            holder.textViewItemExpiry.setText("Exp: " + item.getExpiryDate());
            holder.textViewItemExpiry.setVisibility(View.VISIBLE);

            // Expiry status check
            checkExpiryStatus(item.getExpiryDate(), holder.textViewItemExpiry);
        } else {
            holder.textViewItemExpiry.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });

        holder.buttonDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(item);
            }
        });
    }

    private void checkExpiryStatus(String dateStr, TextView expiryTextView) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date expiryDate = sdf.parse(dateStr);
            if (expiryDate != null) {
                Date today = new Date();
                long diffMillis = expiryDate.getTime() - today.getTime();
                long diffDays = diffMillis / (24 * 60 * 60 * 1000);

                if (diffDays < 0) {
                    expiryTextView.setText("EXPIRED (" + dateStr + ")");
                    expiryTextView.setTextColor(Color.RED);
                } else if (diffDays <= 3) {
                    expiryTextView.setText("Expiring Soon (" + dateStr + ")");
                    expiryTextView.setTextColor(Color.parseColor("#F57C00")); // Orange
                } else {
                    expiryTextView.setTextColor(Color.GRAY);
                }
            }
        } catch (Exception e) {
            expiryTextView.setTextColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName, textViewItemQuantity, textViewItemExpiry;
        ImageButton buttonDelete;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemQuantity = itemView.findViewById(R.id.textViewItemQuantity);
            textViewItemExpiry = itemView.findViewById(R.id.textViewItemExpiry);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
