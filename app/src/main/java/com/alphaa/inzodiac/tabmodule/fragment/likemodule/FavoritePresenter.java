package com.alphaa.inzodiac.tabmodule.fragment.likemodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.UserFavoriteParm;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class FavoritePresenter {
    String TAG = getClass().getSimpleName();


    private Activity activity;
    private ApiCallBackInterFace.favouriteInfoCallback callback;

    public FavoritePresenter(Activity activity, ApiCallBackInterFace.favouriteInfoCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void UserFavoriteListData(String userid,String text) {


        callback.showLoaderProcess();
        FavoriteParm favoriteParm = new FavoriteParm();
        favoriteParm.setPageNumber("1");
        favoriteParm.setPageSize("10");
        favoriteParm.setUser_id(userid);
        favoriteParm.setSearch_text(text);

        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().my_matches_list(favoriteParm);
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
//                            Gson gson = new Gson();
//                            FavoriteModel favoriteModel =  gson.fromJson(response.body().toString(),FavoriteModel.class);


                           // String stresult = response.body().string();
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

    public void dislikeRequest(String uid,String otherid) {

        callback.showLoaderProcess();
        UserFavoriteParm parm = new UserFavoriteParm();
        parm.setUser_id(uid);
        parm.setOther_user_id(otherid);

        Log.e(TAG, "sendLikeRequest: 111111 "+new Gson().toJson(parm,UserFavoriteParm.class) );
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().dislikeUser(parm);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
              /*  try {
                    Log.e(TAG, "onResponse: sendLikeRequest "+response.body().string() );
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("dislike ", stresult);
                            callback.ondislikeInfoResponse(stresult);
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
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.hideLoaderProcess();

            }
        });

    }


}


