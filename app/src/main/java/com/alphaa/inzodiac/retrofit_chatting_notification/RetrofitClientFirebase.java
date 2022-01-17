package com.alphaa.inzodiac.retrofit_chatting_notification;


import com.alphaa.inzodiac.RetrofitConnrect.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientFirebase {

    private static RetrofitClientFirebase minstance;


    public static final String BASE_URL = "https://fcm.googleapis.com/fcm/";




    private Retrofit retrofit,retrofitfirebase;



    //client server
   // http://alphawizztest.tk/Inzodiac/api/



    private RetrofitClientFirebase() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();


    }

    public static RetrofitClientFirebase getInstance() {
        if (minstance == null) {
            minstance = new RetrofitClientFirebase();
        }
        return minstance;
    }

    public ApiInterface getApi() {
        return retrofit.create(ApiInterface.class);
    }













}
