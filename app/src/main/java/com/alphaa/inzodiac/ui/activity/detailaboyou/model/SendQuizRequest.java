
package com.alphaa.inzodiac.ui.activity.detailaboyou.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendQuizRequest {

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
    private List<Object> data = null;

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

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

}
