package com.alphaa.inzodiac.ui.activity.aboutself;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.gallery.models.Image;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.ui.signupmodel.SignupPojo;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class AboutSelfPresenter {

    String TAG = getClass().getSimpleName();


    private Activity activity;
    private ApiCallBackInterFace.AboutInfoCallback callback;

    public AboutSelfPresenter(Activity activity, ApiCallBackInterFace.AboutInfoCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void aboutSelfData(String user_id, String about, String height, String img, String gender, String intrested_in, String orientation, String looking_for, String dob,
                              String ethnicity, String bodytype, String haircolour, String hororscopetype , String married, String children , String smoke , String drink ,
                              String language, ArrayList<Image> imglist,String chinesezodaic,String westernzodaic,String city,String country
    ,String pet,String religion,String date_food,String education,String my_work) {


        Log.e(TAG, height+"  aboutSelfData: dobbbb "+dob );
        callback.showLoaderProcess();
        MediaType text = MediaType.parse("text/plain");
        RequestBody gender1 = RequestBody.create(text, gender);
        RequestBody dob1 = RequestBody.create(text, dob);
        RequestBody ethnicity1 = RequestBody.create(text, ethnicity);
        RequestBody bodytype1 = RequestBody.create(text, bodytype);
        RequestBody height1 = RequestBody.create(text, height);
        RequestBody haircolour1 = RequestBody.create(text, haircolour);
        RequestBody eyecolour1 = RequestBody.create(text, "");
        RequestBody hororscopetype1 = RequestBody.create(text, hororscopetype);
        RequestBody married1 = RequestBody.create(text, married);
        RequestBody children1 = RequestBody.create(text, children);
        RequestBody language1 = RequestBody.create(text, language);
        RequestBody smoke1 = RequestBody.create(text, smoke);
        RequestBody drink1 = RequestBody.create(text, drink);
        RequestBody about1 = RequestBody.create(text, about);
        RequestBody orientation1 = RequestBody.create(text, orientation);
        RequestBody intrested_in1 = RequestBody.create(text, intrested_in);
        RequestBody looking_for1 = RequestBody.create(text, looking_for);


        RequestBody westernzodaic1 = RequestBody.create(text, westernzodaic);
        RequestBody chinesexodaic1 = RequestBody.create(text, chinesezodaic);


        RequestBody city1 = RequestBody.create(text, city);
        RequestBody country1 = RequestBody.create(text, country);

       // RequestBody looking_for1 = RequestBody.create(text, "3");
        RequestBody user_id1 = RequestBody.create(text, user_id);
        RequestBody category_id1 = RequestBody.create(text, "2"  );


        RequestBody pets1 = RequestBody.create(text, pet);
        RequestBody religion1 = RequestBody.create(text, religion);
        RequestBody datefood1 = RequestBody.create(text, date_food);
        RequestBody education1 = RequestBody.create(text, education);
        RequestBody mywork1 = RequestBody.create(text, my_work);



        MultipartBody.Part[] array = null;
        if (imglist != null && imglist.size()> 0) {
             array = new MultipartBody.Part[imglist.size()];
            for (int i = 0; i < imglist.size(); i++) {
                Log.e(TAG, "aboutSelfData: image path "+imglist.get(i).path );
                File file = new File(imglist.get(i).path);
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("profile_pic[]", file.getName(),
                        requestBody);
                array[i] = filePart;
            }
        }

        Call<SignupPojo> call = RetrofitClient.getInstance()
                .getApi().AboutSelf(gender1,dob1,ethnicity1,bodytype1,height1,haircolour1,eyecolour1,hororscopetype1, married1,children1,language1,
                        smoke1,drink1,about1,orientation1,intrested_in1,looking_for1,user_id1,country1,city1,westernzodaic1,chinesexodaic1,pets1,religion1,datefood1,
                        education1,mywork1,married1,array);
        call.enqueue(new Callback<SignupPojo>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<SignupPojo> call, @NonNull retrofit2.Response<SignupPojo> response) {

                Log.e(TAG, "AboutSelf111111111: "+new Gson().toJson(response.body()));


                callback.hideLoaderProcess();
                if (response.isSuccessful()){

                    SignupPojo signupPojo = response.body();

                    if (signupPojo.getStatus()){

                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                        //SignupPojo target = new SignupPojo();
                       // String json = gson.toJson(target);


                        AppSession.setStringPreferences(activity, Constants.USERPREFFERDHOROSCOPE, signupPojo.getData().getHoroscopetype());

                        AppSession.setStringPreferences(activity, Constants.USERORIENTATION, signupPojo.getData().getOrientation());



                        callback.onAboutInfoResponse(signupPojo.getMessage());

                    }else {
                        //Toast.makeText(get, "", Toast.LENGTH_SHORT).show();
                        callback.onAboutInfoResponse("Something went wrong, please try after sometime");
                    }

                }else {

                }




              /*  try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("AboutSelf  response", stresult);
                            callback.onAboutInfoResponse(stresult);
                            break;
                        }
                        case 400:

                            Log.e(TAG, "onResponse: errr 11111 "+new Gson().toJson(response.errorBody()) );

                        case 401:
                        case 500:
                        case 405:
                            {
                                Log.e(TAG, "onResponse: errr 2222 "+new Gson().toJson(response.errorBody()) );

                                callback.hideLoaderProcess();
                            String result = Objects.requireNonNull(response.errorBody()).string();
                            Log.d("response400", result);
                            JSONObject jsonObject = new JSONObject(result);
                            String statusCode = jsonObject.optString("status");
                            String msg = jsonObject.optString("message");


                                callback.onApiErrorResponse(msg,"");


                            break;


                        }

                        default:
                            try {
                                Log.e(TAG, "onResponse: error 333 "+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
*/            }

            @Override
            public void onFailure(@NonNull Call<SignupPojo> call, @NonNull Throwable t) {
                callback.hideLoaderProcess();

                Log.e(TAG, "onFailure: "+t.getMessage() );

            }
        });

    }



}