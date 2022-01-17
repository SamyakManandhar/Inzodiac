package com.alphaa.inzodiac.ui.activity.signupmodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class SignupPresenter {

    String TAG = getClass().getSimpleName();


    private Activity activity;
    private ApiCallBackInterFace.signupInfoCallback callback;

    public SignupPresenter(Activity activity, ApiCallBackInterFace.signupInfoCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void signupData(String email, String password,String tokoen,String mobile,String fullname,String lat,String longg,String Firebase_token) {


        callback.showLoaderProcess();


        MediaType text = MediaType.parse("text/plain");
        RequestBody mName = RequestBody.create(text, fullname);
        RequestBody mEmail = RequestBody.create(text, email);
        RequestBody mMobile = RequestBody.create(text, mobile);
        RequestBody mPassword = RequestBody.create(text, password);
        RequestBody mLat = RequestBody.create(text, lat);
        RequestBody mLong = RequestBody.create(text, longg);
//        RequestBody mToken = RequestBody.create(text, Firebase_token);
        RequestBody mToken = RequestBody.create(text, "12345");


        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().get_signup(mName,mEmail,mMobile,mPassword,mLat,mLong,mToken);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e(TAG,"response  11111 "+ stresult);
                            callback.onsignupInfoResponse(stresult);
                            break;
                        }
                        case 400:
                        case 401:
                        case 500:
                            {
                                callback.hideLoaderProcess();
                            String result = Objects.requireNonNull(response.errorBody()).string();
                            Log.e(TAG,"response400"+ result);
                            JSONObject jsonObject = new JSONObject(result);
                            String statusCode = jsonObject.optString("status");
                            String msg = jsonObject.optString("message");

                                Log.e(TAG, "onResponse: msggg "+msg );

                                callback.onApiErrorResponse(msg,"");


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

                Log.e(TAG, "onFailure: "+t.getMessage() );
                Toast.makeText(activity, activity.getString(R.string.somethingwrong), Toast.LENGTH_SHORT).show();

            }
        });

    }



}