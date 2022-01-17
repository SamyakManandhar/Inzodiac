package com.alphaa.inzodiac.staticdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alphaa.inzodiac.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ZodaicActivity extends AppCompatActivity {


    //ArrayList<ChineseData> chineseDataArrayList;
    //ArrayList<WesternData> westernDataArrayList;

    //ArrayList<BirthdayData> birthdayDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zodaic);
        chinesedata();
        westerdata();


    }

    public static ArrayList<WesternData> westerdata() {
        ///////////////////////westen data

        ArrayList<WesternData> westernDataArrayList =  new ArrayList<>();

        //1
        westernDataArrayList.add(new WesternData("AQUARIUS","Jan-20","Feb-18","Air","Fixed","Uranus, Saturn",
                "Light-Blue, Silver","Saturday","4, 7, 11, 22, 29","Garnet","Garnet","Turquoise, Natural Diamond"));


        //2
        westernDataArrayList.add(new WesternData("PISCES","Feb-19","Mar-20","Water","Mutable","Neptune, Jupiter",
                "Mauve, Lilac, Purple, Violet, Sea green","Thursday","3, 9, 12, 15, 18, 24","Amethyst","Amethyst","Sapphire, Carnelian"));


        //3
        westernDataArrayList.add(new WesternData("ARIES","Mar-21","Apr-19","Fire","Cardinal","Mars",
                "Red","Tuesday","1, 8, 17","Aquamarine, Bloodstone","Heliotrope","Ruby, Emerald"));


        //4
        westernDataArrayList.add(new WesternData("TAURUS","Apr-20","May-20","Earth","Fixed","Venus",
                "Green, Pink","Friday, Monday","2, 6, 9, 12, 24","Natural diamond","Sapphire, Natural diamond","Emerald, Cat's eye, Pearl, Crystal"));


        //5
        westernDataArrayList.add(new WesternData("GEMINI","May-21","Jun-20","Air","Mutable","Mercury",
                "Light-Green,Yellow","Wednesday","5, 7, 14, 23","Emerald, Chrysoprase","Agate","Amethyst, Lodestone"));






        //6
        westernDataArrayList.add(new WesternData("CANCER",	"Jun-21",	"Jul-22",	"Water",	"Cardinal",	"Moon",
                "White",	"Monday, Thursday"	,"2, 3, 15, 20	","Pearl, Moonstone",	"Emerald","Pearl, Crystal, Sapphire, Carnelian "));

        //7
        westernDataArrayList.add(new WesternData("LEO",	"Jul-23",	"Aug-22",	"Fire",	"Fixed",	"Sun",
                "Gold, Yellow, Orange",	"Sunday"	,"1, 3, 10, 19","Ruby, Carnelian"	,	"Onyx","Topaz, Natural diamond"));

        //8
        westernDataArrayList.add(new WesternData(        "VIRGO",	"Aug-23"	,"Sep-22",	"Earth",	"Mutable",	"Mercury"	,
                "Grey, Beige, Pale-Yellow",	"Wednesday",	"5, 14, 15, 23, 32",	"Peridot, Sardonyx",	"Carnelian","Amethyst, Lodestone"
        ));

        //9
        westernDataArrayList.add(new WesternData("LIBRA",	"Sep-23"	,"Oct-22",	"Air",	"Cardinal",	"Venus",
                "Pink, Green	","Friday"	,"4, 6, 13, 15, 24","Sapphire, Lapis Lazuli",		"Chrysolite","Emerald, Cat's eye"));

        //10
        westernDataArrayList.add(new WesternData("SCORPIO",	"Oct-23",	"Nov-21",	"Water",	"Fixed",	"Pluto, Mars",
                "Scarlet, Red, Rust",	"Tuesday",	"8, 11, 18, 22","Opal"	,	"Beryl","Ruby, Emerald"));


        //11
        westernDataArrayList.add(new WesternData("SAGITTARIUS",	"Nov-2" ,"Dec-21",	"Fire",	"Mutable",	"Jupiter",
                "Blue",	"Thursday"	,"3, 7, 9, 12, 21	","Topaz, Citrine"	,"Topaz","Sapphire, Carnelian"));

        //12
        westernDataArrayList.add(new WesternData("CAPRICORN",	"Dec-22",	"Jan-19",	"Earth",	"Cardinal",	"Saturn",
                "Brown, Black",	"Saturday"	,"4, 8, 13, 22"	,"Tanzanite, Turquoise",	"Ruby","Turquoise, Natural diamond"));










        return westernDataArrayList;




    }


    public static ArrayList<ChineseData> chinesedata() {

        ArrayList<ChineseData> chineseDataArrayList = new ArrayList<>();

        //1
        chineseDataArrayList.add(new ChineseData("RAT","","","North, Northwest, Southwest","Mid-Winter","2,3",
                "5,9","Blue, Gold, Green","Yellow, Brown","South, Southeast","Lily, African Violet"));

        //2
        chineseDataArrayList.add(new ChineseData("OX","","","North, South","Late-Winter","1,4",
                "5,6","White, Yellow, Green","Blue","Southwest","Tulip, Peach Blossoms"));

        //3
        chineseDataArrayList.add(new ChineseData("TIGER","","","East, North, South","Early Spring","1,3,4",
                "6,7,8","Blue, Grey, Orange","Brown","Southwest","Yellow Lily, Cineraria"));

        //4
        chineseDataArrayList.add(new ChineseData("RABBIT","","","East, South, Northwest","Mid-Spring","3,4,6",
                "1,7,8","Pink, Red, Purple, Blue","Dark Brown, Dark Yellow, White","North, West, Southwest","Plantain Lily, Jasmine"));

        //5
        chineseDataArrayList.add(new ChineseData("DRAGON","","","East, North, South","Late Spring","1,6,7",
                "3,8","Gold, Silver, Greyish White","Blue, Green","Northwest","Bleeding-Heart glory bower, Dragon Flower"));

        //6
        chineseDataArrayList.add(new ChineseData("SNAKE","","","East, West, Southwest","Early Summer","2,8,9",
                "1,6,7","Black, Red, Yellow","Brown, White, Gold","Northeast, Northwest","Orchid, Cactus"));

        //7
        chineseDataArrayList.add(new ChineseData("HORSE","","","East, West, Southwest","Mid-Summer","2, 3, 7",
                "1, 5, 6","Green, Yellow","Blue, White","North, Northeast","Calla Lily, Jasmine"));


        //8
        chineseDataArrayList.add(new ChineseData("GOAT","","","North","Late Summer","2, 7",
                "4, 9","Brown, Red, Purple","Blue, Black","Southwest","Carnations, Primroses"));


        //9
        chineseDataArrayList.add(new ChineseData("MONKEY","","","North, Northwest, West","Early Autumn","4, 9",
                "2, 7","White, Blue, Gold","Red, Pink","South, Southeast","Chrysanthemum, Crape-myrtle"));


        //10
        chineseDataArrayList.add(new ChineseData("ROOSTER","","","South, Southeast","Mid-Autumn","5, 7, 8",
                "1, 3, 9","Gold, Brown, Yellow","Red","East","Gladiola, Cockscomb"));


        //11
        chineseDataArrayList.add(new ChineseData("DOG","","","East, South, Northeast","Late Autumn","3, 4, 9",
                "1, 6, 7","Green, Red, Purple","Blue, White, Gold","Southeast","Rose, Cymbidium Orchids"));


        //12
        chineseDataArrayList.add(new ChineseData("PIG","","","East, Southwest","Early Winter","2, 5, 8",
                "1, 7","Yellow, Grey, Brown, Gold","Red, Blue, Green","Southeast","Hydrangea, Daisy"));

        return chineseDataArrayList;
    }


    public void colorData(){

        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("Blue", "#0000ff");
        hmap.put("Gold", "#ffd700");
        hmap.put("Green", "#008000");
        hmap.put("White", "#000000");
        hmap.put("Yellow", "#ffff00");
        hmap.put("Grey", "#808080");
        hmap.put("Orange", "#FFA500");
        hmap.put("Pink", "#ffc0cb");
        hmap.put("Red", "#ff0000");
        hmap.put("Purple", "#800080");
        hmap.put("Silver", "#c0c0c0");
        hmap.put("Greyish", "#808080");
        hmap.put("Black", "#111111");
        hmap.put("Brown", "#A52A2A");

    }
    public void directionIconData(){

        HashMap<String, Integer> hmap = new HashMap<String, Integer>();
        hmap.put("North", R.drawable.ic_arrow_north);
        hmap.put("South",  R.drawable.ic_arrow_south);
        hmap.put("East",  R.drawable.ic_arrow_east);
        hmap.put("West",  R.drawable.ic_arrow_west);
    }


    public static ArrayList<BirthdayData>  birthDayData(){

        ArrayList<BirthdayData> birthdayDataArrayList = new ArrayList<>();

        //1
        birthdayDataArrayList.add(new BirthdayData("Feb 05 1924-Jan 23 1925",	"Yang Wood",	"甲",	"子" ,
                "Rat"	,"Feb 02 1984-Jan 21 1985"));

        //2
        birthdayDataArrayList.add(new BirthdayData(	"Jan 24 1925-Feb 12 1926",	"Yin Wood",	"乙",	"丑",
                "Ox",	"Jan 22 1985-Feb 08 1986"
        ));


        //3
        birthdayDataArrayList.add(new BirthdayData(	"Feb 13 1926-Feb 01 1927",	"Yang Fire",	"丙",	"寅",
                "Tiger",	"Feb 09 1986-Jan 28 1987"
        ));

        //4
        birthdayDataArrayList.add(new BirthdayData(	"Feb 02 1927-Jan 22 1928",	"Yin Fire",	"丁",	"卯",
                "Rabbit",	"Jan 29 1987-Feb 16 1988"
        ));

        //5
        birthdayDataArrayList.add(new BirthdayData(	"Jan 23 1928-Feb 09 1929",	"Yang Earth"	,"戊"	,"辰",
                "Dragon",	"Feb 17 1988-Jan 05 1989"
        ));

        //6
        birthdayDataArrayList.add(new BirthdayData(	"Feb 10 1929-Jan 29 1930"	,"Yin Earth"	,"己"	,"巳",
                "Snake",	"Feb 06 1989-Jan 26 1990"
        ));


        //7
        birthdayDataArrayList.add(new BirthdayData(	"Jan 30 1930-Feb 16 1931"	,"Yang Metal"	,"庚",	"午",
                "Horse",	"Jan 27 1990-Feb 14 1991"
        ));

        //8
        birthdayDataArrayList.add(new BirthdayData(	"Feb 17 1931-Feb 05 1932",	"Yin Metal",	"辛",	"未",
                "Goat",	"Feb 15 1991-Feb 03 1992"
        ));

        //9
        birthdayDataArrayList.add(new BirthdayData(	"Feb 06 1932-Jan 25 1933",	"Yang Water",	"壬",	"申",
                "Monkey",	"Feb 04 1992-Jan 22 1993"
        ));

        //10
        birthdayDataArrayList.add(new BirthdayData(	"Jan 26 1933-Feb 13 1934",	"Yin Water",	"癸",	"酉",
                "Rooster",	"Jan 23 1993-Feb 09 1994"
        ));

        //11
        birthdayDataArrayList.add(new BirthdayData(	"Feb 14 1934-Feb 03 1935",	"Yang Wood",	"甲",	"戌",
                "Dog"	,"Feb 10 1994-Jan 30 1995"
        ));

        //12
        birthdayDataArrayList.add(new BirthdayData(	"Feb 04 1935-Jan 23 1936",	"Yin Wood",	"乙",	"亥",
                "Pig",	"Jan 31 1995-Feb 18 1996"
        ));






        birthdayDataArrayList.add(new BirthdayData(	"Jan 24 1936-Feb 10 1937",	"Yang Fire",	"丙",	"子",
                "Rat",	"Feb 19 1996-Feb 06 1997"));


        birthdayDataArrayList.add(new BirthdayData("Feb 11 1937-Jan 30 1938",	"Yin Fire",	"丁",	"丑",
                "Ox",	"Feb 07 1997-Jan 27 1998"));
        birthdayDataArrayList.add(new BirthdayData("Jan 31 1938-Feb 18 1939",	"Yang Earth",	"戊",	"寅",
                "Tiger"	,"Jan 28 1998-Feb 15 1999"));
        birthdayDataArrayList.add(new BirthdayData("Feb 19 1939-Feb 07 1940",	"Yin Earth",	"己",	"卯",
                "Rabbit",	"Feb 16 1999-Feb 04 2000"));
        birthdayDataArrayList.add(new BirthdayData("Feb 08 1940-Jan 26 1941",	"Yang Metal",	"庚",	"辰",
                "Dragon",	"Feb 05 2000-Jan 23 2001"));
        birthdayDataArrayList.add(new BirthdayData("Jan 27 1941-Feb 14 1942",	"Yin Metal",	"辛",	"巳",
                "Snake",	"Jan 24 2001-Feb 11 2002"));


        birthdayDataArrayList.add(new BirthdayData("Feb 15 194-Feb 04 1943",	"Yang Water",	"壬",	"午",
                "Horse",	"Feb 12 2002-Jan 31 2003"));
//        birthdayDataArrayList.add(new BirthdayData("Feb 05 1943-Jan 24 1944",	"Yin Water",	"癸",	"未",
//                "Goat",	"Feb 01 2003-Jan 21 2004"));
        birthdayDataArrayList.add(new BirthdayData("Feb 05 1943-Jan 24 1944",	"Yin Water",	"癸",	"未",
                "Goat",	"Feb 01 2003-Jan 21 2004"));
        birthdayDataArrayList.add(new BirthdayData("Jan 25 1944-Feb 12 1945",	"Yang Wood",	"甲",	"申",
                "Monkey",	"Jan 22 2004-Feb 08 2005"));
        birthdayDataArrayList.add(new BirthdayData("Feb 13 1945-Feb 01 1946",	"Yin Wood",	"乙",	"酉",
                "Rooster",	"Feb 09 2005-Jan 28 2006"));
        birthdayDataArrayList.add(new BirthdayData("Feb 02 1946-Jan 21 1947",	"Yang Fire",	"丙",	"戌",
                "Dog",	"Jan 29 2006-Feb 17 2007"));
        birthdayDataArrayList.add(new BirthdayData("Jan 22 1947-Feb 09 1948",	"Yin Fire",	"丁",	"亥",
                "Pig",	"Feb 18 2007-Feb 06 2008"));






        birthdayDataArrayList.add(new BirthdayData("Feb 10 1948-Jan 28 1949",	"Yang Earth",	"戊",	"子",
                "Rat",	"Feb 07 2008-Jan 25 2009"));
        birthdayDataArrayList.add(new BirthdayData("Jan 29 1949-Feb 16 1950",	"Yin Earth",	"己",	"丑",
                "Ox",	"Jan 26 2009-Feb 13 2010"));
        birthdayDataArrayList.add(new BirthdayData("Feb 17 1950-Feb 05 1951",	"Yang Metal",	"庚",	"寅",
                "Tiger",	"Feb 14 2010-Feb 02 2011"));
        birthdayDataArrayList.add(new BirthdayData("Feb 06 1951-Jan 26 1952",	"Yin Metal",	"辛",	"卯",
                "Rabbit",	"Feb 03 2011-Jan 22 2012"));
        birthdayDataArrayList.add(new BirthdayData("Jan 27 1952-Feb 13 1953",	"Yang Water",	"壬",	"辰",
                "Dragon",	"Jan 23 2012-Feb 09 2013"));

        birthdayDataArrayList.add(new BirthdayData("Feb 14 1953-Feb 02 1954",	"Yin Water",	"癸",	"巳",
                "Snake",	"Feb 10 2013-Jan 30 2014"));

        birthdayDataArrayList.add(new BirthdayData("Feb 03 1954-Jan 23 1955",	"Yang Wood",	"甲",	"午",
                "Horse",	"Jan 31 2014-Feb 18 2015"));

        birthdayDataArrayList.add(new BirthdayData("Jan 24 1955-Feb 11 1956",	"Yin Wood",	"乙",	"未",
                "Goat",	"Feb 19 2015-Feb 07 2016"));

        birthdayDataArrayList.add(new BirthdayData("Feb 12 1956-Jan 30 1957",	"Yang Fire",	"丙",	"申",
                "Monkey",	"Feb 08 2016-Jan 27 2017"));





        birthdayDataArrayList.add(new BirthdayData("Jan 31 1957-Feb 17 1958",	"Yin Fire",	"丁",	"酉",
                "Rooster",	"Jan 28 2017-Feb 15 2018"));
        birthdayDataArrayList.add(new BirthdayData("Feb 18 1958-Feb 07 1959",	"Yang Earth",	"戊",	"戌",
                "Dog",	"Feb 16 2018-Feb 04 2019"));
        birthdayDataArrayList.add(new BirthdayData("Feb 08 1959-Jan 27 1960",	"Yin Earth",	"己",	"亥",
                "Pig",	"Feb 05 2019-Jan 24 2020"));


        birthdayDataArrayList.add(new BirthdayData("Jan 28 1960-Feb 14 1961",	"Yang Metal",	"庚",	"子",
                "Rat",	"Jan 25 2020-Feb 11 2021"));
        birthdayDataArrayList.add(new BirthdayData("Feb 15 1961-Feb 04 1962",	"Yin Metal",	"辛",	"丑",
                "Ox",	"Feb 12 2021-Jan 31 2022"));
        birthdayDataArrayList.add(new BirthdayData("Feb 05 1962-Jan 24 1963",	"Yang Water",	"壬",	"寅",
                "Tiger",	"Feb 01 2022-Jan 21 2023"));
        birthdayDataArrayList.add(new BirthdayData("Jan 25 1963-Feb 12 1964",	"Yin Water",	"癸",	"卯",
                "Rabbit",	"Jan 22 2023-Feb 09 2024"));
        birthdayDataArrayList.add(new BirthdayData("Feb 13 1964-Feb 01 1965",	"Yang Wood",	"甲",	"辰",
                "Dragon",	"Feb 10 2024-Jan 28 2025"));
        birthdayDataArrayList.add(new BirthdayData("Feb 02 1965-Jan 20 1966",	"Yin Wood",	"乙",	"巳",
                "Snake",	"Jan 29 2025-Feb 16 2026"));
        birthdayDataArrayList.add(new BirthdayData("Jan 21 1966-Feb 08 1967",	"Yang Fire",	"丙",	"午",
                "Horse",	"Feb 17 2026-Feb 05 2027"));
        birthdayDataArrayList.add(new BirthdayData("Feb 09 1967-Jan 29 1968",	"Yin Fire",	"丁",	"未",
                "Goat",	"Feb 06 2027-Jan 25 2028"));
        birthdayDataArrayList.add(new BirthdayData("Jan 30 1968-Feb 16 1969",	"Yang Earth",	"戊",	"申",
                "Monkey",	"Jan 26 2028-Feb 12 2029"));
        birthdayDataArrayList.add(new BirthdayData("Feb 17 1969-Feb 05 1970",	"Yin Earth",	"己",	"酉",
                "Rooster",	"Feb 13 2029-Feb 02 2030"));
        birthdayDataArrayList.add(new BirthdayData("Feb 06 1970-Jan 26 1971",	"Yang Metal",	"庚",	"戌",
                "Dog",	"Feb 03 2030-Jan 22 2031"));

        birthdayDataArrayList.add(new BirthdayData("Jan 27 1971-Feb 14 1972",	"Yin Metal",	"辛",	"亥",
                "Pig",	"Jan 23 2031-Feb 10 2032"));


        birthdayDataArrayList.add(new BirthdayData("Feb 15 1972-Feb 02 1973",	"Yang Water",	"壬",	"子",
                "Rat",	"Feb 11 2032-Jan 30 2033"));
        birthdayDataArrayList.add(new BirthdayData("Feb 03 1973-Jan 22 1974",	"Yin Water",	"癸",	"丑",
                "Ox",	"Jan 31 2033-Feb 18 2034"));
        birthdayDataArrayList.add(new BirthdayData("Jan 23 1974-Feb 10 1975",	"Yang Wood",	"甲",	"寅",
                "Tiger",	"Feb 19 2034-Feb 07 2035"));
        birthdayDataArrayList.add(new BirthdayData("Feb 11 1975-Jan 30 1976",	"Yin Wood",	"乙",	"卯",
                "Rabbit",	"Feb 08 2035-Jan 27 2036"));
        birthdayDataArrayList.add(new BirthdayData("Jan 31 1976-Feb 17 1977",	"Yang Fire",	"丙",	"辰",
                "Dragon",	"Jan 28 2036-Feb 14 2037"));
        birthdayDataArrayList.add(new BirthdayData("Feb 18 1977-Feb 06 1978",	"Yin Fire",	"丁",	"巳",
                "Snake",	"Feb 15 2037-Feb 03 2038"));
        birthdayDataArrayList.add(new BirthdayData("Feb 07 1978-Jan 27 1979",	"Yang Earth",	"戊",	"午",
                "Horse",	"Feb 04 2038-Jan 23 2039"));
        birthdayDataArrayList.add(new BirthdayData("Jan 28 1979-Feb 15 1980",	"Yin Earth",	"己",	"未",
                "Goat",	"Jan 24 2039-Feb 11 2040"));
        birthdayDataArrayList.add(new BirthdayData("Feb 16 1980-Feb 04 1981",	"Yang Metal",	"庚",	"申",
                "Monkey",	"Feb 12 2040-Jan 31 2041"));
        birthdayDataArrayList.add(new BirthdayData("Feb 05 1981-Jan 24 1982",	"Yin Metal",	"辛",	"酉",
                "Rooster",	"Feb 01 2041-Jan 21 2042"));
        birthdayDataArrayList.add(new BirthdayData("Jan 25 1982-Feb 12 1983",	"Yang Water",	"壬",	"戌",
                "Dog",	"Jan 22 2042-Feb 09 2043"));
        birthdayDataArrayList.add(new BirthdayData("Feb 13 1983-Feb 01 1984",	"Yin Water",	"癸",	"亥",
                "Pig",	"Feb 10 2043-Jan 29 2044"));



        //Feb 06 1989–Jan 26 1990


        ///////////////////next 60
        //1
        birthdayDataArrayList.add(new BirthdayData("Feb 05 1924-Jan 23 1925",	"Yang Wood",	"甲",	"子" ,
                "Rat"	,"Feb 05 1924-Jan 23 1925"));

        //2
        birthdayDataArrayList.add(new BirthdayData(	"Jan 24 1925-Feb 12 1926",	"Yin Wood",	"乙",	"丑",
                "Ox",	"Jan 24 1925-Feb 12 1926"
        ));


        //3
        birthdayDataArrayList.add(new BirthdayData(	"Feb 13 1926-Feb 01 1927",	"Yang Fire",	"丙",	"寅",
                "Tiger",	"Feb 13 1926-Feb 01 1927"
        ));

        //4
        birthdayDataArrayList.add(new BirthdayData(	"Feb 02 1927-Jan 22 1928",	"Yin Fire",	"丁",	"卯",
                "Rabbit",	"Feb 02 1927-Jan 22 1928"
        ));

        //5
        birthdayDataArrayList.add(new BirthdayData(	"Jan 23 1928-Feb 09 1929",	"Yang Earth"	,"戊"	,"辰",
                "Dragon",	"Jan 23 1928-Feb 09 1929"
        ));

        //6
        birthdayDataArrayList.add(new BirthdayData(	"Feb 10 1929-Jan 29 1930"	,"Yin Earth"	,"己"	,"巳",
                "Snake",	"Feb 10 1929-Jan 29 1930"
        ));


        //7
        birthdayDataArrayList.add(new BirthdayData(	"Jan 30 1930-Feb 16 1931"	,"Yang Metal"	,"庚",	"午",
                "Horse",	"Jan 30 1930-Feb 16 1931"
        ));

        //8
        birthdayDataArrayList.add(new BirthdayData(	"Feb 17 1931-Feb 05 1932",	"Yin Metal",	"辛",	"未",
                "Goat",	"Feb 17 1931-Feb 05 1932"
        ));

        //9
        birthdayDataArrayList.add(new BirthdayData(	"Feb 06 1932-Jan 25 1933",	"Yang Water",	"壬",	"申",
                "Monkey",	"Feb 06 1932-Jan 25 1933"
        ));

        //10
        birthdayDataArrayList.add(new BirthdayData(	"Jan 26 1933-Feb 13 1934",	"Yin Water",	"癸",	"酉",
                "Rooster",	"Jan 26 1933-Feb 13 1934"
        ));

        //11
        birthdayDataArrayList.add(new BirthdayData(	"Feb 14 1934-Feb 03 1935",	"Yang Wood",	"甲",	"戌",
                "Dog"	,"Feb 14 1934-Feb 03 1935"
        ));

        //12
        birthdayDataArrayList.add(new BirthdayData(	"Feb 04 1935-Jan 23 1936",	"Yin Wood",	"乙",	"亥",
                "Pig",	"Feb 04 1935-Jan 23 1936"
        ));






        birthdayDataArrayList.add(new BirthdayData(	"Jan 24 1936-Feb 10 1937",	"Yang Fire",	"丙",	"子",
                "Rat",	"Jan 24 1936-Feb 10 1937"));


        birthdayDataArrayList.add(new BirthdayData("Feb 11 1937-Jan 30 1938",	"Yin Fire",	"丁",	"丑",
                "Ox",	"Feb 11 1937-Jan 30 1938"));
        birthdayDataArrayList.add(new BirthdayData("Jan 31 1938-Feb 18 1939",	"Yang Earth",	"戊",	"寅",
                "Tiger"	,"Jan 31 1938-Feb 18 1939"));
        birthdayDataArrayList.add(new BirthdayData("Feb 19 1939-Feb 07 1940",	"Yin Earth",	"己",	"卯",
                "Rabbit",	"Feb 19 1939-Feb 07 1940"));
        birthdayDataArrayList.add(new BirthdayData("Feb 08 1940-Jan 26 1941",	"Yang Metal",	"庚",	"辰",
                "Dragon",	"Feb 08 1940-Jan 26 1941"));
        birthdayDataArrayList.add(new BirthdayData("Jan 27 1941-Feb 14 1942",	"Yin Metal",	"辛",	"巳",
                "Snake",	"Jan 27 1941-Feb 14 1942"));


        birthdayDataArrayList.add(new BirthdayData("Feb 15 1942-Feb 04 1943",	"Yang Water",	"壬",	"午",
                "Horse",	"Feb 15 1942-Feb 04 1943"));
        birthdayDataArrayList.add(new BirthdayData("Feb 05 1943-Jan 24 1944",	"Yin Water",	"癸",	"未",
                "Goat",	"Feb 05 1943-Jan 24 1944"));
//        birthdayDataArrayList.add(new BirthdayData("Feb 05 1943-Jan 24 1944",	"Yin Water",	"癸",	"未",
//                "Goat",	"Feb 05 1943-Jan 24 1944"));
        birthdayDataArrayList.add(new BirthdayData("Jan 25 1944-Feb 12 1945",	"Yang Wood",	"甲",	"申",
                "Monkey",	"Jan 25 1944-Feb 12 1945"));
        birthdayDataArrayList.add(new BirthdayData("Feb 13 1945-Feb 01 1946",	"Yin Wood",	"乙",	"酉",
                "Rooster",	"Feb 13 1945-Feb 01 1946"));
        birthdayDataArrayList.add(new BirthdayData("Feb 02 1946-Jan 21 1947",	"Yang Fire",	"丙",	"戌",
                "Dog",	"Feb 02 1946-Jan 21 1947"));
        birthdayDataArrayList.add(new BirthdayData("Jan 22 1947-Feb 09 1948",	"Yin Fire",	"丁",	"亥",
                "Pig",	"Jan 22 1947-Feb 09 1948"));






        birthdayDataArrayList.add(new BirthdayData("Feb 10 1948-Jan 28 1949",	"Yang Earth",	"戊",	"子",
                "Rat",	"Feb 10 1948-Jan 28 1949"));
        birthdayDataArrayList.add(new BirthdayData("Jan 29 1949-Feb 16 1950",	"Yin Earth",	"己",	"丑",
                "Ox",	"Jan 29 1949-Feb 16 1950"));
        birthdayDataArrayList.add(new BirthdayData("Feb 17 1950-Feb 05 1951",	"Yang Metal",	"庚",	"寅",
                "Tiger",	"Feb 17 1950-Feb 05 1951"));
        birthdayDataArrayList.add(new BirthdayData("Feb 06 1951-Jan 26 1952",	"Yin Metal",	"辛",	"卯",
                "Rabbit",	"Feb 06 1951-Jan 26 1952"));
        birthdayDataArrayList.add(new BirthdayData("Jan 27 1952-Feb 13 1953",	"Yang Water",	"壬",	"辰",
                "Dragon",	"Jan 27 1952-Feb 13 1953"));

        birthdayDataArrayList.add(new BirthdayData("Feb 14 1953-Feb 02 1954",	"Yin Water",	"癸",	"巳",
                "Snake",	"Feb 14 1953-Feb 02 1954"));

        birthdayDataArrayList.add(new BirthdayData("Feb 03 1954-Jan 23 1955",	"Yang Wood",	"甲",	"午",
                "Horse",	"Feb 03 1954-Jan 23 1955"));

        birthdayDataArrayList.add(new BirthdayData("Jan 24 1955-Feb 11 1956",	"Yin Wood",	"乙",	"未",
                "Goat",	"Jan 24 1955-Feb 11 1956"));

        birthdayDataArrayList.add(new BirthdayData("Feb 12 1956-Jan 30 1957",	"Yang Fire",	"丙",	"申",
                "Monkey",	"Feb 12 1956-Jan 30 1957"));





        birthdayDataArrayList.add(new BirthdayData("Jan 31 1957-Feb 17 1958",	"Yin Fire",	"丁",	"酉",
                "Rooster",	"Jan 31 1957-Feb 17 1958"));
        birthdayDataArrayList.add(new BirthdayData("Feb 18 1958-Feb 07 1959",	"Yang Earth",	"戊",	"戌",
                "Dog",	"Feb 18 1958-Feb 07 1959"));
        birthdayDataArrayList.add(new BirthdayData("Feb 08 1959-Jan 27 1960",	"Yin Earth",	"己",	"亥",
                "Pig",	"Feb 08 1959-Jan 27 1960"));


        birthdayDataArrayList.add(new BirthdayData("Jan 28 1960-Feb 14 1961",	"Yang Metal",	"庚",	"子",
                "Rat",	"Jan 28 1960-Feb 14 1961"));
        birthdayDataArrayList.add(new BirthdayData("Feb 15 1961-Feb 04 1962",	"Yin Metal",	"辛",	"丑",
                "Ox",	"Feb 15 1961-Feb 04 1962"));
        birthdayDataArrayList.add(new BirthdayData("Feb 05 1962-Jan 24 1963",	"Yang Water",	"壬",	"寅",
                "Tiger",	"Feb 05 1962-Jan 24 1963"));
        birthdayDataArrayList.add(new BirthdayData("Jan 25 1963-Feb 12 1964",	"Yin Water",	"癸",	"卯",
                "Rabbit",	"Jan 25 1963-Feb 12 1964"));
        birthdayDataArrayList.add(new BirthdayData("Feb 13 1964-Feb 01 1965",	"Yang Wood",	"甲",	"辰",
                "Dragon",	"Feb 13 1964-Feb 01 1965"));
        birthdayDataArrayList.add(new BirthdayData("Feb 02 1965-Jan 20 1966",	"Yin Wood",	"乙",	"巳",
                "Snake",	"Feb 02 1965-Jan 20 1966"));
        birthdayDataArrayList.add(new BirthdayData("Jan 21 1966-Feb 08 1967",	"Yang Fire",	"丙",	"午",
                "Horse",	"Jan 21 1966-Feb 08 1967"));
        birthdayDataArrayList.add(new BirthdayData("Feb 09 1967-Jan 29 1968",	"Yin Fire",	"丁",	"未",
                "Goat",	"Feb 09 1967-Jan 29 1968"));
        birthdayDataArrayList.add(new BirthdayData("Jan 30 1968-Feb 16 1969",	"Yang Earth",	"戊",	"申",
                "Monkey",	"Jan 30 1968-Feb 16 1969"));
        birthdayDataArrayList.add(new BirthdayData("Feb 17 1969-Feb 05 1970",	"Yin Earth",	"己",	"酉",
                "Rooster",	"Feb 17 1969-Feb 05 1970"));
        birthdayDataArrayList.add(new BirthdayData("Feb 06 1970-Jan 26 1971",	"Yang Metal",	"庚",	"戌",
                "Dog",	"Feb 06 1970-Jan 26 1971"));

        birthdayDataArrayList.add(new BirthdayData("Jan 27 1971-Feb 14 1972",	"Yin Metal",	"辛",	"亥",
                "Pig",	"Jan 27 1971-Feb 14 1972"));


        birthdayDataArrayList.add(new BirthdayData("Feb 15 1972-Feb 02 1973",	"Yang Water",	"壬",	"子",
                "Rat",	"Feb 15 1972-Feb 02 1973"));
        birthdayDataArrayList.add(new BirthdayData("Feb 03 1973-Jan 22 1974",	"Yin Water",	"癸",	"丑",
                "Ox",	"Feb 03 1973-Jan 22 1974"));
        birthdayDataArrayList.add(new BirthdayData("Jan 23 1974-Feb 10 1975",	"Yang Wood",	"甲",	"寅",
                "Tiger",	"Jan 23 1974-Feb 10 1975"));
        birthdayDataArrayList.add(new BirthdayData("Feb 11 1975-Jan 30 1976",	"Yin Wood",	"乙",	"卯",
                "Rabbit",	"Feb 11 1975-Jan 30 1976"));
        birthdayDataArrayList.add(new BirthdayData("Jan 31 1976-Feb 17 1977",	"Yang Fire",	"丙",	"辰",
                "Dragon",	"Jan 31 1976-Feb 17 1977"));
        birthdayDataArrayList.add(new BirthdayData("Feb 18 1977-Feb 06 1978",	"Yin Fire",	"丁",	"巳",
                "Snake",	"Feb 18 1977-Feb 06 1978"));
        birthdayDataArrayList.add(new BirthdayData("Feb 07 1978-Jan 27 1979",	"Yang Earth",	"戊",	"午",
                "Horse",	"Feb 07 1978-Jan 27 1979"));
        birthdayDataArrayList.add(new BirthdayData("Jan 28 1979-Feb 15 1980",	"Yin Earth",	"己",	"未",
                "Goat",	"Jan 28 1979-Feb 15 1980"));
        birthdayDataArrayList.add(new BirthdayData("Feb 16 1980-Feb 04 1981",	"Yang Metal",	"庚",	"申",
                "Monkey",	"Feb 16 1980-Feb 04 1981"));
        birthdayDataArrayList.add(new BirthdayData("Feb 05 1981-Jan 24 1982",	"Yin Metal",	"辛",	"酉",
                "Rooster",	"Feb 05 1981-Jan 24 1982"));
        birthdayDataArrayList.add(new BirthdayData("Jan 25 1982-Feb 12 1983",	"Yang Water",	"壬",	"戌",
                "Dog",	"Jan 25 1982-Feb 12 1983"));
        birthdayDataArrayList.add(new BirthdayData("Feb 13 1983-Feb 01 1984",	"Yin Water",	"癸",	"亥",
                "Pig",	"Feb 13 1983-Feb 01 1984"));









        return birthdayDataArrayList;
    }



}