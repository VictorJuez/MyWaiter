package com.example.victorjuez.mywaiter.View.Checkout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Model.CartItem;
import com.example.victorjuez.mywaiter.R;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyViewHolder> {

    ArrayList<CartItem> cart;

    public CheckoutAdapter(ArrayList<CartItem> cart) {
        this.cart = cart;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.checkout_item,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        CartItem cartItem = cart.get(i);

        myViewHolder.plateTextView.setText(String.valueOf(cartItem.getQty())+"x "+cartItem.getPlate().name);
        myViewHolder.priceTextView.setText(String.valueOf(cartItem.getPlate().price * cartItem.getQty()) + "€");
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView plateTextView, priceTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            plateTextView = itemView.findViewById(R.id.mytextview);
            priceTextView = itemView.findViewById(R.id.mytextview2);
        }
    }
}
