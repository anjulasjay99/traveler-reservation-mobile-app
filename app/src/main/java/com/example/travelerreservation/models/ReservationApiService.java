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

    @POST("api/ticket")
    Call<ReservationResponse> addReservation(@Body ReservationRequest request);

    @PUT("api/ticket/{reservationId}")
    Call<ReservationResponse> editReservation(
            @Path("reservationId") int reservationId,
            @Body ReservationRequest request
    );

    @DELETE("api/ticket/{reservationId}")
    Call<Void> deleteReservation(@Path("reservationId") int reservationId);

    @GET("api/ticket")
    Call<List<ReservationResponse>> getReservations();
}

