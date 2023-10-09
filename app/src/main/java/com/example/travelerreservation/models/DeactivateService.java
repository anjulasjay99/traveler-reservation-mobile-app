package com.example.travelerreservation.models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DeactivateService {
    @PUT("travelers/deactivate/{nic}")
    Call<DeactivateResponse> deactivate(@Path("nic") String nic);
}
