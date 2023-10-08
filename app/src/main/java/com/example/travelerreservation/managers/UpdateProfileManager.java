package com.example.travelerreservation.managers;

import android.util.Log;

import com.example.travelerreservation.models.UpdateProfileRequest;
import com.example.travelerreservation.models.UpdateProfileResponse;
import com.example.travelerreservation.models.UpdateProfileService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileManager {
    private static UpdateProfileManager singleton;
    private UpdateProfileService updateProfileService;

    public static UpdateProfileManager getInstance() {
        if (singleton == null)
            singleton = new UpdateProfileManager();

        return singleton;
    }

    private UpdateProfileManager() {
        updateProfileService = NetworkManager.getInstance().createService(UpdateProfileService.class);
    }

    public void updateProfile(
            String nic,
            String firstName,
            String lastName,
            String dateOfBirth,
            int phoneNo,
            String email,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No internet connectivity");
            return;
        }

        UpdateProfileRequest body = new UpdateProfileRequest(nic, firstName, lastName, dateOfBirth, phoneNo, email);
        updateProfileService.update(nic, body)
                .enqueue(new Callback<UpdateProfileResponse>() {
                    @Override
                    public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {

                        Log.i("UPTRES:", String.valueOf(response.code()));
                        if (response.code() == 200) {
                            onSuccess.run();
                        } else {
                            onError.accept("An error occurred!");
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                        Log.e("UPDATEERR:", t.toString());
                        Log.e("URL:", call.request().url().toString());
                        onError.accept("Unknown error occurred when updating the profile");

                    }
                });

    }

}
