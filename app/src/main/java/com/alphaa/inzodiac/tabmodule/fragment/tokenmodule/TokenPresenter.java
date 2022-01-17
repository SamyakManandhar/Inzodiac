package com.alphaa.inzodiac.tabmodule.fragment.tokenmodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class TokenPresenter {
    String TAG = getClass().getSimpleName();


    private Activity activity;
    private ApiCallBackInterFace.favouriteInfoCallback callback;

    public TokenPresenter(Activity activity, ApiCallBackInterFace.favouriteInfoCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void TokenListData(String uid) {
        callback.showLoaderProcess();
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().getToken_list(uid);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                callback.hideLoaderProcess();
                try {
                    switch (response.code()) {
                        case 200: {
                            callback.hideLoaderProcess();
                            Log.e(TAG, "onResponse: 22222222222" );
                            String stresult = null;
                            try {
                                stresult = Objects.requireNonNull(response.body()).string();
                            } catch (IOException e) {
                                Log.e(TAG, "onResponse: error "+e.getMessage() );
                                e.printStackTrace();
                            }
                            Log.e(TAG, "response111111 "+ stresult);
                            callback.onfavouriteInfoResponse(stresult);
                            break;
                        }
                        case 400:
                        case 401:
                        case 500: {
                            callback.hideLoaderProcess();
                            String result = Objects.requireNonNull(response.errorBody()).string();
                            Log.d("response400", result);
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

            }
        });

    }



}


