package com.example.travelerreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelerreservation.managers.ReservationManager;

public class ActivityReservationSummary extends AppCompatActivity {
    private TextView customerNameSummaryTextView;
    private TextView trainNameSummaryTextView;
    private TextView dateAndTimeSummaryTextView;
    private Button confirmButton;
    private Button cancelButton;
    private ReservationManager reservationManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_summary);

        getSupportActionBar().setTitle("Reservation Summary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customerNameSummaryTextView = findViewById(R.id.customerNameSummaryTextView);
        trainNameSummaryTextView = findViewById(R.id.trainNameSummaryTextView);
        dateAndTimeSummaryTextView = findViewById(R.id.dateAndTimeSummaryTextView);
        confirmButton = findViewById(R.id.confirmButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Retrieve reservation data from the Intent
        Intent intent = getIntent();
        String customerName = intent.getStringExtra("customerName");
        String trainName = intent.getStringExtra("trainName");
        String dateAndTime = intent.getStringExtra("dateAndTime");
        String action = intent.getStringExtra("action");

        // Display reservation details in TextViews
        customerNameSummaryTextView.setText(customerName);
        trainNameSummaryTextView.setText(trainName);
        dateAndTimeSummaryTextView.setText(dateAndTime);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(action == "Edit"){

                    reservationManager.editReservation(1001, customerName,trainName,"date","time",() -> handleReservationEditSuccessful(),
                            error -> handleReservationEditFailed(error));
                } else if (action == "Delete") {
                    reservationManager.removeReservation(1001,  () -> handleReservationDeleteSuccessful(),
                            error -> handleReservationDeleteFailed(error));
                }
                else {

                }

                setResult(RESULT_OK);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel and return to the "View Reservations" activity without making changes
                setResult(RESULT_CANCELED);
                finish();
            }
        });


    }

    private void handleReservationEditSuccessful(){
        progressDialog.dismiss();
        Toast.makeText(this, "Successful!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }


    private void handleReservationEditFailed(String error){
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private void handleReservationDeleteSuccessful(){
        progressDialog.dismiss();
        Toast.makeText(this, "Successful!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }


    private void handleReservationDeleteFailed(String error){
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}

