package com.example.travelerreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.travelerreservation.managers.ContextManager;
import com.example.travelerreservation.managers.LogInManager;

public class MainActivity extends AppCompatActivity {
    Button travelerSignUpBtn;
    Button loginBtn;

    Button updateProfileBtn;

    Button logout;

    Button myProfile;

    LogInManager logInManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContextManager.getInstance().setApplicationContext(getApplicationContext());
        getSupportActionBar().setTitle("Home");


        this.logInManager = LogInManager.getInstance();

        if(!this.logInManager.getIsLoggedIn()) {
            this.onClickLogin();
        }

        this.travelerSignUpBtn = findViewById(R.id.travelerSignUpBtn);
        this.loginBtn = findViewById(R.id.travelerSignUpBtn2);
        this.updateProfileBtn = findViewById(R.id.travelerSignUpBtn3);
        this.logout = findViewById(R.id.logoutBtn);
        this.myProfile = findViewById(R.id.viewProfileBtn);

        this.travelerSignUpBtn.setOnClickListener(view -> onClickTravelerSignUp());
        this.loginBtn.setOnClickListener(view -> onClickLogin());
        this.updateProfileBtn.setOnClickListener(view -> onClickUpdate());
        this.logout.setOnClickListener(view -> onClickLogout());
        this.myProfile.setOnClickListener(view -> onClickMyProfile());
    }

    private void onClickTravelerSignUp() {
        Intent intent = new Intent(this, TravelerSignUp.class);
        startActivity(intent);
        //finish();
    }

    private void onClickLogin() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finish();
    }

    private void onClickUpdate() {
        Intent intent = new Intent(this, UpdateProfile.class);
        startActivity(intent);
        //finish();
    }

    private void onClickLogout() {
        this.logInManager.logout();
        onClickLogin();
    }

    private void onClickMyProfile() {
        Intent intent = new Intent(this, TravelerProfile.class);
        startActivity(intent);
    }
}