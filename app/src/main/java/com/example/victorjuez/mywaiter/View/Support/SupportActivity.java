package com.example.victorjuez.mywaiter.View.Support;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Controller.ActiveRestaurant;
import com.example.victorjuez.mywaiter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class SupportActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    private ImageView restaurantProfileImage;
    private FirebaseStorage storage;

    private ActiveRestaurant activeRestaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        mTextMessage = (TextView) findViewById(R.id.message);
        restaurantProfileImage = findViewById(R.id.restaurantProfileImage);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_dashboard);

        loadFragment(new FragmentDashboard());


        //load restaurant image
        activeRestaurant = ActiveRestaurant.getInstance();
        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference restaurantImageReference = storageReference.child("restaurants/"+String.valueOf(activeRestaurant.getRestaurant().id)+"/profilePhoto.png");

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

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()){

            case R.id.navigation_order:
                fragment = new FragmentOrder();
                break;

            case R.id.navigation_dashboard:
                fragment = new FragmentDashboard();
                break;

            case R.id.navigation_waiter:
                fragment = new FragmentWaiter();
                break;
        }

        return loadFragment(fragment);
    }
}
