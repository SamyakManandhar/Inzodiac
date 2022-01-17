package com.alphaa.inzodiac.app.prefs;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.alphaa.inzodiac.ui.activity.loginmodule.LoginActivity;

import static com.alphaa.inzodiac.app.prefs.Params.CatID;
import static com.alphaa.inzodiac.app.prefs.Params.IS_LOGGEDIN;
import static com.alphaa.inzodiac.app.prefs.Params.OtherID;
import static com.alphaa.inzodiac.app.prefs.Params.USERDATAMODEL;
import static com.alphaa.inzodiac.app.prefs.Params.UserId;


public class PrefHelper {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences1;
    private final SharedPreferences.Editor editor1;


    private static PrefHelper sInstance;

    public static synchronized PrefHelper instance() {
        return sInstance;
    }

    @SuppressLint("CommitPrefEdits")
    public PrefHelper(Context context) {
        sInstance = this;
        String prefsFile = context.getPackageName();
        sharedPreferences = context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sharedPreferences1 = context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE);
        editor1 = sharedPreferences.edit();
    }
    public void setUserId(String userid) {
        editor.putString(UserId, userid);
        this.editor.apply();
    }


    public String getUserId() {
        return sharedPreferences.getString(UserId, "");

    }

    public void setotherid(String otherid) {
        editor.putString(OtherID, otherid);
        this.editor.apply();
    }


    public String getotherid() {
        return sharedPreferences.getString(OtherID, "");

    }

    public void setUserDataModel(String UserData) {
        editor.putString(USERDATAMODEL, UserData);
        this.editor.apply();
    }


    public String getUserDataModel() {
        return sharedPreferences.getString(USERDATAMODEL, "");

    }


    public void setCatId(String Catid) {
        editor.putString(CatID, Catid);
        this.editor.apply();
    }


    public String getCatId() {
        return sharedPreferences.getString(CatID, "");

    }




    public static void init(Context context) {
        if (sInstance == null) {
            new PrefHelper(context);
        }
    }


    public void logout(Activity activity) {
        editor.clear();
        editor.apply();
        Intent showLogin = new Intent(activity, LoginActivity.class);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(showLogin);
        activity.finish();
    }


    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGEDIN, false);
    }

    public void issetLoggedIn() {
        editor.putBoolean(IS_LOGGEDIN, true);
        this.editor.apply();
    }


}
