package com.alphaa.inzodiac.tabmodule.fragment.menumodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alphaa.filter.Filter;
import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class MenuPresenter {

    String TAG = getClass().getSimpleName();

    private Activity activity;
    private ApiCallBackInterFace.menuInfoCallback callback;

    public MenuPresenter(Activity activity, ApiCallBackInterFace.menuInfoCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void UserListData(String catid, String userid,String looking_for,String age,
                             String ethnicity,String bodytype,String intent,String country,String city,String distance,
                                     String latitude,
                                     String longitude,String gender,String orientation) {


        callback.showLoaderProcess();
        MenuParm menuParm = new MenuParm(userid,catid,looking_for,age,ethnicity,bodytype,intent,country,city,distance,
                latitude,longitude,gender,orientation);

        Log.e(TAG, "UserListData: menuuuuu "+new Gson().toJson(menuParm,MenuParm.class));
        Call<Filter> call = RetrofitClient.getInstance()
                .getApi().UserList(menuParm);
        call.enqueue(new Callback<Filter>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<Filter> call, @NonNull retrofit2.Response<Filter> response) {
                Log.e(TAG, "  onResponse: UserList "+new Gson().toJson(response.body()));

                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {


                            //String stresult = Objects.requireNonNull(response.body()).string();
                            //Log.e("response   UserList  ", stresult);

                            Filter userMenuModel = response.body();

                            Log.e(TAG, "onResponse: usermenu "+userMenuModel.getData().get(0).getAbout() );
                            //String stresult = Objects.requireNonNull(response.body()).string();

                            //String stresult = new Gson().toJson(userMenuModel,UserMenuModel.class);

                            Gson gson = new Gson();
                            String stresult = gson.toJson(userMenuModel);


                            Log.e(TAG, "onResponse: str result "+stresult);

                            callback.onmenuInfoResponse(stresult);
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
            public void onFailure(@NonNull Call<Filter> call, @NonNull Throwable t) {
                callback.hideLoaderProcess();

            }
        });

    }









    public void UserListUsingZodaic(String zodaic_chinese,String zodaic_western) {


        Log.e(TAG, zodaic_western+"   :UserListUsingZodaic: cccccchines "+zodaic_chinese );
        callback.showLoaderProcess();
        //MenuParm menuParm = new MenuParm(zodaic_western,zodaic_chinese);

        MenuParm menuParm = new MenuParm(zodaic_western,zodaic_chinese);

        Log.e(TAG, "UserListData: menuuuuu "+new Gson().toJson(menuParm,MenuParm.class));
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().UserListUsingZodaic(menuParm);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e(TAG,"response   UserList UserListUsingZodaic "+ stresult);
                            callback.onmenuInfoResponse(stresult);
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


