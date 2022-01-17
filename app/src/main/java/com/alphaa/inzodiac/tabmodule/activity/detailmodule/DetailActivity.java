package com.alphaa.inzodiac.tabmodule.activity.detailmodule;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.addminustoken.TokenAddPresenter;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityDetailBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.staticdata.SetChineseWesternZodaic;
import com.alphaa.inzodiac.tabmodule.activity.chatmodule.ChatActivity;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.SendQuizRequest;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.PDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import static com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient.IMAGE_BASE_URL;

public class DetailActivity extends BaseActivity implements View.OnClickListener, ApiCallBackInterFace.DetailInfoCallback {
    String TAG = getClass().getSimpleName();

    private ActivityDetailBinding binding;
    private String uid;
    private String otherid,otherName,otherProfile;
    private ArrayList<DetailModel.ProfilePic> photoList;

    String comesfrom;

    HashMap<String,Integer> hashMapWestern;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        photoList = new ArrayList<>();
        uid = getIntent().getStringExtra("uid");
        new DetailPresenter(this,this).detailData(uid);
        inItView();
        setAdapter();

        Log.e(TAG, "onCreate: " );

        Intent intent = getIntent();
        comesfrom = intent.getStringExtra("comesfrom");

        Log.e(TAG, "onCreate: comes from "+comesfrom );
        if (comesfrom!=null){
            binding.tvSendRequest.setVisibility(View.VISIBLE);
            binding.llPlaygame.setVisibility(View.VISIBLE);
            binding.ivchat.setVisibility(View.VISIBLE);
            binding.ivlike.setVisibility(View.GONE);
            binding.ivdislike.setVisibility(View.VISIBLE);
        }else {
            binding.tvSendRequest.setVisibility(View.GONE);
            binding.llPlaygame.setVisibility(View.GONE);
            binding.ivchat.setVisibility(View.GONE);
            binding.ivlike.setVisibility(View.VISIBLE);
            binding.ivdislike.setVisibility(View.VISIBLE);
        }
    }

    private void setAdapter() {
        PhotoAdapter adapter = new PhotoAdapter(activity,photoList);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);

    }

    private void inItView() {
        setClicks(binding.ivback,binding.ivchat,binding.ivlike,binding.ivdislike,binding.tvSendRequest);

    }


    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_send_request:

                /*if (AppSession.getStringPreferences(getApplicationContext(), Constants.TOTAL_TOKEN)!=null)
                {
                    if (Integer.parseInt(AppSession.getStringPreferences(getApplicationContext(), Constants.TOTAL_TOKEN))>0){
                        String uid = getPrefHelper().getUserId();
                        new DetailPresenter(this,this).sendRequest(uid,otherid);

                    }else {
                        Toast.makeText(this, "Don't have enough tokens to send request, Please purchase tokens.", Toast.LENGTH_SHORT).show();
                    }
                }*/
                String uid = getPrefHelper().getUserId();
                new DetailPresenter(this,this).sendRequest(uid,otherid);

                

                break;

            case R.id.ivback:
                onBackPressed();
            break;
            case R.id.ivlike:

                Log.e(TAG, "onClick: like button" );

                /*if (AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE)==null){
                    Toast.makeText(this, "Please Activate Premium Subscription", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE).toLowerCase().contains("yes")){

                    String uid1 = getPrefHelper().getUserId();
                new DetailPresenter(this,this).sendLikeRequest(uid1,otherid);

                }else {
                    Toast.makeText(this, "Can't send Request, Please Activate Premium Subscription", Toast.LENGTH_SHORT).show();
                }*/

                String uid1 = getPrefHelper().getUserId();
                new DetailPresenter(this,this).sendLikeRequest(uid1,otherid);


                break;

            case R.id.ivdislike:

                Log.e(TAG, "onClick: dislike button" );
                String uid2 = getPrefHelper().getUserId();
                new DetailPresenter(this,this).dislikeRequest(uid2,otherid);


            break;
            case R.id.ivchat: {
                getPrefHelper().setotherid(otherid);
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("otherid",otherid);
                intent.putExtra("otherName",otherName);
                intent.putExtra("otherProfile",otherProfile);
                startActivity(intent);
                finish();
            }
            break;
        }
    }

    @Override
    public void ondetailInfoResponse(String s) {

        Log.e(TAG, "ondetailInfoResponse: ssssssssss "+s );
        String s1 = s.replace("null","\"\"");
         DetailModel detailModel = new Gson().fromJson(s1,DetailModel.class);
         setDetailData(detailModel);
    }

    @Override
    public void onFavoriteInfoResponse(String s) {


        Log.e(TAG, "onFavoriteInfoResponse: ssssss "+s );


        SendQuizRequest detailModel = new Gson().fromJson(s, SendQuizRequest.class);
        Log.e(TAG, "onFavoriteInfoResponse: "+detailModel.getMessage() );
        Toast.makeText(activity, ""+detailModel.getMessage(), Toast.LENGTH_SHORT).show();



        Intent intent = new Intent(this, TabActivity.class);
        intent.putExtra("intent","1");
        //startActivity(intent);
        finish();
    }

    @Override
    public void onSendRequesr(String s) {


        Log.e(TAG, "onSendRequesr: ssss "+s );

        Toast.makeText(activity, ""+s, Toast.LENGTH_SHORT).show();


        new TokenAddPresenter(DetailActivity.this, new ApiCallBackInterFace.TokenAddCallBack() {
            @Override
            public void onTokenAdd(String s) {

            }

            @Override
            public void showLoaderProcess() {

            }

            @Override
            public void hideLoaderProcess() {

            }

            @Override
            public void onApiErrorResponse(String Errorresponse, String error_type) {

            }
        })
                .addToken(AppSession.getStringPreferences(getApplicationContext(), Constants.USEREId)
                        ,"1"
                        ,"minus");

    }


    @Override
    public void showLoaderProcess() {

        PDialog.pdialog(this);
    }

    @Override
    public void hideLoaderProcess() {
        PDialog.hideDialog();
    }

    @Override
    public void onApiErrorResponse(String Errorresponse, String error_type) {

    }

    private void setDetailData(DetailModel detailModel) {

        binding.llmain.setVisibility(View.VISIBLE);



        DetailModel.Data data = detailModel.getData();


        Log.e(TAG, "setDetailData: 11111111"+new Gson().toJson(detailModel.getData(),DetailModel.Data.class) );
        Log.e(TAG, "setDetailData: 22222222 "+data.getId() );
        otherid =data.getId();
        otherName =data.getName();
        otherProfile ="";

        try {

            binding.tvname.setText(data.getName());
            binding.tvageGender.setText(data.getAge() + ", " + data.getGender());
            binding.tvabout.setText(data.getAbout());
            binding.tvethncity.setText(data.getEthnicity());
            binding.tvlang.setText(data.getLanguage());
            binding.tvbodytype.setText(data.getBodytype());
            binding.tvhair.setText(data.getHaircolour());
            binding.htype.setText(data.getHoroscopetype());
            photoList = data.getProfilePic();
            if (!photoList.get(0).getImage().isEmpty()) {
                Glide.with(this).load(IMAGE_BASE_URL + photoList.get(0).getImage()).into(binding.ivpic);

            }

        }catch (Exception e){

        }

        setAdapter();

//        binding.tvrstatus.setText(data.r());

        Log.e(TAG, "setDetailData: dob "+data.getDob() );






        //*******************************set chinese zodaic=======================

        String newDateString = null;
        Log.e(TAG, "setDetailData: doobbb "+data.getDob() );

        if (data.getDob()!=null&&!data.getDob().isEmpty()) {

            Log.e(TAG, "setDetailData: iiiiiiiiiiiiiiffffffff" );

          /*  String date = data.getDob();
            Log.e(TAG, "setDetailData: date  "+data.getDob() );
            SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yyyy");
            Date newDate = null;
            try {
                newDate = spf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("yyyy/MM/dd");
            newDateString = spf.format(newDate);

            Log.e(TAG, "setDetailData: newdate "+newDateString );*/


            SetChineseWesternZodaic.setAllDataChinese(data.getDob(), binding.ivChinese, binding.tvChinese);

            //binding.ivChinese.setVisibility(View.VISIBLE);
            //binding.tvChinese.setVisibility(View.VISIBLE);
        }else {

            Log.e(TAG, "setDetailData: eeeeeeeeeeeee " );
            binding.ivChinese.setVisibility(View.GONE);
            binding.tvChinese.setVisibility(View.GONE);

            binding.ivWestern.setVisibility(View.GONE);
            binding.tvWestern.setVisibility(View.GONE);
        }

        //********************************set chinese************************************ends



        //============================set western======================================starts
        //set western
        if (data.getDob()!=null&&!data.getDob().isEmpty()) {

            westernIconData();
            String strwesternzodaic = SetChineseWesternZodaic.birthDateMatchWithWestern(data.getDob());
            Log.e(TAG, "onViewCreated: strwesternzodaic  "+strwesternzodaic );

            binding.ivWestern.setImageResource(hashMapWestern.get(strwesternzodaic));
            binding.tvWestern.setText(strwesternzodaic);
        }else {
            Log.e(TAG, "setDetailData: 33333333333333" );
            binding.ivWestern.setVisibility(View.GONE);
            binding.tvWestern.setVisibility(View.GONE);
        }
        //==========================set western========================================ends









    }







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