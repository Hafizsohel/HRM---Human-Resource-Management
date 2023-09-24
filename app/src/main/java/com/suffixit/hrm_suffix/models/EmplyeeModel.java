package com.suffixit.hrm_suffix.models;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "emplyee_table", indices = @Index(value = {"id"}, unique = true))
public class EmplyeeModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String username;
    private String name;
    private String BloodGroup;
    private String Designation;
    private String Email;
    private String Gender;
    private String PhoneNumber;


    public EmplyeeModel() {
    }


    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }
    public EmplyeeModel(int id, String username, String name, String bloodGroup, String designation, String email, String gender, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.name = name;
        BloodGroup = bloodGroup;
        Designation = designation;
        Email = email;
        Gender = gender;
        PhoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername(String username) {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName(String name) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "EmplyeeModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", BloodGroup='" + BloodGroup + '\'' +
                ", Designation='" + Designation + '\'' +
                ", Email='" + Email + '\'' +
                ", Gender='" + Gender + '\'' +
                ", PhoneNumber=" + PhoneNumber +
                '}';
    }
}

