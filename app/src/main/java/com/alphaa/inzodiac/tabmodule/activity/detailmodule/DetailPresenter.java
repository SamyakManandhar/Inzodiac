package com.alphaa.inzodiac.tabmodule.activity.detailmodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.SendQuizRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class DetailPresenter {
    String TAG = getClass().getSimpleName();

    private Activity activity;
    private ApiCallBackInterFace.DetailInfoCallback callback;

    public DetailPresenter(Activity activity, ApiCallBackInterFace.DetailInfoCallback callback) {
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
                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                            //String stresult = Objects.requireNonNull(response.body()).string();
                           // Log.d("response", stresult);

                            //DetailModel detailModel = response.body();

                            String stresult = new Gson().toJson(response.body(),DetailModel.class);

                            Log.e(TAG, "onResponse: strresult  "+stresult );


                            callback.ondetailInfoResponse(stresult);
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



    public void sendRequest(String uid,String otheruserid) {

        Log.e(TAG, otheruserid+"  :sendRequest: uid "+uid );

        callback.showLoaderProcess();

       SendGameRequest sendGameRequest = new SendGameRequest();
       sendGameRequest.setUser_id(uid);
       sendGameRequest.setOther_user_id(otheruserid);



        Call<SendQuizRequest> call = RetrofitClient.getInstance()
                .getApi().sendGameRequest(sendGameRequest);
        call.enqueue(new Callback<SendQuizRequest>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<SendQuizRequest> call, @NonNull retrofit2.Response<SendQuizRequest> response) {


                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {

                            //ResponseBody responseBody = (response.body()).string();

                            SendQuizRequest sendQuizRequest = response.body();

                           /* if (sendQuizRequest.getStatus()==1){




                            }
*/

                           /* String string = (response.body()).string();
                            Log.e(TAG, "onResponse: string "+string );

                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("response4444", stresult);
                            */








                            callback.onSendRequesr(sendQuizRequest.getMessage());
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

                    Log.e(TAG, "onResponse: error "+e.getMessage() );
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendQuizRequest> call, @NonNull Throwable t) {
                callback.hideLoaderProcess();

            }
        });

    }


    public void sendLikeRequest(String uid,String otherid) {


        callback.showLoaderProcess();
        UserFavoriteParm parm = new UserFavoriteParm();
        parm.setUser_id(uid);
        parm.setOther_user_id(otherid);


        Log.e(TAG, "sendLikeRequest: 111111 "+new Gson().toJson(parm,UserFavoriteParm.class) );
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().sendLikeRequest(parm);
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
                            Log.e("sendLikeRequest", stresult);
                            callback.onFavoriteInfoResponse(stresult);
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
                            Log.e("sendLikeRequest", stresult);
                            callback.onFavoriteInfoResponse(stresult);
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


/*
    public void LikeHeartData(String uid,String otherid) {


        callback.showLoaderProcess();
        UserFavoriteParm parm = new UserFavoriteParm();
        parm.setUser_id(uid);
        parm.setOther_user_id(otherid);
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().user_favorite(parm);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.d("response", stresult);
                            callback.onFavoriteInfoResponse(stresult);
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
*/



}