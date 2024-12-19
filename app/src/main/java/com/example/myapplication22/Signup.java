package com.example.myapplication22;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText;
    private DB DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstNameEditText = findViewById(R.id.editTextFirstname);
        lastNameEditText = findViewById(R.id.editTextLastname);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);

        Button signUpButton = findViewById(R.id.signUp);
        Button loginButton = findViewById(R.id.LoginButton);
        DB = new DB(this);

        signUpButton.setOnClickListener(v -> createAccount());

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(Signup.this, Login.class);
            startActivity(intent);
            finish();
        });

        DB db=new DB(this);
        String deletemail="adealluhani@gmail.com";
        boolean deleteuser=db.deleteUser(deletemail);


    }


    private void createAccount() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (validateFields(firstName, lastName, email, password)) {
            if (DB.checkEmail(email)) {
                Toast.makeText(this, "This account already exists!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Signup.this, Verify.class);
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            }
        }
    }

    private boolean validateFields(String firstName, String lastname, String email, String password) {

        if (TextUtils.isEmpty(firstName) || !firstName.matches("[a-zA-Z]+")) {
            firstNameEditText.setError("First name must not be empty");
            return false;
        }

        if (TextUtils.isEmpty(lastname) || !lastname.matches("[a-zA-Z]+")) {
            lastNameEditText.setError("Last name cannot be empty.");
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Please enter a valid email address");
            return false;
        }

        if (TextUtils.isEmpty(password) ||
                !Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%*^&+=]).{6,20}$").matcher(password).matches()) {
            passwordEditText.setError("Password should be longer than 8 characters and contain at least,one Captial letter,one character,one number");
            return false;
        }
        return true;
    }
}