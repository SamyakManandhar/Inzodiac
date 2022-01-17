package com.alphaa.inzodiac.ui.activity.phoneloginmodule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityEditPhoneBinding;
import com.alphaa.inzodiac.databinding.ActivityPhoneLoginBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.ui.activity.aboutself.AboutSelfActivity;
import com.alphaa.inzodiac.ui.activity.editphonemodule.EditPhoneActivity;
import com.alphaa.inzodiac.ui.activity.loginmodule.LoginActivity;
import com.alphaa.inzodiac.ui.activity.loginmodule.LoginPresenter;
import com.alphaa.inzodiac.ui.activity.phoneverification.PhoneVerificationActivity;
import com.alphaa.inzodiac.ui.activity.phoneverification.VerificationPresenter;
import com.alphaa.inzodiac.ui.activity.signupmodule.UserdatailModel;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.CheckNetwork;
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
import java.util.Date;

import static com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient.IMAGE_BASE_URL;

public class PhoneLoginActivity extends BaseActivity implements View.OnClickListener, ApiCallBackInterFace.loginInfoCallback {
    String TAG = getClass().getSimpleName();

    private ActivityPhoneLoginBinding binding;
    private Validation validation;

    private String firbaseToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_login);

        if (getIntent().hasExtra("firbaseToken")) {
            firbaseToken = getIntent().getStringExtra("firbaseToken");
        }

        setClicks(binding.submitBtn, binding.ivBack);
    }

    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
        validation = new Validation(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn: {
                doSave();
            }
            break;
            case R.id.iv_back: {
                finish();
            }
            break;
            default:
                break;
        }
    }

    private void doSave() {
        if (validation.isMobileNoValid(binding.phoneNum)) {
            if (CheckNetwork.isNetAvailable(getApplicationContext())) {
                new LoginPresenter(this, this).loginPhone(binding.phoneNum.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), Toast.LENGTH_LONG).show();
            }
        }
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
        Log.e(TAG, "onApiErrorResponse: " + Errorresponse);
        Toast.makeText(activity, "" + Errorresponse, Toast.LENGTH_SHORT).show();
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

        String imgpath = "";
        if (userdatailModel.getData().getProfilePic().size() != 0)
            imgpath = IMAGE_BASE_URL + userdatailModel.getData().getProfilePic().get(0);

        AppSession.setStringPreferences(getApplicationContext(), Constants.AUTH_FLOW, "login");

        if (userdatailModel.getData().getIs_complete().equalsIgnoreCase("0")) {

            AppSession.setStringPreferences(getApplicationContext(), Constants.USEREIDSIGNUP, userdatailModel.getData().getId());
            AppSession.setStringPreferences(getApplicationContext(), Constants.USER_FB_TOKEN_SIGNUP, firbaseToken);

            AppSession.setStringPreferences(getApplicationContext(), Constants.AUTH_TYPE, "phone");
            AppSession.setStringPreferences(getApplicationContext(), Constants.IS_COMPLETED_PROFILE, "0");
            AppSession.setStringPreferences(getApplicationContext(), Constants.USER_PHONE_SIGNUP, binding.phoneNum.getText().toString());

            goPhoneVerification();
        }
        else {

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



            addUserFirebaseDatabase(userdatailModel.getData().getSubscription(), firbaseToken, userdatailModel.getData().getName(),
                    userdatailModel.getData().getEmail(), userdatailModel.getData().getId(),
                    imgpath);

            AppSession.setStringPreferences(getApplicationContext(), Constants.AUTH_TYPE, "phone");
            AppSession.setStringPreferences(getApplicationContext(), Constants.IS_COMPLETED_PROFILE, "1");

            goPhoneVerification();
        }
    }

    @Override
    public void onSocialLoginInfoResponse(String email, String name, String image) {

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

                Log.e(TAG, "onComplete:addUserFirebaseDatabase ");
            }
        });
    }

    void goPhoneVerification() {
        UserdatailModel.SignupDatum data = new UserdatailModel.SignupDatum();
        data.setPhone(binding.phoneNum.getText().toString());

        Intent intent = new Intent(activity, PhoneVerificationActivity.class);
        intent.putExtra("user_data", data);
        startActivity(intent);
    }
}