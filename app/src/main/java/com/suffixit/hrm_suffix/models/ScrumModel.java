package com.suffixit.hrm_suffix.models;

public class ScrumModel {

    private int id;
    private String name;

    public ScrumModel() {
    }

    public ScrumModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
