package com.example.travelerreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button travelerSignUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.travelerSignUpBtn = findViewById(R.id.travelerSignUpBtn);
        this.travelerSignUpBtn.setOnClickListener(view -> onClickTravelerSignUp());
    }

    private void onClickTravelerSignUp() {
        Intent intent = new Intent(this, TravelerSignUp.class);
        startActivity(intent);
        finish();
    }
}