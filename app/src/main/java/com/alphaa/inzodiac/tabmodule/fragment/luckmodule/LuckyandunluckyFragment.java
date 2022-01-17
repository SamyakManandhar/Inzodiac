package com.alphaa.inzodiac.tabmodule.fragment.luckmodule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseFragment;
import com.alphaa.inzodiac.databinding.ActivityLuckyandunlucky1Binding;
import com.alphaa.inzodiac.databinding.ActivityLuckyandunluckyBinding;
import com.alphaa.inzodiac.lucky_unlucky_adapter.LuckyColorsAdapter;
import com.alphaa.inzodiac.lucky_unlucky_adapter.LuckyDirectionAdapter;
import com.alphaa.inzodiac.lucky_unlucky_adapter.LuckyNumberAdapter;
import com.alphaa.inzodiac.lucky_unlucky_adapter.UnLuckyNumberAdapter;
import com.alphaa.inzodiac.staticdata.BirthdayData;
import com.alphaa.inzodiac.staticdata.ChineseData;
import com.alphaa.inzodiac.staticdata.SetChineseWesternZodaic;
import com.alphaa.inzodiac.staticdata.WesternData;
import com.alphaa.inzodiac.staticdata.ZodaicActivity;
import com.alphaa.inzodiac.tabmodule.fragment.horomonthmodule.HoroMonthActivity;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LuckyandunluckyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LuckyandunluckyFragment extends BaseFragment {
    String TAG = getClass().getSimpleName();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ActivityLuckyandunlucky1Binding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //ArrayList<ChineseData> chineseDataArrayList = new ArrayList<>();

    ChineseData  chineseData ;
    List<String> zodaiclist;


    //ArrayList<BirthdayData> birthdayDataArrayList = new ArrayList<>();

    String dob;
    Date strDobDate,strStartYear,strendYear;


    HashMap<String,Integer> hashMapChinese;


    ///////////wester data
    //ArrayList<WesternData> westernDataArrayList = new ArrayList<>();
    WesternData westernData;

    String asociateAnimal = "no",asociateElement="";


    HashMap<String,Integer> hashMapWestern;






    LinearLayout ll_western_header,ll_chinese_header;
    String gethoroscopetype;

    public LuckyandunluckyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LuckyandunluckyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LuckyandunluckyFragment newInstance(String param1, String param2) {
        LuckyandunluckyFragment fragment = new LuckyandunluckyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_luckyandunlucky1 , container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.update.setOnClickListener(v -> {
            //binding.frame1.setVisibility(View.VISIBLE);
            //binding.rl.setVisibility(View.GONE);


            //HoroMonthActivity filterFragment = HoroMonthActivity.newInstance("", "");
            //replaceFragment(filterFragment, R.id.frame, true);


            AppCompatActivity activity = (AppCompatActivity) view.getContext();

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, new HoroMonthActivity())
                    .addToBackStack(null)
                    .commit();



        });

        Log.e(TAG, "onViewCreated: dob "+ AppSession.getStringPreferences(getContext(), Constants.USERBIRTHDATE));


        dob = AppSession.getStringPreferences(getContext(), Constants.USERBIRTHDATE);

        //Log.e(TAG, "onViewCreated: preffered type  "+AppSession.getStringPreferences(getApplicationContext(),Constants) );








        /////////////////bundel data
        String bundelstring = this.getArguments().getString("message");

        Log.e(TAG, "onViewCreated: bundel "+bundelstring );
        ///set zodiac icon
        chineseIconData();

        try {
            binding.ivZodaicChinese.setImageResource(hashMapChinese.get(bundelstring));
            binding.tvZodaicChinese.setText(bundelstring);
        }catch (Exception e){

        }


        if (dob.isEmpty()){

        }else {
            setAllDataChinese(dob);
        }


        //setAllDataChinese("1984/3/9");

        //setAllDataChinese("2020/12/26");

        //set western data=======================================start

        //



        binding.llChineseData.setVisibility(View.GONE);
        binding.llWesternData.setVisibility(View.VISIBLE);

        binding.ivZodaicChinese.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorGray1));
        binding.tvZodaicChinese.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorGray1));



        //set western
        if (dob!=null&&!dob.isEmpty()) {

            westernIconData();
            String strwesternzodaic = SetChineseWesternZodaic.birthDateMatchWithWestern(dob);
            Log.e(TAG, "onViewCreated: strwesternzodaic  "+strwesternzodaic );


            if (strwesternzodaic.equalsIgnoreCase("no")){
                setAllDataWestern("CAPRICORN");


            }else {
                setAllDataWestern(strwesternzodaic);
            }

            try {
                binding.ivZodaicWestern.setImageResource(hashMapWestern.get(strwesternzodaic));
                binding.tvZodaicWestern.setText(strwesternzodaic);
            }catch (Exception e){

            }
        }else {
            binding.ivZodaicWestern.setVisibility(View.GONE);
            binding.tvZodaicWestern.setVisibility(View.GONE);
        }
        //set western data=======================================ends






        ///==================================get horoscope============================start


        gethoroscopetype = AppSession.getStringPreferences(getContext(), Constants.USERPREFFERDHOROSCOPE);
        Log.e(TAG, "onViewCreated: horoscope type  "+gethoroscopetype);

        //gethoroscopetype = "Western";
        if (gethoroscopetype.contains("Western")){

            Log.e(TAG, "onViewCreated: wwwwwwwwwww" );

            binding.llChineseHeader.setVisibility(View.GONE);
            binding.llWesternHeader.setVisibility(View.VISIBLE);

            binding.llChineseData.setVisibility(View.GONE);
            binding.llWesternData.setVisibility(View.VISIBLE);


        }else if (gethoroscopetype.contains("Chinese")){
            binding.llChineseHeader.setVisibility(View.VISIBLE);
            binding.llWesternHeader.setVisibility(View.GONE);

            binding.llChineseData.setVisibility(View.VISIBLE);
            binding.llWesternData.setVisibility(View.GONE);


            binding.ivZodaicChinese.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
            binding.tvZodaicChinese.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite));


        }else {
            binding.llChineseHeader.setVisibility(View.VISIBLE);
            binding.llWesternHeader.setVisibility(View.VISIBLE);

            binding.ivZodaicChinese.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorGray1));
            binding.tvZodaicChinese.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorGray1));

        }


        ///==================================get horoscope============================ends













        binding.llChineseHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set chinese data=======================================


                if (asociateAnimal.matches("no")){


                    Toast.makeText(activity, "No data found", Toast.LENGTH_SHORT).show();
                    return;
                }

                setAllDataChinese(dob);

                binding.llChineseData.setVisibility(View.VISIBLE);
                binding.llWesternData.setVisibility(View.GONE);

                binding.ivZodaicWestern.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorGray1));
                binding.tvZodaicWestern.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorGray1));

                binding.ivZodaicChinese.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));
                binding.tvZodaicChinese.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));


            }
        });


        binding.llWesternHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set western data

                String strwesternzodaic = SetChineseWesternZodaic.birthDateMatchWithWestern(dob);
                Log.e(TAG, "onViewCreated: strwesternzodaic  "+strwesternzodaic );

