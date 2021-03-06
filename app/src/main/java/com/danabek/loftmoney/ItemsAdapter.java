package com.danabek.loftmoney;


import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private static Context context;

    public ItemsAdapter(Context context) {
        this.context = context;
    }

    private List<ItemPosition> items = Collections.emptyList();
    private ItemsAdapterListener listener = null;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public void setItems(List<ItemPosition> items) {

        this.items = items;
        notifyDataSetChanged();
    }

    public void addItem(ItemPosition item) {
        this.items.add(item);
        notifyItemInserted(items.size());
    }

    ItemPosition removeItem(int position) {
        ItemPosition item = items.get(position);
        items.remove(position);
        notifyItemRemoved(position);
        return (item);
    }

    void setListener(ItemsAdapterListener listener) {
        this.listener = listener;
    }

    void toggleItem(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.put(position, false);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    List<Integer> getSelectedPositions() {
        List<Integer> selectedPositions = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            int key = selectedItems.keyAt(i);
            if (selectedItems.get(key)) {
                selectedPositions.add(key);
            }
        }
        return selectedPositions;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_position, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemPosition item = items.get(position);
        holder.bindItem(item, selectedItems.get(position));
        holder.setListener(item, listener, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);

        }

        public void bindItem(ItemPosition item, boolean selected) {
            name.setText(item.getName());
            price.setText(String.valueOf(item.getPrice()));
            itemView.setSelected(selected);
        }

        void setListener(ItemPosition item, ItemsAdapterListener listener, int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(item, position);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.onItemLongClick(item, position);
                    }
                    return true;
                }
            });
        }

    }


}

