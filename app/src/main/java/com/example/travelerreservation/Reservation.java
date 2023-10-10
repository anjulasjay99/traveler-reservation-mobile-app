package com.example.travelerreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.travelerreservation.models.ReservationApiService;
import com.example.travelerreservation.models.ReservationRequest;
import com.example.travelerreservation.models.ReservationResponse;
import com.example.travelerreservation.managers.ReservationManager;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reservation extends AppCompatActivity {

    private EditText customerNameEditText;
    private Spinner trainNameSpinner;
    private EditText dateEditText;
    private EditText timeEditText;
    private Button createReservationButton;
    private ReservationManager reservationManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        getSupportActionBar().setTitle("Make Reservation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customerNameEditText = findViewById(R.id.customerNameEditText);
        trainNameSpinner = findViewById(R.id.trainNameSpinner);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        createReservationButton = findViewById(R.id.createReservationButton);




        // Populate the train name dropdown (Spinner) with data
        ArrayAdapter<String> trainNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getTrainNames());
        trainNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trainNameSpinner.setAdapter(trainNameAdapter);

        createReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the form
                String customerName = customerNameEditText.getText().toString();
                String selectedTrainName = trainNameSpinner.getSelectedItem().toString();
                String date = dateEditText.getText().toString();
                String time = timeEditText.getText().toString();

                reservationManager.addReservation(customerName,selectedTrainName,date,time,() -> handleReservationSuccessful(),
                        error -> handleReservationFailed(error));

            }
        });
    }

    private String[] getTrainNames() {
        return new String[]{"Train A", "Train B", "Train C"};
    }

    private void handleReservationSuccessful(){
        progressDialog.dismiss();
        Toast.makeText(this, "Successful!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }


    private void handleReservationFailed(String error){
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

}