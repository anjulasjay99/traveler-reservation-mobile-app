package com.example.travelerreservation.models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UpdateProfileService {
    @PUT("travelers/{nic}")
    Call<UpdateProfileResponse> update(@Path("nic") String nic, @Body UpdateProfileRequest updateProfileRequest);

}
