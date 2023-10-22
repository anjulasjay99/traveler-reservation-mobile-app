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

    @POST("ticket")
    Call<ReservationResponse> addReservation(@Body ReservationRequest request);

    @PUT("ticket/{reservationId}")
    Call<ReservationResponse> editReservation(
            @Path("reservationId") String reservationId,
            @Body ReservationRequest request
    );

    @DELETE("ticket/{reservationId}")
    Call<Void> deleteReservation(@Path("reservationId") int reservationId);

    @GET("ticket")
    Call<List<ReservationResponse>> getReservations();
}

