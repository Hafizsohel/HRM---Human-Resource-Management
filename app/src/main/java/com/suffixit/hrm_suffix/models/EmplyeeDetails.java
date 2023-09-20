package com.suffixit.hrm_suffix.models;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "emplyeeDetails_table", indices = @Index(value = {"id"}, unique = true))
public class EmplyeeDetails {

    private String emplyeeName;
    private String emplyeeAdree;
    private String emplyeePhoneNumber;
    private String emplyeeGender;
    private String emplyeeBloodGroup;


    public String getEmplyeeName() {
        return emplyeeName;
    }

    public void setEmplyeeName(String emplyeeName) {
        this.emplyeeName = emplyeeName;
    }

    public String getEmplyeeAdree() {
        return emplyeeAdree;
    }

    public void setEmplyeeAdree(String emplyeeAdree) {
        this.emplyeeAdree = emplyeeAdree;
    }

    public String getEmplyeePhoneNumber() {
        return emplyeePhoneNumber;
    }

    public void setEmplyeePhoneNumber(String emplyeePhoneNumber) {
        this.emplyeePhoneNumber = emplyeePhoneNumber;
    }

    public String getEmplyeeGender() {
        return emplyeeGender;
    }

    public void setEmplyeeGender(String emplyeeGender) {
        this.emplyeeGender = emplyeeGender;
    }

    public String getEmplyeeBloodGroup() {
        return emplyeeBloodGroup;
    }

    public void setEmplyeeBloodGroup(String emplyeeBloodGroup) {
        this.emplyeeBloodGroup = emplyeeBloodGroup;
    }

    public EmplyeeDetails(String emplyeeName, String emplyeeAdree, String emplyeePhoneNumber, String emplyeeGender, String emplyeeBloodGroup) {
        this.emplyeeName = emplyeeName;
        this.emplyeeAdree = emplyeeAdree;
        this.emplyeePhoneNumber = emplyeePhoneNumber;
        this.emplyeeGender = emplyeeGender;
        this.emplyeeBloodGroup = emplyeeBloodGroup;
    }
}
