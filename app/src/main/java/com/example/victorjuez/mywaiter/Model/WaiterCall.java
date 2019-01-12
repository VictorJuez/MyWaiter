package com.example.victorjuez.mywaiter.Model;

public class WaiterCall {
    public String userId;
    public int table;
    public String time;

    public WaiterCall() {
    }

    public WaiterCall(String userId, int table, String time) {
        this.userId = userId;
        this.table = table;
        this.time = time;
    }
}
