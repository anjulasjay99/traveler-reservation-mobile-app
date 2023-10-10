package com.example.travelerreservation.models;

public class Reservation {
    private String customerName;
    private String trainName;
    private String dateAndTime;

    public Reservation(String customerName, String trainName, String dateAndTime) {
        this.customerName = customerName;
        this.trainName = trainName;
        this.dateAndTime = dateAndTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }
}
