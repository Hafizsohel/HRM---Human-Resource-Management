package com.suffixit.hrm_suffix.models;

public class LeaveStatusModel {

    private String userId;
    private String dateOfApplication;
    private String status;

    public LeaveStatusModel() {
    }

    public LeaveStatusModel(String userId, String dateOfApplication, String status) {
        this.userId = userId;
        this.dateOfApplication = dateOfApplication;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateOfApplication() {
        return dateOfApplication;
    }

    public void setDateOfApplication(String dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LeaveStatusModel{" +
                "userId='" + userId + '\'' +
                ", dateOfApplication='" + dateOfApplication + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
