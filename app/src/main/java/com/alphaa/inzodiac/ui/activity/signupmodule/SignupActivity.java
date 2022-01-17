package com.alphaa.inzodiac.ui.activity.signupmodule;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivitySignupBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.aboutmodule.AboutActivity;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.CityResponse;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.CountryResponse;
import com.alphaa.inzodiac.ui.activity.aboutself.AboutSelfActivity;
import com.alphaa.inzodiac.ui.activity.detailaboyou.DetailAboYouActivity;
import com.alphaa.inzodiac.ui.activity.editprofile.EditProfileActivity;
import com.alphaa.inzodiac.ui.activity.loginmodule.LoginActivity;
import com.alphaa.inzodiac.ui.activity.phoneverification.PhoneVerificationActivity;
import com.alphaa.inzodiac.ui.activity.phoneverification.VerificationPresenter;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.CheckNetwork;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.FirebaseUserModel;
import com.alphaa.inzodiac.utility.LocationModePermmission;
import com.alphaa.inzodiac.utility.PDialog;
import com.alphaa.inzodiac.utility.Validation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SignupActivity extends BaseActivity implements View.OnClickListener,  ApiCallBackInterFace.phoneVerifyInfoCallback

        , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    String TAG = getClass().getSimpleName();

    private ActivitySignupBinding binding;
    private Validation validation;
    String firbaseToken;


    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    ArrayList<String> permissions = new ArrayList<>();
    // integer for permissions results request
    private static final int ALL_PERMISSIONS_RESULT = 1011;

    LocationModePermmission locationModePermmission = new LocationModePermmission();


    //city

    private ArrayList<CountryResponse.DataItem> countryList;
    private ArrayList<CityResponse.DataItem> cityList;
    private String id;

    private boolean bool5 = false;
    private boolean bool6 = false;

    String city = "", country = "";


    //location
    int locationMode;
    private Geocoder geocoder;
    private List<Address> addresses;

    private Location location;
    private final int ANIMATION_TYPE_RIPPLE = 0;
    private int whichAnimationWasRunning = ANIMATION_TYPE_RIPPLE;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final long UPDATE_INTERVAL = 500, FASTEST_INTERVAL = 500;
    private String customer_recent_location_id, recent_address_line1, recent_address_line2, recent_customer_id,
            recent_area_name, recent_location_name, recent_city, recent_langtude, recent_latitude,
            recent_location_homepage_header_to_display, recent_location_to_display, dialogeClose = "";
    private String address_line1, address_line2, location_name, area_name, address, contact_number,
            langitude, latitude, address_type_id, state, pin_code, land_mark, AddressBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        inItView();


        //fireabse token
        FirebaseApp.initializeApp(SignupActivity.this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            // Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        firbaseToken = task.getResult().getToken();
                        Log.e("token", firbaseToken);
                    }
                });

        permission();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");

        if (name != null) {
            binding.etFullname.setText(name);
            binding.etEmail.setText(email);
        }


        Log.e(TAG, name + "  onSocialLoginInfoResponse: email " + email);

    }


    private void inItView() {
        setClicks(binding.signin, binding.register, binding.iveye, binding.ivback, binding.tvTerms);
        validation = new Validation(this);


        permission();
        //====================locatio===========starts


        geocoder = new Geocoder(this, Locale.getDefault());
        permission();
        //gpsDialoge();

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        int myVersion = Build.VERSION.SDK_INT;
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }


        if (myVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (locationMode == 0 || locationMode == 1) {
                locationModePermmission.displayLocationSettingsRequest(SignupActivity.this);
            }
        } else if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showSettingsAlert();
        }
        //=====================location ======================ends

    }


    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.register: {

                permission();

                if (validation.isNameValid(binding.etFullname) && validation.isEmailValid(binding.etEmail) && validation.ispasswordValid(binding.etPass) && validation.isMobileNoValid(binding.etMob)) {
                    if (!binding.checkbox.isChecked()) {
                        Toast.makeText(activity, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
                    } else {
                        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
                            new VerificationPresenter(this, this).checkSamePhoneNumber(binding.etMob.getText().toString());
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            break;

            case R.id.signin: {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            break;
            case R.id.ivback: {
                onBackPressed();
            }
            break;
            case R.id.iveye: {
                ShowHidePass();
            }
            break;

            case R.id.tv_terms:

                Intent intent = new Intent(activity, AboutActivity.class);
                intent.putExtra("name", "Terms & Conditions");
                startActivity(intent);

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
    public void showLoaderProcess() {
        PDialog.pdialog(this);
    }

    @Override
    public void hideLoaderProcess() {
        PDialog.hideDialog();
    }

    @Override
    public void onApiErrorResponse(String Errorresponse, String error_type) {
        Log.e(TAG, "onApiErrorResponse: " + Errorresponse);
        Toast.makeText(activity, "" + Errorresponse, Toast.LENGTH_SHORT).show();
    }

    ///permission
    private void permission() {
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
                locationModePermmission.displayLocationSettingsRequest(SignupActivity.this);
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
                            new AlertDialog.Builder(SignupActivity.this).
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

    /*===============================current location==================*/
    private void getAddress(Location location) {


        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address_line1 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            location_name = addresses.get(0).getThoroughfare() != null ? addresses.get(0).getThoroughfare() : addresses.get(0).getFeatureName();

            //savedLocation_name = addresses.get(0).getThoroughfare() != null ? addresses.get(0).getThoroughfare() : addresses.get(0).getFeatureName();
            //savedArea_Name = addresses.get(0).getSubLocality() != null ? addresses.get(0).getSubLocality() : location_name;

            area_name = addresses.get(0).getSubLocality() != null ? addresses.get(0).getSubLocality() : location_name;
            city = addresses.get(0).getLocality() != null ? addresses.get(0).getLocality() : addresses.get(0).getSubAdminArea();
            state = addresses.get(0).getAdminArea();
            pin_code = addresses.get(0).getPostalCode();
            latitude = String.valueOf(addresses.get(0).getLatitude());
            langitude = String.valueOf(addresses.get(0).getLongitude());

            if (city == null) {
                location_name = area_name = pin_code;
                city = state;
            } else if (city != null && addresses.get(0).getFeatureName().equalsIgnoreCase(addresses.get(0).getCountryName())) {
                location_name = area_name = address_line1.split(",", 1)[0];
            }

            Address obj = addresses.get(0);

            Log.e(TAG, city + " :getAddress: llaaaaaa  111111  " + obj.getCountryName());

            country = obj.getCountryName();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.e(TAG, "onConnected: ");
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        Log.e(TAG, "onConnected: location " + location);
        if (location != null) {
            getAddress(location);
            // Toast.makeText(HomeActivity.this, "Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude(), Toast.LENGTH_LONG).show();
            // Toast.makeText(HomeActivity.this, "Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude(), Toast.LENGTH_LONG).show();
        }
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.e(TAG, "onLocationChanged: " + location);
        if (location == null) {
            getAddress(location);
        }
    }


    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e(TAG, requestCode + " onActivityResult: " + resultCode);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {

                //Toast.makeText(this, "Gps opened", Toast.LENGTH_SHORT).show();
                //if user allows to open gps
                Log.e(TAG, "onActivityResult result ok" + data.toString());
                // Intent intent = new Intent(getApplicationContext(),CurrentAddAddressActivity.this);
                // LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

            } else if (resultCode == RESULT_CANCELED) {

                // in case user back press or refuses to open gps
                Log.e(TAG, "onActivityResult result cancelled" + data.toString());
            }
        }
    }


    private void gpsDialoge() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getApplicationContext()).addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                            LocationRequest locationRequest = LocationRequest.create();
                            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            locationRequest.setInterval(500);
                            locationRequest.setFastestInterval(500);
                            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                            builder.setAlwaysShow(true);
                            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                                @Override
                                public void onResult(LocationSettingsResult result) {
                                    final Status status = result.getStatus();

                                    //Log.e(TAG, "onResult: "+result.getLocationSettingsStates(). );
                                   /* switch (status.getStatusCode()) {
                                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                            try {
                                                // Show the dialog by calling startResolutionForResult(),
                                                // and check the result in onActivityResult().
                                                //status.startResolutionForResult(getActivity(), REQUEST_LOCATION);
                                                startIntentSenderForResult(status.getResolution().getIntentSender(),
                                                        REQUEST_LOCATION, null, 0, 0, 0, null);
                                                //nextScreen();
                                                // finish();
                                            } catch (IntentSender.SendIntentException e) {
                                                // Ignore the error.
                                            }
                                            break;
                                    }*/
                                }
                            });

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            // Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
        //make home selected
       /* if (!checkPlayServices()) {
            Toast.makeText(CurrentAddAddressActivity.this, "You need to install Google Play Services to use the App properly", Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    public void onCheckSamePhoneResponse(Boolean b) {
        if (b) {
            Toast.makeText(SignupActivity.this, "Your phone number is already in use.", Toast.LENGTH_LONG).show();
        } else {

            AppSession.setStringPreferences(getApplicationContext(), Constants.AUTH_FLOW, "register");
            AppSession.setStringPreferences(getApplicationContext(), Constants.AUTH_TYPE, "email_login");

            UserdatailModel.SignupDatum data = new UserdatailModel.SignupDatum();
            data.setEmail(binding.etEmail.getText().toString());
            data.setPassword(binding.etPass.getText().toString());
            data.setPhone(binding.etMob.getText().toString());
            data.setName(binding.etFullname.getText().toString());
            data.setLat("22.7196");
            data.setLong("75.8577");
            data.setFirebase_token(firbaseToken);

            Intent intent = new Intent(activity, PhoneVerificationActivity.class);
            intent.putExtra("user_data", data);
            startActivity(intent);
        }
    }

}