package com.example.lokovoznje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    EditText mEmail, mPassword;
    Button mLoginButton;
    TextView logirajButton;
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.textinptEmail);
        mPassword = findViewById(R.id.textinptPassword);
        logirajButton = findViewById(R.id.registerBtn);
        fAuth = FirebaseAuth.getInstance();
        mLoginButton = findViewById(R.id.logirajButton);
        mProgressBar = findViewById(R.id.progressBarLogin);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Unesite email!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Unesite lozinku!");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Lozinka mora biti duža od 6 znamenaka!");
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Uspješno logiran korisnik!.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }
                        else{
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        logirajButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}