package com.example.musicstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.musicstore.R;
import com.example.musicstore.forgotpwd_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {

    Button login;
    EditText email,password;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setStatusBarColor(ContextCompat.getColor(Login_Activity.this, com.google.android.material.R.color.m3_ref_palette_black));

        auth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login_btn);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
    }

    public void login(View view) {
        loginUser();
    }

    private void loginUser() {

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

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
        if(userPassword.length() < 3)
        {
            Toast.makeText(this, "Password length must be grather than 3 letter", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Intent i = new Intent(Login_Activity.this,MainActivity.class);
                            startActivity(i);
                            Toast.makeText(Login_Activity.this, "Login successful...", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Login_Activity.this, "Error"+task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void btn4(View view) {
        Intent i = new Intent(this,Registration_Activity.class);
        startActivity(i);
    }

    public void skipbtn(View view) {
        Intent i = new Intent(Login_Activity.this,MainActivity.class);
        startActivity(i);
    }

    public void btn3(View view) {
        Intent i = new Intent(this, forgotpwd_Activity.class);
        startActivity(i);
    }
}