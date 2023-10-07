package com.example.travelerreservation.managers;

import com.example.travelerreservation.models.SignUpRequest;
import com.example.travelerreservation.models.SignUpResponse;
import com.example.travelerreservation.models.SignUpService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpManager {

    private static SignUpManager singleton;
    private SignUpService signUpService;

    public static SignUpManager getInstance() {
        if (singleton == null)
            singleton = new SignUpManager();

        return singleton;
    }

    private SignUpManager() {
        signUpService = NetworkManager.getInstance().createService(SignUpService.class);
    }

    public void signUp(
            String nic,
            String firstName,
            String lastName,
            String dateOfBirth,
            String phoneNo,
            String email,
            String password,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No internet connectivity");
            return;
        }

        SignUpRequest body = new SignUpRequest(nic, firstName, lastName, dateOfBirth, phoneNo, email, password);
        signUpService.signUp(body)
                .enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                        onSuccess.run();
                        /*
                        if (response.body().success) {
                            onSuccess.run();
                        } else {
                            onError.accept("An error occurred!");
                        }*/
                    }

                    @Override
                    public void onFailure(Call<SignUpResponse> call, Throwable t) {
                        onError.accept("Unknown error occurred while signing up");
                    }
                });

    }

}
