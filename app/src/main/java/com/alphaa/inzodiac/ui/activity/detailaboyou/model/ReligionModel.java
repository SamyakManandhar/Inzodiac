package com.alphaa.inzodiac.ui.activity.detailaboyou.model;

public class ReligionModel {

    public String name;

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public ReligionModel(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public boolean status;
}
