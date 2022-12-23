package com.example.ecommerce.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RepoLoginActivity extends AppCompatActivity {


    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_login);

        mEmail = findViewById(R.id.etEmail);
        mPassword = findViewById(R.id.etPassword);
        mLoginButton = findViewById(R.id.buttonLogin);

        mAuth = FirebaseAuth.getInstance();


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.equals(mEmail.getText().toString(), "Admin") && Objects.equals(mPassword.getText().toString(), "Admin"))
                { startActivity(new Intent(RepoLoginActivity.this,AdminPanelActivity.class));
                }else
                    Toast.makeText(RepoLoginActivity.this, "Your not top Admin", Toast.LENGTH_SHORT).show();

            }
        });

    }


    }
