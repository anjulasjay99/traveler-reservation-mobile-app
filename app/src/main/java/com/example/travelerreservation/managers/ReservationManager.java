package com.example.travelerreservation.managers;

import android.util.Log;

import com.example.travelerreservation.models.ReservationApiService;
import com.example.travelerreservation.models.ReservationRequest;
import com.example.travelerreservation.models.ReservationResponse;
import com.example.travelerreservation.models.SignUpRequest;
import com.example.travelerreservation.models.SignUpResponse;
import com.example.travelerreservation.models.SignUpService;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationManager {

    private static ReservationManager singleton;
    private ReservationApiService reservationService;

    //return SignUpManager singleton object
    public static ReservationManager getInstance() {
        if (singleton == null)
            singleton = new ReservationManager();

        return singleton;
    }

    private ReservationManager() {
        reservationService = NetworkManager.getInstance().createService(ReservationApiService.class);
    }

    public void addReservation(
            String customerName,
            String trainName,
            String dateOfBooking,
            String timeOfBooking,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        {
            if (!NetworkManager.getInstance().isNetworkAvailable()) {
                onError.accept("No internet connectivity");
                return;
            }

            ReservationRequest request = new ReservationRequest(customerName, trainName, dateOfBooking, timeOfBooking);

            reservationService.addReservation(request).enqueue(new Callback<ReservationResponse>() {
                @Override
                public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                    if (response.isSuccessful()) {
                        // Reservation created successfully
                        onSuccess.run();
                    } else {
                        // Handle error response
                        onError.accept("An error occurred when creating the reservation");
                    }
                }

                @Override
                public void onFailure(Call<ReservationResponse> call, Throwable t) {
                    // Handle network or API call failure
                    onError.accept("Unknown error occurred when creating the reservation");
                }
            });

        }
    }



    public void editReservation(
            int reservationId,
            String customerName,
            String trainName,
            String dateOfBooking,
            String timeOfBooking,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No internet connectivity");
            return;
        }

        ReservationRequest request = new ReservationRequest(customerName, trainName, dateOfBooking, timeOfBooking);

        reservationService.editReservation(reservationId, request).enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                if (response.isSuccessful()) {
                    onSuccess.run();
                } else {
                    onError.accept("An error occurred when editing the reservation");
                }
            }

            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                onError.accept("Unknown error occurred when editing the reservation");
            }
        });
    }


    public void removeReservation(
            int reservationId,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No internet connectivity");
            return;
        }

        reservationService.deleteReservation(reservationId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    onSuccess.run();
                } else {
                    onError.accept("An error occurred when removing the reservation");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.accept("Unknown error occurred when removing the reservation");
            }
        });
    }


    public void viewReservations(
            Consumer<List<ReservationResponse>> onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No internet connectivity");
            return;
        }

        reservationService.getReservations().enqueue(new Callback<List<ReservationResponse>>() {
            @Override
            public void onResponse(Call<List<ReservationResponse>> call, Response<List<ReservationResponse>> response) {
                if (response.isSuccessful()) {
                    List<ReservationResponse> reservations = response.body();
                    onSuccess.accept(reservations);
                } else {
                    onError.accept("An error occurred when fetching reservations");
                }
            }

            @Override
            public void onFailure(Call<List<ReservationResponse>> call, Throwable t) {
                onError.accept("Unknown error occurred when fetching reservations");
            }
        });
    }






}
