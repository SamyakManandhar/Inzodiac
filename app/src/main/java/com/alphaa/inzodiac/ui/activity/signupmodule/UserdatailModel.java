package com.alphaa.inzodiac.ui.activity.signupmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserdatailModel {

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

    public static class SignupDatum  implements Serializable {
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("profile_pic")
        @Expose
        private List<Object> profilePic=null;
        @SerializedName("user_type")
        @Expose
        private Object userType;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("ethnicity")
        @Expose
        private String ethnicity;
        @SerializedName("bodytype")
        @Expose
        private String bodytype;
        @SerializedName("height")
        @Expose
        private String height;
        @SerializedName("haircolour")
        @Expose
        private String haircolour;
        @SerializedName("eyecolour")
        @Expose
        private String eyecolour;
        @SerializedName("horoscopetype")
        @Expose
        private String horoscopetype;
        @SerializedName("married")
        @Expose
        private String married;
        @SerializedName("children")
        @Expose
        private String children;
        @SerializedName("smoke")
        @Expose
        private String smoke;
        @SerializedName("drink")
        @Expose
        private String drink;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("create_date")
        @Expose
        private String createDate;
        @SerializedName("otp")
        @Expose
        private String otp;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("long")
        @Expose
        private String _long;
        @SerializedName("otp_status")
        @Expose
        private String otpStatus;


        @SerializedName("is_complete")
        @Expose
        private String is_complete;



        @SerializedName("intrested_in")
        @Expose
        private String intrested_in;
        @SerializedName("western_zodaic")
        @Expose
        private String western_zodaic;
        @SerializedName("chinese_zodaic")
        @Expose
        private String chinese_zodaic;
        @SerializedName("orientation")
        @Expose
        private String orientation;
        @SerializedName("total_token")
        @Expose
        private String total_token;
        @SerializedName("subscription")
        @Expose
        private String subscription;
        @SerializedName("Firebase_token")
        @Expose
        private String Firebase_token;


        public String getFirebase_token() {
            return Firebase_token;
        }

        public void setFirebase_token(String firebase_token) {
            Firebase_token = firebase_token;
        }

        public String getTotal_token() {
            return total_token;
        }

        public void setTotal_token(String total_token) {
            this.total_token = total_token;
        }

        public String getSubscription() {
            return subscription;
        }

        public void setSubscription(String subscription) {
            this.subscription = subscription;
        }

        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }

        public String getWestern_zodaic() {
            return western_zodaic;
        }

        public void setWestern_zodaic(String western_zodaic) {
            this.western_zodaic = western_zodaic;
        }

        public String getChinese_zodaic() {
            return chinese_zodaic;
        }

        public void setChinese_zodaic(String chinese_zodaic) {
            this.chinese_zodaic = chinese_zodaic;
        }

        public String getIntrested_in() {
            return intrested_in;
        }

        public void setIntrested_in(String intrested_in) {
            this.intrested_in = intrested_in;
        }

        public String getIs_complete() {
            return is_complete;
        }

        public void setIs_complete(String is_complete) {
            this.is_complete = is_complete;
        }

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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }


        public List<Object> getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(List<Object> profilePic) {
            this.profilePic = profilePic;
        }

        public Object getUserType() {
            return userType;
        }

        public void setUserType(Object userType) {
            this.userType = userType;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getEthnicity() {
            return ethnicity;
        }

        public void setEthnicity(String ethnicity) {
            this.ethnicity = ethnicity;
        }

        public String getBodytype() {
            return bodytype;
        }

        public void setBodytype(String bodytype) {
            this.bodytype = bodytype;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getHaircolour() {
            return haircolour;
        }

        public void setHaircolour(String haircolour) {
            this.haircolour = haircolour;
        }

        public String getEyecolour() {
            return eyecolour;
        }

        public void setEyecolour(String eyecolour) {
            this.eyecolour = eyecolour;
        }

        public String getHoroscopetype() {
            return horoscopetype;
        }

        public void setHoroscopetype(String horoscopetype) {
            this.horoscopetype = horoscopetype;
        }

        public String getMarried() {
            return married;
        }

        public void setMarried(String married) {
            this.married = married;
        }

        public String getChildren() {
            return children;
        }

        public void setChildren(String children) {
            this.children = children;
        }

        public String getSmoke() {
            return smoke;
        }

        public void setSmoke(String smoke) {
            this.smoke = smoke;
        }

        public String getDrink() {
            return drink;
        }

        public void setDrink(String drink) {
            this.drink = drink;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLong() {
            return _long;
        }

        public void setLong(String _long) {
            this._long = _long;
        }

        public String getOtpStatus() {
            return otpStatus;
        }

        public void setOtpStatus(String otpStatus) {
            this.otpStatus = otpStatus;
        }

    }

}
