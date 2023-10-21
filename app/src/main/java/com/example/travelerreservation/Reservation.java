package com.example.travelerreservation;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.travelerreservation.models.ReservationApiService;
import com.example.travelerreservation.models.ReservationRequest;
import com.example.travelerreservation.models.ReservationResponse;
import com.example.travelerreservation.managers.ReservationManager;


import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reservation extends AppCompatActivity  {

    private EditText customerNameEditText;
    private Spinner trainNameSpinner;
    private EditText dateEditText;
    private EditText timeEditText;
    private Button createReservationButton;
    private ReservationManager reservationManager;
    private Button dateButton;
    private Button timeButton;
    ProgressDialog progressDialog;
    String customerName;
    String selectedTrainName;
    String date;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        getSupportActionBar().setTitle("Make Reservation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customerNameEditText = findViewById(R.id.customerNameEditText);
        trainNameSpinner = findViewById(R.id.trainNameSpinner);

        createReservationButton = findViewById(R.id.createReservationButton);

        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });


        // Populate the train name dropdown (Spinner) with data
        ArrayAdapter<String> trainNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getTrainNames());
        trainNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trainNameSpinner.setAdapter(trainNameAdapter);

        createReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the form
                customerName = customerNameEditText.getText().toString();
                selectedTrainName = trainNameSpinner.getSelectedItem().toString();
                date = dateEditText.getText().toString();
                time = timeEditText.getText().toString();

                reservationManager.addReservation(customerName,selectedTrainName,date,time,() -> handleReservationSuccessful(),
                        error -> handleReservationFailed(error));

            }
        });
    }

    private String[] getTrainNames() {
        return new String[]{"Ruhunu Kumari", "Galu Kumari", "Kandy Express"};
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

    private void showDatePicker() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Handle the selected date
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                dateButton.setText(selectedDate);
                date = selectedDate;
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePicker() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Handle the selected time
                String selectedTime = hourOfDay + ":" + minute;
                timeButton.setText(selectedTime);
                time = selectedTime;
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }


}