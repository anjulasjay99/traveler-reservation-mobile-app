package com.example.travelerreservation.models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpService {
    @POST("traveler")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

}
