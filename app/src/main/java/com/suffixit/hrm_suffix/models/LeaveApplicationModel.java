package com.suffixit.hrm_suffix.models;

public class LeaveApplicationModel {
    private String dateOfApplication;
    private String name;
    private String userId;
    private String designation;
    private String leaveReason;
    private String fromDate;
    private String toDate;
    private String contactNumber;

    private boolean radioCL;
    private boolean radioML;
    private String status;

    public LeaveApplicationModel() {
    }

    public LeaveApplicationModel(String dateOfApplication, String name, String userId, String designation,
                                 String leaveReason, String fromDate, String toDate, String contactNumber,
                                 boolean radioCL, boolean radioML
                                 ) {
        this.dateOfApplication = dateOfApplication;
        this.name = name;
        this.status = "Pending";
        this.userId = userId;
        this.designation = designation;
        this.leaveReason = leaveReason;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.contactNumber = contactNumber;

        this.radioCL = radioCL;
        this.radioML = radioML;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean isRadioCL() {
        return radioCL;
    }

    public void setRadioCL(boolean radioCL) {
        this.radioCL = radioCL;
    }

    public boolean isRadioML() {
        return radioML;
    }

    public void setRadioML(boolean radioML) {
        this.radioML = radioML;
    }

    @Override
    public String toString() {
        return "LeaveApplicationModel{" +
                "dateOfApplication='" + dateOfApplication + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", designation='" + designation + '\'' +
                ", leaveReason='" + leaveReason + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", radioCL=" + radioCL +
                ", radioML=" + radioML +
                ", status='" + status + '\'' +
                '}';
    }
}
