package com.alphaa.inzodiac.ui.activity.aboutself;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.SendGameRequest;
import com.alphaa.inzodiac.ui.activity.aboutself.aboutmodel.AboutSelfCategoryPojo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;


public class AboutSelfCategoryPresenter {

    String TAG = getClass().getSimpleName();

    private Activity activity;
    private ApiCallBackInterFace.CategoryUpdateCallback callback;

    public AboutSelfCategoryPresenter(Activity activity, ApiCallBackInterFace.CategoryUpdateCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }


    public void setCategory(String uid,String catid) {


        Log.e(TAG, "setCategory: cattttttiddddd "+catid );

        callback.showLoaderProcess();

        SendGameRequest sendGameRequest = new SendGameRequest();
        sendGameRequest.setUser_id(uid);
        sendGameRequest.setCategory_id(catid);

        Log.e(TAG, catid+"  :sendRequest: uid "+new Gson().toJson(sendGameRequest,SendGameRequest.class));


        Call<AboutSelfCategoryPojo> call = RetrofitClient.getInstance()
                .getApi().userCategoryUpdate(sendGameRequest);
        call.enqueue(new Callback<AboutSelfCategoryPojo>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<AboutSelfCategoryPojo> call, @NonNull retrofit2.Response<AboutSelfCategoryPojo> response) {
                callback.hideLoaderProcess();

                Log.e(TAG, "onResponse: setCategory  "+new Gson().toJson(response.body()));
                try {

                    switch (response.code()) {
                        case 200: {

                           /* ResponseBody responseBody = response.body();

                            String string = String.valueOf(responseBody.source());
                            Log.e(TAG, "onResponse: string "+string );

                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("response4444", stresult);*/

                            AboutSelfCategoryPojo sendQuizRequest = response.body();


                            callback.onCategoryUpdateResponse(sendQuizRequest.getMessage());
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
            public void onFailure(@NonNull Call<AboutSelfCategoryPojo> call, @NonNull Throwable t) {
                callback.hideLoaderProcess();
                Log.e(TAG, "onFailure: "+t.getMessage() );

            }
        });

    }



}


