package com.alphaa.inzodiac.retrofit_chatting_notification;

public class MessageData{



    private String body,message,otherid,otherName;

    public MessageData(String body, String message, String otherid, String otherName) {
        this.body = body;
        this.message = message;
        this.otherid = otherid;
        this.otherName = otherName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOtherid() {
        return otherid;
    }

    public void setOtherid(String otherid) {
        this.otherid = otherid;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }
}
