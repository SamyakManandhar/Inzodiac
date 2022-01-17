package com.alphaa.inzodiac.tabmodule.fragment.profilemodule.profiledetailmodule;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseFragment;
import com.alphaa.inzodiac.databinding.FragmentProfileDetailBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.staticdata.BirthdayData;
import com.alphaa.inzodiac.staticdata.ChineseData;
import com.alphaa.inzodiac.staticdata.SetChineseWesternZodaic;
import com.alphaa.inzodiac.staticdata.WesternData;
import com.alphaa.inzodiac.staticdata.ZodaicActivity;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailModel;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.PhotoAdapter;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.tabmodule.fragment.luckmodule.LuckyandunluckyFragment;
import com.alphaa.inzodiac.tabmodule.fragment.profilemodule.settingmodule.SettingFragment;
import com.alphaa.inzodiac.tabmodule.fragment.tokenmodule.TokenFragment;
import com.alphaa.inzodiac.ui.activity.editprofile.EditProfileActivity;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.PDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient.IMAGE_BASE_URL;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileDetailFragment extends BaseFragment implements View.OnClickListener, ApiCallBackInterFace.ProfileDetailInfoCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

   // String TAG = getClass().getSimpleName();

    public String TAG = getClass().getSimpleName();

String profiledata;
    private FragmentProfileDetailBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<DetailModel.ProfilePic> photoList;




    /////////////zodaic

    HashMap<String,Integer> hashMapChinese;
