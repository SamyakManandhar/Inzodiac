package com.alphaa.inzodiac.tabmodule.fragment.menumodule;

public class MenuParm {
    String user_id;
    String category_id;
    String looking_for;
    String age;
    String ethnicity;
    String bodytype;
    String intent;
    String country;
    String city;
    String distance;
    String latitude;
    String longitude;
    String intrested_in;
    String orientation;
    String horoscopetype;
    String relationship;
    String children;
    String smoke;
    String drink;
    String  from_age;
    String to_age;


    String gender;
    //String orientation;



    public MenuParm(String user_id, String category_id, String looking_for, String age, String ethnicity, String bodytype, String intent, String country, String city,
                    String distance,
                    String latitude,
                    String longitude,String gender,String orientation) {
        this.user_id = user_id;
        this.category_id = category_id;
        this.looking_for = looking_for;
        this.age = age;
        this.ethnicity = ethnicity;
        this.bodytype = bodytype;
        this.intent = intent;
        this.country = country;
        this.city = city;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
        this.gender = gender;
        this.orientation = orientation;
    }

    public MenuParm(String user_id, String category_id, String looking_for, String age, String ethnicity, String bodytype, String intent, String country, String city,
                    String distance, String latitude, String longitude, String intrested_in, String orientation, String horoscopetype, String relationship,
                    String children, String smoke, String drink, String from_age, String to_age,String gender) {
        this.user_id = user_id;
        this.category_id = category_id;
        this.looking_for = looking_for;
        this.age = age;
        this.ethnicity = ethnicity;
        this.bodytype = bodytype;
        this.intent = intent;
        this.country = country;
        this.city = city;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
        this.intrested_in = intrested_in;
        this.orientation = orientation;
        this.horoscopetype = horoscopetype;
        this.relationship = relationship;
        this.children = children;
        this.smoke = smoke;
        this.drink = drink;
        this.from_age = from_age;
        this.to_age = to_age;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIntrested_in() {
        return intrested_in;
    }

    public void setIntrested_in(String intrested_in) {
        this.intrested_in = intrested_in;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getHoroscopetype() {
        return horoscopetype;
    }

    public void setHoroscopetype(String horoscopetype) {
        this.horoscopetype = horoscopetype;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
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

    public String getFrom_age() {
        return from_age;
    }

    public void setFrom_age(String from_age) {
        this.from_age = from_age;
    }

    public String getTo_age() {
        return to_age;
    }

    public void setTo_age(String to_age) {
        this.to_age = to_age;
    }

    String western_zodaic,chinese_zodaic;



    public MenuParm(String western_zodaic, String chinese_zodaic) {
        this.western_zodaic = western_zodaic;
        this.chinese_zodaic = chinese_zodaic;
    }
}
