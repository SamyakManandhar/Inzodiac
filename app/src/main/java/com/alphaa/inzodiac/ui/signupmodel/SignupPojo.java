
package com.alphaa.inzodiac.ui.signupmodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupPojo {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private SignupDatum data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public SignupDatum getData() {
        return data;
    }

    public void setData(SignupDatum data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
