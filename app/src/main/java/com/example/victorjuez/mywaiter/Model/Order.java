package com.example.victorjuez.mywaiter.Model;


import java.util.ArrayList;

public class Order {
    public String id;
    public String userId;
    public int restaurantId;
    public ArrayList<Integer> plateId;
    public ArrayList<Integer> qty;

    public Order(String id, String userId, int restaurantId, ArrayList<Integer> plateId, ArrayList<Integer> qty) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.plateId = plateId;
        this.qty = qty;
    }
}
