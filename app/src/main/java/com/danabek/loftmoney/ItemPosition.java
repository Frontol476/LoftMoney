package com.danabek.loftmoney;

public class ItemPosition {
    private String name;
    private String price;

    public ItemPosition(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

}
