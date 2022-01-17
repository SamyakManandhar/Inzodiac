package com.alphaa.inzodiac.staticdata;

public class BirthdayData {

    String yearStart,element,heavenlyStem,earthlyBranch,animal,yearEnd;

    public BirthdayData(String yearStart, String element, String heavenlyStem, String earthlyBranch, String animal, String yearEnd) {
        this.yearStart = yearStart;
        this.element = element;
        this.heavenlyStem = heavenlyStem;
        this.earthlyBranch = earthlyBranch;
        this.animal = animal;
        this.yearEnd = yearEnd;
    }

    public String getYearStart() {
        return yearStart;
    }

    public void setYearStart(String yearStart) {
        this.yearStart = yearStart;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getHeavenlyStem() {
        return heavenlyStem;
    }

    public void setHeavenlyStem(String heavenlyStem) {
        this.heavenlyStem = heavenlyStem;
    }

    public String getEarthlyBranch() {
        return earthlyBranch;
    }

    public void setEarthlyBranch(String earthlyBranch) {
        this.earthlyBranch = earthlyBranch;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(String yearEnd) {
        this.yearEnd = yearEnd;
    }
}
