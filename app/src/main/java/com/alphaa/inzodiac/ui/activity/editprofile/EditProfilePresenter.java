package com.alphaa.inzodiac.ui.activity.editprofile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailModel;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;


public class EditProfilePresenter {

    String TAG = getClass().getSimpleName();

    private Activity activity;
    private ApiCallBackInterFace.ProfileDetailInfoCallback callback;

    public EditProfilePresenter(Activity activity, ApiCallBackInterFace.ProfileDetailInfoCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void detailData(String uid) {


        callback.showLoaderProcess();

        Call<DetailModel> call = RetrofitClient.getInstance()
                .getApi().UserDetails(uid);
        call.enqueue(new Callback<DetailModel>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<DetailModel> call, @NonNull retrofit2.Response<DetailModel> response) {

               /* try {
                    Log.e(TAG, "onResponse: detailData  "+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                            //String stresult = Objects.requireNonNull(response.body()).string();
                            //Log.e(TAG,"response  profile present data"+ stresult);


                            String stresult = new Gson().toJson(response.body(), DetailModel.class);

                            Log.e(TAG, "onResponse: strresult  "+stresult );


                            callback.onProfiledetailInfoResponse(stresult);
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
            public void onFailure(@NonNull Call<DetailModel> call, @NonNull Throwable t) {
                callback.hideLoaderProcess();

            }
        });

    }









}