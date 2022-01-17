package com.alphaa.inzodiac.ui.activity.editphonemodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.ui.activity.phoneverification.SamePhoneCheckParam;
import com.alphaa.inzodiac.utility.AppSession;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class UpdatePhonePresenter {

    String TAG = getClass().getSimpleName();

    private Activity activity;
    private ApiCallBackInterFace.updatePhoneNumberCallback callback;

    public UpdatePhonePresenter(Activity activity, ApiCallBackInterFace.updatePhoneNumberCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void updatePhoneNumber(String mobile, String userId) {
        callback.showLoaderProcess();

        Log.e(TAG, "Phone number check : " + mobile);

        UpdatePhoneParam data = new UpdatePhoneParam();
        data.setPhone_no(mobile);
        data.setUserid(userId);

        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().updatePhoneNumber(data);

        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                callback.hideLoaderProcess();
                try {

                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("response update Phone ", stresult);
                            JsonObject jsonObject = new Gson().fromJson( stresult, JsonObject.class);
                            callback.onUpdatePhoneResponse(jsonObject.get("status").getAsBoolean());
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


