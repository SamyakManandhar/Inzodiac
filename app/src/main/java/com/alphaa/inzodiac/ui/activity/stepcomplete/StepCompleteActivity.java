package com.alphaa.inzodiac.ui.activity.stepcomplete;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityStepCompleteBinding;
import com.alphaa.inzodiac.gallery.models.Image;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.staticdata.BirthdayData;
import com.alphaa.inzodiac.staticdata.SetChineseWesternZodaic;
import com.alphaa.inzodiac.staticdata.ZodaicActivity;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailModel;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.tabmodule.fragment.profilemodule.profiledetailmodule.ProfileDetailPresenter;
import com.alphaa.inzodiac.ui.activity.aboutself.AboutSelfActivity;
import com.alphaa.inzodiac.ui.activity.aboutself.AboutSelfPresenter;
import com.alphaa.inzodiac.ui.activity.loginmodule.LoginActivity;
import com.alphaa.inzodiac.ui.activity.loginmodule.LoginPresenter;
import com.alphaa.inzodiac.ui.activity.phoneverification.PhoneVerificationActivity;
import com.alphaa.inzodiac.ui.activity.signupmodule.UserdatailModel;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.FirebaseUserModel;
import com.alphaa.inzodiac.utility.PDialog;
import com.alphaa.inzodiac.utility.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient.IMAGE_BASE_URL;

public class StepCompleteActivity extends BaseActivity implements View.OnClickListener, ApiCallBackInterFace.AboutInfoCallback, ApiCallBackInterFace.loginInfoCallback {
    String TAG = getClass().getSimpleName();

    ActivityStepCompleteBinding binding;
    String height = "", img = "", strgender = "", strinterest = "", orientation = "", looking = "", date = "", ethnicityname = "", bodytype = "", haircolor = "",
            htype = "", married = "", child = "", smoke = "", drink = "", lang = "", city = "", country = "", pet = "", religion = "", datefood = "", education = "", mywork = "";
    ArrayList<Image> imglist;


    Date strDobDate, strStartYear, strendYear;

