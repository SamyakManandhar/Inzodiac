package com.alphaa.inzodiac.addminustoken;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.addminustoken.model.TokenAddPojo;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailModel;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.SendGameRequest;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.UserFavoriteParm;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.SendQuizRequest;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class TokenAddPresenter {
    String TAG = getClass().getSimpleName();

    private Activity activity;
    private ApiCallBackInterFace.TokenAddCallBack callback;

    public TokenAddPresenter(Activity activity, ApiCallBackInterFace.TokenAddCallBack callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void addToken(String uid,String token,String type) {

        callback.showLoaderProcess();

        Call<TokenAddPojo> call = RetrofitClient.getInstance()
                .getApi().addAndSubtractToken(uid,token,type);
        call.enqueue(new Callback<TokenAddPojo>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<TokenAddPojo> call, @NonNull retrofit2.Response<TokenAddPojo> response) {

                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                           // Log.e("sendLikeRequest", stresult);
                            //callback.onFavoriteInfoResponse(stresult);
                            Log.e(TAG, " tokenadded onResponse: "+new Gson().toJson(response.body()));

                            TokenAddPojo tokenAddPojo = response.body();

                           /* AppSession.setStringPreferences(activity, Constants.TOTAL_TOKEN,tokenAddPojo.getData().getTotalToken());

                            if (type.matches("add")){
                                Toast.makeText(activity, token+" is added to account", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(activity, "Cost is "+token+" token", Toast.LENGTH_SHORT).show();
                            }*/

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


                            break;
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TokenAddPojo> call, @NonNull Throwable t) {
                callback.hideLoaderProcess();

            }
        });

    }





}