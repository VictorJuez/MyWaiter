package com.example.victorjuez.mywaiter.Controller;

import com.example.victorjuez.mywaiter.Model.CartItem;
import com.example.victorjuez.mywaiter.Model.Plate;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCartController {
    //TODO: create Model class cart with attributes plate and attribute qty;
    private static final ShoppingCartController ourInstance = new ShoppingCartController();

    private ArrayList<CartItem> cart;

    public static ShoppingCartController getInstance() {
        return ourInstance;
    }

    private ShoppingCartController() {
        cart = new ArrayList<>();
    }

    public void addToCart(Plate plate, int qty){
        CartItem cartItem = new CartItem(plate, qty);
        if(cart.contains(cartItem)){
            int index = cart.indexOf(cartItem);
            cart.get(index).setQty(qty);
        }
        else {
            cart.add(cartItem);
        }
        logPrint();
    }

    public ArrayList<CartItem> getCart() {
        return cart;
    }

    public ArrayList<Integer> getPlatesID(){
        ArrayList<Integer> ids = new ArrayList<>();
        for(CartItem cartItem : cart){
          ids.add(cartItem.getPlate().id);
        }

        return ids;
    }

    public ArrayList<Integer> getPlatesQty(){
        ArrayList<Integer> ids = new ArrayList<>();
        for(CartItem cartItem : cart){
            ids.add(cartItem.getQty());
        }

        return ids;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for(CartItem cartItem : cart){
            totalPrice+=cartItem.getPlate().price*cartItem.getQty();
        }
        return totalPrice;
    }

    private void logPrint() {
        for(CartItem cartItem : cart){
            System.out.println("Plate: "+cartItem.getPlate().name+", Qty: "+cartItem.getQty());
        }
    }

    public void empty(){
        cart = new ArrayList<>();
    }

    public void removePlate(Plate plate){
        CartItem cartItem = cart.get(cart.indexOf(new CartItem(plate, 0)));
        cart.remove(new CartItem(plate, 0));
    }
}
