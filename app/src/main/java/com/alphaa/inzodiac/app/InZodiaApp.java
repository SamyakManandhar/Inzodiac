package com.alphaa.inzodiac.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alphaa.inzodiac.app.prefs.PrefHelper;
import com.alphaa.inzodiac.ui.activity.signupmodule.UserdatailModel;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.FirebaseUserModel;
import com.apradanas.prismoji.PrismojiManager;
import com.apradanas.prismoji.one.PrismojiOneProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import net.khirr.library.foreground.Foreground;

import static com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient.IMAGE_BASE_URL;

public class InZodiaApp extends Application {

    public static String DEVICE_ID = "";
    //  public static String DEVICE_TYPE = "";

    private static PrefHelper prefHelper;
    public static String AUTH_TOKEN;
    public static String FIREBASE_TOKEN = "";
    public static InZodiaApp instance = null;

    public static synchronized InZodiaApp getInstance() {
        if (instance != null) {
            return instance;
        }
        return new InZodiaApp();
    }

    public static PrefHelper getPrefHelper() {
        return prefHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Foreground.Companion.init(this);
        PrismojiManager.install(new PrismojiOneProvider());
        Foreground.Companion.isForeground();
        Foreground.Companion.isBackground();
        PrefHelper.init(this);
        prefHelper = new PrefHelper(this);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wl.acquire();
        wl.release();

        final Foreground.Listener foregroundListener = new Foreground.Listener() {
            @Override
            public void foreground() {
                Log.e("Foreground", "Go to foreground");
                onlineOfflinStatus("online");
            }

            @Override
            public void background() {
                Log.e("Foreground", "Go to background");
                onlineOfflinStatus("offline");

            }
        };

        Foreground.Companion.addListener(foregroundListener);


    }

    private void onlineOfflinStatus(String status) {
        PrefHelper prefHelper = new PrefHelper(this);
        if (prefHelper.isLoggedIn()) {
            if (getPrefHelper().getUserDataModel() != null && !getPrefHelper().getUserDataModel().isEmpty()) {
                UserdatailModel userdatailModel = new Gson().fromJson(getPrefHelper().getUserDataModel(), UserdatailModel.class);
                String userName = userdatailModel.getData().getName();
                String userid = userdatailModel.getData().getId();
                String useremail = userdatailModel.getData().getEmail();
                String usersubscription = userdatailModel.getData().getSubscription();
                String usertoken = userdatailModel.getData().getFirebase_token();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                FirebaseUserModel userModel = new FirebaseUserModel();
                userModel.name = userName;
                userModel.email = useremail;
                if (userdatailModel.getData().getProfilePic().size() > 0) {
                    userModel.profilePic = IMAGE_BASE_URL + userdatailModel.getData().getProfilePic().get(0);
                }
                userModel.uid = userid;
                userModel.readstatus = false;
                userModel.onoffstatus = status;
                userModel.subscription = usersubscription;
                userModel.firebaseToken = usertoken;
                database.child(Constants.USER_TABLE).child(userid).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

            }
        }
    }

}
