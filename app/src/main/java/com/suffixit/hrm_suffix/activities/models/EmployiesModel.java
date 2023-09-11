package com.suffixit.hrm_suffix.activities.models;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "employies_table", indices = @Index(value = {"id"}, unique = true))
public class EmployiesModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String employeeId;
    private String employeeName;
    private String employeePhone;
    private String employeeMail;
    private String employeeGender;
    private String employeeBloodGroup;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeMail() {
        return employeeMail;
    }

    public void setEmployeeMail(String employeeMail) {
        this.employeeMail = employeeMail;
    }

    public String getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(String employeeGender) {
        this.employeeGender = employeeGender;
    }

    public String getEmployeeBloodGroup() {
        return employeeBloodGroup;
    }

    public void setEmployeeBloodGroup(String employeeBloodGroup) {
        this.employeeBloodGroup = employeeBloodGroup;
    }

    public EmployiesModel(String employeeId, String employeeName, String employeePhone, String employeeMail, String employeeGender, String employeeBloodGroup) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.employeeMail = employeeMail;
        this.employeeGender = employeeGender;
        this.employeeBloodGroup = employeeBloodGroup;
    }
}
