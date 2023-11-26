package com.suffixit.hrm_suffix.models;

public class UserModel {
    private String userId;
    private String name;
    private String Designation;

    public UserModel() {
    }

    public UserModel(String userId, String name, String designation) {
        this.userId = userId;
        this.name = name;
        Designation = designation;
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

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }


    @Override
    public String toString() {
        return "UserModel{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", Designation='" + Designation + '\'' +
                '}';
    }
}
