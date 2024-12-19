package com.example.myapplication22;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();

        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, Signup.class);
            startActivity(intent);
            finish();
        }, 4000);
    }
}