package com.alphaa.inzodiac.ui.activity.detailaboyou.model;

public class BodyModel {

    public String name;

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public BodyModel(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public boolean status;
}
