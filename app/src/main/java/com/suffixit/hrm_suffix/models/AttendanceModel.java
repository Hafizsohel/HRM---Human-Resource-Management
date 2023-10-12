package com.suffixit.hrm_suffix.models;


public class AttendanceModel {
    private String date;
    private String day;
    private String checkInTime;
    private String checkoutTime;

    public AttendanceModel() {
    }

    public AttendanceModel(String date, String day, String checkInTime, String checkoutTime) {
        this.date = date;
        this.day = day;
        this.checkInTime = checkInTime;
        this.checkoutTime = checkoutTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    @Override
    public String toString() {
        return "AttendanceModel{" +
                "date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", checkInTime='" + checkInTime + '\'' +
                ", checkoutTime='" + checkoutTime + '\'' +
                '}';
    }
}
