package com.alphaa.inzodiac.staticdata;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphaa.inzodiac.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SetChineseWesternZodaic {
    static String TAG = "SetChineseWesternZodaic";


    public  static HashMap<String,Integer> hashMapChinese;
     public  static Date strDobDate,strStartYear,strendYear;

    static ChineseData chineseData;
     public  static String getasociated;


    //public  static HashMap<String,Integer> hashMapChinese;

    ////******************************chinese zodaic sign***************starts

    public static void setAllDataChinese(String userbirthdate, ImageView imageView, TextView textView){

        ArrayList<ChineseData> chineseDataArrayList = ZodaicActivity.chinesedata();

        try {
            getasociated = birthDateMatch(userbirthdate);
        }catch (Exception e){

        }

        Log.e(TAG, "birthDayData: getasco  7777  "+getasociated );

        ///set zodiac icon
        chineseIconData();

        if (getasociated!=null&&!getasociated.matches("no")){

            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);

            imageView.setImageResource(hashMapChinese.get(getasociated));
            textView.setText(getasociated);

            int index = 0;
            for(int i = 0; i<chineseDataArrayList.size(); i++){
                if(chineseDataArrayList.get(i).getChineseZodaic().matches(getasociated.toUpperCase())){
                    index = i;
                    break;
                }
            }

            Log.e(TAG, "birthDayData: indexxx 7777 "+index );

            chineseData = chineseDataArrayList.get(index);

        }else {
            Log.e(TAG, "setAllDataChinese: index is not foundd111111111" );
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }

    }


    public static void chineseIconData(){
        //int drawableint;
        hashMapChinese = new HashMap<String, Integer>();
        hashMapChinese.put("Rat", R.drawable.ic_rat_white);
        hashMapChinese.put("Ox",  R.drawable.ic_ox_white);
        hashMapChinese.put("Tiger",  R.drawable.ic_tiger_white);
        hashMapChinese.put("Rabbit",  R.drawable.ic_rabbit_white);
        hashMapChinese.put("Dragon", R.drawable.ic_dragon_white);
        hashMapChinese.put("Snake",  R.drawable.ic_snake_white);
        hashMapChinese.put("Goat",  R.drawable.ic_goat_white);
        hashMapChinese.put("Monkey",  R.drawable.ic_monkey_white);
        hashMapChinese.put("Rooster", R.drawable.ic_rooster_white);
        hashMapChinese.put("Dog",  R.drawable.ic_dog_white);
        hashMapChinese.put("Pig",  R.drawable.ic_pig_white);
        hashMapChinese.put("Horse",  R.drawable.ic_horse_white);

    }
    public static String birthDateMatch(String dateofbirth){

        String asociateAnimal = "no";

        ArrayList<BirthdayData> birthdayDataArrayList = ZodaicActivity.birthDayData();

        for (int i = 0; i <birthdayDataArrayList.size(); i++) {

            String birthdayData = birthdayDataArrayList.get(i).getYearEnd();


            String date_1984_2043 = birthdayData;



            //split date
            String[] datearray =date_1984_2043.split("-");


            String startdate = datearray[0];
            String enddate = datearray[1];




            //convert dob to date object
            //birth date match  ///2021/02/10


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            try {
                strDobDate = sdf.parse(dateofbirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }



            //convert startdate to date object
            SimpleDateFormat sdfstart1 = new SimpleDateFormat("MMM dd yyyy");
            try {

                strStartYear = sdfstart1.parse(startdate);

            } catch (ParseException e) {

                e.printStackTrace();
            }


            //convert end date to dateobject
            SimpleDateFormat sdfend = new SimpleDateFormat("MMM dd yyyy");
            try {
                strendYear = sdfend.parse(enddate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            try {


               /* if (strDobDate.compareTo(strStartYear) > 0 && strDobDate.compareTo(strendYear) < 0) {
                    ///System.out.println("Date 1 occurs after Date 2");
                    Log.e(TAG, i + " birthDateMatch: match111 yesssss");
                    asociateAnimal = birthdayDataArrayList.get(i).getAnimal();
                    break;
                } else {
                    Log.e(TAG, "birthDateMatch: match111  noooo");
                    //System.out.println("Date 1 occurs before Date 2");
                }
*/

                if (
                        (strDobDate.compareTo(strStartYear)==0&&strDobDate.compareTo(strendYear) < 0)||
                                (strDobDate.compareTo(strStartYear)>0&&strDobDate.compareTo(strendYear) == 0)||
                                (strDobDate.compareTo(strStartYear) > 0 && strDobDate.compareTo(strendYear) < 0)
                ){

                    asociateAnimal = birthdayDataArrayList.get(i).getAnimal();
                    break;
                } else {

                    //System.out.println("Date 1 occurs before Date 2");
                }








            }catch (Exception e){

            }
        }

        return asociateAnimal;

    }
    ////******************************chinese zodaic sign***************ends




























    //====================================================================================



    /////////////////////////////////////////

    public static String birthDateMatchWithWestern(String dateofbirth){


        Date strDobDateWestern=null,strendYearWestern = null,strStartYearWestern=null;
        String asociateZodaic = "no";

        ArrayList<WesternData> birthdayDataArrayList = ZodaicActivity.westerdata();

        for (int i = 0; i <birthdayDataArrayList.size(); i++) {

            //String birthdayData = birthdayDataArrayList.get(i).getYearEnd();

            //Log.e(TAG, "birthDateMatch: 1111111   "+birthdayData );

            //String date_1984_2043 = birthdayData;

            Log.e(TAG, "birthDateMatch: iiiiiiiiiiiiii   "+i );

            //split date
            String[] datearray =dateofbirth.split("/");

            //Log.e(TAG, "birthDateMatch: datearray "+datearray.length );

            String getyear = datearray[0];

            //String enddate = datearray[1];

            String startdate =  birthdayDataArrayList.get(i).getDateRange()+"-"+getyear;//datearray[0];
            String enddate = birthdayDataArrayList.get(i).getDateEnd()+"-"+getyear;//datearray[1];

            //String startdate = "1982/12/23"; //birthdayDataArrayList.get(i).getDateRange()+"-"+getyear;//datearray[0];
            //String enddate = "1983/01/1";//birthdayDataArrayList.get(i).getDateEnd()+"-"+getyear;//datearray[1];




            //convert dob to date object
            //birth date match  ///2021/02/10

            //dateofbirth = "1982/12/24";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            try {
                strDobDateWestern = sdf.parse(dateofbirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }



            //convert startdate to date object
            SimpleDateFormat sdfstart1 = new SimpleDateFormat("MMM-dd-yyyy");
            try {

                strStartYearWestern = sdfstart1.parse(startdate);

            } catch (ParseException e) {

                e.printStackTrace();
            }



            //convert end date to dateobject
            SimpleDateFormat sdfend = new SimpleDateFormat("MMM-dd-yyyy");
            try {
                strendYearWestern = sdfend.parse(enddate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {

                if (strDobDateWestern.getTime()>=strStartYearWestern.getTime()&&strDobDateWestern.getTime()<=strendYearWestern.getTime()){

                }



                if (
                        (strDobDateWestern.compareTo(strStartYearWestern)==0&&strDobDateWestern.compareTo(strendYearWestern) < 0)||
                        (strDobDateWestern.compareTo(strStartYearWestern)>0&&strDobDateWestern.compareTo(strendYearWestern) == 0)||
                        (strDobDateWestern.compareTo(strStartYearWestern) > 0 && strDobDateWestern.compareTo(strendYearWestern) < 0)
                ){
                    asociateZodaic = birthdayDataArrayList.get(i).getWesternZodiac();
                    break;
                } else {

                    //System.out.println("Date 1 occurs before Date 2");
                }

                /*if (strDobDateWestern.compareTo(strStartYearWestern) > 0 && strDobDateWestern.compareTo(strendYearWestern) < 0) {
                    ///System.out.println("Date 1 occurs after Date 2");
                    Log.e(TAG, i + " birthDateMatchWithWestern: match111 yesssss");
                    asociateZodaic = birthdayDataArrayList.get(i).getWesternZodiac();
                    break;
                } else {
                    Log.e(TAG, "birthDateMatchWithWestern: match111  noooo");
                    //System.out.println("Date 1 occurs before Date 2");
                }*/
            }catch (Exception e){

            }
        }
        Log.e(TAG, "birthDateMatchWithWestern: asociated animal "+asociateZodaic );

        return asociateZodaic;

    }


}
