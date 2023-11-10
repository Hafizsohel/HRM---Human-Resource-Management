package com.suffixit.hrm_suffix.models;

public class LeaveApplicationModel {
    private String dateOfApplication;
    private String name;
    private String employeeId;
    private String designation;
    private String leaveReason;
    private String fromDate;
    private String toDate;
    private String contactNumber;

    public LeaveApplicationModel() {
    }

    public LeaveApplicationModel(String dateOfApplication, String name, String employeeId, String designation, String leaveReason, String fromDate, String toDate, String contactNumber) {
        this.dateOfApplication = dateOfApplication;
        this.name = name;
        this.employeeId = employeeId;
        this.designation = designation;
        this.leaveReason = leaveReason;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.contactNumber = contactNumber;
    }

    public String getDateOfApplication() {
        return dateOfApplication;
    }

    public void setDateOfApplication(String dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "LeaveApplicationModel{" +
                "dateOfApplication='" + dateOfApplication + '\'' +
                ", name='" + name + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", designation='" + designation + '\'' +
                ", leaveReason='" + leaveReason + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}
