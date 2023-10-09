package com.example.travelerreservation.models;

public class UpdateProfileRequest {
    String nic;
    String firstName;
    String lastName;
    String dateOfBirth;
    int phoneNo;
    String email;

    String password;

    public UpdateProfileRequest(String nic, String firstName, String lastName, String dateOfBirth, int phoneNo, String email, String password) {
        this.nic = nic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
    }
}
