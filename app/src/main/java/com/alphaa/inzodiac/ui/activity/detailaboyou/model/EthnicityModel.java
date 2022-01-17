package com.alphaa.inzodiac.ui.activity.detailaboyou.model;

public class EthnicityModel {

    public String name;

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public EthnicityModel(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public boolean status;
}
