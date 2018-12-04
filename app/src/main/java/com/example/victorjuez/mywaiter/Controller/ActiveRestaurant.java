package com.example.victorjuez.mywaiter.Controller;

import com.example.victorjuez.mywaiter.Model.Restaurant;

public class ActiveRestaurant {
    private static final ActiveRestaurant ourInstance = new ActiveRestaurant();
    private Restaurant restaurant;

    public static ActiveRestaurant getInstance() {
        return ourInstance;
    }
    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant(){
        return this.restaurant;
    }

    private ActiveRestaurant() {
    }
}
