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

    public EmplyeeModel() {
    }

    public EmplyeeModel(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }
}
