package com.example.victorjuez.mywaiter.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.victorjuez.mywaiter.Model.Session;
import com.example.victorjuez.mywaiter.Model.User;
import com.example.victorjuez.mywaiter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button loginButton, registerButton;
    private EditText emailET, email2ET, psswd2ET, psswdET;
    private ProgressDialog progressDialog;
    private Session session;

    private FirebaseAuth firebaseAuth;
    final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        emailET = findViewById(R.id.email);
        email2ET = findViewById(R.id.email2);
        psswdET = findViewById(R.id.password);
        psswd2ET = findViewById(R.id.password2);

        session = Session.getInstance();

        progressDialog = new ProgressDialog(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }


    private void registerUser() {
        final String email = emailET.getText().toString().trim();
        String email2 = email2ET.getText().toString().trim();
        String password = psswdET.getText().toString().trim();
        String password2 = psswd2ET.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email2) || TextUtils.isEmpty(password2)){
            Toast.makeText(getApplicationContext(), "All camps have to be entered", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(password.length()<6){
            Toast.makeText(getApplicationContext(), "Password has to be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(!email.equals(email2)){
            Toast.makeText(this, "Emails are not equals", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(!password.equals(password2)){
            Toast.makeText(this, "Passwords are not equals", Toast.LENGTH_SHORT).show();
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
                            final User user = new User(email, userId);
                            usersRef.child(userId).setValue(user, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    if(databaseError!=null) Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_LONG).show();

                                    else{
                                        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        session.setCurrentUser(user);
                                        Intent intent = new Intent(RegisterActivity.this, ScanActivity.class);
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
}
