package com.alphaa.inzodiac.retrofit_chatting_notification;

public class SendNotificationModel {


    MessageData messageData;

    public SendNotificationModel(MessageData messageData) {
        this.messageData = messageData;
    }

    public MessageData getMessageData() {
        return messageData;
    }

    public void setMessageData(MessageData messageData) {
        this.messageData = messageData;
    }
}



