package com.example.travelerreservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewReservationsActivity extends AppCompatActivity {

    private ListView reservationsListView;
    private List<Reservation> reservationList;
    private ReservationAdapter reservationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservations);

        reservationsListView = findViewById(R.id.reservationsListView);
        reservationList = new ArrayList<>(); // Replace with your data source

        // Create a custom adapter to display reservations
        reservationAdapter = new ReservationAdapter();
        reservationsListView.setAdapter(reservationAdapter);

        // Create a new Reservation object and add it to the reservationList
        Reservation reservation1 = new Reservation("John Doe", "Train A", "2023-10-15 10:00 AM");
        reservationList.add(reservation1);

        Reservation reservation2 = new Reservation("Alice Smith", "Train B", "2023-10-16 11:30 AM");
        reservationList.add(reservation2);

        Reservation reservation3 = new Reservation("Bob Johnson", "Train C", "2023-10-17 03:45 PM");
        reservationList.add(reservation3);

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

            TextView customerNameTextView = convertView.findViewById(R.id.customerNameTextView);
            TextView trainNameTextView = convertView.findViewById(R.id.trainNameTextView);
            TextView dateAndTimeTextView = convertView.findViewById(R.id.dateAndTimeTextView);
            Button editReservationButton = convertView.findViewById(R.id.editReservationButton);
            Button removeReservationButton = convertView.findViewById(R.id.removeReservationButton);

            final Reservation reservation = reservationList.get(position);

            customerNameTextView.setText(reservation.getCustomerName());
            trainNameTextView.setText(reservation.getTrainName());
            dateAndTimeTextView.setText(reservation.getDateAndTime());

            editReservationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Pass reservation data to the summary activity
                    Intent intent = new Intent(ViewReservationsActivity.this, ActivityReservationSummary.class);
                    intent.putExtra("customerName", reservation.getCustomerName());
                    intent.putExtra("trainName", reservation.getTrainName());
                    intent.putExtra("dateAndTime", reservation.getDateAndTime());
                    intent.putExtra("action", "Edit");
                    startActivityForResult(intent, 201);
                }
            });

            removeReservationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Pass reservation data to the summary activity
                    Intent intent = new Intent(ViewReservationsActivity.this, ActivityReservationSummary.class);
                    intent.putExtra("customerName", reservation.getCustomerName());
                    intent.putExtra("trainName", reservation.getTrainName());
                    intent.putExtra("dateAndTime", reservation.getDateAndTime());
                    intent.putExtra("action", "Delete");
                    startActivityForResult(intent, 201);
                }
            });


            return convertView;
        }
    }

    // You'll need a Reservation class to represent reservation data
    private class Reservation {
        private String customerName;
        private String trainName;
        private String dateAndTime;

        public Reservation(String customerName, String trainName, String dateAndTime) {
            this.customerName = customerName;
            this.trainName = trainName;
            this.dateAndTime = dateAndTime;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getTrainName() {
            return trainName;
        }

        public String getDateAndTime() {
            return dateAndTime;
        }
    }
}
