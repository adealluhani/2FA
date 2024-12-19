package com.example.myapplication22;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private DB DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        DB = new DB(this);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> login());
    }
//db  kodit -5
   // validimi -2
    //error handling -5
    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password) ||
                !Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%*^&+=]).{6,20}$").matcher(password).matches()) {
            passwordEditText.setError("Password should be longer than 8 characters and contain at least,one Captial letter,one character,one number");
            return;
        }

        if (DB.validateUser(email, password)) {
            Toast.makeText(this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}