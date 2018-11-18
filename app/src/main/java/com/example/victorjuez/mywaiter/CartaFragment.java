package com.example.victorjuez.mywaiter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CartaFragment extends Fragment {

    private List<Plato> platoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PlatosAdapter pAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carta, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        pAdapter = new PlatosAdapter(platoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(rootView.getContext(), LinearLayoutManager.VERTICAL,16));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(rootView.getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Plato plato = platoList.get(position);
                Toast.makeText(getContext(),plato.getNombre() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        preparePlatoData();



        return rootView;
    }

    private void preparePlatoData() {
        Plato plato = new Plato("plato1", "descripcion1", "1€");
        platoList.add(plato);

        plato = new Plato("plato2", "descripcion2", "2€");
        platoList.add(plato);

        plato = new Plato("plato3", "descripcion3", "3€");
        platoList.add(plato);

        plato = new Plato("plato4", "descripcion4", "4€");
        platoList.add(plato);


        pAdapter.notifyDataSetChanged();
    }


}
