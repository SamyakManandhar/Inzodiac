package com.alphaa.inzodiac.ui.activity.loginmodule;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityLoginBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.ui.activity.aboutself.AboutSelfActivity;
import com.alphaa.inzodiac.ui.activity.editphonemodule.EditPhoneActivity;
import com.alphaa.inzodiac.ui.activity.phoneloginmodule.PhoneLoginActivity;
import com.alphaa.inzodiac.ui.activity.signupmodule.SignupActivity;
import com.alphaa.inzodiac.ui.activity.signupmodule.UserdatailModel;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.CheckNetwork;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.FirebaseUserModel;
import com.alphaa.inzodiac.utility.LocationModePermmission;
import com.alphaa.inzodiac.utility.PDialog;
import com.alphaa.inzodiac.utility.Validation;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient.IMAGE_BASE_URL;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ApiCallBackInterFace.loginInfoCallback, GoogleApiClient.OnConnectionFailedListener {

    private ActivityLoginBinding binding;
    private String TAG = "LoginActivity";
    private String firbaseToken;
    private CallbackManager callbackManager;
    private Validation validation;
    private LoginButton login_button;
    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    // integer for permissions results request
    private static final int ALL_PERMISSIONS_RESULT = 1011;

    LocationModePermmission locationModePermmission = new LocationModePermmission();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        validation = new Validation(this);

        inItView();
    }


    private void inItView() {
        //facebook code
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        binding.loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));


        //fireabse token
        FirebaseApp.initializeApp(LoginActivity.this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        Log.e(TAG, "onComplete: task " + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            // Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        firbaseToken = task.getResult().getToken();
                        Log.e("token111111111 ", firbaseToken);
                    }
                });


        //google code
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        setClicks(binding.login, binding.register, binding.iveye, binding.ivfacebook, binding.ivgoogle, binding.ivBack, binding.phoneLoginBtn);
        runtimePermission();

    }


    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_back:

                finish();

                break;

            case R.id.login: {
                Validation validation = new Validation(this);
                if (validation.isEmailValid(binding.etEmail) && validation.ispasswordValid(binding.etEmail)) {
                    if (CheckNetwork.isNetAvailable(getApplicationContext())) {

                        Log.e(TAG, "onClick: firebase token " + firbaseToken);
                        new LoginPresenter(this, this).loginData(binding.etEmail.getText().toString(), binding.etPass.getText().toString(), firbaseToken, "");
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;

            case R.id.register: {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.ivfacebook: {
                new LoginPresenter(this, this).getFacebook(binding.loginButton, callbackManager);
            }
            break;
            case R.id.ivgoogle: {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
                mGoogleApiClient.connect();
            }
            break;

            case R.id.phoneLoginBtn: {
                Intent intent = new Intent(LoginActivity.this, PhoneLoginActivity.class);
                intent.putExtra("firbaseToken", firbaseToken);
                startActivity(intent);
            }
            break;

            case R.id.iveye: {
                ShowHidePass();
            }
            break;
        }
    }

    public void ShowHidePass() {
        if (binding.etPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            binding.iveye.setImageResource(R.drawable.ic_eye);
            binding.etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            binding.iveye.setImageResource(R.drawable.ic_eye_hidden);
            binding.etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }


    @Override
    public void onLoginInfoResponse(String s) {

        // Log.e(TAG, "onLoginInfoResponse: ss "+s );
        UserdatailModel userdatailModel = null;
        String s1 = "";
        try {
            //s1 = s.replace("null","\"\"");
            s1 = s;//.replace("null","\"\"");
            userdatailModel = new Gson().fromJson(s1, UserdatailModel.class);
            Log.e(TAG, "onLoginInfoResponse: aaa" + userdatailModel.getData().getId() + ", " +  userdatailModel.getData().getEmail() + ", " +  userdatailModel.getData().getName());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.e(TAG, "onLoginInfoResponse: exception " + e.getMessage());
        }

        //userdatailModel.getData().setIs_complete("1");
        String imgpath = "";
        if (userdatailModel.getData().getProfilePic().size() != 0)
            imgpath = IMAGE_BASE_URL + userdatailModel.getData().getProfilePic().get(0);

        if (userdatailModel.getData().getIs_complete().equalsIgnoreCase("0")) {

            AppSession.setStringPreferences(getApplicationContext(), Constants.USEREIDSIGNUP, userdatailModel.getData().getId());
            AppSession.setStringPreferences(getApplicationContext(), Constants.USER_FB_TOKEN_SIGNUP, firbaseToken);
            if (binding.etEmail.getText().toString().isEmpty()) {
                AppSession.setStringPreferences(getApplicationContext(), Constants.AUTH_TYPE, "social");
                AppSession.setStringPreferences(getApplicationContext(), Constants.USER_EMAIL_SIGNUP, userdatailModel.getData().getEmail());
                AppSession.setStringPreferences(getApplicationContext(), Constants.USER_NAME_SIGNUP, userdatailModel.getData().getName());
            }
            else {
                AppSession.setStringPreferences(getApplicationContext(), Constants.AUTH_TYPE, "email_login");
                AppSession.setStringPreferences(getApplicationContext(), Constants.USER_EMAIL_SIGNUP, binding.etEmail.getText().toString());
                AppSession.setStringPreferences(getApplicationContext(), Constants.USER_PASS_SIGNUP, binding.etPass.getText().toString());
            }

            if (userdatailModel.getData().getPhone().isEmpty()) {
                AppSession.setStringPreferences(getApplicationContext(), Constants.AUTH_FLOW, "login");

                Intent intent = new Intent(LoginActivity.this, EditPhoneActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(activity, "Please Complete Profile", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, AboutSelfActivity.class);
                startActivity(intent);
            }

        } else {

            Log.e(TAG, userdatailModel.getData().getIntrested_in() + " onLoginInfoResponse: birth " + userdatailModel.getData().getDob());


            //2020/12/26

            String inputPattern = "MM-dd-yyyy";
            String outputPattern = "yyyy/MM/dd";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

            Date date = null;
            String newdate = null;

            try {
                date = inputFormat.parse(userdatailModel.getData().getDob());
                newdate = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.e(TAG, "onLoginInfoResponse: new date " + newdate);

            getPrefHelper().setUserId(userdatailModel.getData().getId());
            getPrefHelper().setUserDataModel(s1);
            getPrefHelper().issetLoggedIn();

            AppSession.setStringPreferences(getApplicationContext(), Constants.USEREId, userdatailModel.getData().getId());
            AppSession.setStringPreferences(getApplicationContext(), Constants.USERBIRTHDATE, newdate);
            AppSession.setStringPreferences(getApplicationContext(), Constants.USERPHONE, userdatailModel.getData().getPhone());
            AppSession.setStringPreferences(getApplicationContext(), Constants.USERNAME, userdatailModel.getData().getName());
            AppSession.setStringPreferences(getApplicationContext(), Constants.USEREMAIL, userdatailModel.getData().getEmail());
            AppSession.setStringPreferences(getApplicationContext(), Constants.USERIMAGE, imgpath);

            AppSession.setStringPreferences(getApplicationContext(), Constants.USERINTERESTED_IN, userdatailModel.getData().getIntrested_in());

            AppSession.setStringPreferences(getApplicationContext(), Constants.USERPREFFERDHOROSCOPE, userdatailModel.getData().getHoroscopetype());
            AppSession.setStringPreferences(getApplicationContext(), Constants.WESTERNZODAICSIGN, userdatailModel.getData().getWestern_zodaic());
            AppSession.setStringPreferences(getApplicationContext(), Constants.CHINESEZODAICSIGN, userdatailModel.getData().getChinese_zodaic());


            ////////=================other user gender

            if (userdatailModel.getData().getGender().toLowerCase().equalsIgnoreCase("male")) {
                AppSession.setStringPreferences(getApplicationContext(), Constants.OTHERUSERGENDER, "female");
            } else {
                AppSession.setStringPreferences(getApplicationContext(), Constants.OTHERUSERGENDER, "male");
            }

            AppSession.setStringPreferences(getApplicationContext(), Constants.USERORIENTATION, userdatailModel.getData().getOrientation());


            //token
            AppSession.setStringPreferences(getApplicationContext(), Constants.TOTAL_TOKEN, userdatailModel.getData().getTotal_token());
            AppSession.setStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE, userdatailModel.getData().getSubscription());


            addUserFirebaseDatabase(userdatailModel.getData().getSubscription(), firbaseToken, userdatailModel.getData().getName(),
                    userdatailModel.getData().getEmail(), userdatailModel.getData().getId(),
                    imgpath);
            Toast.makeText(activity, "" + userdatailModel.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, TabActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onSocialLoginInfoResponse(String email, String name, String image) {

        Log.e(TAG + firbaseToken, name + "  onSocialLoginInfoResponse: email " + email);
        new LoginPresenter(this, this).socialLoginData(name, email, firbaseToken);


        //Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        //intent.putExtra("name",name);
        //intent.putExtra("email",email);
        //startActivity(intent);

    }


    @Override
    public void showLoaderProcess() {
        PDialog.pdialog(this);
    }

    @Override
    public void hideLoaderProcess() {
        PDialog.hideDialog();
    }

    @Override
    public void onApiErrorResponse(String Errorresponse, String error_type) {

        Toast.makeText(activity, "" + Errorresponse, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onApiErrorResponse: " + Errorresponse);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e(TAG, RC_SIGN_IN + " onActivityResult: " + requestCode);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Log.e(TAG, "onActivityResult: "+result.getSignInAccount().getDisplayName() );
            new LoginPresenter(this, this).handleSignInResult(mGoogleApiClient, result);

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void addUserFirebaseDatabase(String subscription, String firbaseToken, String name, String email, String uId, String profilepic) {

        Log.e(TAG, "addUserFirebaseDatabase: ");
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseUserModel userModel = new FirebaseUserModel();
        userModel.firebaseToken = firbaseToken;
        userModel.name = name;
        userModel.email = email;
        userModel.profilePic = profilepic;
        userModel.uid = uId;
        userModel.subscription = subscription;
        database.child(Constants.USER_TABLE).child(uId).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Log.e(TAG, "onComplete:addUserFirebaseDatabase ");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    private void runtimePermission() {
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = permissionsToRequest(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }


        int myVersion = Build.VERSION.SDK_INT, locationMode = 0;
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }


        if (myVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (locationMode == 0 || locationMode == 1) {
                locationModePermmission.displayLocationSettingsRequest(LoginActivity.this);
            }
        } else if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showSettingsAlert();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            new AlertDialog.Builder(LoginActivity.this).
                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.
                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("Cancel", null).create().show();

                            return;
                        }
                    }
                } else {

                }

                break;
        }
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();
        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}