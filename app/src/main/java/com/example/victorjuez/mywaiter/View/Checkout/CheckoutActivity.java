package com.example.victorjuez.mywaiter.View.Checkout;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.victorjuez.mywaiter.Controller.ActiveRestaurant;
import com.example.victorjuez.mywaiter.Controller.PlateController;
import com.example.victorjuez.mywaiter.Controller.ShoppingCartController;
import com.example.victorjuez.mywaiter.Model.CartItem;
import com.example.victorjuez.mywaiter.Model.Order;
import com.example.victorjuez.mywaiter.Model.Plate;
import com.example.victorjuez.mywaiter.R;
import com.example.victorjuez.mywaiter.View.Carta.RecyclerTouchListener;
import com.example.victorjuez.mywaiter.View.MainActivity;
import com.example.victorjuez.mywaiter.View.OrderConfirmed;
import com.example.victorjuez.mywaiter.View.PlateActivity;
import com.example.victorjuez.mywaiter.View.ScanActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class CheckoutActivity extends AppCompatActivity {
    //TODO: [Layout] make empty cart button floating

    Button emptyCartButton, orderButton;
    ShoppingCartController shoppingCartController;
    PlateController plateController;
    ActiveRestaurant activeRestaurant;

    RecyclerView recyclerView;
    CheckoutAdapter checkoutAdapter;
    TextView totalPrice;

    DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_checkout);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),RestaurantActivity.class));
                finish();
            }
        });

        //checkoutCartText = findViewById(R.id.checkout_cart_text);
        emptyCartButton = findViewById(R.id.empty_cart_button);
        orderButton = findViewById(R.id.order_button);

        shoppingCartController = ShoppingCartController.getInstance();
        plateController = PlateController.getInstance();
        activeRestaurant = ActiveRestaurant.getInstance();

        recyclerView = findViewById(R.id.recyclerCheckout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkoutAdapter = new CheckoutAdapter(shoppingCartController.getCart());
        recyclerView.setAdapter(checkoutAdapter);
        totalPrice = findViewById(R.id.totalPrice);
        totalPrice.setText(String.valueOf(shoppingCartController.getTotalPrice())+"€");

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Plate plate = shoppingCartController.getCart().get(position).getPlate();
                //Toast.makeText(getContext(),plate.getName() + " is selected!", Toast.LENGTH_SHORT).show();
                plateController.setSelectedPlate(plate);
                Intent intent = new Intent(CheckoutActivity.this, PlateActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        emptyCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCartController.empty();
                checkoutAdapter = new CheckoutAdapter(shoppingCartController.getCart());
                recyclerView.setAdapter(checkoutAdapter);
                totalPrice = findViewById(R.id.totalPrice);
                totalPrice.setText(String.valueOf(shoppingCartController.getTotalPrice())+"€");

                finish();
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<CartItem> cart = shoppingCartController.getCart();
                if (cart.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nothing to order!", Toast.LENGTH_SHORT).show();
                } else {

                    Map<String, Integer> plates = new HashMap<>();
                    for (CartItem cartItem : cart) {
                        plates.put(String.valueOf(cartItem.getPlate().id), cartItem.getQty());
                    }
                    String id = ordersRef.push().getKey();
                    Order order = new Order(id, "1", activeRestaurant.getRestaurant().id, plates);
                    ordersRef.child(id).setValue(order, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                System.out.println("Data could not be saved. " + databaseError.getMessage());
                            } else {
                                Toast.makeText(getApplicationContext(), "Order Confirmed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CheckoutActivity.this, OrderConfirmed.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }
}
