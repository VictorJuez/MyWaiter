package com.example.victorjuez.mywaiter.View.Carta;

import android.app.Activity;
import android.content.Context;
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

import com.example.victorjuez.mywaiter.Controller.ActiveRestaurant;
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

public class CartaFragment extends Fragment implements CartaActivity.onTabSelected{

    Activity activity;

    private List<Plate> plateList = new ArrayList<>();
    private PlateController plateController;
    private RecyclerView recyclerView;
    private PlateAdapter pAdapter;
    private ArrayList<Integer> platesId = new ArrayList<>();
    private int page;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            activity=(Activity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_carta, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

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
                //Toast.makeText(getContext(),plate.getName() + " is selected!", Toast.LENGTH_SHORT).show();
                plateController.setSelectedPlate(plate);
                Intent intent = new Intent(getActivity(), PlateActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        plateController = PlateController.getInstance();
        preparePlatoData();

        return rootView;
    }

    private void preparePlatoData() {
        //TODO: make more efficient communication with DB, we are loading all plates for each fragment and discarding the ones we don't want to show.
        DatabaseReference platesReference = FirebaseDatabase.getInstance().getReference("Plates");
        Query query = platesReference
                .orderByChild("restaurant")
                .equalTo(ActiveRestaurant.getInstance().getRestaurant().id);

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
                    plateController.setPlateList((ArrayList<Plate>) plateList);
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

    private void printPlates() {
        for(Plate plate : plateList){
            System.out.print(plate.name);
            System.out.println();
        }
    }

    public static CartaFragment newInstance(int someInt) {
        CartaFragment myFragment = new CartaFragment();

        Bundle args = new Bundle();
        args.putInt("page", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public void helloWorld() {
        plateList.add(plateController.getPlateList().get(0));
        pAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPlates(int plateSet) {
        
    }
}
