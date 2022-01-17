package com.alphaa.inzodiac.ui.activity.detailaboyou.model;

public class HairModel {

    public String name;

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public HairModel(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public boolean status;
}
