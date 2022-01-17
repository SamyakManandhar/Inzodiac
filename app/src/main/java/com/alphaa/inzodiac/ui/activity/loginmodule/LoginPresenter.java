package com.alphaa.inzodiac.ui.activity.loginmodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.ui.activity.phoneloginmodule.PhoneLoginParam;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class LoginPresenter {

    String TAG = getClass().getSimpleName();

    private Activity activity;
    private ApiCallBackInterFace.loginInfoCallback callback;

    public LoginPresenter(Activity activity, ApiCallBackInterFace.loginInfoCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void loginData(String email, String password, String tokoen, String mobile) {


        callback.showLoaderProcess();
        LoginParm loginParm = new LoginParm();
        loginParm.setEmail(email);
        loginParm.setPassword(password);
        loginParm.setFirebase_token(tokoen);
        loginParm.setMobile("");

        Log.e(TAG, "loginData: "+new Gson().toJson(loginParm,LoginParm.class));

        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().get_login(loginParm);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("response login111111  ", stresult);
                            callback.onLoginInfoResponse(stresult);
                            break;
                        }
                        case 400:
                        case 401:
                        case 500: {
                            callback.hideLoaderProcess();
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
                Toast.makeText(activity, activity.getString(R.string.somethingwrong), Toast.LENGTH_SHORT).show();

                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });

    }


    public void loginPhone(String mobile) {

        callback.showLoaderProcess();
        PhoneLoginParam loginParm = new PhoneLoginParam();
        loginParm.setPhone_no(mobile);

        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().loginWithPhoneNumber(loginParm);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("response login111111  ", stresult);
                            callback.onLoginInfoResponse(stresult);
                            break;
                        }
                        case 400:
                        case 401:
                        case 500: {
                            callback.hideLoaderProcess();
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
                Toast.makeText(activity, activity.getString(R.string.somethingwrong), Toast.LENGTH_SHORT).show();

                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    public void socialLoginData(String name, String email,String Firebase_token) {


        callback.showLoaderProcess();
      /*  LoginParm loginParm = new LoginParm();
        loginParm.setEmail(email);
        loginParm.setPassword(password);
        loginParm.setFirebase_token(tokoen);
        loginParm.setMobile("");*/

        Log.e(TAG, "socialLoginData: "+Firebase_token );
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getApi().SocialLogin(name,email,"1",Firebase_token);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                try {
                    callback.hideLoaderProcess();
                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("response social login", stresult);
                            callback.onLoginInfoResponse(stresult);
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

    public void getFacebook(LoginButton login_button, CallbackManager callbackManager) {

        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                login_button.setVisibility(View.GONE);

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("JSON", "" + response.getJSONObject().toString());

                        try {
                            String email = object.getString("email");
                            String name = object.getString("name");
                            String first_name = object.optString("first_name");
                            String last_name = object.optString("last_name");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                            callback.onSocialLoginInfoResponse(email,name,image_url);
                            LoginManager.getInstance().logOut();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,first_name,last_name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });
    }


    public void handleSignInResult(GoogleApiClient mGoogleApiClient, GoogleSignInResult result) {
        Log.e("TAG", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            try {

                String personPhotoUrl = "";
                GoogleSignInAccount acct = result.getSignInAccount();

                Log.e("TAG", "display name: " + acct.getDisplayName());

                String personName = acct.getDisplayName();
                if (acct.getPhotoUrl() != null) {
                     personPhotoUrl = acct.getPhotoUrl().toString();
                }
                String email = acct.getEmail();

                Log.e("TAG", "Name: " + personName + ", email: " + email
                        + ", Image: " + personPhotoUrl);

                callback.onSocialLoginInfoResponse(email,personName,personName);
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);

            }
            catch (Exception e){
                Log.e("error",e.getMessage());
            }
        }
    }

}


