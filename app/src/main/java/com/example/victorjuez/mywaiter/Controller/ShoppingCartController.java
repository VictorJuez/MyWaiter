package com.example.victorjuez.mywaiter.Controller;

import com.example.victorjuez.mywaiter.Model.CartItem;
import com.example.victorjuez.mywaiter.Model.Plate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Singleton that manages the cart functionality when buying plates.
 */
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

    /**
     * Adds a Plate and the quantity of it to the cart
     * @param plate the Plate which has to be added to the card.
     * @param qty the quantity of the selected plate.
     */
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

    /**
     * Remove a plate from the cart
     * @param plate the Plate that is going to be deleted from the shopping cart.
     */
    public void removeFromCart(Plate plate){
        cart.remove(new CartItem(plate, 0));
    }

    public ArrayList<CartItem> getCart() {
        return cart;
    }

    public ArrayList<CartItem> getOrdered() {
        return ordered;
    }

    /**
     * Get all the id of the plates that are currently in the cart.
     * @return all the plates ids
     */
    public ArrayList<Integer> getPlatesCartID(){
        ArrayList<Integer> ids = new ArrayList<>();
        for(CartItem cartItem : cart){
          ids.add(cartItem.getPlate().id);
        }

        return ids;
    }

    /**
     * Get the total price of plates stored in the current cart
     * @return total price
     */
    public int getTotalPriceCart() {
        int totalPrice = 0;
        for(CartItem cartItem : cart){
            totalPrice+=cartItem.getPlate().price*cartItem.getQty();
        }
        return totalPrice;
    }

    /**
     * Get the total price of plates already ordered (stored in the order array)
     * @return total price
     */
    public int getTotalPriceOrdered(){
        int totalPrice = 0;
        for(CartItem orderItem : ordered){
            totalPrice+=orderItem.getPlate().price*orderItem.getQty();
        }
        return totalPrice;
    }

    /**
     * Prints on console all the plates and its quantity stored in the cart.
     */
    private void logPrint() {
        for(CartItem cartItem : cart){
            System.out.println("Plate: "+cartItem.getPlate().name+", Qty: "+cartItem.getQty());
        }
    }

    /**
     * Deletes the current cart for a new empty one.
     */
    public void emptyCart(){
        cart = new ArrayList<>();
    }

    /**
     * Makes an order passing all the plates stored into the current Cart to the Ordered array.
     */
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
