package com.example.victorjuez.mywaiter.View.Restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Controller.ShoppingCartController;
import com.example.victorjuez.mywaiter.R;
import com.example.victorjuez.mywaiter.View.Carta.CartaActivity;
import com.example.victorjuez.mywaiter.View.Checkout.CheckoutAdapter;

public class FragmentOrder extends Fragment {

    private RecyclerView recyclerView;
    private CheckoutAdapter checkoutAdapter;
    private ShoppingCartController shoppingCartController;
    private TextView totalPrice;
    private LinearLayout orderLayout,orderEmpty;
    private Button orderButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);

        recyclerView = view.findViewById(R.id.recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderEmpty = view.findViewById(R.id.orderEmpty);
        orderLayout = view.findViewById(R.id.orderLayout);
        orderButton = view.findViewById(R.id.order_button);
        shoppingCartController = ShoppingCartController.getInstance();

        if(shoppingCartController.getOrdered().isEmpty()){
            orderEmpty.setVisibility(View.VISIBLE);
            orderLayout.setVisibility(View.GONE);
        }

        checkoutAdapter = new CheckoutAdapter(shoppingCartController.getOrdered());
        recyclerView.setAdapter(checkoutAdapter);
        totalPrice = view.findViewById(R.id.totalPrice);
        totalPrice.setText(String.valueOf(shoppingCartController.getTotalPriceOrdered())+"€");

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartaActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
