package com.alphaa.inzodiac.westerncompatibility;

public  class WesternCompatibilityModel {

   String Zodiac, ClassicCompatibility,	OppositesAttract,	MoreUnderstandingRequired,
    PassionPotential,	WildCard,	OurPick,	Friends,	Business ;

    public WesternCompatibilityModel(String zodiac, String classicCompatibility, String oppositesAttract, String moreUnderstandingRequired, String passionPotential, String wildCard, String ourPick, String friends, String business) {
        Zodiac = zodiac;
        ClassicCompatibility = classicCompatibility;
        OppositesAttract = oppositesAttract;
        MoreUnderstandingRequired = moreUnderstandingRequired;
        PassionPotential = passionPotential;
        WildCard = wildCard;
        OurPick = ourPick;
        Friends = friends;
        Business = business;
    }

    public String getZodiac() {
        return Zodiac;
    }

    public void setZodiac(String zodiac) {
        Zodiac = zodiac;
    }

    public String getClassicCompatibility() {
        return ClassicCompatibility;
    }

    public void setClassicCompatibility(String classicCompatibility) {
        ClassicCompatibility = classicCompatibility;
    }

    public String getOppositesAttract() {
        return OppositesAttract;
    }

    public void setOppositesAttract(String oppositesAttract) {
        OppositesAttract = oppositesAttract;
    }

    public String getMoreUnderstandingRequired() {
        return MoreUnderstandingRequired;
    }

    public void setMoreUnderstandingRequired(String moreUnderstandingRequired) {
        MoreUnderstandingRequired = moreUnderstandingRequired;
    }

    public String getPassionPotential() {
        return PassionPotential;
    }

    public void setPassionPotential(String passionPotential) {
        PassionPotential = passionPotential;
    }

    public String getWildCard() {
        return WildCard;
    }

    public void setWildCard(String wildCard) {
        WildCard = wildCard;
    }

    public String getOurPick() {
        return OurPick;
    }

    public void setOurPick(String ourPick) {
        OurPick = ourPick;
    }

    public String getFriends() {
        return Friends;
    }

    public void setFriends(String friends) {
        Friends = friends;
    }

    public String getBusiness() {
        return Business;
    }

    public void setBusiness(String business) {
        Business = business;
    }
}
