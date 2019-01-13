package com.example.victorjuez.mywaiter.Model;

public class CartItem {
    private Plate plate;
    private int qty;

    public CartItem(Plate plate, int qty) {
        this.plate = plate;
        this.qty = qty;
    }

    public Plate getPlate() {
        return plate;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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
