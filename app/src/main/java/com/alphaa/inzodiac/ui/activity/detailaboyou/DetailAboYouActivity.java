package com.alphaa.inzodiac.ui.activity.detailaboyou;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.base.HeightSliderDialog;
import com.alphaa.inzodiac.databinding.ActivityDetailAboYouBinding;
import com.alphaa.inzodiac.databinding.DialogHeightBinding;
import com.alphaa.inzodiac.databinding.DialogMonthlyHoroscopeBinding;
import com.alphaa.inzodiac.gallery.models.Image;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.CityResponse;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.CitySpinnerAdapter;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.CountryResponse;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.CountrySpinnerAdapter;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.FilterPresenter;
import com.alphaa.inzodiac.ui.activity.aboutself.AboutSelfPresenter;
import com.alphaa.inzodiac.ui.activity.detailaboyou.adapter.BodyAdapter;
import com.alphaa.inzodiac.ui.activity.detailaboyou.adapter.DateFoodAdapter;
import com.alphaa.inzodiac.ui.activity.detailaboyou.adapter.EthnicityAdapter;
import com.alphaa.inzodiac.ui.activity.detailaboyou.adapter.HairAdapter;
import com.alphaa.inzodiac.ui.activity.detailaboyou.adapter.MyWorkAdapter;
import com.alphaa.inzodiac.ui.activity.detailaboyou.adapter.PetsAdapter;
import com.alphaa.inzodiac.ui.activity.detailaboyou.adapter.ReligionAdapter;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.BodyModel;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.DateFoodModel;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.EthnicityModel;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.HairModel;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.MyWorkModel;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.PetsModel;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.ReligionModel;
import com.alphaa.inzodiac.ui.activity.loginmodule.LoginActivity;
import com.alphaa.inzodiac.ui.activity.stepcomplete.StepCompleteActivity;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.LocationModePermmission;
import com.alphaa.inzodiac.utility.PDialog;
import com.alphaa.inzodiac.utility.Validation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DetailAboYouActivity extends BaseActivity implements View.OnClickListener,ApiCallBackInterFace.AboutInfoCallback, HeightSliderDialog.HeightSliderCallBack, ApiCallBackInterFace.FilterInfoCallback
,//get current location
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{
    String TAG = getClass().getSimpleName();

    ActivityDetailAboYouBinding binding;
    ArrayList<EthnicityModel> ethnicityList;
    ArrayList<BodyModel> bodyList;
    ArrayList<HairModel> hairList;

    ArrayList<ReligionModel> religionModelArrayList = new ArrayList<>();
    ArrayList<PetsModel> petsModelArrayList;
    ArrayList<DateFoodModel> dateFoodModelArrayList;
    ArrayList<MyWorkModel> myWorkModelArrayList;

    ArrayList<Image>imglist;
    String height="",img="",strgender = "", strinterest = "", orientation = "", looking = "",date = "", ethnicityname = "Native American", bodytype = "Regularly",
            haircolor = "Red",
            htype = "Western",
            married = "Complicated", child = "Maybe someday", smoke = "Socially", drink = "Socially", lang = "No",
    religion = "Atheist",pets="None",datefood = "Vegan",mywork = "Company or Industry";

    //city

    private ArrayList<CountryResponse.DataItem> countryList;
    private ArrayList<CityResponse.DataItem> cityList;
    private String id;

    private boolean bool5 = false;
    private boolean bool6 = false;

    String city="",country="";





    //location
    LocationModePermmission locationModePermmission=new LocationModePermmission();
    int locationMode;private Geocoder geocoder;
    private List<Address> addresses;

    private Location location;
    private final int ANIMATION_TYPE_RIPPLE = 0;
    private int whichAnimationWasRunning = ANIMATION_TYPE_RIPPLE;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final long UPDATE_INTERVAL = 500, FASTEST_INTERVAL = 500;
    private String customer_recent_location_id, recent_address_line1, recent_address_line2, recent_customer_id,
            recent_area_name, recent_location_name, recent_city, recent_langtude, recent_latitude,
            recent_location_homepage_header_to_display, recent_location_to_display, dialogeClose = "";
    private String address_line1, address_line2, location_name, area_name, address, contact_number,
            langitude, latitude, address_type_id, state, pin_code, land_mark, AddressBook;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    // integer for permissions results request
    private static final int ALL_PERMISSIONS_RESULT = 1011;





    String userid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_abo_you);
        inItView();

        userid = AppSession.getStringPreferences(getApplicationContext(), Constants.USEREId);
    }


    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    private void inItView() {


        countryList = new ArrayList<>();
        cityList = new ArrayList<>();
        new FilterPresenter(activity, this).countriesData();




        //if (getIntent().getData()!=null) {

            //if (!getIntent().getStringExtra("skip").equals("skip")) {

        strgender = getIntent().getStringExtra("strgender");
        strinterest = getIntent().getStringExtra("strinterest");
        date = getIntent().getStringExtra("date");
        Log.e(TAG, "inItView: dddddddddddddddd  "+date );
        orientation = getIntent().getStringExtra("orientation");
        looking = getIntent().getStringExtra("looking");
                if (getIntent().getStringExtra("img") != null) {
                    img = getIntent().getStringExtra("img");
                }
                imglist = getIntent().getParcelableArrayListExtra("imglist");


                Log.e(TAG, date+"  inItView: looking " + looking);

            //}
        /*}else {
            Log.e(TAG, "inItView: nullllllllll" );
        }
*/
        //binding.etLeftheight.setText("150");
        //binding.etRightheight.setText("219");
        //binding.llheight.setVisibility(View.VISIBLE);











        setClicks(binding.cardHeight,binding.rleast, binding.rlweast, binding.rlmsingle, binding.rlmcomplicated, binding.rlcnonever, binding.rlcmaybesomeday, binding.rlsnever,
                binding.rlssocially, binding.rldnever, binding.rldsocially, binding.rllyes, binding.rllno, binding.next, binding.skip, binding.ivback,binding.tvHeight,binding.rlmPartnered,
                binding.rlmMarried,binding.rlmNa,binding.rlcalreadyhave,binding.rlcallgrownup,binding.rlcNa,binding.rlsregularly,binding.rlsna,binding.rldregularly,binding.rldna
        ,binding.rlboth);

        ethnicityList = new ArrayList<EthnicityModel>() {{
            add(new EthnicityModel("Native American", true));
            add(new EthnicityModel(" Black African", false));
            add(new EthnicityModel("Black West Indian", false));
            add(new EthnicityModel("East Asian", false));
            add(new EthnicityModel("Hispanic/Latino", false));
            add(new EthnicityModel("Midle Eastern", false));
            add(new EthnicityModel("Pacific Islander", false));
            add(new EthnicityModel(" South Asian", false));
            add(new EthnicityModel("Mixed Race", false));
            add(new EthnicityModel("White/Caucasian", false));
            add(new EthnicityModel("Other", false));

        }};












        bodyList = new ArrayList<BodyModel>() {{
            add(new BodyModel("Regularly", true));
            add(new BodyModel("Every now & then", false));
            add(new BodyModel("  I will if you will", false));
            add(new BodyModel(" I'm unable to exercise", false));
            add(new BodyModel("No thanks", false));
            add(new BodyModel("N/A", false));
        }};

        hairList = new ArrayList<HairModel>() {{
            add(new HairModel("Red", true));
            add(new HairModel("Black", false));
            add(new HairModel("Brown", false));
            add(new HairModel("Ginger", false));
            add(new HairModel("Grey", false));
            add(new HairModel("Bald", false));
            add(new HairModel("Shaved", false));
        }};


        religionModelArrayList = new ArrayList<ReligionModel>(){{
                add(new ReligionModel("Atheist", true));
                add(new ReligionModel("Judaism", false));
                add(new ReligionModel("Wicca", false));
                add(new ReligionModel("Taoism", false));
                add(new ReligionModel("Christianity", false));
                add(new ReligionModel("Confucianism", false));
                add(new ReligionModel("Baha’i", false));
                add(new ReligionModel("Druidism", false));
                add(new ReligionModel("Islam", false));
                add(new ReligionModel("Hinduism", false));
                add(new ReligionModel("Spiritual", false));
                add(new ReligionModel("Zoroastrianism", false));
                add(new ReligionModel("Jainism", false));
                add(new ReligionModel("Sikhism", false));
                add(new ReligionModel("Buddhism", false));
            }};


        petsModelArrayList = new ArrayList<PetsModel>(){{
                add(new PetsModel("None", true));
            add(new PetsModel("Cats/Dogs", false));
            add(new PetsModel("Other pets", false));
            add(new PetsModel("N/A", false));
            }};


        dateFoodModelArrayList = new ArrayList<DateFoodModel>(){{
            add(new DateFoodModel("Vegan", true));
            add(new DateFoodModel("Vegetarian", false));
            add(new DateFoodModel("Pescetarian", false));
            add(new DateFoodModel("I love meat", false));
            add(new DateFoodModel("I love all food", false));
            add(new DateFoodModel("N/A", false));
        }};

        myWorkModelArrayList = new ArrayList<MyWorkModel>(){{
            add(new MyWorkModel("Company or Industry", true));
            add(new MyWorkModel("N/A", false));
        }};


        setAdapter();
        setAdapter1();
        setAdapter2();
        setAdapter3();
        setAdapter4();
        setAdapter5();
        //setAdapter6();


        //====================locatio===========starts




        geocoder = new Geocoder(this, Locale.getDefault());
        permission();
        //gpsDialoge();

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        int myVersion= Build.VERSION.SDK_INT;
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            locationMode =      Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }


        if (myVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (locationMode == 0 || locationMode == 1) {
                locationModePermmission.displayLocationSettingsRequest(DetailAboYouActivity.this);
            }
        }else if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showSettingsAlert();
        }
        //=====================location ======================ends







    }



    public static String centimeterToFeet(String centemeter) {
        int feetPart = 0;
        int inchesPart = 0;
        if(!TextUtils.isEmpty(centemeter)) {
            double dCentimeter = Double.valueOf(centemeter);
          /*  feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
            System.out.println((dCentimeter / 2.54) - (feetPart * 12));
            inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));
*/
             feetPart = (int) Math.floor(dCentimeter / 30.48);
             inchesPart = (int) Math.round((dCentimeter / 2.54) - ((int) feetPart * 12));
        }
        return String.format("%d' %d''", feetPart, inchesPart);
    }


    private void setAdapter() {
        EthnicityAdapter adapter = new EthnicityAdapter(this, ethnicityList, pos -> ethnicityname = ethnicityList.get(pos).name);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
    }

    private void setAdapter1() {
        BodyAdapter adapter = new BodyAdapter(this, bodyList, pos -> bodytype = bodyList.get(pos).name);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        binding.recyclerView1.setLayoutManager(manager);
        binding.recyclerView1.setAdapter(adapter);
    }

    private void setAdapter2() {
        HairAdapter adapter = new HairAdapter(this, hairList, pos -> haircolor = hairList.get(pos).name);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        binding.recyclerView2.setLayoutManager(manager);
        binding.recyclerView2.setAdapter(adapter);
    }

    private void setAdapter3() {
        ReligionAdapter adapter = new ReligionAdapter(this, religionModelArrayList, pos -> religion = religionModelArrayList.get(pos).name);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        binding.recyclerViewReligion.setLayoutManager(manager);
        binding.recyclerViewReligion.setAdapter(adapter);
    }

    private void setAdapter4() {
        PetsAdapter adapter = new PetsAdapter(this, petsModelArrayList, pos -> pets = petsModelArrayList.get(pos).name);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        binding.recyclerViewPets.setLayoutManager(manager);
        binding.recyclerViewPets.setAdapter(adapter);
    }
    private void setAdapter5() {
        DateFoodAdapter adapter = new DateFoodAdapter(this, dateFoodModelArrayList, pos -> datefood = dateFoodModelArrayList.get(pos).name);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        binding.recyclerViewDatefood.setLayoutManager(manager);
        binding.recyclerViewDatefood.setAdapter(adapter);
    }
    private void setAdapter6() {
        MyWorkAdapter adapter = new MyWorkAdapter(this, myWorkModelArrayList, pos -> mywork = myWorkModelArrayList.get(pos).name);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        binding.recyclerViewMywork.setLayoutManager(manager);
        binding.recyclerViewMywork.setAdapter(adapter);
    }




    private void setCitydata(int pos) {

        Log.e(TAG, "setCitydata: " );
        id = countryList.get(pos).getId();

        Log.e(TAG, "setCitydata: idd "+id);
        new FilterPresenter(activity, this).cityData(id);
    }

    @Override
    public void oncountryInfoResponse(String s) {
        CountryResponse countryResponse =
                new Gson().fromJson(s, CountryResponse.class);
        CountryResponse.DataItem data = new CountryResponse.DataItem("0","Country","1");
        countryList.add(0,data);
        countryList.addAll(countryResponse.getData());

        binding.countrysp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e(TAG, "onItemSelected: countrysp position "+position );
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                if (bool5 && position !=0) {
                    //tot5 =1;
                   // total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                   // binding.tot.setText(String.valueOf(total));
                   // binding.lrtot.setVisibility(View.VISIBLE);
                    country = countryList.get(position).getName();
                    binding.tvcountry.setText(country);
                    binding.rlcountry.setBackground(getResources().getDrawable(R.drawable.purple_boarder_back));
                    binding.tv5.setTextColor(getResources().getColor(R.color.white));
                    binding.tvcountry.setTextColor(getResources().getColor(R.color.white));
                    binding.iv5.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_white_arrow));
                }
                bool5 = true;
                if (position != 0) {
                    setCitydata(position);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        CountrySpinnerAdapter adapter = new CountrySpinnerAdapter(activity, countryList);
        binding.countrysp.setAdapter(adapter);


    }

    @Override
    public void oncityInfoResponse(String s) {

        Log.e(TAG, "oncityInfoResponse: sssssss "+s );

        if (cityList!=null)cityList.clear();
        CityResponse cityResponse =
                new Gson().fromJson(s, CityResponse.class);

        CityResponse.DataItem data = new CityResponse.DataItem("0","City","1");
        cityList.add(0,data);
        cityList.addAll(cityResponse.getData());
        CitySpinnerAdapter adapter = new CitySpinnerAdapter(activity, cityList);
        binding.citysp.setAdapter(adapter);
        binding.citysp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e(TAG, "onItemSelected:oncityInfoResponse "+position );
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                if (bool6 && position !=0) {
                    //tot6 =1;
                    //total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    //binding.tot.setText(String.valueOf(total));
                    //binding.lrtot.setVisibility(View.VISIBLE);
                    city = cityList.get(position).getName();
                    binding.tvcity.setText(city);
                    binding.rlcity.setBackground(getResources().getDrawable(R.drawable.purple_boarder_back));
                    binding.tv6.setTextColor(getResources().getColor(R.color.white));
                    binding.tvcity.setTextColor(getResources().getColor(R.color.white));
                    binding.iv6.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_white_arrow));
                }
                bool6 = true;

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    @Override
    public void onFilterInfoResponse(String s) {

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.skip: {
                Intent intent = new Intent(DetailAboYouActivity.this, StepCompleteActivity.class);
                intent.putExtra("skip", "skip");
                startActivity(intent);
            }
            break;
            case R.id.ivback: {
                //onBackPressed();

                Log.e(TAG, "onClick: backe " );
                finish();
            }
            break;

             case R.id.tv_height: {
                dialogHeight();
                //binding.llheight.setVisibility(View.VISIBLE);
//                HeightSliderDialog heightSliderDialog = HeightSliderDialog.getInstance(activity);
//                heightSliderDialog.show(getSupportFragmentManager());
//                heightSliderDialog.intializeHeightListener(this);
            }
            break;

            case R.id.card_height: {
                //dialogHeight();
                //binding.llheight.setVisibility(View.VISIBLE);
//                HeightSliderDialog heightSliderDialog = HeightSliderDialog.getInstance(activity);
//                heightSliderDialog.show(getSupportFragmentManager());
//                heightSliderDialog.intializeHeightListener(this);
            }
            break;

            case R.id.next: {
                Validation validation = new Validation(this);
                //if (validation.isheightValid(binding.etHeight.getText().toString())) {

                Log.e(TAG, city+"  :onClick: cityyyyy: "+country );

                /*if (!validation.isCountryValid(binding.tvcountry)){

                    return;

                }else if (!validation.isCityValid(binding.tvcity)){
                    return;
                }*/

                    Log.e(TAG, htype+"  onClick: dddddddddd "+binding.tvHeight.getText() );



                String uid = AppSession.getStringPreferences(getApplicationContext(), Constants.USEREIDSIGNUP);
                String westernzodaic = AppSession.getStringPreferences(getApplicationContext(), Constants.WESTERNZODAICSIGN);
                String chinesezodaic = AppSession.getStringPreferences(getApplicationContext(), Constants.CHINESEZODAICSIGN);


                Log.e(TAG, "onClick: htypeee "+htype );
                    //////////////////
                new AboutSelfPresenter(this,this).aboutSelfData( uid,"",""+binding.tvHeight.getText(),img,strgender, strinterest, orientation,
                        looking, date, ethnicityname, bodytype, haircolor, htype , married, child , smoke , drink ,  lang,imglist,chinesezodaic.toLowerCase(),westernzodaic.toLowerCase(),city,country,
                        pets,religion,datefood,"education",mywork);

               // }
            }
            break;


            //horoscope type===================================================start

            case R.id.rleast: {
                binding.east.setTextColor(activity.getResources().getColor(R.color.white));
                binding.rleast.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.weast.setTextColor(getResources().getColor(R.color.black));
                binding.rlweast.setBackground(null);

                binding.both.setTextColor(getResources().getColor(R.color.black));
                binding.rlboth.setBackground(null);

                htype = "Chinese";


            }
            break;
            case R.id.rlweast: {
                binding.east.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rleast.setBackground(null);

                binding.weast.setTextColor(getResources().getColor(R.color.white));
                binding.rlweast.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.both.setTextColor(getResources().getColor(R.color.black));
                binding.rlboth.setBackground(null);

                htype = "Western";
            }
            break;

            case R.id.rlboth: {

                binding.both.setTextColor(getResources().getColor(R.color.white));
                binding.rlboth.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.east.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rleast.setBackground(null);

                binding.weast.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlweast.setBackground(null);

                htype = "Both";
            }
            break;

            //horoscope type===================================================start



            //////----------------------relationship-------------------------------------------
            case R.id.rlmsingle: {
                binding.msingle.setTextColor(activity.getResources().getColor(R.color.white));
                binding.rlmsingle.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.mcomplicated.setTextColor(getResources().getColor(R.color.black));
                binding.rlmcomplicated.setBackground(null);

                binding.mMarried.setTextColor(getResources().getColor(R.color.black));
                binding.rlmMarried.setBackground(null);


                binding.mPartnered.setTextColor(getResources().getColor(R.color.black));
                binding.rlmPartnered.setBackground(null);

                binding.mNa.setTextColor(getResources().getColor(R.color.black));
                binding.rlmNa.setBackground(null);


                married = "Single";
            }

            break;
            case R.id.rlmcomplicated: {

                binding.mcomplicated.setTextColor(getResources().getColor(R.color.white));
                binding.rlmcomplicated.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.msingle.setTextColor(getResources().getColor(R.color.black));
                binding.rlmsingle.setBackground(null);

                binding.mMarried.setTextColor(getResources().getColor(R.color.black));
                binding.rlmMarried.setBackground(null);

                binding.mPartnered.setTextColor(getResources().getColor(R.color.black));
                binding.rlmPartnered.setBackground(null);

                binding.mNa.setTextColor(getResources().getColor(R.color.black));
                binding.rlmNa.setBackground(null);


                married = "Complicated";
            }
            break;

            case R.id.rlmPartnered: {

                binding.mPartnered.setTextColor(getResources().getColor(R.color.white));
                binding.rlmPartnered.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));


                binding.msingle.setTextColor(getResources().getColor(R.color.black));
                binding.rlmsingle.setBackground(null);

                binding.mMarried.setTextColor(getResources().getColor(R.color.black));
                binding.rlmMarried.setBackground(null);

                binding.mcomplicated.setTextColor(getResources().getColor(R.color.black));
                binding.rlmcomplicated.setBackground(null);

                binding.mNa.setTextColor(getResources().getColor(R.color.black));
                binding.rlmNa.setBackground(null);


                married = "Partnered";
            }
            break;

            case R.id.rlmMarried: {

                binding.mMarried.setTextColor(getResources().getColor(R.color.white));
                binding.rlmMarried.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.msingle.setTextColor(getResources().getColor(R.color.black));
                binding.rlmsingle.setBackground(null);

                binding.mPartnered.setTextColor(getResources().getColor(R.color.black));
                binding.rlmPartnered.setBackground(null);

                binding.mcomplicated.setTextColor(getResources().getColor(R.color.black));
                binding.rlmcomplicated.setBackground(null);

                binding.mNa.setTextColor(getResources().getColor(R.color.black));
                binding.rlmNa.setBackground(null);


                married = "Married";
            }
            break;

            case R.id.rlmNa: {

                binding.mNa.setTextColor(getResources().getColor(R.color.white));
                binding.rlmNa.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.mMarried.setTextColor(getResources().getColor(R.color.black));
                binding.rlmMarried.setBackground(null);

                binding.msingle.setTextColor(getResources().getColor(R.color.black));
                binding.rlmsingle.setBackground(null);

                binding.mPartnered.setTextColor(getResources().getColor(R.color.black));
                binding.rlmPartnered.setBackground(null);

                binding.mcomplicated.setTextColor(getResources().getColor(R.color.black));
                binding.rlmcomplicated.setBackground(null);

                married = "N/A";
            }
            break;
            //////----------------------relationship-------------------------------------------




            ////===================children================================================

            case R.id.rlcnonever: {
                binding.cnonever.setTextColor(activity.getResources().getColor(R.color.white));
                binding.rlcnonever.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.cmaybesomeday.setTextColor(getResources().getColor(R.color.black));
                binding.rlcmaybesomeday.setBackground(null);

                binding.calreadyhave.setTextColor(getResources().getColor(R.color.black));
                binding.rlcalreadyhave.setBackground(null);

                binding.callgrownup.setTextColor(getResources().getColor(R.color.black));
                binding.rlcallgrownup.setBackground(null);

                binding.cNa.setTextColor(getResources().getColor(R.color.black));
                binding.rlcNa.setBackground(null);


                child = "No-Never";

            }
            break;


            case R.id.rlcmaybesomeday: {
                binding.cmaybesomeday.setTextColor(getResources().getColor(R.color.white));
                binding.rlcmaybesomeday.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.cnonever.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlcnonever.setBackground(null);


                binding.calreadyhave.setTextColor(getResources().getColor(R.color.black));
                binding.rlcalreadyhave.setBackground(null);

                binding.callgrownup.setTextColor(getResources().getColor(R.color.black));
                binding.rlcallgrownup.setBackground(null);

                binding.cNa.setTextColor(getResources().getColor(R.color.black));
                binding.rlcNa.setBackground(null);



                child = "Maybe someday";

            }
            break;


               case R.id.rlcalreadyhave: {

                   binding.calreadyhave.setTextColor(getResources().getColor(R.color.white));
                   binding.rlcalreadyhave.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                   binding.callgrownup.setTextColor(getResources().getColor(R.color.black));
                   binding.rlcallgrownup.setBackground(null);

                binding.cmaybesomeday.setTextColor(getResources().getColor(R.color.black));
                binding.rlcmaybesomeday.setBackground(null);

                binding.cnonever.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlcnonever.setBackground(null);

                   binding.cNa.setTextColor(getResources().getColor(R.color.black));
                   binding.rlcNa.setBackground(null);



                   child = "Already Have";

            }
            break;

            case R.id.rlcallgrownup: {

                binding.callgrownup.setTextColor(getResources().getColor(R.color.white));
                binding.rlcallgrownup.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                   binding.calreadyhave.setTextColor(getResources().getColor(R.color.black));
                   binding.rlcalreadyhave.setBackground(null);

                binding.cmaybesomeday.setTextColor(getResources().getColor(R.color.black));
                binding.rlcmaybesomeday.setBackground(null);

                binding.cnonever.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlcnonever.setBackground(null);

                binding.cNa.setTextColor(getResources().getColor(R.color.black));
                binding.rlcNa.setBackground(null);



                child = "All Grown Up";

            }
            break;


        case R.id.rlcNa: {

                binding.cNa.setTextColor(getResources().getColor(R.color.white));
                binding.rlcNa.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.calreadyhave.setTextColor(getResources().getColor(R.color.black));
                binding.rlcalreadyhave.setBackground(null);

                binding.cmaybesomeday.setTextColor(getResources().getColor(R.color.black));
                binding.rlcmaybesomeday.setBackground(null);

                binding.callgrownup.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlcallgrownup.setBackground(null);

                binding.cnonever.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlcnonever.setBackground(null);

                child = "N/A";
            }
            break;






            ////===================children================================================ends


            ///====================smoking----------------------------starts
            case R.id.rlsnever: {
                binding.snever.setTextColor(activity.getResources().getColor(R.color.white));
                binding.rlsnever.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.ssocially.setTextColor(getResources().getColor(R.color.black));
                binding.rlssocially.setBackground(null);

                binding.sregularly.setTextColor(getResources().getColor(R.color.black));
                binding.rlsregularly.setBackground(null);

                binding.sna.setTextColor(getResources().getColor(R.color.black));
                binding.rlsna.setBackground(null);


                smoke = "Never";







            }
            break;
            case R.id.rlssocially: {
                binding.snever.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlsnever.setBackground(null);


                binding.ssocially.setTextColor(getResources().getColor(R.color.white));
                binding.rlssocially.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));


                binding.sregularly.setTextColor(getResources().getColor(R.color.black));
                binding.rlsregularly.setBackground(null);

                binding.sna.setTextColor(getResources().getColor(R.color.black));
                binding.rlsna.setBackground(null);

                smoke = "Socially";
            }
            break;


            case R.id.rlsregularly: {

                binding.sregularly.setTextColor(getResources().getColor(R.color.white));
                binding.rlsregularly.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.snever.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlsnever.setBackground(null);

                binding.sna.setTextColor(getResources().getColor(R.color.black));
                binding.rlsna.setBackground(null);

                binding.ssocially.setTextColor(getResources().getColor(R.color.black));
                binding.rlssocially.setBackground(null);

                smoke = "Regularly";
            }
            break;

            case R.id.rlsna: {

                binding.sna.setTextColor(getResources().getColor(R.color.white));
                binding.rlsna.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.snever.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlsnever.setBackground(null);

                binding.sregularly.setTextColor(getResources().getColor(R.color.black));
                binding.rlsregularly.setBackground(null);

                binding.ssocially.setTextColor(getResources().getColor(R.color.black));
                binding.rlssocially.setBackground(null);

                smoke = "N/A";
            }
            break;





            ///====================smoking----------------------------ends









                        ///====================drinking----------------------------starts
            case R.id.rldnever: {
                binding.dnever.setTextColor(activity.getResources().getColor(R.color.white));
                binding.rldnever.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.dsocially.setTextColor(getResources().getColor(R.color.black));
                binding.rldsocially.setBackground(null);

                binding.dregularly.setTextColor(getResources().getColor(R.color.black));
                binding.rldregularly.setBackground(null);

                binding.dna.setTextColor(getResources().getColor(R.color.black));
                binding.rldna.setBackground(null);


                drink = "Never";







            }
            break;
            case R.id.rldsocially: {
                binding.dnever.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rldnever.setBackground(null);


                binding.dsocially.setTextColor(getResources().getColor(R.color.white));
                binding.rldsocially.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));


                binding.dregularly.setTextColor(getResources().getColor(R.color.black));
                binding.rldregularly.setBackground(null);

                binding.dna.setTextColor(getResources().getColor(R.color.black));
                binding.rldna.setBackground(null);

                drink = "Socially";
            }
            break;


            case R.id.rldregularly: {

                binding.dregularly.setTextColor(getResources().getColor(R.color.white));
                binding.rldregularly.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.dnever.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rldnever.setBackground(null);

                binding.dna.setTextColor(getResources().getColor(R.color.black));
                binding.rldna.setBackground(null);

                binding.dsocially.setTextColor(getResources().getColor(R.color.black));
                binding.rldsocially.setBackground(null);

                drink = "Regularly";
            }
            break;

            case R.id.rldna: {

                binding.dna.setTextColor(getResources().getColor(R.color.white));
                binding.rldna.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.dnever.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rldnever.setBackground(null);

                binding.dregularly.setTextColor(getResources().getColor(R.color.black));
                binding.rldregularly.setBackground(null);

                binding.dsocially.setTextColor(getResources().getColor(R.color.black));
                binding.rldsocially.setBackground(null);

                drink = "N/A";
            }
            break;





            ///====================drinking----------------------------ends












           /* case R.id.rldyes: {
                binding.dyes.setTextColor(activity.getResources().getColor(R.color.white));
                binding.rldyes.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.dno.setTextColor(getResources().getColor(R.color.black));
                binding.rldno.setBackground(null);
                drink = "Yes";


            }
            break;
            case R.id.rldno: {
                binding.dyes.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rldyes.setBackground(null);
                drink = "No";

                binding.dno.setTextColor(getResources().getColor(R.color.white));
                binding.rldno.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));
            }
            break;*/










            case R.id.rllyes: {
                binding.lyes.setTextColor(activity.getResources().getColor(R.color.white));
                binding.rllyes.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));

                binding.lno.setTextColor(getResources().getColor(R.color.black));
                binding.rllno.setBackground(null);
                lang = "Yes";


            }
            break;
            case R.id.rllno: {
                binding.lyes.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rllyes.setBackground(null);
                lang = "No";

                binding.lno.setTextColor(getResources().getColor(R.color.white));
                binding.rllno.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));
            }
            break;
        }
    }

    @Override
    public void heighttextOnClick(String left, String Right) {
        height = left + "-" + Right;
        binding.etLeftheight.setText(left);
        binding.etRightheight.setText(Right);
    }



    public void dialogHeight() {
        DialogHeightBinding binding1;
        final Dialog dialog = new Dialog(DetailAboYouActivity.this,R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        binding1 = DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()), R.layout.dialog_height, null, false);
        dialog.setContentView(binding1.getRoot());
        //binding.cat.setText("Do you want to exit?");


        NumberPicker numberPicker = dialog.findViewById(R.id.number_picker);

        ///height number picker======================================

        numberPicker.setMaxValue(199);
        numberPicker.setMinValue(0);

        //int[] testArray = new int[50];
