
package com.alphaa.inzodiac.ui.activity.contactus.contactusmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactUsPojo {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("error_line")
    @Expose
    private Integer errorLine;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ContactUsDatum data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getErrorLine() {
        return errorLine;
    }

    public void setErrorLine(Integer errorLine) {
        this.errorLine = errorLine;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContactUsDatum getData() {
        return data;
    }

    public void setData(ContactUsDatum data) {
        this.data = data;
    }

}
