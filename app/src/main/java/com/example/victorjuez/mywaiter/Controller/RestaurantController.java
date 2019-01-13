package com.example.victorjuez.mywaiter.Controller;

import com.example.victorjuez.mywaiter.Model.Restaurant;

/**
 * Singleton to save and load between all the system a specific Restaurant for management purposes.
 * We use it for example after QR read successfully, the restaurant read will be the selected Restaurant.
 */
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
