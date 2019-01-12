package com.example.victorjuez.mywaiter.Model;


import java.util.ArrayList;
import java.util.Map;

public class Order {
    public String id;
    public String userId;
    public int restaurantId;
    public int table;
    public Map<String, Integer> plates;

    public Order(String id, String userId, int restaurantId, int table, Map<String, Integer> plates) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.table = table;
        this.plates = plates;
    }
}
