package com.danabek.loftmoney;

public interface ItemsAdapterListener {
    void onItemClick(ItemPosition item, int position);

    void onItemLongClick(ItemPosition item, int position);
}
