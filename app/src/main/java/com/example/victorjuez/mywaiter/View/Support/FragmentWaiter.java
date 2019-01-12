package com.example.victorjuez.mywaiter.View.Support;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.victorjuez.mywaiter.Controller.ActiveRestaurant;
import com.example.victorjuez.mywaiter.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FragmentWaiter extends Fragment {

    private RatingBar ratingBar;
    private ActiveRestaurant activeRestaurant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiter, null);

        ratingBar = view.findViewById(R.id.ratingBar);
        activeRestaurant = ActiveRestaurant.getInstance();
        final DatabaseReference restaurantRef = FirebaseDatabase.getInstance().getReference("Restaurants/"+activeRestaurant.getRestaurant().id+"/votes");

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getActivity(), "Rating is: "+rating, Toast.LENGTH_SHORT).show();
                //restaurantRef.child("1").setValue(rating); //child num would be user id
                restaurantRef.push().setValue(rating);
                activeRestaurant.getRestaurant().addVote((int) rating);

            }
        });

        return view;
    }
}
