
package com.alphaa.inzodiac.ui.activity.notification.notificationmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("recepient_id")
    @Expose
    private String recepientId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("is_read")
    @Expose
    private String isRead;
    @SerializedName("types")
    @Expose
    private String types;
    @SerializedName("types_id")
    @Expose
    private String typesId;
    @SerializedName("create_dt")
    @Expose
    private String createDt;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecepientId() {
        return recepientId;
    }

    public void setRecepientId(String recepientId) {
        this.recepientId = recepientId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getTypesId() {
        return typesId;
    }

    public void setTypesId(String typesId) {
        this.typesId = typesId;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

}
