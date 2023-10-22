package com.example.travelerreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelerreservation.managers.ReservationManager;

import java.util.ArrayList;

public class ReservationEdit extends AppCompatActivity {

    private LinearLayout summaryCard;
    private Button cancelButton;

    private EditText customerNameEditText;
    private Spinner trainNameSpinner;
    private Button dateButton;
    private Button timeButton;
    private Button editReservationButton;
    private TextView customerNameSummary;
    private TextView customerDateSummary;
    private TextView customerTrainSummary;
    private TextView customerTimeSummary;

    private Button confirmButton;
    private ReservationManager reservationManager;
    private ProgressDialog progressDialog;

    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_edit);

        reservationManager = ReservationManager.getInstance();

        getSupportActionBar().setTitle("Edit Reservation");

//
        // Initialize the edit TextViews
        customerNameEditText = findViewById(R.id.customerNameEditText);
        trainNameSpinner = findViewById(R.id.trainNameSpinner);
        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        editReservationButton = findViewById(R.id.editReservationButton);

        // Initialize the summary TextViews
        customerNameSummary = findViewById(R.id.customerNameSummary);
        customerDateSummary = findViewById(R.id.customerDateSummary);
        customerTrainSummary = findViewById(R.id.customerTrainSummary);
        customerTimeSummary = findViewById(R.id.customerTimeSummary);

        // Initialize the Confirm Button
        confirmButton = findViewById(R.id.confirmButton);


        // Populate the train name dropdown (Spinner) with data
        ArrayAdapter<String> trainNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getTrainNames());
        trainNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trainNameSpinner.setAdapter(trainNameAdapter);

        // Retrieve reservation data from the Intent
        Intent intent = getIntent();
        String customerName = intent.getStringExtra("customerName");
        String trainName = intent.getStringExtra("trainName");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        id = intent.getStringExtra("id");

        int selection = getIndexOfTrainName(trainName);
        customerNameEditText.setText(customerName);
        trainNameSpinner.setSelection(selection);
        dateButton.setText(date);
        timeButton.setText(time);
//
        confirmButton.setOnClickListener(v -> {
            // Get the edited values
            String editedCustomerName = customerNameEditText.getText().toString();
            String editedTrainName = trainNameSpinner.getSelectedItem().toString();
            String editedDate = dateButton.getText().toString();
            String editedTime = timeButton.getText().toString();

            // Set the summary card visibility to visible
            summaryCard.setVisibility(View.VISIBLE);

            // Set the summary fields with the edited values
            customerNameSummary.setText("Customer Name: " + editedCustomerName);
            customerTrainSummary.setText("Train: " + editedTrainName);
            customerDateSummary.setText("Date: " + editedDate);
            customerTimeSummary.setText("Time: " + editedTime);
        });

        editReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("EDITCLICK", "INNN");
                // Retrieve the edited values from the UI elements
                String editedCustomerName = customerNameEditText.getText().toString();
                String editedTrainName = trainNameSpinner.getSelectedItem().toString();
                String editedDate = dateButton.getText().toString();
                String editedTime = timeButton.getText().toString();

                // Call the reservationManager.editReservation method
                reservationManager.editReservation(
                        id,
                        editedCustomerName,
                        editedTrainName,
                        editedDate,
                        editedTime,
                        () -> handleReservationEditSuccessful(),
                        error -> handleReservationEditFailed(error)
                );
            }
        });
//
   }
//
    private String[] getTrainNames() {
        return new String[]{"Ruhunu Kumari", "Galu Kumari", "Kandy Express"};
    }

    private int getIndexOfTrainName(String targetTrainName) {
        String[] trainNames = getTrainNames();

        for (int i = 0; i < trainNames.length; i++) {
            if (trainNames[i].equals(targetTrainName)) {
                return i;
            }
        }

        // If the targetTrainName is not found, return a default index (e.g., 0)
        return 0;
    }
    private void handleReservationEditSuccessful(){

        Toast.makeText(this, "Successful!", Toast.LENGTH_LONG).show();
        //summaryCard.setVisibility(View.GONE);
    }


    private void handleReservationEditFailed(String error){

        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

}

