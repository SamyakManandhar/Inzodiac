package com.alphaa.inzodiac.tabmodule.fragment.updateprmodule;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanData {

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
        @SerializedName("is_plan")
        @Expose
        private String is_plan;

        @SerializedName("id")
        @Expose
        private String id;

        public String getIs_plan() {
            return is_plan;
        }

        public void setIs_plan(String is_plan) {
            this.is_plan = is_plan;
        }

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("status")
        @Expose
        private String status;

        public String getPer_week_price() {
            return per_week_price;
        }

        public void setPer_week_price(String per_week_price) {
            this.per_week_price = per_week_price;
        }

        @SerializedName("per_week_price")
        @Expose
        private String per_week_price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}

