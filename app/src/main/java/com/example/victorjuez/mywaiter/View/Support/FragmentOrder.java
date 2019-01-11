package com.example.victorjuez.mywaiter.View.Support;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Controller.ShoppingCartController;
import com.example.victorjuez.mywaiter.R;
import com.example.victorjuez.mywaiter.View.Checkout.CheckoutAdapter;

public class FragmentOrder extends Fragment {

    private RecyclerView recyclerView;
    private CheckoutAdapter checkoutAdapter;
    private ShoppingCartController shoppingCartController;
    private TextView totalPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);

        recyclerView = view.findViewById(R.id.recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        shoppingCartController = ShoppingCartController.getInstance();

        checkoutAdapter = new CheckoutAdapter(shoppingCartController.getCart());
        recyclerView.setAdapter(checkoutAdapter);
        totalPrice = view.findViewById(R.id.totalPrice);
        totalPrice.setText(String.valueOf(shoppingCartController.getTotalPrice())+"€");

        return view;
    }
}
