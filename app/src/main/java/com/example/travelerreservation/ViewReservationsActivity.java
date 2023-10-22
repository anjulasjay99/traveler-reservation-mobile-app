package com.example.travelerreservation;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelerreservation.managers.ReservationManager;
import com.example.travelerreservation.models.ReservationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ViewReservationsActivity extends AppCompatActivity {

    private ListView reservationsListView;
    private List<ReservationResponse> reservationList;
    private ReservationAdapter reservationAdapter;
    private ReservationManager reservationManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservations);

        reservationManager = ReservationManager.getInstance();

        reservationsListView = findViewById(R.id.reservationsListView);
        reservationList = new ArrayList<ReservationResponse>(); // Replace with your data source

        reservationManager.viewReservations(
                fetchedReservations -> {
                    // Handle the retrieved reservations and update your UI
                    reservationList.clear(); // Clear existing data
                    reservationList.addAll(fetchedReservations); // Add the new data
                    reservationAdapter.notifyDataSetChanged(); // Notify the adapter
                },
                errorMessage -> {
                    // Handle the error, e.g., display a message to the user
                    Log.e("ReservationManager", "Error: " + errorMessage);
                }
        );

        // Create a custom adapter to display reservations
        reservationAdapter = new ReservationAdapter();
        reservationsListView.setAdapter(reservationAdapter);

        // Create a new Reservation object and add it to the reservationList
//        ReservationResponse reservation1 = new Reservation("John Doe", "Ruhunu Kumari", "2023-10-15" ,  "10:00 AM");
//        reservationList.add(reservation1);
//
//        ReservationResponse reservation2 = new Reservation("Alice Smith", "Express B", "2023-10-16" , "11:30 AM");
//        reservationList.add(reservation2);
//
//        ReservationResponse reservation3 = new Reservation("Bob Johnson", "Galu Kumari", "2023-10-17" ,"03:45 PM");
//        reservationList.add(reservation3);

        // Make sure to notify the adapter that the data has changed
        reservationAdapter.notifyDataSetChanged();

    }

    // Custom adapter to populate the ListView with reservations
    private class ReservationAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return reservationList.size();
        }

        @Override
        public Object getItem(int position) {
            return reservationList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(ViewReservationsActivity.this).inflate(R.layout.activity_reservation_item, parent, false);
            }

          //  TextView customerNameTextView = convertView.findViewById(R.id.customerNameTextView);
            TextView trainNameTextView = convertView.findViewById(R.id.trainNameTextView);
            TextView dateAndTimeTextView = convertView.findViewById(R.id.dateAndTimeTextView);
            Button editReservationButton = convertView.findViewById(R.id.editReservationButton);
            Button removeReservationButton = convertView.findViewById(R.id.removeReservationButton);

            final ReservationResponse reservation = reservationList.get(position);

       //     customerNameTextView.setText(reservation.getCustomerName());
            trainNameTextView.setText(reservation.getTrainName());
    //        dateAndTimeTextView.setText(reservation.getDate() + reservation.getTime());

            editReservationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Pass reservation data to the summary activity
                    Intent intent = new Intent(ViewReservationsActivity.this, ReservationEdit.class);
                    intent.putExtra("id", reservation.getId());
                    intent.putExtra("customerName", reservation.getCustomerName());
                    intent.putExtra("trainName", reservation.getTrainName());
                    intent.putExtra("date", reservation.getDateOfBooking());
                    intent.putExtra("time", reservation.getTimeOfBooking());
                    intent.putExtra("action", "Edit");
                    startActivityForResult(intent, 201);
                }
            });

            removeReservationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create a confirmation dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewReservationsActivity.this);
                    builder.setMessage("Are you sure you want to remove this reservation?")
                            .setTitle("Confirm Removal");

                    // Add "Yes" button to remove the reservation
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Remove the reservation from the list
                            reservationList.remove(position);
                            reservationAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });

                    // Add "No" button to cancel the removal
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    // Create and show the dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });



            return convertView;
        }
    }

    private class Reservation {
        private String customerName;
        private String trainName;
        private String date;

        private String time;

        public Reservation(String customerName, String trainName, String date, String time) {
            this.customerName = customerName;
            this.trainName = trainName;
            this.date = date;
            this.time = time;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getTrainName() {
            return trainName;
        }

        public String getDate() {
            return date;
        }
        public String getTime() {
            return time;
        }
    }

    private void handleReservationFetchSuccess(){
        progressDialog.dismiss();
        Toast.makeText(this, "All Reservations fetched!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }

    //Called if sign up failed
    private void handleReservationFetchFailed(String error){
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
