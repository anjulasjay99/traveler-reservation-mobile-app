package com.example.travelerreservation.models;

public class ReservationRequest {
    private String customerName;
    private String trainName;
    private String dateOfBooking;
    private String timeOfBooking;

    // Constructors
    public ReservationRequest() {
    }

    public ReservationRequest(String customerName, String trainName, String dateOfBooking, String timeOfBooking) {
        this.customerName = customerName;
        this.trainName = trainName;
        this.dateOfBooking = dateOfBooking;
        this.timeOfBooking = timeOfBooking;
    }

    // Getters and setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(String dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public String getTimeOfBooking() {
        return timeOfBooking;
    }

    public void setTimeOfBooking(String timeOfBooking) {
        this.timeOfBooking = timeOfBooking;
    }
}

