package com.alphaa.inzodiac.tabmodule.fragment.filtermodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alphaa.filter.Filter;
import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.fragment.menumodule.MenuParm;
import com.alphaa.inzodiac.tabmodule.fragment.menumodule.UserMenuModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class FilterPresenter {
    String TAG = getClass().getSimpleName();


    private Activity activity;
    private ApiCallBackInterFace.FilterInfoCallback callback;

    public FilterPresenter(Activity activity, ApiCallBackInterFace.FilterInfoCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void countriesData() {


        callback.showLoaderProcess();

        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().get_countries();
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
                            callback.oncountryInfoResponse(stresult);
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

    public void cityData(String id) {


        callback.showLoaderProcess();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("country_id",id);
        Countryid countryid = new Countryid();
        countryid.setCountry_id(id);

        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().get_cities(countryid);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

              /*  try {
                    Log.e(TAG, "onResponse: "+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("response  cityData  ", stresult);
                            callback.oncityInfoResponse(stresult);
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

                Log.e(TAG, "onFailure: "+t.getMessage() );

            }
        });

    }


    public void UserListData(String catid, String userid,String looking_for,String age,
                             String ethnicity,String bodytype,String intent,String country,String city,String distance,
                                     String latitude,
                                     String longitude,

                             String intrested_in,
                                     String orientation,
                                     String horoscopetype,
                                     String relationship,
                                     String children,
                                     String smoke,
                                     String drink,
                                     String  from_age,
                                     String to_age,

                             String gender



    ) {


        callback.showLoaderProcess();
        MenuParm menuParm = new MenuParm(userid,catid,looking_for,age,ethnicity,bodytype,intent,country,city,distance,latitude,longitude,
                 intrested_in,
                 orientation,
                 horoscopetype,
                 relationship,
                 children,
                 smoke,
                 drink,
                  from_age,
                 to_age,
                gender
        );

        Log.e(TAG, "UserListData: menu param "+new Gson().toJson(menuParm) );

        //String abc = "{\"age\":\"\",\"bodytype\":\"\",\"category_id\":\"3\",\"city\":\"\",\"country\":\"\",\"distance\":\"10\",\"ethnicity\":\"\",\"from_age\":\"18\",\"intent\":\"\",\"latitude\":\"22.7196\",\"longitude\":\"75.8577\",\"looking_for\":\"\",\"relationship\":\"\",\"to_age\":\"100\",\"user_id\":\"3\"}";

        Call<Filter> call = RetrofitClient.getInstance()
                .getApi().UserListFilter(menuParm);
        call.enqueue(new Callback<Filter>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<Filter> call, @NonNull retrofit2.Response<Filter> response) {
                Log.e(TAG, "onResponse: UserListData  "+new Gson().toJson(response.body()));
                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {

                            Filter userMenuModel = response.body();

                            //String stresult = Objects.requireNonNull(response.body()).string();

                            Gson gson = new Gson();
                            String stresult = gson.toJson(userMenuModel);

                            Log.e(TAG, "onResponse: str11111 "+stresult );

                            callback.onFilterInfoResponse(stresult);
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




}