//                setAllDataWestern(strwesternzodaic);

                if (strwesternzodaic.equalsIgnoreCase("no")){
                    setAllDataWestern("CAPRICORN");
                }else {
                    setAllDataWestern(strwesternzodaic);
                }

               // setAllDataWestern("Capricorn");

                binding.llChineseData.setVisibility(View.GONE);
                binding.llWesternData.setVisibility(View.VISIBLE);

                binding.ivZodaicChinese.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorGray1));
                binding.tvZodaicChinese.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorGray1));


                binding.ivZodaicWestern.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));
                binding.tvZodaicWestern.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));




             /*   ///====================================horoscope type============

                if (gethoroscopetype.equalsIgnoreCase("Western")){

                    binding.llChineseHeader.setVisibility(View.GONE);
                    binding.llWesternHeader.setVisibility(View.VISIBLE);
                }else if (gethoroscopetype.equalsIgnoreCase("Chinese")){
                    binding.llChineseHeader.setVisibility(View.VISIBLE);
                    binding.llWesternHeader.setVisibility(View.GONE);
                }else {
                    binding.llChineseHeader.setVisibility(View.VISIBLE);
                    binding.llWesternHeader.setVisibility(View.VISIBLE);
                }
                //==============================horoscope type=========================
*/




            }
        });

    }


    //***********************************chinese data*************************************starts
    private void setAdapterLuckyNumber() {

       // ChineseData  chineseData = chineseDataArrayList.get(0);

        zodaiclist = Arrays.asList(chineseData.getLuckyNumber().split("\\s*,\\s*"));
        Log.e(TAG, "setAdapterLuckyNumber: items "+zodaiclist );

        LuckyNumberAdapter luckyNumberAdapter = new LuckyNumberAdapter(getContext(),zodaiclist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        binding.rvLuckynumbers.setLayoutManager(manager);
        binding.rvLuckynumbers.setAdapter(luckyNumberAdapter);

    }

    private void setAdapterUnLuckyNumber() {

       // ChineseData  chineseData = chineseDataArrayList.get(0);

        zodaiclist = Arrays.asList(chineseData.getUnluckyNumber().split("\\s*,\\s*"));
        Log.e(TAG, "setAdapterUnLuckyNumber: items "+zodaiclist );

        UnLuckyNumberAdapter luckyNumberAdapter = new UnLuckyNumberAdapter(getContext(),zodaiclist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        binding.rvUnluckynumbers.setLayoutManager(manager);
        binding.rvUnluckynumbers.setAdapter(luckyNumberAdapter);

    }

    private void setAdapterLuckyColor() {

        zodaiclist  = Arrays.asList(chineseData.getLuckyColor().split("\\s*,\\s*"));
        Log.e(TAG, "setAdapterLuckyNumber: items "+zodaiclist );

        LuckyColorsAdapter luckyNumberAdapter = new LuckyColorsAdapter(getContext(),zodaiclist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        binding.rvLuckycolor.setLayoutManager(manager);
        binding.rvLuckycolor.setAdapter(luckyNumberAdapter);

    }

    private void setAdapterUnLuckyColor() {

       // ChineseData  chineseData = chineseDataArrayList.get(0);

        zodaiclist = Arrays.asList(chineseData.getUnluckyColor().split("\\s*,\\s*"));
        Log.e(TAG, "setAdapterLuckyNumber: items "+zodaiclist );

        LuckyColorsAdapter luckyNumberAdapter = new LuckyColorsAdapter(getContext(),zodaiclist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        binding.rvUnluckycolor.setLayoutManager(manager);
        binding.rvUnluckycolor.setAdapter(luckyNumberAdapter);

    }

    private void setAdapterLuckydirection() {

        // ChineseData  chineseData = chineseDataArrayList.get(0);

        zodaiclist = Arrays.asList(chineseData.getDirection().split("\\s*,\\s*"));
        Log.e(TAG, "setAdapterLuckyNumber: items "+zodaiclist );

        LuckyDirectionAdapter luckyNumberAdapter = new LuckyDirectionAdapter(getContext(),zodaiclist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        binding.rvLuckydirections.setLayoutManager(manager);
        binding.rvLuckydirections.setAdapter(luckyNumberAdapter);

    }

    private void setAdapterUnLuckydirection() {

        // ChineseData  chineseData = chineseDataArrayList.get(0);

        zodaiclist = Arrays.asList(chineseData.getUnluckydirections().split("\\s*,\\s*"));
        Log.e(TAG, "setAdapterUnLuckydirection: items "+zodaiclist );

        LuckyDirectionAdapter luckyNumberAdapter = new LuckyDirectionAdapter(getContext(),zodaiclist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        binding.rvUnluckydirections.setLayoutManager(manager);
        binding.rvUnluckydirections.setAdapter(luckyNumberAdapter);

    }



   /* public void setChineseData(){
        //1
        chineseDataArrayList.add(new ChineseData("RAT","","","North","Mid-Winter","2,3",
                "5,9","Blue, Gold, Green","Yellow, Brown"));

        //2
        chineseDataArrayList.add(new ChineseData("OX","","","North","Late-Winter","1,4",
                "5,6","White, Yellow, Green","Blue"));


        //3
        chineseDataArrayList.add(new ChineseData("TIGER","","","East","Early Spring","1,3,4",
                "6,7,8","Blue, Grey, Orange","Brown"));


        //4
        chineseDataArrayList.add(new ChineseData("RABBIT","","","East","Mid-Spring","3,4,6",
                "1,7,8","Pink, Red, Purple, Blue","Dark Brown, Dark Yellow, White"));


        //5
        chineseDataArrayList.add(new ChineseData("DRAGON","","","East","Late Spring","1,6,7",
                "3,8","Gold, Silver, Greyish White","Blue, Green"));

        //6
        chineseDataArrayList.add(new ChineseData("SNAKE","","","South","Early Summer","2,8,9",
                "1,6,7","Black, Red, Yellow","Brown, White, Gold"));

        //7
        chineseDataArrayList.add(new ChineseData("HORSE","","","South","Mid-Summer","2, 3, 7",
                "1, 5, 6","Green, Yellow","Blue, White"));



        //8
        chineseDataArrayList.add(new ChineseData("GOAT","","","South","Late Summer","2, 7",
                "4, 9","Brown, Red, Purple","Blue, Black"));


        //9
        chineseDataArrayList.add(new ChineseData("MONKEY","","","West","Early Autumn","4, 9",
                "2, 7","White, Blue, Gold","Red, Pink"));


        //10
        chineseDataArrayList.add(new ChineseData("ROOSTER","","","West","Mid-Autumn","5, 7, 8",
                "1, 3, 9","Gold, Brown, Yellow","Red"));


        //11
        chineseDataArrayList.add(new ChineseData("DOG","","","West","Late Autumn","3, 4, 9",
                "1, 6, 7","Green, Red, Purple","Blue, White, Gold"));


        //12
        chineseDataArrayList.add(new ChineseData("PIG","","","North","Early Winter","2, 5, 8",
                "1, 7","Yellow, Grey, Brown, Gold","Red, Blue, Green"));


    }
*/
    public void setAllDataChinese(String userbirthdate){


        //setChineseData();

        //birthdaydata();

        ArrayList<ChineseData> chineseDataArrayList = ZodaicActivity.chinesedata();

        String getasociated = birthDateMatch(userbirthdate);

        Log.e(TAG, "birthDayData: getasco  7777  "+getasociated );



        if (!getasociated.matches("no")){


            ///set zodiac icon
            chineseIconData();
            binding.ivZodaicChinese.setImageResource(hashMapChinese.get(getasociated));
            binding.tvZodaicChinese.setText(getasociated);

            binding.ivZodaicChinese.setVisibility(View.VISIBLE);
            binding.tvZodaicChinese.setVisibility(View.VISIBLE);

            int index = 0;
            for(int i = 0; i<chineseDataArrayList.size(); i++){
                if(chineseDataArrayList.get(i).getChineseZodaic().matches(getasociated.toUpperCase())){
                     index = i;
                     break;
                }
            }



            chineseData = chineseDataArrayList.get(index);

            Log.e(TAG, " birthDayData: indexxx 7777 "+index );

            setAdapterLuckyNumber();
            setAdapterUnLuckyNumber();
            setAdapterLuckyColor();
            setAdapterUnLuckyColor();
            setAdapterLuckydirection();
            setAdapterUnLuckydirection();

            binding.tvLuckyflower.setText(chineseData.getLuckyflowers());

        }else {
            binding.ivZodaicChinese.setVisibility(View.GONE);
            binding.tvZodaicChinese.setVisibility(View.GONE);

        }

    }


    public String birthDateMatch(String dateofbirth){





        ArrayList<BirthdayData> birthdayDataArrayList = ZodaicActivity.birthDayData();

        Log.e(TAG+birthdayDataArrayList.size(), "birthDateMatch: dateofbirth vvv "+dateofbirth );
        for (int i = 0; i <birthdayDataArrayList.size(); i++) {

            String birthdayData = birthdayDataArrayList.get(i).getYearEnd();

            Log.e(TAG, "birthDateMatch: 1111111   "+birthdayData );

            String date_1984_2043 = birthdayData;

            Log.e(TAG, "birthDateMatch: 333333 "+date_1984_2043 );


            //split date
            String[] datearray =date_1984_2043.split("-");

            Log.e(TAG, "birthDateMatch: datearray "+datearray.length );

            String startdate = datearray[0];
            String enddate = datearray[1];

            Log.e(TAG+dateofbirth, enddate+" split birthDateMatch: "+startdate );



            //convert dob to date object
            //birth date match  ///2021/02/10


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            try {
                strDobDate = sdf.parse(dateofbirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.e(TAG, startdate+"   :birthDateMatch: strdate dob object "+strDobDate );



            //convert startdate to date object
            SimpleDateFormat sdfstart1 = new SimpleDateFormat("MMM dd yyyy");
            try {

                Log.e(TAG, "birthDateMatch: ssssssss "+startdate );
                strStartYear = sdfstart1.parse(startdate);
                Log.e(TAG, "birthDateMatch: ssssssss 1111   "+strStartYear );
            } catch (ParseException e) {

                Log.e(TAG, "birthDateMatch: eeeeee "+e.getMessage() );
                e.printStackTrace();
            }
            Log.e(TAG, startdate+" birthDateMatch: strdate start object "+strStartYear );


            //convert end date to dateobject
            SimpleDateFormat sdfend = new SimpleDateFormat("MMM dd yyyy");
            try {
                strendYear = sdfend.parse(enddate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.e(TAG, "birthDateMatch: strdate end  object "+strendYear );
            Log.e(TAG, "birthDateMatch: last date object  dob       "+strDobDate );
            Log.e(TAG, "birthDateMatch: last date object 111 start  "+strStartYear );
            Log.e(TAG, "birthDateMatch: last date object 222 end    "+strendYear );

            if(strDobDate.compareTo(strStartYear) > 0 && strDobDate.compareTo(strendYear) < 0) {
                ///System.out.println("Date 1 occurs after Date 2");
                Log.e(TAG, i+" birthDateMatch: match111 yesssss" );
                asociateAnimal = birthdayDataArrayList.get(i).getAnimal();

                asociateElement = birthdayDataArrayList.get(i).getElement();

                binding.tvAsociateElement.setText(asociateElement);

                break;
            } else  {
                Log.e(TAG, "birthDateMatch: match111  noooo" );
                //System.out.println("Date 1 occurs before Date 2");
            }
        }
        Log.e(TAG, asociateElement+"  birthDateMatch: asociated animal "+asociateAnimal );

        return asociateAnimal;

    }

    public void chineseIconData(){
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
    //***********************************chinese data*************************************ends














    //**************************************western data******************************starts

    /*private void westerdata() {
        ///////////////////////westen data

        //1
        westernDataArrayList.add(new WesternData("AQUARIUS","Jan-20","Feb-18","Air","Fixed","Uranus, Saturn",
                "Light-Blue, Silver","Saturday","4, 7, 11, 22, 29","","Garnet"));


        //2
        westernDataArrayList.add(new WesternData("PISCES","Feb-19","Mar-20","Water","Mutable","Neptune, Jupiter",
                "Mauve, Lilac, Purple, Violet, Sea green","Thursday","3, 9, 12, 15, 18, 24","","Amethyst"));


        //3
        westernDataArrayList.add(new WesternData("ARIES","Mar-21","Apr-19","Fire","Cardinal","Mars",
                "Red","Tuesday","1, 8, 17","","Heliotrope"));


        //4
        westernDataArrayList.add(new WesternData("TAURUS","Apr-20","May-20","Earth","Fixed","Venus",
                "Green, Pink","Friday, Monday","2, 6, 9, 12, 24","","Sapphire, Natural diamond"));


        //5
        westernDataArrayList.add(new WesternData("GEMINI","May-21","Jun-20","Air","Mutable","Mercury",
                "Light-Green,Yellow","Wednesday","5, 7, 14, 23","","Agate"));






        westernDataArrayList.add(new WesternData("CANCER",	"Jun-21",	"Jul-22",	"Water",	"Cardinal",	"Moon",
                "White",	"Monday, Thursday"	,"2, 3, 15, 20	","",	"Emerald"));

        westernDataArrayList.add(new WesternData("LEO",	"Jul-23",	"Aug-22",	"Fire",	"Fixed",	"Sun",
                "Gold, Yellow, Orange",	"Sunday"	,"1, 3, 10, 19",""	,	"Onyx"));

        westernDataArrayList.add(new WesternData(        "VIRGO",	"Aug-23"	,"Sep-22",	"Earth",	"Mutable",	"Mercury"	,
                "Grey, Beige, Pale-Yellow",	"Wednesday",	"5, 14, 15, 23, 32",	"",	"Carnelian"
        ));
        westernDataArrayList.add(new WesternData("LIBRA",	"Sep-23"	,"Oct-22",	"Air",	"Cardinal",	"Venus",
                "Pink, Green	","Friday"	,"4, 6, 13, 15, 24","",		"Chrysolite"));
        westernDataArrayList.add(new WesternData("SCORPIO",	"Oct-23",	"Nov-21",	"Water",	"Fixed",	"Pluto, Mars",
                "Scarlet, Red, Rust",	"Tuesday",	"8, 11, 18, 22",""	,	"Beryl"));


        westernDataArrayList.add(new WesternData("SAGITTARIUS",	"Nov-2" ,"Dec-21",	"Fire",	"Mutable",	"Jupiter",
                "Blue",	"Thursday"	,"3, 7, 9, 12, 21	",""	,"Topaz"));

        westernDataArrayList.add(new WesternData("CAPRICORN",	"Dec-22",	"Jan-19",	"Earth",	"Cardinal",	"Saturn",
                "Brown, Black",	"Saturday"	,"4, 8, 13, 22"	,"",	"Ruby"));














    }*/

    public WesternData getWesternDataSingleRow(String zodaic_sign){


        ArrayList<WesternData> westernDataArrayList = ZodaicActivity.westerdata();


        WesternData westernData1;
        int index = 0;
        Log.e(TAG, "getWesternDataSingleRow: "+westernDataArrayList.size() );
        for(int j = 0; j<westernDataArrayList.size(); j++){
            if(westernDataArrayList.get(j).getWesternZodiac().toLowerCase().matches(zodaic_sign.toLowerCase())){
                index = j;
                break;
            }
        }

        Log.e(TAG, "birthDayData: indexxx 7777 "+index );
        westernData1 = westernDataArrayList.get(index);

        return westernData1;

    }





    private void setAdapterLuckyNumberwestern(WesternData westernData2) {

        zodaiclist = Arrays.asList(westernData2.getLuckyNumber().split("\\s*,\\s*"));
        Log.e(TAG, "setAdapterLuckyNumberwestern: items "+zodaiclist );

        LuckyNumberAdapter luckyNumberAdapter = new LuckyNumberAdapter(getContext(),zodaiclist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        binding.rvLuckynumbersWestern.setLayoutManager(manager);
        binding.rvLuckynumbersWestern.setAdapter(luckyNumberAdapter);

    }


    private void setAdapterLuckyColorWestern(WesternData westernData2) {

        zodaiclist  = Arrays.asList(westernData2.getColor().split("\\s*,\\s*"));
        Log.e(TAG, "setAdapterLuckyColorWestern: items "+zodaiclist );

        LuckyColorsAdapter luckyNumberAdapter = new LuckyColorsAdapter(getContext(),zodaiclist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        binding.rvLuckycolorWestern.setLayoutManager(manager);
        binding.rvLuckycolorWestern.setAdapter(luckyNumberAdapter);

    }

    public void setAllDataWestern(String z_){

        //westerdata();

        westernData = getWesternDataSingleRow(z_);

        Log.e(TAG, "setAllDataWestern: western "+westernData.getWesternZodiac() );
        setAdapterLuckyNumberwestern(westernData);
        setAdapterLuckyColorWestern(westernData);



        ///set element
        binding.tvElement.setText(westernData.getElement());
        binding.tvQuality.setText(westernData.getQuality());
        binding.tvRuler.setText(westernData.getRuler());
        binding.tvDay.setText(westernData.getDay());
        binding.tvZodaicbirthstone.setText(westernData.getZodiacBirthStone());
        binding.tvZodaicdaybirthstone.setText(westernData.getZodiacDayBirthStone());
        binding.tvMonthbirthstone.setText(westernData.getMonthBirthStone());
        binding.tvDatestart.setText(westernData.getDateRange());
        binding.tvDateend.setText(westernData.getDateEnd());

    }

    //**************************************western data******************************ends








    public void westernIconData(){
        //int drawableint;
        hashMapWestern = new HashMap<String, Integer>();
        hashMapWestern.put("AQUARIUS", R.drawable.ic_aquarius_white);
        hashMapWestern.put("PISCES",  R.drawable.ic_pisces_white);
        hashMapWestern.put("ARIES",  R.drawable.ic_aries_white);
        hashMapWestern.put("TAURUS",  R.drawable.ic_taurus_white);
        hashMapWestern.put("GEMINI", R.drawable.ic_gemini_white);
        hashMapWestern.put("CANCER",  R.drawable.ic_cancer_white);
        hashMapWestern.put("LEO",  R.drawable.ic_leo_white);
        hashMapWestern.put("VIRGO",  R.drawable.ic_virgo_white);
        hashMapWestern.put("LIBRA", R.drawable.ic_libra_white);
        hashMapWestern.put("SCORPIO",  R.drawable.ic_scorpio_white);
        hashMapWestern.put("SAGITTARIUS",  R.drawable.ic_sagittarius_white);
        hashMapWestern.put("CAPRICORN",  R.drawable.ic_capricorn_white);

    }



}