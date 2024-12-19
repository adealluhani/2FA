package com.example.myapplication22;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.SecureRandom;

public class Verify extends AppCompatActivity {
    private EditText otpEditText;
    private String generatedOtp, firstName, lastName, email, password;
    private DB DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        otpEditText = findViewById(R.id.editTextOTP);
        Button verifyButton = findViewById(R.id.verifyButton);
        Button resendButton = findViewById(R.id.resendButton);
        DB = new DB(this);


        generatedOtp = generateOtp();
        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        sendEmail(email, generatedOtp);

        verifyButton.setOnClickListener(v -> {
            String enteredOtp = otpEditText.getText().toString().trim();
            verify(enteredOtp, generatedOtp);
        });

        resendButton.setOnClickListener(v -> resendCode(email));
    }

    public void verify(String enteredOtp, String generatedOtp) {
        if (TextUtils.isEmpty(enteredOtp)) {
            Toast.makeText(this, "Please enter the OTP.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (enteredOtp.equals(generatedOtp)) {
            if (DB.insertData(firstName, lastName, email, password)) {
                Toast.makeText(this, "OTP verified .your account has been created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Verify.this, Login.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "An error occurred .try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid OTP.Resend again the otp code", Toast.LENGTH_SHORT).show();
        }

    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(900000) + 100000;
        return String.valueOf(otp);
    }

    private void sendEmail(String email, String otp) {
        new Thread(() -> {
            if (EmailOTP.sendEmail(email, otp)) {
                runOnUiThread(() -> Toast.makeText(this, "otp sent to your email", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Failed to send OTP", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public void resendCode(String email) {
        generatedOtp = generateOtp();
        sendEmail(email, generatedOtp);

    }

}