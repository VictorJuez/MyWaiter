package com.example.victorjuez.mywaiter.View.Carta;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Model.Plato;
import com.example.victorjuez.mywaiter.R;

import java.util.List;

public class PlatosAdapter extends RecyclerView.Adapter<PlatosAdapter.MyViewHolder> {

    private List<Plato> platosList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre, precio, descripcion;

        public MyViewHolder(View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.nombre);
            descripcion = (TextView) view.findViewById(R.id.descripcion);
            precio = (TextView) view.findViewById(R.id.precio);
        }
    }


    public PlatosAdapter(List<Plato> platosList) {
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
        Plato plato = platosList.get(position);
        holder.nombre.setText(plato.getNombre());
        holder.descripcion.setText(plato.getDescripcion());
        holder.precio.setText(plato.getPrecio());
    }

    @Override
    public int getItemCount() {
        return platosList.size();
    }
}
