package com.example.victorjuez.mywaiter.View.Carta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.victorjuez.mywaiter.Controller.RestaurantController;
import com.example.victorjuez.mywaiter.Controller.PlateController;
import com.example.victorjuez.mywaiter.Model.Plate;
import com.example.victorjuez.mywaiter.R;
import com.example.victorjuez.mywaiter.View.PlateActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartaFragment extends Fragment {
    //Controllers
    private PlateController plateController;

    //Views
    private RecyclerView recyclerView;

    //Variables
    private List<Plate> plateList = new ArrayList<>();
    private PlateAdapter pAdapter;
    private ArrayList<Integer> platesId = new ArrayList<>();
    private int page;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_carta, container, false);

        //Configuring recyclerview
        recyclerView = rootView.findViewById(R.id.recycler_view);
        pAdapter = new PlateAdapter(plateList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(rootView.getContext(), LinearLayoutManager.VERTICAL,16));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(rootView.getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Plate plate = plateList.get(position);
                plateController.setSelectedPlate(plate);
                Intent intent = new Intent(getActivity(), PlateActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //Loading plates given the tab
        plateController = PlateController.getInstance();
        preparePlateData();

        return rootView;
    }

    private void preparePlateData() {
        //TODO: make more efficient communication with DB, we are loading all plates for each fragment and discarding the ones we don't want to show.
        DatabaseReference platesReference = FirebaseDatabase.getInstance().getReference("Plates");
        Query query = platesReference
                .orderByChild("restaurant")
                .equalTo(RestaurantController.getInstance().getRestaurant().id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Plate plate = snapshot.getValue(Plate.class);
                        if(!platesId.contains(plate.id) && plate.set == page){
                            plateList.add(plate);
                            platesId.add(plate.id);
                        }
                    }
                    pAdapter.notifyDataSetChanged();
                }
                else {
                    System.out.println("This plate doesn't exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setPage(int page) {
        this.page = page;
    }

}
