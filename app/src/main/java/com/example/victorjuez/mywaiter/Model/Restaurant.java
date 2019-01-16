package com.example.victorjuez.mywaiter.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant {
    public String name;
    public String address;
    public String email;
    public String description;
    public String telephone;
    public int id;
    public ArrayList<String> tags;
    public ArrayList<Integer> plates;
    public HashMap<String,Integer> votes;

    public Restaurant() {
    }

    public Restaurant(String name, String address, String email, String description, String telephone, int id, ArrayList<String> tags, ArrayList<Integer> plates, HashMap<String, Integer> votes) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.description = description;
        this.telephone = telephone;
        this.id = id;
        this.tags = tags;
        this.plates = plates;
        this.votes = votes;
    }

    /**
     * Given all the votes of the restaurant calculates the average rate.
     * @return the average rate of the restaurant.
     */
    public int rating() {
        int totalRate = 0;
        if(votes == null) return 0;
        for(int rate : votes.values()){
            totalRate+=rate;
        }
        totalRate = totalRate/votes.size();
        return totalRate;
    }

    /**
     * Adds a vote to the restaurant.
     * @param value the rate willing to be added as a vote.
     */
    public void addVote(int value){
        votes.put("user",value);
    }
}
