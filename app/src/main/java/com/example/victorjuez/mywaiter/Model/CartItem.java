package com.example.victorjuez.mywaiter.Model;

public class CartItem {
    private Plate plate;
    private int qty;

    public CartItem(Plate plate) {
        this.plate = plate;
        this.qty = 1;
    }

    public void incrementQty(){
        ++this.qty;
        return;
    }

    public Plate getPlate() {
        return plate;
    }

    public int getQty() {
        return qty;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof CartItem)) return false;

        CartItem aux = (CartItem) obj;

        if(aux.getPlate().id == this.getPlate().id) return true;
        return false;
    }
}
