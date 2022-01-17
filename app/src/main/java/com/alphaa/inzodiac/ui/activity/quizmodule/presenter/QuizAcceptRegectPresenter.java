package com.alphaa.inzodiac.ui.activity.quizmodule.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.SendGameRequest;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.SendQuizRequest;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;


public class QuizAcceptRegectPresenter {

    String TAG = getClass().getSimpleName();

    private Activity activity;
    private ApiCallBackInterFace.QuizRequestAcceptRejectCallback callback;

    public QuizAcceptRegectPresenter(Activity activity, ApiCallBackInterFace.QuizRequestAcceptRejectCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }


    public void quizRequestAcceptRegect(String uid,String requesttype,String requestid) {

        Log.e(TAG+requesttype, requestid+"  :sendRequest: uid "+uid );

        callback.showLoaderProcess();

        SendGameRequest sendGameRequest = new SendGameRequest();
        sendGameRequest.setUser_id(uid);
        sendGameRequest.setRequest_id(requestid);
        sendGameRequest.setRequest_type(requesttype);



        Call<SendQuizRequest> call = RetrofitClient.getInstance()
                .getApi().acceptGameRequest(sendGameRequest);
        call.enqueue(new Callback<SendQuizRequest>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<SendQuizRequest> call, @NonNull retrofit2.Response<SendQuizRequest> response) {
                callback.hideLoaderProcess();



               /* try {
                    Log.e(TAG, "onResponse: 222222 "+response.body().string() );
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                try {

                    switch (response.code()) {
                        case 200: {

                           /* ResponseBody responseBody = response.body();

                            String string = String.valueOf(responseBody.source());
                            Log.e(TAG, "onResponse: string "+string );

                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("response4444", stresult);*/

                            SendQuizRequest sendQuizRequest = response.body();


                            callback.onAcceptRejectRequesr(sendQuizRequest.getMessage());
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
            public void onFailure(@NonNull Call<SendQuizRequest> call, @NonNull Throwable t) {
                callback.hideLoaderProcess();

            }
        });

    }



}


