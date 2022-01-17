package com.alphaa.inzodiac.westerncompatibility;

import java.util.ArrayList;

public  class WesternCompatibilityData {

    public static ArrayList<WesternCompatibilityModel>  getwesterncompatibilitydata(){




        ArrayList<WesternCompatibilityModel> arrayList = new ArrayList<>();

        //1
        arrayList.add(new WesternCompatibilityModel("Aries",	"Gemini, Leo, Sagittarius, Aquarius",
                "Libra",
                "Cancer, Virgo, Pisces",
                "Aries, Gemini, Scorpio, Sagittarius, Capricorn",
                "Taurus",	"Leo",	"Sagittarius, Aquarius", "Scorpio, Capricorn"
        ));
        //2
        arrayList.add(new WesternCompatibilityModel("Taurus",	"Cancer, Virgo, Capricorn, Pisces",
                "Scorpio",	"Gemini, Leo, Libra, Sagittarius, Aquarius",
                "Aries, Taurus, Cancer, Scorpio, Pisces",	"Sagittarius",	"Taurus",
                "Libra, Scorpio",	"Aquarius, Pisces"
        ));

        //3
        arrayList.add(new WesternCompatibilityModel("Gemini",	"Aries, Leo, Libra, Aquarius",
                "Sagittarius",	"Taurus, Gemini, Cancer, Capricorn, Pisces",
                "Aries, Gemini, Scorpio, Sagittarius, Aquarius",	"Virgo",	"Aries",
                "Gemini, Scorpio","	Virgo, Pisces"
        ));

        //4
        arrayList.add(new WesternCompatibilityModel("Cancer",	"Taurus, Virgo, Scorpio, Pisces",
                "Capricorn",	"Aries, Gemini, Leo, Libra, Sagittarius, Aquarius",
                "Taurus, Cancer, Scorpio, Capricorn, Pisces",	"Aries",
                "Capricorn",	"Taurus, Leo",	"Aries, Capricorn"
        ));

        //5
        arrayList.add(new WesternCompatibilityModel("Leo",	"Sagittarius, Aries, Gemini, Libra",
                "Aquarius",	"Taurus, Cancer, Virgo, Scorpio, Pisces",
                "Gemini, Leo, Libra, Capricorn",	"Capricorn",	"Aquarius",
                "Aquarius, Pisces",	"Taurus, Gemini"
        ));

        //6
        arrayList.add(new WesternCompatibilityModel("Virgo",	"Capricorn, Taurus, Cancer, Scorpio",
                "Pisces","	Aries, Leo, Sagittarius, Aquarius",
                "Taurus, Gemini, Virgo, Libra, Pisces",	"Leo",	"Cancer",
                "Capricorn, Pisces",	"Gemini, Aquarius"
        ));

        //7
        arrayList.add(new WesternCompatibilityModel("Libra",	"Aquarius, Gemini, Leo, Sagittarius",
                "Aries",	"Taurus, Cancer, Scorpio, Capricorn",
                "Gemini, Libra, Leo, Virgo, Pisces",	"Cancer",	"Libra",
                "Leo, Aquarius",	"Cancer, Scorpio"
        ));
        //8
        arrayList.add(new WesternCompatibilityModel("Scorpio",	"Pisces, Cancer, Virgo, Capricorn",
                "Taurus","	Leo, Libra, Sagittarius",	"Aries, Cancer, Virgo, Scorpio, Capricorn, Aquarius",
                "Gemini",	"Pisces",	"Gemini, Libra",	"Taurus, Leo"
        ));

        //9
        arrayList.add(new WesternCompatibilityModel("Sagittarius",	"Libra, Aquarius, Aries, Leo","Gemini",
                "Taurus, Cancer, Virgo, Scorpio, Capricorn",	"Aries, Leo, Libra, Sagittarius",
                "Pisces",	"Sagittarius",	"Aries, Pisces",	"Aries, Virgo"
        ));


        //10
        arrayList.add(new WesternCompatibilityModel("Capricorn",	"Scorpio, Pisces, Taurus, Virgo",
                "Cancer","	Gemini, Libra, Sagittarius, Aquarius",
                "Aries, Taurus, Cancer, Leo, Scorpio, Capricorn",	"Aquarius",
                "Scorpio",	"Virgo, Scorpio",	"Leo, Libra"
        ));
        //11
        arrayList.add(new WesternCompatibilityModel("Aquarius",	"Gemini, Libra, Sagittarius, Aries",
                "Leo",	"Taurus, Cancer, Virgo, Capricorn, Pisces",
                "Aries, Leo, Libra, Aquarius",	"Scorpio",	"Gemini",
                "Aries, Sagittarius",	"Leo, Scorpio"
        ));

        //12
        arrayList.add(new WesternCompatibilityModel("Pisces",	"Cancer, Scorpio, Taurus, Capricorn",
                "Virgo",	"Aries, Gemini, Leo, Sagittarius, Aquarius",
                "Cancer, Libra, Scorpio, Pisces",	"Libra",	"Virgo",
                "Cancer, Leo","Virgo, Sagittarius"
        ));

        return arrayList;
    }
}
