package com.suffixit.hrm_suffix.models;


import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "attendance_table", indices = @Index(value = {"id"}, unique = true))
public class AttendanceModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String userId;
    private String date;
    private String day;
    private String checkInTime;
    private String checkoutTime;
    private String totalHrs;

    public AttendanceModel() {
    }

    public AttendanceModel(String date, String day, String checkInTime, String checkoutTime, String totalHrs) {
        this.id = id;
        this.date = date;
        this.day = day;
        this.checkInTime = checkInTime;
        this.checkoutTime = checkoutTime;
        this.totalHrs = totalHrs;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public String getTotalHrs() {
        return totalHrs;
    }

    public void setTotalHrs(String totalHrs) {
        this.totalHrs = totalHrs;
    }

    @Override
    public String toString() {
        return "AttendanceModel{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", checkInTime='" + checkInTime + '\'' +
                ", checkoutTime='" + checkoutTime + '\'' +
                ", totalHrs=" + totalHrs +
                '}';
    }
}
