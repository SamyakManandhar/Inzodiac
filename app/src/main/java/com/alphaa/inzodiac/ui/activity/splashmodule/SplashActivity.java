package com.alphaa.inzodiac.ui.activity.splashmodule;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.ui.activity.letstart.LetStartActivity;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        printHaskey();


       // AppSession.setStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE, "no");

        try {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getPrefHelper().isLoggedIn()){
                        Intent start = new Intent(SplashActivity.this, TabActivity.class);
                        startActivity(start);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        finish();
                    }else {
                        Intent start = new Intent(SplashActivity.this, LetStartActivity.class);
                        startActivity(start);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        finish();
                    }
                }
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printHaskey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.alpha.inzodia", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}