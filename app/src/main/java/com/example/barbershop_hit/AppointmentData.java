package com.example.barbershop_hit;

public class AppointmentData {
    public String userName;
    public String mail;
    public String phoneNumber;
    public String selectedDate;
    public String selectedTime;

    public AppointmentData() {

    }

    public AppointmentData(String userName, String mail, String phoneNumber, String selectedDate, String selectedTime) {
        this.userName = userName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.selectedDate = selectedDate;
        this.selectedTime = selectedTime;
    }
}
