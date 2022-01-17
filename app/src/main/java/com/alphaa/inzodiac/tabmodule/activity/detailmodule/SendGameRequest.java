package com.alphaa.inzodiac.tabmodule.activity.detailmodule;

public class SendGameRequest {

    String user_id,other_user_id,request_id,request_type,category_id;



    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOther_user_id() {
        return other_user_id;
    }

    public void setOther_user_id(String other_user_id) {
        this.other_user_id = other_user_id;
    }
}
