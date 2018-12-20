package com.example.victorjuez.mywaiter.Model;

public class Plate {
    //TODO: price has to be float
    public String name;
    public String description;
    public int price;
    public String ingredients;
    public int id;
    public int restaurant;
    public int set;

    public Plate(){

    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Plate)) return false;

        Plate aux = (Plate) obj;

        if(aux.id == this.id) return true;
        return false;
    }
}
