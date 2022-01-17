package com.alphaa.inzodiac.tabmodule.fragment.filtermodule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseFragment;
import com.alphaa.inzodiac.databinding.FragmentFilterBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.EthnicityModel;
import com.alphaa.inzodiac.utility.PDialog;
import com.google.gson.Gson;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends BaseFragment implements ApiCallBackInterFace.FilterInfoCallback, View.OnClickListener {

    String TAG = getClass().getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentFilterBinding binding;
    private ArrayList<CountryResponse.DataItem> countryList;
    private ArrayList<CityResponse.DataItem> cityList;
    private String id;
    private boolean bool1 = false;
    private boolean bool2 = false;
    private boolean bool3 = false;
    private boolean bool4 = false;
    private boolean bool5 = false;
    private boolean bool6 = false;
    private static String catid = "";
    private int total = 0;
    private int tot1 = 0,tot2 = 0,tot3 = 0,tot4 = 0,tot5 = 0,tot6 = 0,tot7 = 0;
    private String userid,looking_for="",age="",ethnicity="",bodytype="",intent="",country="",city="", intrested_in,
            orientation,
            horoscopetype,
            relationship,
            children,
            smoke,
            drink,
            from_age="0",
            to_age="100";
    int progressChangedValue = 0;

    public static FilterFragment newInstance(String param1, String param2) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        catid =param1;
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inItView();

    }


    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }

    private void inItView() {
        countryList = new ArrayList<>();
        cityList = new ArrayList<>();
        new FilterPresenter(activity, this).countriesData();
        //new FilterPresenter(activity, this).cityData();
        lookingSp();
        intentSp();
        bodySp();
        ethncitySp();
        rangeSpinner();
        setClicks(binding.ivfilter1);


        distanceSeekBar();

        interestedinSp();
        orientationSp();
        preferdhoroscopeSp();
        childrenSp();
        smokeSp();
        drinkSp();


    }

    private void distanceSeekBar() {
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;

                Log.e(TAG, "onProgressChanged: "+progressChangedValue );
                binding.startdistance.setText(""+progressChangedValue+" Miles");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void lookingSp() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Looking For");
        arrayList.add("Don't know");
        arrayList.add("Chat");
        arrayList.add("Business relationship");
        arrayList.add("Friends");
        arrayList.add("Casual");
        arrayList.add("Serious relationship");

        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(activity, arrayList);
        binding.looksp.setAdapter(adapter);

        binding.looksp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                if (bool1 && position !=0) {
                    tot1 =1;
                    total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);
                    looking_for = arrayList.get(position);
                    binding.tvlook.setText(looking_for);
                    binding.rllook.setBackground(getResources().getDrawable(R.drawable.purple_boarder_back));
                    binding.tv1.setTextColor(getResources().getColor(R.color.white));
                    binding.tvlook.setTextColor(getResources().getColor(R.color.white));
                    binding.iv1.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_white_arrow));
                }
                bool1 = true;

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void intentSp() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Relationship");
        arrayList.add("Single");
        arrayList.add("Complicated");
        arrayList.add("Partnered");
        arrayList.add("Married");
        arrayList.add("N/A");

        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(activity, arrayList);
        binding.intnetsp.setAdapter(adapter);

        binding.intnetsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                if (bool2 && position !=0) {
                    tot2 =1;
                    total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);
                    intent = arrayList.get(position);
                    binding.tvintnet.setText(intent);
                    binding.rlintnet.setBackground(getResources().getDrawable(R.drawable.purple_boarder_back));
                    binding.tv2.setTextColor(getResources().getColor(R.color.white));
                    binding.tvintnet.setTextColor(getResources().getColor(R.color.white));
                    binding.iv2.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_white_arrow));
                }
                bool2 = true;
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void bodySp() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Exercise");
        arrayList.add("Regularly");
        arrayList.add("Every now & then");
        arrayList.add("I will if you will");
        arrayList.add("I'm unable to exercise");
        arrayList.add(" No thanks");
        arrayList.add("N/A");














        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(activity, arrayList);
        binding.bodysp.setAdapter(adapter);

        binding.bodysp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                if (bool3 && position !=0) {
                    tot3 =1;
                    total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);
                    bodytype = arrayList.get(position);
                    binding.tvbody.setText(bodytype);
                    binding.rlbody.setBackground(getResources().getDrawable(R.drawable.purple_boarder_back));
                    binding.tv3.setTextColor(getResources().getColor(R.color.white));
                    binding.tvbody.setTextColor(getResources().getColor(R.color.white));
                    binding.iv3.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_white_arrow));
                }
                bool3 = true;
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void ethncitySp() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Ethnicity");
        arrayList.add("Native American");
        arrayList.add("Black African");
        arrayList.add("Black West Indian");
        arrayList.add("East Asian");
        arrayList.add("Hispanic/Latino");
        arrayList.add("Midle Eastern");
        arrayList.add("Pacific Islander");
        arrayList.add("South Asian");
        arrayList.add("Mixed Race");
        arrayList.add("White/Caucasian");
        arrayList.add("Other");


        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(activity, arrayList);
        binding.ethncitysp.setAdapter(adapter);

        binding.ethncitysp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                if (bool4 && position !=0) {
                    tot4 =1;
                    total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);
                    ethnicity = arrayList.get(position);
                    binding.tvethncity.setText(ethnicity);
                    binding.rlethncity.setBackground(getResources().getDrawable(R.drawable.purple_boarder_back));
                    binding.tv4.setTextColor(getResources().getColor(R.color.white));
                    binding.tvethncity.setTextColor(getResources().getColor(R.color.white));
                    binding.iv4.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_white_arrow));
                }
                bool4 = true;

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }




    private void interestedinSp() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Interested In");
        arrayList.add("Male");
        arrayList.add("Female");
        arrayList.add("Non-Binary");

        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(activity, arrayList);
        binding.interestsp.setAdapter(adapter);

        binding.interestsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                if (bool4 && position !=0) {
                    tot4 =1;
                    total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);
                    intrested_in = arrayList.get(position);
                    binding.tvinterestin.setText(intrested_in);
                    binding.rlinterestin.setBackground(getResources().getDrawable(R.drawable.purple_boarder_back));
                    binding.tvInterestinHeading.setTextColor(getResources().getColor(R.color.white));
                    binding.tvinterestin.setTextColor(getResources().getColor(R.color.white));
                    binding.ivInterestedin.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_white_arrow));
                }
                bool4 = true;

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void orientationSp() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Orientation");
        arrayList.add("Straight");
        arrayList.add("Gay");

        arrayList.add("Lesbian");
        arrayList.add("Bisexual");
        arrayList.add("Asexual");
        arrayList.add("Demisexual");
        arrayList.add("Pansexual");
        arrayList.add("Queer");
        arrayList.add("Questioning");

        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(activity, arrayList);
        binding.orientationSp.setAdapter(adapter);

        binding.orientationSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                if (bool4 && position !=0) {
                    tot4 =1;
                    total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);
                    orientation = arrayList.get(position);
                    binding.tvOrientation.setText(orientation);
                    binding.rlorientation.setBackground(getResources().getDrawable(R.drawable.purple_boarder_back));
                    binding.tvOrientationheading.setTextColor(getResources().getColor(R.color.white));
                    binding.tvOrientation.setTextColor(getResources().getColor(R.color.white));
                    binding.ivOrientation.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_white_arrow));
                }
                bool4 = true;

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    private void preferdhoroscopeSp() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Horoscope Type");
        arrayList.add("Western");
        arrayList.add("Chinese");
        arrayList.add("Both");

        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(activity, arrayList);
        binding.preferedhoroscopeSp.setAdapter(adapter);

        binding.preferedhoroscopeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                if (bool4 && position !=0) {
                    tot4 =1;
                    total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);
                    horoscopetype = arrayList.get(position);
                    binding.tvPreferdhorscope.setText(horoscopetype);
                    binding.rlpreferedhoroscope.setBackground(getResources().getDrawable(R.drawable.purple_boarder_back));
                    binding.tvPreferedhoroscopeheading.setTextColor(getResources().getColor(R.color.white));
                    binding.tvPreferdhorscope.setTextColor(getResources().getColor(R.color.white));
                    binding.ivPreferedhoroscope.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_white_arrow));
                }
                bool4 = true;

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    private void childrenSp() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Wants Children");
        arrayList.add("No-Never");
        arrayList.add("Maybe someday");
        arrayList.add("Already have");

        arrayList.add("All grown up");
        arrayList.add("N/A");














        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(activity, arrayList);
        binding.childrenSp.setAdapter(adapter);

        binding.childrenSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                if (bool4 && position !=0) {
                    tot4 =1;
                    total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);
                    children = arrayList.get(position);
                    binding.tvChildren.setText(children);
                    binding.rlchildren.setBackground(getResources().getDrawable(R.drawable.purple_boarder_back));
                    binding.tvChildrenheading.setTextColor(getResources().getColor(R.color.white));
                    binding.tvChildren.setTextColor(getResources().getColor(R.color.white));
                    binding.ivChildren.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_white_arrow));
                }
                bool4 = true;

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    private void smokeSp() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Smoke");
        arrayList.add("Never");
        arrayList.add("Socially");
        arrayList.add("Regularly");
        arrayList.add("N/A");







        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(activity, arrayList);
        binding.smokeSp.setAdapter(adapter);

        binding.smokeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                if (bool4 && position !=0) {
                    tot4 =1;
                    total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);
                    smoke = arrayList.get(position);
                    binding.tvSmoke.setText(smoke);
                    binding.rlsmoke.setBackground(getResources().getDrawable(R.drawable.purple_boarder_back));
                    binding.tvSmokeheading.setTextColor(getResources().getColor(R.color.white));
                    binding.tvSmoke.setTextColor(getResources().getColor(R.color.white));
                    binding.ivSmoke.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_white_arrow));
                }
                bool4 = true;

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }



    private void drinkSp() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Drink");
        arrayList.add("Never");
        arrayList.add("Socially");
        arrayList.add("Regularly");
        arrayList.add("N/A");








        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(activity, arrayList);
        binding.drinkSp.setAdapter(adapter);

        binding.drinkSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                if (bool4 && position !=0) {
                    tot4 =1;
                    total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);
                    drink = arrayList.get(position);
                    binding.tvDrink.setText(drink);
                    binding.rldrink.setBackground(getResources().getDrawable(R.drawable.purple_boarder_back));
                    binding.tvDrinkheading.setTextColor(getResources().getColor(R.color.white));
                    binding.tvDrink.setTextColor(getResources().getColor(R.color.white));
                    binding.ivDrink.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_white_arrow));
                }
                bool4 = true;

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }




    private void rangeSpinner() {


        binding.sbRangeAge.setProgress(18F, 100F);
        binding.sbRangeAge.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float sb_range_3, float rightValue, boolean isFromUser) {
                int intvalue = (int) Math.round(sb_range_3);

                Log.e(TAG, "onRangeChanged: "+intvalue );
                if (intvalue > 0) {
                    tot7 = 1;
                    total = tot1 + tot2 + tot3 + tot4 + tot5 + tot6 + tot7;
                }

                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);

                String mytext = String.valueOf(intvalue);
                binding.startage.setText(mytext);
                from_age = mytext;

                age = mytext;

                int intvalue1 = (int) Math.round(rightValue);
                String mytext1 = String.valueOf(intvalue1);
                binding.endage.setText(mytext1);
                to_age = mytext1;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }
        });


    }



    //distance









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
                    tot5 =1;
                    total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);
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
                    tot6 =1;
                    total = tot1+tot2+tot3+tot4+tot5+tot6+tot7;
                    binding.tot.setText(String.valueOf(total));
                    binding.lrtot.setVisibility(View.VISIBLE);
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

        Log.e(TAG, "onFilterInfoResponse: sssssssss "+s );

        String s1 = s.replace("null","\"\"");
        Intent intent = new Intent(activity, TabActivity.class);
        intent.putExtra("userlist",s1);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void showLoaderProcess() {
        PDialog.pdialog(activity);
    }

    @Override
    public void hideLoaderProcess() {
        PDialog.hideDialog();
    }

    @Override
    public void onApiErrorResponse(String Errorresponse, String error_type) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivfilter1:{
                userid = getPrefHelper().getUserId();
                catid = getPrefHelper().getCatId();

                Log.e(TAG, progressChangedValue+"  onClick: ivfilter1  "+"   "+TabActivity.latt+"  :  "+TabActivity.longg);

                String latt = String.valueOf(TabActivity.latt);
                String longg = String.valueOf(TabActivity.longg);


                if (progressChangedValue ==0){
                    latt = "";
                    longg = "";
                }else {
                    if (latt.equalsIgnoreCase("0.0")) {

                        latt = "";
                        longg = "";
                    }
                }
                Log.e(TAG, progressChangedValue+"  onClick: ivfilter1 after  "+"   "+latt+"  :  "+longg);


                new FilterPresenter(activity,this).UserListData(catid,userid,looking_for,"",ethnicity,
                        bodytype,"",country,city,""+progressChangedValue,
                       // ""+TabActivity.latt,""+TabActivity.longg,

                        latt,longg,
                        intrested_in,
                        orientation,
                        horoscopetype,
                        intent,
                        children,
                        smoke,
                        drink,
                        from_age,
                        to_age,
                        "");

            }
            break;
        }
    }


}