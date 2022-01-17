package com.alphaa.inzodiac.ui.activity.contactus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.ui.activity.contactus.contactusmodel.ContactUsPojo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class ContactUsPresenter {

    String TAG = getClass().getSimpleName();


    private Activity activity;
    private ApiCallBackInterFace.ContactUsCallback callback;

    public ContactUsPresenter(Activity activity, ApiCallBackInterFace.ContactUsCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void contactUs(String name ,String phone, String email,String subject,String  msg) {


        Log.e(TAG, "contactUs: name "+name );
        Log.e(TAG, "contactUs: phone "+phone );
        Log.e(TAG, "contactUs: email "+email );
        Log.e(TAG, "contactUs: subject "+subject );
        Log.e(TAG, "contactUs: msg "+msg );

        callback.showLoaderProcess();

        Call<ContactUsPojo> call = RetrofitClient.getInstance()
                .getApi().contactUs(name,phone,email,subject,msg);
        call.enqueue(new Callback<ContactUsPojo>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ContactUsPojo> call, @NonNull retrofit2.Response<ContactUsPojo> response) {

                Log.e(TAG, "onResponse: "+new Gson().toJson(response.body()));
                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {



                            //ContactUsPojo contactUsPojo = new Gson().to

                            //Gson gson = new Gson();
                           // String json = gson.toJson(response.);


                            String stresult = new Gson().toJson(response.body()); //Objects.requireNonNull(response.body()).string();
                            Log.e("onResponse contactUs  ", stresult);
                            callback.oncontactusInfoResponse(stresult);
                            break;
                        }
                        case 400:
                        case 401:
                        case 500:
                            {

                            String result = Objects.requireNonNull(response.errorBody()).string();
                            Log.d("response400", result);
                            JSONObject jsonObject = new JSONObject(result);
                            String statusCode = jsonObject.optString("status");
                            String msg = jsonObject.optString("message");

                            if (statusCode.equals("true")) {
                                callback.onApiErrorResponse(msg,"");

                            }
                            break;
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ContactUsPojo> call, @NonNull Throwable t) {
                callback.hideLoaderProcess();

            }
        });

    }




}