package com.example.victorjuez.mywaiter.View.Carta;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Controller.ActiveRestaurant;
import com.example.victorjuez.mywaiter.Model.Plate;
import com.example.victorjuez.mywaiter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlateAdapter extends RecyclerView.Adapter<PlateAdapter.MyViewHolder> {

    private List<Plate> platosList;
    private ActiveRestaurant activeRestaurant;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre, precio, ingredients;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.name);
            ingredients = (TextView) view.findViewById(R.id.ingredients);
            precio = (TextView) view.findViewById(R.id.price);
            image = view.findViewById(R.id.photo_plate);
        }
    }


    public PlateAdapter(List<Plate> platosList) {
        this.platosList = platosList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plato_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Plate plate = platosList.get(position);
        holder.nombre.setText(plate.name);
        holder.ingredients.setText(plate.ingredients);
        holder.precio.setText(String.valueOf(plate.price)+"€");

        activeRestaurant = ActiveRestaurant.getInstance();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference restaurantImageReference = storageReference.child("restaurants/"+String.valueOf(activeRestaurant.getRestaurant().id)+"/plates/"+plate.id+"/"+"list.png");

        restaurantImageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .fit()
                        .centerCrop()
                        .into(holder.image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("failed to load image");
            }
        });
    }

    @Override
    public int getItemCount() {
        return platosList.size();
    }
}
