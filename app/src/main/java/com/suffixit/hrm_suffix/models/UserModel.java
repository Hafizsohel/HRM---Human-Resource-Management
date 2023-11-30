package com.suffixit.hrm_suffix.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserModel {
    private String userId;
    private String name;
    private String Designation;
    private String profileImg;
    public UserModel() {
    }

    public UserModel(String userId, String name, String designation, String profileImg) {
        this.userId = userId;
        this.name = name;
        Designation = designation;
        this.profileImg = profileImg;
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

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", Designation='" + Designation + '\'' +
                ", profileImg='" + profileImg + '\'' +
                '}';
    }

    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView imageView, String profileImg){
        Glide.with(imageView)
                .load(profileImg)
                .into(imageView);
    }
}
