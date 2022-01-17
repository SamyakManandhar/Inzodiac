package com.alphaa.inzodiac.retrofit_chatting_notification;

import com.google.gson.annotations.SerializedName;

public class RequestNotificaton {



    @SerializedName("to") //  "to" changed to token
    private String to;

    @SerializedName("data")
    private SendNotificationModel sendNotificationModel;

    public SendNotificationModel getSendNotificationModel() {
        return sendNotificationModel;
    }

    public void setSendNotificationModel(SendNotificationModel sendNotificationModel) {
        this.sendNotificationModel = sendNotificationModel;
    }

    public String getToken() {
        return to;
    }

    public void setToken(String token) {
        this.to = token;
    }


}
