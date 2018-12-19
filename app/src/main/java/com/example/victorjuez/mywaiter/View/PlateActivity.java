package com.example.victorjuez.mywaiter.View;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Controller.ActiveRestaurant;
import com.example.victorjuez.mywaiter.Controller.PlateController;
import com.example.victorjuez.mywaiter.Model.Plate;
import com.example.victorjuez.mywaiter.Model.Restaurant;
import com.example.victorjuez.mywaiter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PlateActivity extends AppCompatActivity {

    private PlateController plateController;
    private Plate selectedPlate;

    private TextView plateNameView;
    private TextView ingredientsView;
    private TextView platePriceView;
    private ImageView plateDetailedView;

    private Restaurant selectedRestaurant;
    private ActiveRestaurant activeRestaurant;

    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate);

        activeRestaurant = ActiveRestaurant.getInstance();
        selectedRestaurant = activeRestaurant.getRestaurant();

        plateNameView = findViewById(R.id.plate_name);
        ingredientsView = findViewById(R.id.ingredients_description);
        platePriceView = findViewById(R.id.plate_price);
        plateDetailedView = findViewById(R.id.plate_detailed);

        plateController = PlateController.getInstance();
        selectedPlate = plateController.getSelectedPlate();

        plateNameView.setText(selectedPlate.name);
        ingredientsView.setText(selectedPlate.ingredients);
        platePriceView.setText(selectedPlate.price);

        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference restaurantImageReference = storageReference.child("restaurants/"+String.valueOf(selectedRestaurant.id)+"/plates/"+selectedPlate.id+"/"+"detailed.png");

        restaurantImageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .fit()
                        .centerCrop()
                        .into(plateDetailedView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("failed to load image");
            }
        });

    }
}
