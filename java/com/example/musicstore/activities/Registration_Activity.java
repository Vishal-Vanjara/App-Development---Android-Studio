package com.example.musicstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.musicstore.R;
import com.example.musicstore.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registration_Activity extends AppCompatActivity {

    Button signup;
    EditText name,email,password;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setStatusBarColor(ContextCompat.getColor(Registration_Activity.this, com.google.android.material.R.color.m3_ref_palette_black));

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        signup = findViewById(R.id.signup_btn);
        name = findViewById(R.id.Name);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
    }

    public void signup(View view) {
        createUser();

        Intent i = new Intent(this, Login_Activity.class);

        startActivity(i);
    }

    private void createUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(TextUtils.isEmpty(userName))
        {
            Toast.makeText(this, "Name is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userEmail))
        {
            Toast.makeText(this, "Email is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword))
        {
            Toast.makeText(this, "Password is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length() < 5)
        {
            Toast.makeText(this, "Password length must be grather than 5 letter", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            UserModel userModel = new UserModel(userName,userEmail,userPassword);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("User").child(id).setValue(userModel);
                            Toast.makeText(Registration_Activity.this, "registration Successful", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Registration_Activity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void signin_txt(View view) {
        Intent i = new Intent(this,Login_Activity.class);
        startActivity(i);
    }
}