//    Date strDobDate,strStartYear,strendYear;

    ChineseData chineseData;
    String getasociated;


    //western
    HashMap<String,Integer> hashMapWestern;

    // TODO: Rename and change types of parameters

    // TODO: Rename and change types and number of parameters
    public static ProfileDetailFragment newInstance(String param1, String param2) {
        ProfileDetailFragment fragment = new ProfileDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_detail , container, false);
        return binding.getRoot();
    }

    private void setAdapter() {
        PhotoAdapter adapter = new PhotoAdapter(activity,photoList);
        LinearLayoutManager manager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photoList = new ArrayList<>();
        inItView();



        String dob = AppSession.getStringPreferences(getContext(), Constants.USERBIRTHDATE);


        /*try {
            setAllDataChinese(dob);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Log.e(TAG, "onViewCreated: dob "+dob );


        //setchinese


        binding.ivChinese.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));




        if (dob!=null&&!dob.isEmpty()) {
            SetChineseWesternZodaic.setAllDataChinese(dob, binding.ivChinese, binding.tvChinese);

            String getasociated = birthDateMatch(dob);

            //binding.ivChinese.setVisibility(View.VISIBLE);
            //binding.tvChinese.setVisibility(View.VISIBLE);
        }else {
            binding.ivChinese.setVisibility(View.GONE);
            binding.tvChinese.setVisibility(View.GONE);
        }



        //set western
        if (dob!=null&&!dob.isEmpty()) {

            //2020/12/26
            //1900/1/20

            westernIconData();
            String strwesternzodaic = SetChineseWesternZodaic.birthDateMatchWithWestern(dob);
            Log.e(TAG, "onViewCreated: strwesternzodaic  "+strwesternzodaic );

            if (!strwesternzodaic.matches("no")) {
                binding.ivWestern.setImageResource(hashMapWestern.get(strwesternzodaic));
                binding.tvWestern.setText(strwesternzodaic);
            }
        }else {
            binding.ivWestern.setVisibility(View.GONE);
            binding.tvWestern.setVisibility(View.GONE);
        }


    }

    private void inItView() {
        setClicks(binding.setting,binding.ivlike,binding.ivchat,binding.token,binding.llChineseWestern, binding.tvEditprofile);

    }


    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.setting:
            {
                SettingFragment profileFragment = SettingFragment.newInstance("", "");
                replaceFragment(profileFragment, binding.frame.getId(), true);

                binding.frame.setVisibility(View.VISIBLE);
                binding.rl.setVisibility(View.GONE);
            }
            break;

            case R.id.token:
            {
                TokenFragment profileFragment = TokenFragment.newInstance("", "");
                replaceFragment(profileFragment, binding.frame.getId(), false);
                binding.frame.setVisibility(View.VISIBLE);
                binding.rl.setVisibility(View.GONE);
            }
            break;

            case R.id.tv_editprofile:
            {
                Intent intent = new Intent(activity, EditProfileActivity.class);
                intent.putExtra("profiledata",profiledata);
                startActivity(intent);
//                activity.finish();
            }
            break;


            case R.id.ll_chinese_western:
            {

               /* Bundle bundle = new Bundle();
                //String myMessage = "Stackoverflow is cool!";
                bundle.putString("message", getasociated );
                //fragInfo.setArguments(bundle);

                LuckyandunluckyFragment profileFragment = LuckyandunluckyFragment.newInstance("", "");

                profileFragment.setArguments(bundle);

                replaceFragment(profileFragment, binding.frame.getId(), true);
                binding.frame.setVisibility(View.VISIBLE);
                binding.rl.setVisibility(View.GONE);*/
            }
            break;
            case R.id.ivlike: {
                Intent intent = new Intent(activity, TabActivity.class);
                intent.putExtra("intent","1");
                startActivity(intent);
                activity.finish();
            }
            break;

            case R.id.ivchat: {
                Intent intent = new Intent(activity, TabActivity.class);
                intent.putExtra("intent","2");
                startActivity(intent);
                activity.finish();
            }
            break;
        }
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

    private void setDetailData(DetailModel detailModel) {
        DetailModel.Data data = detailModel.getData();
        binding.llmain.setVisibility(View.VISIBLE);

        Log.e(TAG, "setDetailData: profile data " );
        try {

            binding.tvname.setText(data.getName());
            binding.tvageGender.setText(data.getGender());
            binding.tvDob.setText(data.getDob());
            binding.tvLocation.setText(data.getCity()+","+data.getCountry());
            binding.tvOrientation.setText(data.getOrientation());
            binding.tvLookingfor.setText(data.getLookingFor());
            binding.tvWantchild.setText(data.getChildren());
            binding.tvSmoke.setText(data.getSmoke());
            binding.tvReligion.setText(data.getReligion());
            binding.tvDrink.setText(data.getDrink());
            binding.tvIntrestedin.setText(data.getIntrestedIn());
            binding.tvheight.setText(data.getHeight());
            binding.tvPets.setText(data.getPets());
            binding.tvabout.setText(data.getAbout());
            binding.tvethncity.setText(data.getEthnicity());
            binding.tvlang.setText(data.getLanguage());
            binding.tvbodytype.setText(data.getBodytype());
            binding.tvhair.setText(data.getHaircolour());
            binding.htype.setText(data.getHoroscopetype());
            binding.tvRelation.setText(data.getMarried());
            photoList = data.getProfilePic();

            Log.e(TAG, "setDetailData: photo "+photoList );
            if (photoList==null){
               // Glide.with(this).load("").into(binding.ivpic);
                Glide.with(activity).load("").placeholder(R.drawable.back).into(binding.ivpic);
            }else if (photoList.size()==0){
                //Glide.with(this).load("").into(binding.ivpic);
                Glide.with(activity).load("").placeholder(R.drawable.back).into(binding.ivpic);
            }
            if (!photoList.get(0).getImage().isEmpty()) {
                Glide.with(this).load(IMAGE_BASE_URL + photoList.get(0).getImage()).into(binding.ivpic);
            }
        }catch (Exception e){

        }
        setAdapter();
//        binding.tvrstatus.setText(data.r());
    }

    @Override
    public void onProfiledetailInfoResponse(String s) {
        Log.e(TAG, "onProfiledetailInfoResponse: "+s );
        String s1 = s.replace("null","\"\"");
        profiledata = s1;
        DetailModel detailModel = new Gson().fromJson(s1,DetailModel.class);
        setDetailData(detailModel);
    }


    @Override
    public void onResume() {
        super.onResume();
        System.out.println("callll resume====profile detail");
        String uid = getPrefHelper().getUserId();
        System.out.println("====uidddd==on resume=="+uid);
        new ProfileDetailPresenter(activity,this).detailData(uid);
    }

    ////******************************chinese zodaic sign***************starts
    public void setAllDataChinese(String userbirthdate){

        ArrayList<ChineseData> chineseDataArrayList = ZodaicActivity.chinesedata();

        try {
            getasociated = birthDateMatch(userbirthdate);
        }catch (Exception e){

        }


        ///set zodiac icon
        chineseIconData();
        binding.ivChinese.setImageResource(hashMapChinese.get(getasociated));
        binding.tvChinese.setText(getasociated);

        if (!getasociated.matches("no")){

            int index = 0;
            for(int i = 0; i<chineseDataArrayList.size(); i++){
                if(chineseDataArrayList.get(i).getChineseZodaic().matches(getasociated.toUpperCase())){
                    index = i;
                    break;
                }
            }

            Log.e(TAG, "birthDayData: indexxx 7777 "+index );

            chineseData = chineseDataArrayList.get(index);

        }

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


    public String birthDateMatch(String dateofbirth){
        String asociateAnimal = "no";
        ArrayList<BirthdayData> birthdayDataArrayList = ZodaicActivity.birthDayData();

        Log.e(TAG+birthdayDataArrayList.size(), "birthDateMatch: "+dateofbirth );
        for (int i = 0; i <birthdayDataArrayList.size(); i++) {




            //=================================================
            Date strDobDate = null,strStartYear=null,strendYear=null;



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


                if (strDobDate.compareTo(strStartYear) > 0 && strDobDate.compareTo(strendYear) < 0) {
                    ///System.out.println("Date 1 occurs after Date 2");

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




    //+++++++++++++++++++++++western data========================starts
    public void setAllDataWestern(String z_){

        //westerdata();

        WesternData westernData = getWesternDataSingleRow(z_);

        Log.e(TAG, "setAllDataWestern: western "+westernData.getWesternZodiac() );


    }
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
    //+++++++++++++++++++++++western data========================ends









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