package com.example.victorjuez.mywaiter.View;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.victorjuez.mywaiter.Controller.ShoppingCartController;
import com.example.victorjuez.mywaiter.R;
import com.example.victorjuez.mywaiter.View.Carta.CartaActivity;

public class OrderConfirmed extends AppCompatActivity {

    Button menuButton, exitButton;
    ShoppingCartController shoppingCartController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);

        menuButton = findViewById(R.id.menu_button);
        exitButton = findViewById(R.id.exit_button);
        shoppingCartController = ShoppingCartController.getInstance();

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCartController.empty();
                Intent intent = new Intent(OrderConfirmed.this, CartaActivity.class);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                finish();
            }
        });
    }
}
