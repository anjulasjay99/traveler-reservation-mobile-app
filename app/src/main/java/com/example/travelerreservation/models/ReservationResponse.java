package com.example.travelerreservation.models;

public class ReservationResponse {
    private int id;
    private String customerName;
    private String trainName;
    private String dateOfBooking;
    private String timeOfBooking;

    // Constructors
    public ReservationResponse() {
    }

    public ReservationResponse(int id, String customerName, String trainName, String dateOfBooking, String timeOfBooking) {
        this.id = id;
        this.customerName = customerName;
        this.trainName = trainName;
        this.dateOfBooking = dateOfBooking;
        this.timeOfBooking = timeOfBooking;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

