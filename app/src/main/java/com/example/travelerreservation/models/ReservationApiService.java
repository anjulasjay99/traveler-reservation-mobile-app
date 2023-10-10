package com.example.travelerreservation.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReservationApiService {

    @POST("api/reservations")
    Call<ReservationResponse> addReservation(@Body ReservationRequest request);

    @PUT("api/reservations/{reservationId}")
    Call<ReservationResponse> editReservation(
            @Path("reservationId") int reservationId,
            @Body ReservationRequest request
    );

    @DELETE("api/reservations/{reservationId}")
    Call<Void> deleteReservation(@Path("reservationId") int reservationId);

    @GET("api/reservations")
    Call<List<ReservationResponse>> getReservations();
}

