package com.example.victorjuez.mywaiter.Controller;

import com.example.victorjuez.mywaiter.Model.Plate;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCartController {
    //TODO: create Model class cart with attributes plate and attribute qty;
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

    public HashMap<Plate, Integer> getCart(){
        HashMap<Plate, Integer> auxMap = new HashMap<>();

        for(Plate plate : cart){
            auxMap.put(plate, qty.get(cart.indexOf(plate)));
        }

        return auxMap;
    }

    private void logPrint() {
        for(Plate plate : cart){
            System.out.println("Plate: "+plate.name+", Qty: "+qty.get(cart.indexOf(plate)));
        }
    }

    public void empty(){
        cart = new ArrayList<>();
        qty = new ArrayList<>();
    }
}