    String chinesezodaic, westernzodaic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_step_complete);
        inItView();

    }

    private void inItView() {
        setClicks(binding.finish, binding.skip, binding.ivback);

        //if (!getIntent().getStringExtra("skip").equals("skip")) {
        if (getIntent().getStringExtra("img") != null) {
            img = getIntent().getStringExtra("img");
        }
        imglist = getIntent().getParcelableArrayListExtra("imglist");
        strgender = getIntent().getStringExtra("strgender");
        strinterest = getIntent().getStringExtra("strinterest");
        date = getIntent().getStringExtra("date");
        orientation = getIntent().getStringExtra("orientation");
        looking = getIntent().getStringExtra("looking");
        ethnicityname = getIntent().getStringExtra("ethnicityname");
        bodytype = getIntent().getStringExtra("bodytype");
        haircolor = getIntent().getStringExtra("haircolor");
        htype = getIntent().getStringExtra("htype");
        married = getIntent().getStringExtra("married");
        child = getIntent().getStringExtra("child");
        smoke = getIntent().getStringExtra("smoke");
        lang = getIntent().getStringExtra("lang");
        height = getIntent().getStringExtra("height");


        country = getIntent().getStringExtra("country");
        city = getIntent().getStringExtra("city");

        pet = getIntent().getStringExtra("pet");
        religion = getIntent().getStringExtra("religion");
        datefood = getIntent().getStringExtra("datefood");
        education = getIntent().getStringExtra("education");
        mywork = getIntent().getStringExtra("mywork");
        drink = getIntent().getStringExtra("drinkData");


        Log.e(religion + "  :  " + TAG + "  : " + mywork, education + "  inItView: pet " + pet + " : " + datefood);


        Log.e(TAG, height + "  inItView: looking " + looking);

        Log.e(TAG, height + "  inItView: date " + date);

        Log.e(TAG, city + "  :inItView: country " + country);


        ///========================get zodaic sign====================================

        //String dob = "1992/1/2";

        westernzodaic = SetChineseWesternZodaic.birthDateMatchWithWestern(date);
        chinesezodaic = birthDateMatch(date);

        Log.e(TAG, chinesezodaic + "   :inItView:western zodaic chinese:  " + westernzodaic);


        //}
    }


    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.skip: {
                String uid = AppSession.getStringPreferences(getApplicationContext(), Constants.USEREIDSIGNUP);
                String westernzodaic = AppSession.getStringPreferences(getApplicationContext(), Constants.WESTERNZODAICSIGN);
                String chinesezodaic = AppSession.getStringPreferences(getApplicationContext(), Constants.CHINESEZODAICSIGN);

                new AboutSelfPresenter(this, this).aboutSelfData(uid, "", height, img, strgender, strinterest, orientation,
                        looking, date, ethnicityname, bodytype, haircolor, htype, married, child, smoke, drink, lang, imglist, chinesezodaic.toLowerCase(), westernzodaic.toLowerCase(), city, country,
                        pet, religion, datefood, education, mywork);
            }
            break;

            case R.id.finish: {
                Validation validation = new Validation(this);
                if (validation.isaboutValid(binding.etabout)) {
                    //String uid = getPrefHelper().getUserId();

                    String uid = AppSession.getStringPreferences(getApplicationContext(), Constants.USEREIDSIGNUP);
                    String westernzodaic = AppSession.getStringPreferences(getApplicationContext(), Constants.WESTERNZODAICSIGN);
                    String chinesezodaic = AppSession.getStringPreferences(getApplicationContext(), Constants.CHINESEZODAICSIGN);

                    Log.e(TAG + height, uid + " onClick: marrrrrrrrrrr " + married);
                    new AboutSelfPresenter(this, this).aboutSelfData(uid, binding.etabout.getText().toString(), height, img, strgender, strinterest, orientation,
                            looking, date, ethnicityname, bodytype, haircolor, htype, married, child, smoke, drink, lang, imglist, chinesezodaic.toLowerCase(), westernzodaic.toLowerCase(), city, country,
                            pet, religion, datefood, education, mywork);

                }
            }
            break;

            case R.id.ivback: {

                onBackPressed();
            }
            break;


        }
    }

    @Override
    public void onAboutInfoResponse(String s) {
        loadUserData();
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

    public String birthDateMatch(String dateofbirth) {

        String asociateAnimal = "no";

        ArrayList<BirthdayData> birthdayDataArrayList = ZodaicActivity.birthDayData();

        Log.e(TAG + birthdayDataArrayList.size(), "birthDateMatch: " + dateofbirth);
        for (int i = 0; i < birthdayDataArrayList.size(); i++) {
            String birthdayData = birthdayDataArrayList.get(i).getYearEnd();
            String date_1984_2043 = birthdayData;

            //split date
            String[] datearray = date_1984_2043.split("-");

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
            } catch (Exception e) {

            }
        }

        return asociateAnimal;
    }


    void loadUserData() {
        String auth_type = AppSession.getStringPreferences(getApplicationContext(), Constants.AUTH_TYPE);
        if (auth_type.equals("social")) {
            String uid = AppSession.getStringPreferences(getApplicationContext(), Constants.USEREIDSIGNUP);
            String email = AppSession.getStringPreferences(getApplicationContext(), Constants.USER_EMAIL_SIGNUP);
            String name = AppSession.getStringPreferences(getApplicationContext(), Constants.USER_NAME_SIGNUP);
            String fbToken = AppSession.getStringPreferences(getApplicationContext(), Constants.USER_FB_TOKEN_SIGNUP);

            new LoginPresenter(this, this).socialLoginData(name, email, fbToken);
        }
        else if (auth_type.equals("phone")) {
            String uid = AppSession.getStringPreferences(getApplicationContext(), Constants.USEREIDSIGNUP);
            String phone = AppSession.getStringPreferences(getApplicationContext(), Constants.USER_PHONE_SIGNUP);
            String fbToken = AppSession.getStringPreferences(getApplicationContext(), Constants.USER_FB_TOKEN_SIGNUP);

            new LoginPresenter(this, this).loginPhone(phone);
        }
        else {
            String uid = AppSession.getStringPreferences(getApplicationContext(), Constants.USEREIDSIGNUP);
            String email = AppSession.getStringPreferences(getApplicationContext(), Constants.USER_EMAIL_SIGNUP);
            String pass = AppSession.getStringPreferences(getApplicationContext(), Constants.USER_PASS_SIGNUP);
            String fbToken = AppSession.getStringPreferences(getApplicationContext(), Constants.USER_FB_TOKEN_SIGNUP);

            new LoginPresenter(this, this).loginData(email, pass, fbToken, "");
        }
    }

    @Override
    public void onLoginInfoResponse(String s) {

        // Log.e(TAG, "onLoginInfoResponse: ss "+s );
        UserdatailModel userdatailModel = null;
        String s1 = "";
        try {
            //s1 = s.replace("null","\"\"");
            s1 = s;//.replace("null","\"\"");
            userdatailModel = new Gson().fromJson(s1, UserdatailModel.class);
            Log.e(TAG, "onLoginInfoResponse: aaa" + userdatailModel.getData().getId() + ", " +  userdatailModel.getData().getEmail() + ", " +  userdatailModel.getData().getName());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.e(TAG, "onLoginInfoResponse: exception " + e.getMessage());
        }

        //userdatailModel.getData().setIs_complete("1");
        String imgpath = "";
        if (userdatailModel.getData().getProfilePic().size() != 0)
            imgpath = IMAGE_BASE_URL + userdatailModel.getData().getProfilePic().get(0);


        //2020/12/26

        String inputPattern = "MM-dd-yyyy";
        String outputPattern = "yyyy/MM/dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String newdate = null;

        try {
            date = inputFormat.parse(userdatailModel.getData().getDob());
            newdate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        getPrefHelper().setUserId(userdatailModel.getData().getId());
        getPrefHelper().setUserDataModel(s1);
        getPrefHelper().issetLoggedIn();


        AppSession.setStringPreferences(getApplicationContext(), Constants.USEREId, userdatailModel.getData().getId());
        AppSession.setStringPreferences(getApplicationContext(), Constants.USERBIRTHDATE, newdate);
        AppSession.setStringPreferences(getApplicationContext(), Constants.USERPHONE, userdatailModel.getData().getPhone());
        AppSession.setStringPreferences(getApplicationContext(), Constants.USERNAME, userdatailModel.getData().getName());
        AppSession.setStringPreferences(getApplicationContext(), Constants.USEREMAIL, userdatailModel.getData().getEmail());
        AppSession.setStringPreferences(getApplicationContext(), Constants.USERIMAGE, imgpath);

        AppSession.setStringPreferences(getApplicationContext(), Constants.USERINTERESTED_IN, userdatailModel.getData().getIntrested_in());

        AppSession.setStringPreferences(getApplicationContext(), Constants.USERPREFFERDHOROSCOPE, userdatailModel.getData().getHoroscopetype());
        AppSession.setStringPreferences(getApplicationContext(), Constants.WESTERNZODAICSIGN, userdatailModel.getData().getWestern_zodaic());
        AppSession.setStringPreferences(getApplicationContext(), Constants.CHINESEZODAICSIGN, userdatailModel.getData().getChinese_zodaic());


        ////////=================other user gender


        if (userdatailModel.getData().getGender().toLowerCase().equalsIgnoreCase("male")) {
            AppSession.setStringPreferences(getApplicationContext(), Constants.OTHERUSERGENDER, "female");
        } else {
            AppSession.setStringPreferences(getApplicationContext(), Constants.OTHERUSERGENDER, "male");
        }

        AppSession.setStringPreferences(getApplicationContext(), Constants.USERORIENTATION, userdatailModel.getData().getOrientation());

        //token
        AppSession.setStringPreferences(getApplicationContext(), Constants.TOTAL_TOKEN, userdatailModel.getData().getTotal_token());
        AppSession.setStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE, userdatailModel.getData().getSubscription());


        String fbToken = AppSession.getStringPreferences(getApplicationContext(), Constants.USER_FB_TOKEN_SIGNUP);
        addUserFirebaseDatabase(userdatailModel.getData().getSubscription(), fbToken, userdatailModel.getData().getName(),
                userdatailModel.getData().getEmail(), userdatailModel.getData().getId(),
                imgpath);


        Intent intent = new Intent(StepCompleteActivity.this, TabActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    private void addUserFirebaseDatabase(String subscription, String firbaseToken, String name, String email, String uId, String profilepic) {
        Log.e(TAG, "addUserFirebaseDatabase: ");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseUserModel userModel = new FirebaseUserModel();
        userModel.firebaseToken = firbaseToken;
        userModel.name = name;
        userModel.email = email;
        userModel.profilePic = profilepic;
        userModel.uid = uId;
        userModel.subscription = subscription;
        database.child(Constants.USER_TABLE).child(uId).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.e(TAG, " ");
            }
        });
    }

    @Override
    public void onSocialLoginInfoResponse(String email, String name, String image) {

    }
}

