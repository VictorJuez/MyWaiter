package com.example.victorjuez.mywaiter.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.victorjuez.mywaiter.Controller.ActiveRestaurant;
import com.example.victorjuez.mywaiter.Model.Restaurant;
import com.example.victorjuez.mywaiter.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.*;

import java.io.IOException;
import java.util.ArrayList;

public class ScanActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button scanButton;

    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

    DatabaseReference dbRestaurants;

    boolean found = false;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //TODO: implement the bottom buttons functionalities
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    mTextMessage.setText(R.string.title_profile);
                    return true;
                case R.id.navigation_scan:
                    mTextMessage.setText(R.string.title_scan);
                    return true;
                case R.id.navigation_settings:
                    mTextMessage.setText(R.string.title_settings);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        mTextMessage = (TextView) findViewById(R.id.message);

        scanButton = findViewById(R.id.scanButton);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //in case to wrong reading qr code, to try again
                //found = false;

                //switch to restaurant information screen

                Query query = FirebaseDatabase.getInstance().getReference("Restaurants")
                        .orderByChild("id")
                        .equalTo(2);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            //TODO: only one restaurant received, refactor the for clause
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Restaurant restaurant = snapshot.getValue(Restaurant.class);

                                ActiveRestaurant activeRestaurant = ActiveRestaurant.getInstance();
                                activeRestaurant.setRestaurant(restaurant);

                                Intent intent = new Intent(ScanActivity.this, RestaurantActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else {
                            System.out.println("This restaurant doesn't exists");
                            txtResult.setText("This restaurant doesn't exists");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        txtResult = findViewById(R.id.txtResult);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permissions
                    ActivityCompat.requestPermissions(ScanActivity.this,
                            new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0){
                    if(!found){
                        found = true;
                        txtResult.post(new Runnable(){

                            @Override
                            public void run() {
                                //create vibrate
                                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(1000);

                                String result = qrcodes.valueAt(0).displayValue;
                                //txtResult.setText(qrcodes.valueAt(0).displayValue);
                                treatJson(result);
                            }
                        });
                    }
                }
            }
        });
    }

    private void treatJson(String result){
        JSONObject obj = null;
        try {
            obj = new JSONObject(result);
            if(obj.getJSONObject("mywaiter") != null){
                final int restaurantId = obj.getJSONObject("mywaiter").getInt("id");
                final String table = obj.getJSONObject("mywaiter").getString("table");
                txtResult.setText("Restaurant="+restaurantId+"\n"+"Table="+table);
                Query query = FirebaseDatabase.getInstance().getReference("Restaurants")
                        .orderByChild("id")
                        .equalTo(restaurantId);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            //TODO: only one restaurant received, refactor the for clause
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Restaurant restaurant = snapshot.getValue(Restaurant.class);
                                txtResult.setText("Restaurant id="+restaurantId+"\n"+"Table="+table+"\n\n"+"Restaurant name="+restaurant.name+"\nAddress="+restaurant.address+"\n"+"telephone="+restaurant.telephone);

                                ActiveRestaurant activeRestaurant = ActiveRestaurant.getInstance();
                                activeRestaurant.setRestaurant(restaurant);

                                Intent intent = new Intent(ScanActivity.this, RestaurantActivity.class);
                                startActivity(intent);
                            }
                        }
                        else {
                            System.out.println("This restaurant doesn't exists");
                            txtResult.setText("This restaurant doesn't exists");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        } catch (JSONException e) {
            txtResult.setText("Non valid QR code");
        }
    }

}
