package com.alphaa.inzodiac.RetrofitConnrect;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient minstance;

    //local
//    public static final String BASE_URL = "http://192.168.29.171/Inzodiac/api/index.php/";
//    public static final String IMAGE_BASE_URL = "http://192.168.29.171/Inzodiac/api/";


    //alphawizz server
    public static final String BASE_URL = "http://18.132.224.101/inzodiac/api/index.php/";
    public static final String IMAGE_BASE_URL = "http://18.132.224.101/inzodiac/api/";

    private Retrofit retrofit,retrofitfirebase;

    private RetrofitClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()

                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }

    public static RetrofitClient getInstance() {
        if (minstance == null) {
            minstance = new RetrofitClient();
        }
        return minstance;
    }

    public ApiInterface getApi() {
        return retrofit.create(ApiInterface.class);
    }













}
