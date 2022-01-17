
package com.alphaa.inzodiac.ui.activity.aboutself.aboutmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutSelfCategoryPojo {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private AboutSelfCategoryDatum data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public AboutSelfCategoryDatum getData() {
        return data;
    }

    public void setData(AboutSelfCategoryDatum data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
