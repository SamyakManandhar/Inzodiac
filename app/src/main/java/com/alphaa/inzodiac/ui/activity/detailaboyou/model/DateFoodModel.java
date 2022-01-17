package com.alphaa.inzodiac.ui.activity.detailaboyou.model;

public class DateFoodModel {

    public String name;

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public DateFoodModel(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public boolean status;
}
