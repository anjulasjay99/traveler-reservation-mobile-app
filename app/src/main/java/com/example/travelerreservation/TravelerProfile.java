package com.example.travelerreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.travelerreservation.managers.DatabaseManager;
import com.example.travelerreservation.managers.LogInManager;
import com.example.travelerreservation.models.UserEntity;

public class TravelerProfile extends AppCompatActivity {

    TextView nic;
    TextView firstName;
    TextView lastName;
    TextView dateOfBirth;
    TextView phoneNo;
    TextView email;

    String password;

    TextView updateProfileLink;

    TextView deactivateProfileLink;

    TextView logoutLink;
    DatabaseManager databaseManager;
    LogInManager logInManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveler_profile);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        databaseManager = DatabaseManager.getInstance();
        logInManager = LogInManager.getInstance();

        this.nic = findViewById(R.id.nicValueText);
        this.firstName = findViewById(R.id.firstNameValueText);
        this.lastName = findViewById(R.id.lastNameValueText);
        this.dateOfBirth = findViewById(R.id.dobValueText);
        this.phoneNo = findViewById(R.id.phoneNoValueText);
        this.email = findViewById(R.id.emailValueText);
        this.updateProfileLink = findViewById(R.id.updateProfLink);
        this.deactivateProfileLink = findViewById(R.id.deactivateLink);
        this.logoutLink = findViewById(R.id.logoutLink);

        this.updateProfileLink.setOnClickListener(view -> onClickUpdateLink());
        this.deactivateProfileLink.setOnClickListener(view -> onClickDeactivate());
        this.logoutLink.setOnClickListener(view -> onClickLogout());

        new Thread(() -> {
            UserEntity userEntity = databaseManager.db().userDao().getAll().get(0);

            this.nic.setText(userEntity.nic.toString());
            this.firstName.setText(userEntity.firstName.toString());
            this.lastName.setText(userEntity.lastName.toString());
            this.dateOfBirth.setText(userEntity.dateOfBirth.toString());
            this.phoneNo.setText(String.valueOf(userEntity.phoneNo));
            this.email.setText(userEntity.email.toString());
            this.password = userEntity.password.toString();


        }).start();



    }

    private void onClickUpdateLink() {
        Intent intent = new Intent(this, UpdateProfile.class);
        startActivity(intent);
    }

    private void onClickDeactivate() {
        Intent intent = new Intent(this, DeactivateProfile.class);
        intent.putExtra("nic", this.nic.getText().toString());
        intent.putExtra("password", this.password);
        startActivity(intent);
    }

    private void onClickLogout() {
        this.logInManager.logout();
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}