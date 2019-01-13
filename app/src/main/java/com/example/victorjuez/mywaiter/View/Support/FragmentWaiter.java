package com.example.victorjuez.mywaiter.View.Support;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.victorjuez.mywaiter.Controller.RestaurantController;
import com.example.victorjuez.mywaiter.Model.Session;
import com.example.victorjuez.mywaiter.Model.WaiterCall;
import com.example.victorjuez.mywaiter.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentWaiter extends Fragment {

    private RatingBar ratingBar;
    private RestaurantController restaurantController;
    private Session session;

    private LinearLayout linearLayoutWaiter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiter, null);

        ratingBar = view.findViewById(R.id.ratingBar);
        restaurantController = RestaurantController.getInstance();
        session = Session.getInstance();
        final DatabaseReference restaurantRef = FirebaseDatabase.getInstance().getReference("Restaurants/"+ restaurantController.getRestaurant().id+"/votes");
        linearLayoutWaiter = view.findViewById(R.id.linearLayoutWaiter);

        linearLayoutWaiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference waiterRef = FirebaseDatabase.getInstance().getReference("Restaurants/"+ restaurantController.getRestaurant().id+"/waiterCall");
                Long tsLong = System.currentTimeMillis()/1000;
                String timeStamp = tsLong.toString();
                waiterRef.push().setValue(new WaiterCall(session.getCurrentUser().id,session.getTable(), timeStamp));
                Toast.makeText(getActivity(), "A waiter has been called!", Toast.LENGTH_SHORT).show();
            }
        });

        ratingBar.setRating(session.getRestaurantRate());
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getActivity(), "Rating is: "+rating, Toast.LENGTH_SHORT).show();
                restaurantRef.child(session.getCurrentUser().id).setValue(rating); //child num would be user id
                //restaurantRef.push().setValue(rating); //random generated key
                restaurantController.getRestaurant().addVote((int) rating);
                session.setRestaurantRate((int)rating);
                ((RestaurantActivity)getActivity()).reloadRating();
            }
        });

        return view;
    }
}
