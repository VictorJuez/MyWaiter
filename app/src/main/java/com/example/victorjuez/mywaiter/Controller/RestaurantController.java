package com.example.victorjuez.mywaiter.Controller;

import com.example.victorjuez.mywaiter.Model.Restaurant;

public class RestaurantController {
    private static final RestaurantController ourInstance = new RestaurantController();
    private Restaurant restaurant;

    public static RestaurantController getInstance() {
        return ourInstance;
    }
    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant(){
        return this.restaurant;
    }

    private RestaurantController() {
    }
}