//        String[] pickerVals  = new String[] {"1", "2", "3", "4", "5"};

        String[] pickerVals  = new String[200];

        for (int i = 0,j=100; i < pickerVals.length; i++,j++) {

            String feet = centimeterToFeet(String.valueOf(j));

            pickerVals[i]= feet+"("+j+" cm )";

            //double feet1 = 0.0328 * j;

            //Log.e(TAG+"   :  "+feet1, feet+"   :inItView: iiiiiiiiii:  "+j );
        }

       // double feet1 = 0.0328 * 120;

       // Log.e(TAG+"   :  ", "   :inItView: iiiiiiiiii feet1 :  "+feet1 );


        Log.e(TAG, pickerVals+"   inItView: pikervalues length "+pickerVals.length );
        //ArrayList<String> arrayList = new ArrayList<>();
        //arrayList.addAll(Arrays.asList(pickerVals));

        numberPicker.setDisplayedValues(pickerVals);

        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setValue(54);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //int valuePicker1 = picker1.getValue();
                Log.e(TAG,i+"  picker value "+ pickerVals[i1]);

                Log.e(TAG+numberPicker.getValue(), i1+"  onValueChange: "+i );
            }
        });

        //==========================================================


        binding1.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        binding1.set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Log.e(TAG, "onClick: number "+numberPicker.getValue() );
                binding.tvHeight.setText(pickerVals[numberPicker.getValue()]);
            }
        });



        dialog.show();
    }


    @Override
    public void showLoaderProcess() {

        PDialog.pdialog(DetailAboYouActivity.this);
    }

    @Override
    public void hideLoaderProcess() {

        PDialog.hideDialog();
    }

    @Override
    public void onApiErrorResponse(String Errorresponse, String error_type) {

    }


















        /*===============================current location==================*/

    private void getAddress(Location location) {



        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address_line1 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            location_name = addresses.get(0).getThoroughfare() != null ? addresses.get(0).getThoroughfare() : addresses.get(0).getFeatureName();

            //savedLocation_name = addresses.get(0).getThoroughfare() != null ? addresses.get(0).getThoroughfare() : addresses.get(0).getFeatureName();
            //savedArea_Name = addresses.get(0).getSubLocality() != null ? addresses.get(0).getSubLocality() : location_name;

            area_name = addresses.get(0).getSubLocality() != null ? addresses.get(0).getSubLocality() : location_name;
            city = addresses.get(0).getLocality() != null ? addresses.get(0).getLocality() : addresses.get(0).getSubAdminArea();
            state = addresses.get(0).getAdminArea();
            pin_code = addresses.get(0).getPostalCode();
            latitude = String.valueOf(addresses.get(0).getLatitude());
            langitude = String.valueOf(addresses.get(0).getLongitude());

            if (city == null) {
                location_name = area_name = pin_code;
                city = state;
            } else if (city != null && addresses.get(0).getFeatureName().equalsIgnoreCase(addresses.get(0).getCountryName())) {
                location_name = area_name = address_line1.split(",", 1)[0];
            }


            Address obj = addresses.get(0);

            Log.e(TAG, city+" :getAddress: llaaaaaa  111111  "+obj.getCountryName() );


            country = obj.getCountryName();




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.e(TAG, "onConnected: " );
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        Log.e(TAG, "onConnected: location "+location );
        if (location != null) {
            getAddress(location);
            // Toast.makeText(HomeActivity.this, "Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude(), Toast.LENGTH_LONG).show();
            // Toast.makeText(HomeActivity.this, "Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude(), Toast.LENGTH_LONG).show();
        }
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        //Log.e(TAG, "onLocationChanged: "+location );
        if (location == null) {
            getAddress(location);
        }
    }

    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e(TAG, requestCode+" onActivityResult: "+resultCode );
        if(requestCode==1){

            if(resultCode==RESULT_OK){

                //Toast.makeText(this, "Gps opened", Toast.LENGTH_SHORT).show();
                //if user allows to open gps
                Log.e(TAG,"onActivityResult result ok"+data.toString());
                // Intent intent = new Intent(getApplicationContext(),CurrentAddAddressActivity.this);
               // LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

            }else if(resultCode==RESULT_CANCELED){

                // in case user back press or refuses to open gps
                Log.e(TAG,"onActivityResult result cancelled"+data.toString());
            }
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void permission() {
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = permissionsToRequest(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }
        // we build google api client
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            new AlertDialog.Builder(DetailAboYouActivity.this).
                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.
                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("Cancel", null).create().show();

                            return;
                        }
                    }
                } else {
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
                }

                break;
        }
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();
        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }


    private void gpsDialoge() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getApplicationContext()).addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                            LocationRequest locationRequest = LocationRequest.create();
                            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            locationRequest.setInterval(500);
                            locationRequest.setFastestInterval(500);
                            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                            builder.setAlwaysShow(true);
                            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                                @Override
                                public void onResult(LocationSettingsResult result) {
                                    final Status status = result.getStatus();
                                   /* switch (status.getStatusCode()) {
                                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                            try {
                                                // Show the dialog by calling startResolutionForResult(),
                                                // and check the result in onActivityResult().
                                                //status.startResolutionForResult(getActivity(), REQUEST_LOCATION);
                                                startIntentSenderForResult(status.getResolution().getIntentSender(),
                                                        REQUEST_LOCATION, null, 0, 0, 0, null);
                                                //nextScreen();
                                                // finish();
                                            } catch (IntentSender.SendIntentException e) {
                                                // Ignore the error.
                                            }
                                            break;
                                    }*/
                                }
                            });

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            // Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
        //make home selected
       /* if (!checkPlayServices()) {
            Toast.makeText(CurrentAddAddressActivity.this, "You need to install Google Play Services to use the App properly", Toast.LENGTH_LONG).show();
        }*/
    }



    //========================================location




    @Override
    public void onAboutInfoResponse(String s) {


        Log.e(TAG, "onAboutInfoResponse: "+s );
        
        
        if (s.contains("Something went wrong")){
            Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
        }else {
            
        
/*

        Intent intent = new Intent(DetailAboYouActivity.this, LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
*/

            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailAboYouActivity.this, StepCompleteActivity.class);
            intent.putExtra("img", img);
            intent.putExtra("strgender", strgender);
            intent.putExtra("strinterest", strinterest);
            intent.putExtra("date", date);
            intent.putExtra("orientation", orientation);
            intent.putExtra("looking", looking);
            intent.putExtra("ethnicityname", ethnicityname);
            intent.putExtra("bodytype", bodytype);
            intent.putExtra("haircolor", haircolor);
            intent.putExtra("htype", htype);
            intent.putExtra("married", married);
            intent.putExtra("child", child);
            intent.putExtra("smoke", smoke);
            intent.putExtra("lang", lang);
            intent.putParcelableArrayListExtra("imglist", imglist);
            intent.putExtra("height", "" + binding.tvHeight.getText());
            //intent.putExtra("height", binding.etHeight.getText().toString());
            intent.putExtra("skip", "skip1");
            intent.putExtra("country", country);
            intent.putExtra("city", city);
            intent.putExtra("pet", pets);
            intent.putExtra("religion", religion);
            intent.putExtra("datefood", datefood);
            intent.putExtra("education", "BE");
            intent.putExtra("mywork", mywork);
            intent.putExtra("drinkData", drink);
            startActivity(intent);


        }







    }



}
