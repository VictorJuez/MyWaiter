package com.example.victorjuez.mywaiter.View;

import android.app.ActionBar;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Controller.ActiveRestaurant;
import com.example.victorjuez.mywaiter.Model.Restaurant;
import com.example.victorjuez.mywaiter.R;
import com.example.victorjuez.mywaiter.View.Carta.CartaActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class RestaurantActivity extends AppCompatActivity {

    private Button verCartaButton;
    private ImageView restaurantProfileImage;
    private int restaurantId;
    private TextView restaurantEmail;
    private TextView restaurantTelephone;
    private TextView restaurantAddress;
    private TextView restaurantDescription;
    private LinearLayout tagsLayout;

    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        verCartaButton = findViewById(R.id.verCartaButton);

        verCartaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, CartaActivity.class);
                startActivity(intent);
            }
        });

        restaurantProfileImage = findViewById(R.id.restaurantProfileImage);
        restaurantEmail = findViewById(R.id.restaurantEmail);
        restaurantTelephone = findViewById(R.id.restaurantTelephone);
        restaurantAddress = findViewById(R.id.restaurantAddress);
        restaurantDescription = findViewById(R.id.restaurantDescription);
        tagsLayout = findViewById(R.id.tagsLayout);

        Restaurant restaurant = ActiveRestaurant.getInstance().getRestaurant();
        restaurantId = restaurant.id;

        restaurantTelephone.setText(restaurant.telephone);
        restaurantEmail.setText(restaurant.email);
        restaurantAddress.setText(restaurant.address);
        restaurantDescription.setText(restaurant.description);

        System.out.println(restaurant.tags);

        /*for(String tag : restaurant.tags){
            TextView textView = new TextView(this);
            textView.setText(tag);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    ));
        }*/

        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference restaurantImageReference = storageReference.child("restaurants/"+String.valueOf(restaurantId)+"/profilePhoto.png");

        restaurantImageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .fit()
                        .centerCrop()
                        .into(restaurantProfileImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("failed to load image");
            }
        });

    }
}
