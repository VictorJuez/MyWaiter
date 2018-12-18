package com.example.victorjuez.mywaiter.Model;

import java.util.ArrayList;

public class Restaurant {
    public String name;
    public String address;
    public String email;
    public String description;
    public String telephone;
    public int id;
    public ArrayList<String> tags;
    public ArrayList<Integer> plates;

    public Restaurant() {
    }

    public Restaurant(String name, String address, String email, String description, String telephone, int id, ArrayList<String> tags, ArrayList<Integer> plates) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.description = description;
        this.telephone = telephone;
        this.id = id;
        this.tags = tags;
        this.plates = plates;
    }
}
