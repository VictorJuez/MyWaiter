package com.example.victorjuez.mywaiter.View;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.victorjuez.mywaiter.Controller.ActiveRestaurant;
import com.example.victorjuez.mywaiter.Controller.PlateController;
import com.example.victorjuez.mywaiter.Controller.ShoppingCartController;
import com.example.victorjuez.mywaiter.Model.CartItem;
import com.example.victorjuez.mywaiter.Model.Plate;
import com.example.victorjuez.mywaiter.Model.Restaurant;
import com.example.victorjuez.mywaiter.R;
import com.example.victorjuez.mywaiter.View.Checkout.CheckoutActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PlateActivity extends AppCompatActivity implements View.OnClickListener {
    //Controllers
    private PlateController plateController;
    private Restaurant selectedRestaurant;
    private ActiveRestaurant activeRestaurant;
    private ShoppingCartController shoppingCartController;

    //Views
    private TextView plateNameView;
    private TextView descriptionPlateView;
    private TextView platePriceView;
    private ImageView plateDetailedView;
    private FloatingActionButton addButton, removeButton;
    private Button addToCartButton, removeFromCartButton;
    private TextView platesNum;

    //Variables
    private Plate selectedPlate;
    private boolean updateState;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate);

        //Views
        plateNameView = findViewById(R.id.plate_name);
        descriptionPlateView = findViewById(R.id.description_plate_text);
        platePriceView = findViewById(R.id.plate_price);
        plateDetailedView = findViewById(R.id.plate_detailed);
        platesNum = findViewById(R.id.tv_platesNum);
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(this);
        removeButton = findViewById(R.id.remove_button);
        removeButton.setOnClickListener(this);
        addToCartButton = findViewById(R.id.addToCart_button);
        addToCartButton.setOnClickListener(this);
        removeFromCartButton = findViewById(R.id.remove_cart_button);
        removeFromCartButton.setOnClickListener(this);

        //Controllers
        activeRestaurant = ActiveRestaurant.getInstance();
        shoppingCartController = ShoppingCartController.getInstance();
        plateController = PlateController.getInstance();
        storage = FirebaseStorage.getInstance();

        //Main
        selectedPlate = plateController.getSelectedPlate();
        selectedRestaurant = activeRestaurant.getRestaurant();

        plateNameView.setText(selectedPlate.name);
        descriptionPlateView.setText(selectedPlate.description);
        platePriceView.setText(String.valueOf(selectedPlate.price)+"€");

        updateState = false;
        loadPlateImage();

        //Check and act if is new plate to be added to cart or an Update situation
        if(shoppingCartController.getPlatesID().contains(plateController.getSelectedPlate().id)) updateState = true;
        if(updateState){
            platesNum.setText(String.valueOf(shoppingCartController.getCart().get(shoppingCartController.getCart().indexOf(new CartItem(plateController.getSelectedPlate(),1))).getQty()));
            removeFromCartButton.setVisibility(View.VISIBLE);
            addToCartButton.setText("UPDATE CART");
        }
    }

    private void loadPlateImage() {
        //load image from Firebase Storage
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_button:
                int n = Integer.valueOf((String)platesNum.getText());
                platesNum.setText(String.valueOf(n+1));
                break;
            case R.id.remove_button:
                n = Integer.valueOf((String)platesNum.getText());
                if(n>0)platesNum.setText(String.valueOf(n-1));
                break;
            case R.id.addToCart_button:
                int numPlates = Integer.valueOf((String)platesNum.getText());
                if(numPlates > 0) {
                    shoppingCartController.addToCart(selectedPlate, numPlates);
                    if(updateState) {
                        Intent intent = new Intent(PlateActivity.this, CheckoutActivity.class);
                        startActivity(intent);
                    }
                    Toast.makeText(getApplicationContext(), "Plate: "+selectedPlate.name+" added to cart", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else Toast.makeText(PlateActivity.this, "Can't be ordered 0 plates", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_cart_button:
                shoppingCartController.removePlate(plateController.getSelectedPlate());
                finish();
                break;

        }
    }
}
