package com.alphaa.inzodiac.ui.activity.phoneverification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.ui.activity.loginmodule.LoginParm;
import com.alphaa.inzodiac.ui.activity.signupmodule.UserdatailModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class VerificationPresenter {

    String TAG = getClass().getSimpleName();

    private Activity activity;
    private ApiCallBackInterFace.phoneVerifyInfoCallback callback;

    public VerificationPresenter(Activity activity, ApiCallBackInterFace.phoneVerifyInfoCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void checkSamePhoneNumber(String mobile) {
        callback.showLoaderProcess();

        Log.e(TAG, "Phone number check : " + mobile);

        SamePhoneCheckParam data = new SamePhoneCheckParam();
        data.setPhone_no(mobile);

        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().checkSamePhoneNumber(data);

        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                callback.hideLoaderProcess();
                try {

                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("response login111111  ", stresult);
                            JsonObject jsonObject = new Gson().fromJson( stresult, JsonObject.class);
                            callback.onCheckSamePhoneResponse(jsonObject.get("status").getAsBoolean());
                            break;
                        }
                        case 400:
                        case 401:
                        case 500: {
                            String result = Objects.requireNonNull(response.errorBody()).string();
                            Log.e("response400", result);
                            JSONObject jsonObject = new JSONObject(result);
                            String statusCode = jsonObject.optString("status");
                            String msg = jsonObject.optString("message");
                            callback.onApiErrorResponse(msg, "");

                            break;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.hideLoaderProcess();
                Toast.makeText(activity, "onFailure: "+t.getMessage()  , Toast.LENGTH_SHORT).show();

                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });

    }




}


