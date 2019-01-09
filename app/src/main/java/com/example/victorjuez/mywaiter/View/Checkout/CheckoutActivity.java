package com.example.victorjuez.mywaiter.View.Checkout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Controller.ActiveRestaurant;
import com.example.victorjuez.mywaiter.Controller.ShoppingCartController;
import com.example.victorjuez.mywaiter.Model.CartItem;
import com.example.victorjuez.mywaiter.Model.Order;
import com.example.victorjuez.mywaiter.Model.Plate;
import com.example.victorjuez.mywaiter.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class CheckoutActivity extends AppCompatActivity {
    //TODO: [Layout] make empty cart button floating

    Button emptyCartButton, orderButton;
    ShoppingCartController shoppingCartController;
    ActiveRestaurant activeRestaurant;

    RecyclerView recyclerView;
    CheckoutAdapter checkoutAdapter;
    TextView totalPrice;

    DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //checkoutCartText = findViewById(R.id.checkout_cart_text);
        emptyCartButton = findViewById(R.id.empty_cart_button);
        orderButton = findViewById(R.id.order_button);

        shoppingCartController = ShoppingCartController.getInstance();
        activeRestaurant = ActiveRestaurant.getInstance();

        recyclerView = findViewById(R.id.recyclerCheckout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkoutAdapter = new CheckoutAdapter(shoppingCartController.getCart());
        recyclerView.setAdapter(checkoutAdapter);
        totalPrice = findViewById(R.id.totalPrice);
        totalPrice.setText(String.valueOf(shoppingCartController.getTotalPrice())+"€");

        emptyCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCartController.empty();
                checkoutAdapter = new CheckoutAdapter(shoppingCartController.getCart());
                recyclerView.setAdapter(checkoutAdapter);
                totalPrice = findViewById(R.id.totalPrice);
                totalPrice.setText(String.valueOf(shoppingCartController.getTotalPrice())+"€");
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ordersRef.push().getKey();
                Order order = new Order(id,"1", activeRestaurant.getRestaurant().id, shoppingCartController.getPlatesID(), shoppingCartController.getPlatesQty());
                ordersRef.child(id).setValue(order);
            }
        });
    }
}
