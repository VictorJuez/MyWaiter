package com.example.victorjuez.mywaiter.View.Checkout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.R;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyViewHolder> {

    ArrayList<String> listPlates;

    public CheckoutAdapter(ArrayList<String> listPlates) {
        this.listPlates = listPlates;
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
        myViewHolder.plateTextView.setText(listPlates.get(i));
    }

    @Override
    public int getItemCount() {
        return listPlates.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView plateTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            plateTextView = itemView.findViewById(R.id.mytextview);
        }
    }
}
