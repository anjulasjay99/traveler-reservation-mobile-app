package com.example.travelerreservation.models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LogInService {
    @POST("api/travelerLogin")
    Call<LogInResponse> login(@Body LogInRequest logInRequest);
}
