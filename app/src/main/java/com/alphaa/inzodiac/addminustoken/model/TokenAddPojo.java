
package com.alphaa.inzodiac.addminustoken.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenAddPojo {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private TokenAddDatum data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public TokenAddDatum getData() {
        return data;
    }

    public void setData(TokenAddDatum data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
