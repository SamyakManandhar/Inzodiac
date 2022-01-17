package com.alphaa.inzodiac.ui.activity.likerequest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityDetailBinding;
import com.alphaa.inzodiac.databinding.ActivityRequestnewBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.staticdata.SetChineseWesternZodaic;
import com.alphaa.inzodiac.tabmodule.activity.chatmodule.ChatActivity;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailModel;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailPresenter;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.PhotoAdapter;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.SendQuizRequest;
import com.alphaa.inzodiac.ui.activity.loginmodule.LoginActivity;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.CheckNetwork;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.PDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import static com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient.IMAGE_BASE_URL;

public class LikeRequestActivityNew extends BaseActivity implements View.OnClickListener, ApiCallBackInterFace.DetailInfoCallback,ApiCallBackInterFace.LikeRequestCallback {
    String TAG = getClass().getSimpleName();

    private ActivityRequestnewBinding binding;
    private String uid;
    private String otherid,otherName,otherProfile;
    private ArrayList<DetailModel.ProfilePic> photoList;

    String comesfrom;

    HashMap<String,Integer> hashMapWestern;
    String m_userid,m_otheruserid,m_status,m_username,m_request_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_requestnew);
        photoList = new ArrayList<>();
//        uid = getIntent().getStringExtra("uid");
        //new DetailPresenter(this,this).detailData(uid);

        uid = AppSession.getStringPreferences(getApplicationContext(), Constants.USEREId);
        Log.e(TAG, "onCreate: uid "+uid );
        inItView();
        setAdapter();

        Log.e(TAG, "onCreate: " );

        /*if (comesfrom!=null){
            binding.tvSendRequest.setVisibility(View.VISIBLE);
            binding.llPlaygame.setVisibility(View.VISIBLE);
            binding.ivchat.setVisibility(View.VISIBLE);
        }else {
            binding.tvSendRequest.setVisibility(View.GONE);
            binding.llPlaygame.setVisibility(View.GONE);
            binding.ivchat.setVisibility(View.GONE);
        }*/

        Intent intent = getIntent();
        comesfrom = intent.getStringExtra("comesfrom");
        //m_userid = intent.getStringExtra("m_userid");
        m_otheruserid = intent.getStringExtra("otherid");
        //m_status = intent.getStringExtra("m_status");
        m_username = intent.getStringExtra("otherName");
        m_request_id = intent.getStringExtra("request_id");

        Log.e(TAG, m_otheruserid+"   :onCreate: 1111 " );
        Log.e(TAG, m_username+"   :onCreate: 2222 "+m_request_id );
        Log.e(TAG, comesfrom+"   :onCreate: 3333 " );

        if (uid!=null) {
            new DetailPresenter(this, this).detailData(m_otheruserid);
        }else {
            Intent i = new Intent(LikeRequestActivityNew.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    private void setAdapter() {
        PhotoAdapter adapter = new PhotoAdapter(activity,photoList);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);

    }

    private void inItView() {
        setClicks(binding.ivback,binding.ivdislike,binding.ivlike,binding.tvSendRequest);

    }


    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_send_request:

                String uid = getPrefHelper().getUserId();
                new DetailPresenter(this,this).sendRequest(uid,otherid);


                break;

            case R.id.ivback: {
                onBackPressed();
            }
            break;
            case R.id.ivlike: {

                Log.e(TAG, m_request_id+"  onClick: userid "+m_userid );
                if (CheckNetwork.isNetAvailable(getApplicationContext())) {

                    new LikeRequestPresenter(this, this).quizRequestAcceptRegect(m_otheruserid,"1",m_request_id);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), Toast.LENGTH_LONG).show();
                }

            }
            break;



            case R.id.ivdislike: {
                if (CheckNetwork.isNetAvailable(getApplicationContext())) {

                    new LikeRequestPresenter(this, this).quizRequestAcceptRegect(m_otheruserid,"2",m_request_id);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), Toast.LENGTH_LONG).show();
                }
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
        //finish();
    }

    @Override
    public void onSendRequesr(String s) {


        Log.e(TAG, "onSendRequesr: ssss "+s );

        Toast.makeText(activity, ""+s, Toast.LENGTH_SHORT).show();
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
            binding.tvhair.setVisibility(View.GONE);
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


    @Override
    public void onLikeRequestResponse(String s) {
        Log.e(TAG, "onLikeRequestResponse: ssssss "+s );
        Toast.makeText(activity, ""+s, Toast.LENGTH_SHORT).show();
        finish();
    }
}