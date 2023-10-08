package com.example.travelerreservation.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.travelerreservation.models.LogInRequest;
import com.example.travelerreservation.models.LogInResponse;
import com.example.travelerreservation.models.LogInService;
import com.example.travelerreservation.models.UserDao;
import com.example.travelerreservation.models.UserDto;
import com.example.travelerreservation.models.UserEntity;
import com.example.travelerreservation.utilities.MainThreadHelper;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInManager {
    private static LogInManager singleton;
    private DatabaseManager databaseManager;
    private LogInService logInService;

    private final String loginStateFile = "loginstate";
    private final String isLoggedInKey = "logged_in";

    private final String userNicKey = "user_nic";


    public static LogInManager getInstance() {
        if (singleton == null)
            singleton = new LogInManager();

        return singleton;
    }

    private LogInManager() {
        logInService = NetworkManager.getInstance().createService(LogInService.class);
        databaseManager = DatabaseManager.getInstance();
    }

    public Boolean validateCredentials(String email, String password) {
        if (email == null || email.length() == 0)
            return false;

        if (password == null || password.length() == 0)
            return false;

        return true;
    }



    public void login(
            String email,
            String password,
            Runnable onSuccess,
            Consumer<String> onError
    ) {
        if (!NetworkManager.getInstance().isNetworkAvailable()) {
            onError.accept("No internet connectivity");
            return;
        }

        LogInRequest body = new LogInRequest(email, password);
        logInService.login(body)
                .enqueue(new Callback<LogInResponse>() {
                    @Override
                    public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {


                        if (response.code() == 200) {

                            if(response.body().isActive) {
                                Log.i("USERDET:", response.body().nic);

                                UserDto userDto = new UserDto();
                                userDto.id = response.body().id;
                                userDto.nic = response.body().nic;
                                userDto.firstName = response.body().firstName;
                                userDto.lastName = response.body().lastName;
                                userDto.phoneNo = response.body().phoneNo;
                                userDto.email = response.body().email;
                                userDto.password = response.body().password;
                                userDto.dateOfBirth = response.body().dateOfBirth;
                                userDto.isActive = response.body().isActive;

                                setLoggedInState(true, userDto.nic);
                                saveUserDetails(userDto);
                                onSuccess.run();
                            } else {
                                onError.accept("This account is deactivated!");
                            }

                        } else {
                            onError.accept("An error occurred!");
                        }
                    }

                    @Override
                    public void onFailure(Call<LogInResponse> call, Throwable t) {
                        Log.e("LOGINERR:", t.toString());
                        onError.accept("Unknown error occurred when trying to log in");

                    }
                });

    }

    public void setLoggedInState(boolean isLoggedIn, String nic){
        Context context = ContextManager.getInstance().getApplicationContext();
        SharedPreferences.Editor editor = context.getSharedPreferences(loginStateFile, Context.MODE_PRIVATE).edit();
        editor.putBoolean(isLoggedInKey, isLoggedIn);
        editor.putString(userNicKey, nic);
        editor.apply();
    }

    public boolean getIsLoggedIn(){
        Context context = ContextManager.getInstance().getApplicationContext();
        SharedPreferences prefs = context.getSharedPreferences(loginStateFile, Context.MODE_PRIVATE);
        return prefs.getBoolean(isLoggedInKey, false);
    }

    public void saveUserDetails(UserDto userDto) {
        new Thread(() -> {
            databaseManager.db().userDao().removeAll();
            databaseManager.db().userDao().insert(UserEntity.fromDto(userDto));


        }).start();
    }

    public void logout() {
        new Thread(() -> {
            Context context = ContextManager.getInstance().getApplicationContext();
            SharedPreferences.Editor editor = context.getSharedPreferences(loginStateFile, Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
            databaseManager.db().userDao().removeAll();
        }).start();

    }


}
