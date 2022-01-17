package com.alphaa.inzodiac.ui.forgotmodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class ForgotPresenter {

    private Activity activity;
    private ApiCallBackInterFace.forgetPasswordInfoCallback callback;

    public ForgotPresenter(Activity activity, ApiCallBackInterFace.forgetPasswordInfoCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void forgotData(String email) {


        callback.showLoaderProcess();
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().forget_password(email);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("response", stresult);
                            callback.onforgetPasswordInfoResponse(stresult);
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


