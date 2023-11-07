package com.suffixit.hrm_suffix.models;

public class ReportModel {
    private String userId;
    private String name;
    private String date;
    private String status;

    public ReportModel() {
    }

    public ReportModel(String userId, String name, String date, String status) {
        this.userId = userId;
        this.name = name;
        this.date = date;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReportModel{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}


