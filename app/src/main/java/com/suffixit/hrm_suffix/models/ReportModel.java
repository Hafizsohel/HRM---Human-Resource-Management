package com.suffixit.hrm_suffix.models;

public class ReportModel {
    private String userId;
    private String name;
    private String date;

    public ReportModel() {
    }

    public ReportModel(String userId, String name, String date) {
        this.userId = userId;
        this.name = name;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "ReportModel{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}


