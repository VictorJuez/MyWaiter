package com.example.victorjuez.mywaiter.View.Checkout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Controller.ShoppingCartController;
import com.example.victorjuez.mywaiter.Model.Plate;
import com.example.victorjuez.mywaiter.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CheckoutActivity extends AppCompatActivity {
    //TODO: [Layout] make empty cart button floating

    Button emptyCartButton;
    TextView checkoutCartText;
    ShoppingCartController shoppingCartController;

    ArrayList<String> plateNames;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //checkoutCartText = findViewById(R.id.checkout_cart_text);
        emptyCartButton = findViewById(R.id.empty_cart_button);

        shoppingCartController = ShoppingCartController.getInstance();

        emptyCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCartController.empty();
                checkoutCartText.setText("");
            }
        });

        recyclerView = findViewById(R.id.recyclerCheckout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        plateNames = new ArrayList<>();
        plateNames.add("plato1");
        plateNames.add("plato2");
        plateNames.add("plate3");
        plateNames.add("plate4");

        CheckoutAdapter checkoutAdapter = new CheckoutAdapter(plateNames);
        recyclerView.setAdapter(checkoutAdapter);

    }

    private void printCart() {
        HashMap<Plate, Integer> cart = shoppingCartController.getCart();
        String text = "";
        Iterator it = cart.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            Plate plate = (Plate) pair.getKey();

            //System.out.println(plate.name + " = " + pair.getValue());
            text+=pair.getValue()+"x "+plate.name+"\t\t\t\t"+plate.price+"\n";
            checkoutCartText.setText(text);

            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
