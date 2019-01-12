package com.example.victorjuez.mywaiter.Model;

public class Session {
    private static final Session ourInstance = new Session();

    private User CurrentUser;
    private int restaurantRate;

    public static Session getInstance() {
        return ourInstance;
    }

    private Session() {
    }

    public User getCurrentUser() {
        return CurrentUser;
    }

    public int getRestaurantRate() {
        return restaurantRate;
    }

    public void setCurrentUser(User currentUser) {
        CurrentUser = currentUser;
    }

    public void setRestaurantRate(int restaurantRate) {
        this.restaurantRate = restaurantRate;
    }
}
