package com.example.travelerreservation.models;

public class LogInRequest {
    public String email;
    public String password;

    public LogInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
