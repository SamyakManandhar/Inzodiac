
package com.alphaa.inzodiac.tabmodule.fragment.tokenmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TokenModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
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
    private ArrayList<Datum> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("token_name")
        @Expose
        private String tokenName;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("benifits")
        @Expose
        private ArrayList<BanefitData> benifits = null;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("create_dt")
        @Expose
        private String createDt;
        @SerializedName("update_dt")
        @Expose
        private String updateDt;

        public String getReward_token() {
            return reward_token;
        }

        public void setReward_token(String reward_token) {
            this.reward_token = reward_token;
        }

        public String getVideo_per_day() {
            return video_per_day;
        }

        public void setVideo_per_day(String video_per_day) {
            this.video_per_day = video_per_day;
        }

        @SerializedName("reward_token")
        @Expose
        private String reward_token;

        @SerializedName("video_per_day")
        @Expose
        private String video_per_day;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTokenName() {
            return tokenName;
        }

        public void setTokenName(String tokenName) {
            this.tokenName = tokenName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public ArrayList<BanefitData> getBenifits() {
            return benifits;
        }

        public void setBenifits(ArrayList<BanefitData> benifits) {
            this.benifits = benifits;
        }

        public String getStatus() {
            return status;
        }

        public String getIs_plan() {
            return is_plan;
        }

        public void setIs_plan(String is_plan) {
            this.is_plan = is_plan;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateDt() {
            return createDt;
        }

        public void setCreateDt(String createDt) {
            this.createDt = createDt;
        }

        public String getUpdateDt() {
            return updateDt;
        }

        public void setUpdateDt(String updateDt) {
            this.updateDt = updateDt;
        }
        @SerializedName("is_plan")
        @Expose
        private String is_plan;
    }

    public class BanefitData {

        public String getBenifits() {
            return benifits;
        }

        public void setBenifits(String benifits) {
            this.benifits = benifits;
        }

        @SerializedName("benifits")
        @Expose
        private String benifits;
    }

}
