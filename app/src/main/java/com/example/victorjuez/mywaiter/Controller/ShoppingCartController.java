package com.example.victorjuez.mywaiter.Controller;

import com.example.victorjuez.mywaiter.Model.CartItem;
import com.example.victorjuez.mywaiter.Model.Plate;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCartController {
    //TODO: create Model class cart with attributes plate and attribute qty;
    private static final ShoppingCartController ourInstance = new ShoppingCartController();

    private ArrayList<CartItem> cart;
    private int totalPrice;

    public static ShoppingCartController getInstance() {
        return ourInstance;
    }

    private ShoppingCartController() {
        cart = new ArrayList<>();
        totalPrice = 0;
    }

    public void addToCart(Plate plate){
        CartItem cartItem = new CartItem(plate);
        if(cart.contains(cartItem)){
            int index = cart.indexOf(cartItem);
            cart.get(index).incrementQty();
        }
        else {
            cart.add(cartItem);
        }
        totalPrice+=plate.price;
        logPrint();
    }

    public ArrayList<CartItem> getCart() {
        return cart;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    private void logPrint() {
        for(CartItem cartItem : cart){
            System.out.println("Plate: "+cartItem.getPlate().name+", Qty: "+cartItem.getQty());
        }
    }

    public void empty(){
        cart = new ArrayList<>();
        totalPrice = 0;
    }
}
