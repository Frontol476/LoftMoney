package com.danabek.loftmoney;

public class ItemPosition {
    public static final String TYPE_INCOME = "income";
    public static final String TYPE_EXPENSE = "expense";

    private String name;
    private Double price;
    private String type;


    public ItemPosition(String name, Double price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
