package com.example.victorjuez.mywaiter.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.victorjuez.mywaiter.Model.User;
import com.example.victorjuez.mywaiter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginButton, registerButton;
    private EditText emailEditText, passwdEditText;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        emailEditText = findViewById(R.id.email);
        passwdEditText = findViewById(R.id.password);

        progressDialog = new ProgressDialog(this);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == loginButton){
            loginUser();
        }
        else if (v == registerButton){
            registerUser();
        }
    }

    private void registerUser() {
        final String email = emailEditText.getText().toString().trim();
        String password = passwdEditText.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "email and password has to be entered", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(password.length()<6){
            Toast.makeText(getApplicationContext(), "Password has to be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user is successfully registered and logged in
                            String userId = usersRef.push().getKey();
                            User user = new User(email, userId);
                            usersRef.child(userId).setValue(user, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    if(databaseError!=null) Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_LONG).show();

                                    else{
                                        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwdEditText.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "email and password has to be entered", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Login user...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user is successfully registered and logged in
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                            startActivity(intent);
                        }
                        else Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(),
                                Toast.LENGTH_LONG).show();;
                    }
                });

    }
}
