package com.example.travelerreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelerreservation.managers.ContextManager;
import com.example.travelerreservation.managers.LogInManager;

public class LogIn extends AppCompatActivity {

    LogInManager logInManager;
    EditText emailEditText;
    EditText passwordEditText;

    TextView signUpLink;

    Button loginBtn;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ContextManager.getInstance().setApplicationContext(getApplicationContext());
        getSupportActionBar().hide();

        logInManager = LogInManager.getInstance();

        this.emailEditText = findViewById(R.id.loginEmailEditText);
        this.passwordEditText = findViewById(R.id.loginPasswordEditText);
        this.signUpLink = findViewById(R.id.signUpLink);
        this.loginBtn = findViewById(R.id.logInBtn);

        this.signUpLink.setOnClickListener(view -> goToSignUp());

        this.loginBtn.setOnClickListener(view -> login());

    }

    private void login(){
        if(!emailEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
            logInManager.login(
                    emailEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    () -> handleLoginSuccess(),
                    error -> handleLoginFailed(error));
        } else{
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        }

    }

    private void handleLoginSuccess(){
        //logInManager.setLoggedInState(true);
        Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void handleLoginFailed(String error){
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private void goToSignUp() {
        Intent intent = new Intent(this, TravelerSignUp.class);
        startActivity(intent);
        //finish();
    }

}