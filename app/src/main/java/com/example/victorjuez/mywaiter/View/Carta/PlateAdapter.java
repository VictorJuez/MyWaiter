package com.example.victorjuez.mywaiter.View.Carta;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Model.Plate;
import com.example.victorjuez.mywaiter.R;

import java.util.List;

public class PlateAdapter extends RecyclerView.Adapter<PlateAdapter.MyViewHolder> {

    private List<Plate> platosList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre, precio, descripcion;

        public MyViewHolder(View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.name);
            descripcion = (TextView) view.findViewById(R.id.description);
            precio = (TextView) view.findViewById(R.id.price);
        }
    }


    public PlateAdapter(List<Plate> platosList) {
        this.platosList = platosList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plato_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Plate plate = platosList.get(position);
        holder.nombre.setText(plate.name);
        holder.descripcion.setText(plate.description);
        holder.precio.setText(plate.price);
    }

    @Override
    public int getItemCount() {
        return platosList.size();
    }
}
