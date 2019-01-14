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
import android.widget.TextView;
import android.widget.Toast;

import com.example.victorjuez.mywaiter.Controller.RestaurantController;
import com.example.victorjuez.mywaiter.Controller.PlateController;
import com.example.victorjuez.mywaiter.Controller.ShoppingCartController;
import com.example.victorjuez.mywaiter.Model.CartItem;
import com.example.victorjuez.mywaiter.Model.Order;
import com.example.victorjuez.mywaiter.Model.Plate;
import com.example.victorjuez.mywaiter.Model.Session;
import com.example.victorjuez.mywaiter.R;
import com.example.victorjuez.mywaiter.View.Carta.RecyclerTouchListener;
import com.example.victorjuez.mywaiter.View.PlateActivity;
import com.example.victorjuez.mywaiter.View.Restaurant.RestaurantActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {
    //Controllers
    private ShoppingCartController shoppingCartController;
    private PlateController plateController;
    private RestaurantController restaurantController;
    private Session session;

    //Views
    private Button emptyCartButton, orderButton;
    private RecyclerView recyclerView;
    private CheckoutAdapter checkoutAdapter;
    private TextView totalPrice;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //Views
        emptyCartButton = findViewById(R.id.empty_cart_button);
        orderButton = findViewById(R.id.order_button);
        recyclerView = findViewById(R.id.recyclerCheckout);
        totalPrice = findViewById(R.id.totalPrice);
        toolbar = (Toolbar) findViewById(R.id.toolbar_checkout);

        //Controllers
        shoppingCartController = ShoppingCartController.getInstance();
        plateController = PlateController.getInstance();
        restaurantController = RestaurantController.getInstance();
        session = Session.getInstance();

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        totalPrice.setText(String.valueOf(shoppingCartController.getTotalPriceCart())+"€");

        //recyclerView configuration
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkoutAdapter = new CheckoutAdapter(shoppingCartController.getCart());
        recyclerView.setAdapter(checkoutAdapter);
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

        emptyCartButton.setOnClickListener(this);
        orderButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.empty_cart_button:
                shoppingCartController.emptyCart();
                checkoutAdapter = new CheckoutAdapter(shoppingCartController.getCart());
                recyclerView.setAdapter(checkoutAdapter);
                totalPrice = findViewById(R.id.totalPrice);
                totalPrice.setText(String.valueOf(shoppingCartController.getTotalPriceCart())+"€");

                finish();
                break;

            case R.id.order_button:
                ArrayList<CartItem> cart = shoppingCartController.getCart();
                if (cart.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nothing to order!", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Integer> plates = new HashMap<>();
                    for (CartItem cartItem : cart) {
                        plates.put(String.valueOf(cartItem.getPlate().id), cartItem.getQty());
                    }
                    DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Restaurants/"+restaurantController.getRestaurant().id+"/orders");
                    String id = ordersRef.push().getKey();
                    Order order = new Order(id, session.getCurrentUser().id, restaurantController.getRestaurant().id, session.getTable(),plates);
                    ordersRef.child(id).setValue(order, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                System.out.println("Data could not be saved. " + databaseError.getMessage());
                            } else {
                                Toast.makeText(getApplicationContext(), "Order Confirmed", Toast.LENGTH_SHORT).show();
                                shoppingCartController.makeOrder();
                                shoppingCartController.emptyCart();
                                Intent intent = new Intent(CheckoutActivity.this, RestaurantActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
                break;
        }

    }
}
