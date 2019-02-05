package com.danabek.loftmoney;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

   private Context context;

    public ItemsAdapter(Context context) {
        this.context = context;
    }
    private  List<ItemPosition> items = Collections.emptyList();
    public void setItems(List<ItemPosition> items) {

        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_position,parent,false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
          ItemPosition item = items.get(position);
          holder.bindItem(item);

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
        public void bindItem (ItemPosition item) {
            name.setText(item.getName());
            price.setText(item.getPrice());
        }

    }

}

