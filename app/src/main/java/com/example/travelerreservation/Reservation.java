package com.example.travelerreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Reservation extends AppCompatActivity {

    private EditText customerNameEditText;
    private Spinner trainNameSpinner;
    private EditText dateEditText;
    private EditText timeEditText;
    private Button createReservationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


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

                // TODO: Perform reservation creation logic here, e.g., send data to a server or save it locally.
            }
        });
    }

    private String[] getTrainNames() {
        return new String[]{"Train A", "Train B", "Train C"};
    }


}