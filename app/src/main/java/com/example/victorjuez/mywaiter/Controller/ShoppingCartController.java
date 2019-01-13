package com.example.victorjuez.mywaiter.Controller;

import com.example.victorjuez.mywaiter.Model.CartItem;
import com.example.victorjuez.mywaiter.Model.Plate;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCartController {
    private static final ShoppingCartController ourInstance = new ShoppingCartController();

    private ArrayList<CartItem> cart;
    private ArrayList<CartItem> ordered;

    public static ShoppingCartController getInstance() {
        return ourInstance;
    }

    private ShoppingCartController() {
        cart = new ArrayList<>();
        ordered = new ArrayList<>();
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

    public void removeFromCart(Plate plate){
        cart.remove(new CartItem(plate, 0));
    }

    public ArrayList<CartItem> getCart() {
        return cart;
    }

    public ArrayList<CartItem> getOrdered() {
        return ordered;
    }

    public ArrayList<Integer> getPlatesCartID(){
        ArrayList<Integer> ids = new ArrayList<>();
        for(CartItem cartItem : cart){
          ids.add(cartItem.getPlate().id);
        }

        return ids;
    }

    public int getTotalPriceCart() {
        int totalPrice = 0;
        for(CartItem cartItem : cart){
            totalPrice+=cartItem.getPlate().price*cartItem.getQty();
        }
        return totalPrice;
    }

    public int getTotalPriceOrdered(){
        int totalPrice = 0;
        for(CartItem orderItem : ordered){
            totalPrice+=orderItem.getPlate().price*orderItem.getQty();
        }
        return totalPrice;
    }

    private void logPrint() {
        for(CartItem cartItem : cart){
            System.out.println("Plate: "+cartItem.getPlate().name+", Qty: "+cartItem.getQty());
        }
    }

    public void emptyCart(){
        cart = new ArrayList<>();
    }

    public void makeOrder() {
        if(ordered.isEmpty()) ordered = cart;
        else{
            for(CartItem cartItem : cart){
                if(ordered.contains(cartItem)){
                    CartItem orderedItem = ordered.get(ordered.indexOf(cartItem));
                    ordered.get(ordered.indexOf(cartItem)).setQty(orderedItem.getQty()+cartItem.getQty());
                }
                else ordered.add(cartItem);
            }
        }
    }
}
