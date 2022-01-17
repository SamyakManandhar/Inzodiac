
package com.alphaa.filter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

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
    private List<ProfilePic> profilePic = null;
    @SerializedName("user_type")
    @Expose
    private String userType;
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
    @SerializedName("pets")
    @Expose
    private String pets;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("intent")
    @Expose
    private String intent;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("otp_status")
    @Expose
    private String otpStatus;
    @SerializedName("relationship")
    @Expose
    private String relationship;
    @SerializedName("looking_for")
    @Expose
    private String lookingFor;
    @SerializedName("social")
    @Expose
    private String social;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("Firebase_token")
    @Expose
    private String firebaseToken;
    @SerializedName("intrested_in")
    @Expose
    private String intrestedIn;
    @SerializedName("orientation")
    @Expose
    private String orientation;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("western_zodaic")
    @Expose
    private String westernZodaic;
    @SerializedName("chinese_zodaic")
    @Expose
    private String chineseZodaic;
    @SerializedName("date_food")
    @Expose
    private String dateFood;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("my_work")
    @Expose
    private String myWork;
    @SerializedName("update_date")
    @Expose
    private String updateDate;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("total_photo_count")
    @Expose
    private String totalPhotoCount;
    @SerializedName("is_like")
    @Expose
    private String isLike;

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

    public List<ProfilePic> getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(List<ProfilePic> profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
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

    public String getPets() {
        return pets;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getOtpStatus() {
        return otpStatus;
    }

    public void setOtpStatus(String otpStatus) {
        this.otpStatus = otpStatus;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(String lookingFor) {
        this.lookingFor = lookingFor;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getIntrestedIn() {
        return intrestedIn;
    }

    public void setIntrestedIn(String intrestedIn) {
        this.intrestedIn = intrestedIn;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWesternZodaic() {
        return westernZodaic;
    }

    public void setWesternZodaic(String westernZodaic) {
        this.westernZodaic = westernZodaic;
    }

    public String getChineseZodaic() {
        return chineseZodaic;
    }

    public void setChineseZodaic(String chineseZodaic) {
        this.chineseZodaic = chineseZodaic;
    }

    public String getDateFood() {
        return dateFood;
    }

    public void setDateFood(String dateFood) {
        this.dateFood = dateFood;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMyWork() {
        return myWork;
    }

    public void setMyWork(String myWork) {
        this.myWork = myWork;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTotalPhotoCount() {
        return totalPhotoCount;
    }

    public void setTotalPhotoCount(String totalPhotoCount) {
        this.totalPhotoCount = totalPhotoCount;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

}
