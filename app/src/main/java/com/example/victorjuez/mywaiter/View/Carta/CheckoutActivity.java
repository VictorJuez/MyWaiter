package com.example.victorjuez.mywaiter.View.Carta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Controller.ShoppingCartController;
import com.example.victorjuez.mywaiter.Model.Plate;
import com.example.victorjuez.mywaiter.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        TextView checkoutCartText = findViewById(R.id.checkout_cart_text);
        ShoppingCartController shoppingCartController = ShoppingCartController.getInstance();
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
