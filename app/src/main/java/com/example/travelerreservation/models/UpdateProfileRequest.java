package com.example.travelerreservation.models;

public class UpdateProfileRequest {
    String nic;
    String firstName;
    String lastName;
    String dateOfBirth;
    int phoneNo;
    String email;

    public UpdateProfileRequest(String nic, String firstName, String lastName, String dateOfBirth, int phoneNo, String email) {
        this.nic = nic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNo = phoneNo;
        this.email = email;
    }
}
