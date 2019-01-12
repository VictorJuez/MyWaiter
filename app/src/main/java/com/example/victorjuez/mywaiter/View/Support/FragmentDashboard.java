package com.example.victorjuez.mywaiter.View.Support;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Controller.ActiveRestaurant;
import com.example.victorjuez.mywaiter.Model.Restaurant;
import com.example.victorjuez.mywaiter.R;
import com.example.victorjuez.mywaiter.View.Carta.CartaActivity;

public class FragmentDashboard extends Fragment {

    private Button verCartaButton;
    private TextView restaurantEmail;
    private TextView restaurantTelephone;
    private TextView restaurantAddress;
    private TextView restaurantDescription;
    private TextView tag0, tag1, tag2;
    private RatingBar ratingBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, null);

        verCartaButton = view.findViewById(R.id.verCartaButton);

        verCartaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartaActivity.class);
                startActivity(intent);
            }
        });

        restaurantEmail = view.findViewById(R.id.restaurantEmail);
        restaurantTelephone = view.findViewById(R.id.restaurantTelephone);
        restaurantAddress = view.findViewById(R.id.restaurantAddress);
        restaurantDescription = view.findViewById(R.id.restaurantDescription);
        tag0 = view.findViewById(R.id.tag0);
        tag1 = view.findViewById(R.id.tag1);
        tag2 = view.findViewById(R.id.tag2);

        Restaurant restaurant = ActiveRestaurant.getInstance().getRestaurant();

        restaurantTelephone.setText(restaurant.telephone);
        restaurantEmail.setText(restaurant.email);
        restaurantAddress.setText(restaurant.address);
        restaurantDescription.setText(restaurant.description);
        tag0.setText(restaurant.tags.get(0));
        tag1.setText(restaurant.tags.get(1));
        tag2.setText(restaurant.tags.get(2));

        return view;
    }
}
