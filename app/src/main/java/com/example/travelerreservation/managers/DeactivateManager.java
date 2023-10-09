package com.example.travelerreservation.managers;

import android.util.Log;

import com.example.travelerreservation.models.DeactivateResponse;
import com.example.travelerreservation.models.DeactivateService;
import com.example.travelerreservation.models.UpdateProfileRequest;
import com.example.travelerreservation.models.UpdateProfileResponse;
import com.example.travelerreservation.models.UpdateProfileService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeactivateManager {
    private static DeactivateManager singleton;
    private DeactivateService deactivateService;

    //Returns DeactivateManager singleton object
    public static DeactivateManager getInstance() {
        if (singleton == null)
            singleton = new DeactivateManager();

        return singleton;
    }

    private DeactivateManager() {
        deactivateService = NetworkManager.getInstance().createService(DeactivateService.class);
    }

    //Calls backend API to deactivate use account
    public void deactivateProfile(
            String nic,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No internet connectivity");
            return;
        }
        deactivateService.deactivate(nic)
                .enqueue(new Callback<DeactivateResponse>() {
                    @Override
                    public void onResponse(Call<DeactivateResponse> call, Response<DeactivateResponse> response) {

                        Log.i("UPTRES:", String.valueOf(response.code()));
                        if (response.code() == 200) {
                            onSuccess.run();
                        } else {
                            onError.accept("An error occurred!");
                        }
                    }

                    @Override
                    public void onFailure(Call<DeactivateResponse> call, Throwable t) {
                        Log.e("UPDATEERR:", t.toString());
                        Log.e("URL:", call.request().url().toString());
                        onError.accept("Unknown error occurred when deactivating the profile");

                    }
                });

    }
}
