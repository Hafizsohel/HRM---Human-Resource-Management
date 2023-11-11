package com.suffixit.hrm_suffix.models;

public class LeaveStatusModel {

    private String employeeId;
    private String dateOfApplication;
    private String status;

    public LeaveStatusModel() {
    }

    public LeaveStatusModel(String employeeId, String dateOfApplication, String status) {
        this.employeeId = employeeId;
        this.dateOfApplication = dateOfApplication;
        this.status = status;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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
                "employeeId='" + employeeId + '\'' +
                ", dateOfApplication='" + dateOfApplication + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
