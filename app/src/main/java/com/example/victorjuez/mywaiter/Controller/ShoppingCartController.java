package com.example.victorjuez.mywaiter.Controller;

import com.example.victorjuez.mywaiter.Model.Plate;

import java.util.ArrayList;

public class ShoppingCartController {
    private static final ShoppingCartController ourInstance = new ShoppingCartController();

    private ArrayList<Plate> cart;
    private ArrayList<Integer> qty;

    public static ShoppingCartController getInstance() {
        return ourInstance;
    }

    private ShoppingCartController() {
        cart = new ArrayList<>();
        qty = new ArrayList<>();
    }

    public void addToCart(Plate plate){
        if(cart.contains(plate)){
            int index = cart.indexOf(plate);
            qty.add(index, qty.get(index)+1);
        }
        else {
            cart.add(plate);
            qty.add(1);
        }
        logPrint();
    }

    private void logPrint() {
        for(Plate plate : cart){
            System.out.println("Plate: "+plate.name+", Qty: "+qty.get(cart.indexOf(plate)));
        }
    }
}
