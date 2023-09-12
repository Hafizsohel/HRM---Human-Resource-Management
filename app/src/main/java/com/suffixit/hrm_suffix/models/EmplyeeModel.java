package com.suffixit.hrm_suffix.models;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "emplyee_table", indices = @Index(value = {"id"}, unique = true))
public class EmplyeeModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String emplyeeId;
    private String emplyeeName;
    private String emplyeePhone;
    private String emplyeeEmail;
    private String emplyeeGender;
    private String emplyeeBloodGroup;

    public String getEmplyeeId() {
        return emplyeeId;
    }

    public void setEmplyeeId(String emplyeeId) {
        this.emplyeeId = emplyeeId;
    }

    public String getEmplyeeName() {
        return emplyeeName;
    }

    public void setEmplyeeName(String emplyeeName) {
        this.emplyeeName = emplyeeName;
    }

    public String getEmplyeePhone() {
        return emplyeePhone;
    }

    public void setEmplyeePhone(String emplyeePhone) {
        this.emplyeePhone = emplyeePhone;
    }

    public String getEmplyeeEmail() {
        return emplyeeEmail;
    }

    public void setEmplyeeEmail(String emplyeeEmail) {
        this.emplyeeEmail = emplyeeEmail;
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

    public EmplyeeModel(String emplyeeId, String emplyeeName, String emplyeePhone, String emplyeeEmail, String emplyeeGender, String emplyeeBloodGroup) {
        this.emplyeeId = emplyeeId;
        this.emplyeeName = emplyeeName;
        this.emplyeePhone = emplyeePhone;
        this.emplyeeEmail = emplyeeEmail;
        this.emplyeeGender = emplyeeGender;
        this.emplyeeBloodGroup = emplyeeBloodGroup;
    }
}
