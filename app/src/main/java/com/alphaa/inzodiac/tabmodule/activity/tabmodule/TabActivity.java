package com.alphaa.inzodiac.tabmodule.activity.tabmodule;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityTabBinding;
import com.alphaa.inzodiac.databinding.LogoutDialogBinding;
import com.alphaa.inzodiac.staticdata.BirthdayData;
import com.alphaa.inzodiac.staticdata.ZodaicActivity;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.CityResponse;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.CountryResponse;
import com.alphaa.inzodiac.tabmodule.fragment.likemodule.LikeFragment;
import com.alphaa.inzodiac.tabmodule.fragment.luckmodule.LuckyandunluckyFragment;
import com.alphaa.inzodiac.tabmodule.fragment.menumodule.MenuFragment;
import com.alphaa.inzodiac.tabmodule.fragment.messagemodule.MessageFragment;
import com.alphaa.inzodiac.tabmodule.fragment.profilemodule.profiledetailmodule.ProfileDetailFragment;
import com.alphaa.inzodiac.tabmodule.fragment.categoryfragmentmodule.CategoryFragment;
import com.alphaa.inzodiac.ui.activity.detailaboyou.DetailAboYouActivity;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.LocationModePermmission;
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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TabActivity extends BaseActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    String TAG = getClass().getSimpleName();

    ActivityTabBinding binding;
    public  static  FrameLayout frame;
    String getasociated;





    //city

    private ArrayList<CountryResponse.DataItem> countryList;
    private ArrayList<CityResponse.DataItem> cityList;
    private String id;

    private boolean bool5 = false;
    private boolean bool6 = false;

    String city="",country="";





    //location
    LocationModePermmission locationModePermmission=new LocationModePermmission();
    int locationMode;private Geocoder geocoder;
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
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    // integer for permissions results request
    private static final int ALL_PERMISSIONS_RESULT = 1011;




    public static double latt,longg;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab);
        inItView();

        Log.e(TAG, "onCreate: tokens "+AppSession.getStringPreferences(getApplicationContext(), Constants.TOTAL_TOKEN) );
        Log.e(TAG, "onCreate: subscriptions "+AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE) );
    }

    private void inItView() {


        String dob = AppSession.getStringPreferences(getApplicationContext(), Constants.USERBIRTHDATE);
        getasociated = birthDateMatch(dob);



        setClicks(binding.one, binding.two, binding.three, binding.four, binding.five);


        Log.e(TAG, "inItView: "+getIntent().getStringExtra("intent") );
        Log.e(TAG, "inItView: "+getIntent().getStringExtra("userlist") );

        if (getIntent().getStringExtra("intent") != null) {
            if (getIntent().getStringExtra("intent").equals("1")) {
                LikeFragment likeFragment = LikeFragment.newInstance("1", "");
                replaceFragment(likeFragment, binding.frame.getId(), true);
                updateTabView(R.id.two);

            } else if (getIntent().getStringExtra("intent").equals("2")){
                binding.three.callOnClick();
            }
        }else if (getIntent().getStringExtra("userlist") != null){
            MenuFragment menuFragment = MenuFragment.newInstance("", getIntent().getStringExtra("userlist"));
            replaceFragment(menuFragment, binding.frame.getId(), true);
            updateTabView(R.id.one);
        }else {
            binding.one.callOnClick();
        }


















        //====================locatio===========starts




        geocoder = new Geocoder(this, Locale.getDefault());
        permission();
        //gpsDialoge();

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        int myVersion= Build.VERSION.SDK_INT;
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            locationMode =      Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }


        if (myVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (locationMode == 0 || locationMode == 1) {
                locationModePermmission.displayLocationSettingsRequest(TabActivity.this);
            }
        }else if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
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
            case R.id.one:

              /*  MenuFragment menuFragment = MenuFragment.newInstance(binding.frame.getId(), "");
                replaceFragment(menuFragment, binding.frame.getId(), true);
                updateTabView(R.id.one);*/
                CategoryFragment menuFragment = CategoryFragment.newInstance(String.valueOf(binding.frame.getId()), "");
                replaceFragment(menuFragment, binding.frame.getId(), true);
                updateTabView(R.id.one);

                break;

            case R.id.two:
                LikeFragment likeFragment = LikeFragment.newInstance("", "");
                replaceFragment(likeFragment, binding.frame.getId(), true);
                updateTabView(R.id.two);
                break;
            case R.id.three:

               /* if (AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE)==null){
                    Toast.makeText(this, "Please Activate Premium Subscription", Toast.LENGTH_SHORT).show();

                    return;
                }*/

                //if (AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE).toLowerCase().contains("yes")){
                    MessageFragment messageFragment = MessageFragment.newInstance("", "");
                replaceFragment(messageFragment, binding.frame.getId(), true);
                updateTabView(R.id.three);
                /*}else {
                    Toast.makeText(this, "Please Activate Premium Subscription", Toast.LENGTH_SHORT).show();
                }*/
                break;
            case R.id.four:
                ProfileDetailFragment profileFragment = ProfileDetailFragment.newInstance("", "");
                replaceFragment(profileFragment, binding.frame.getId(), true);
                updateTabView(R.id.four);
                break;

            case R.id.five:

                 /*  if (AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE)==null){
                    Toast.makeText(this, "Please Activate Premium Subscription", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE).toLowerCase().contains("yes")){
                }else {
                    Toast.makeText(this, "Please Activate Premium Subscription", Toast.LENGTH_SHORT).show();

                    return;

                }
*/



                Bundle bundle = new Bundle();
                //String myMessage = "Stackoverflow is cool!";
                bundle.putString("message", getasociated );
                //fragInfo.setArguments(bundle);

                LuckyandunluckyFragment luckyandunluckyFragment = LuckyandunluckyFragment.newInstance("", "");

                luckyandunluckyFragment.setArguments(bundle);

                replaceFragment(luckyandunluckyFragment, binding.frame.getId(), true);
                binding.frame.setVisibility(View.VISIBLE);
                //binding.rl.setVisibility(View.GONE);

                updateTabView(R.id.five);

                break;


        }
    }


    private void updateTabView(int flag) {
        switch (flag) {
            case R.id.one:
                binding.imgOne.setImageResource(R.drawable.ic_active_menu);
                binding.imgTwo.setImageResource(R.drawable.ic_inactive_like);
                binding.imgThree.setImageResource(R.drawable.ic_inactive_messages);
                binding.imgFour.setImageResource(R.drawable.ic_inactive_profile);

                binding.imgFive.setImageResource(R.drawable.ic_inactive_zodiac);
                break;
            case R.id.two:
                binding.imgOne.setImageResource(R.drawable.ic_inactive_menu);
                binding.imgTwo.setImageResource(R.drawable.ic_active_like);
                binding.imgThree.setImageResource(R.drawable.ic_inactive_messages);
                binding.imgFour.setImageResource(R.drawable.ic_inactive_profile);
                binding.imgFive.setImageResource(R.drawable.ic_inactive_zodiac);
                break;
            case R.id.three:
                binding.imgOne.setImageResource(R.drawable.ic_inactive_menu);
                binding.imgTwo.setImageResource(R.drawable.ic_inactive_like);
                binding.imgThree.setImageResource(R.drawable.ic_active_messages);
                binding.imgFour.setImageResource(R.drawable.ic_inactive_profile);
                binding.imgFive.setImageResource(R.drawable.ic_inactive_zodiac);
                break;
            case R.id.four:
                binding.imgOne.setImageResource(R.drawable.ic_inactive_menu);
                binding.imgTwo.setImageResource(R.drawable.ic_inactive_like);
                binding.imgThree.setImageResource(R.drawable.ic_inactive_messages);
                binding.imgFour.setImageResource(R.drawable.ic_active_profile);
                binding.imgFive.setImageResource(R.drawable.ic_inactive_zodiac);
                break;

            case R.id.five:
                binding.imgOne.setImageResource(R.drawable.ic_inactive_menu);
                binding.imgTwo.setImageResource(R.drawable.ic_inactive_like);
                binding.imgThree.setImageResource(R.drawable.ic_inactive_messages);
                binding.imgFour.setImageResource(R.drawable.ic_inactive_profile);
                binding.imgFive.setImageResource(R.drawable.ic_active_zodiac);
                break;


        }
    }


    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {



        Log.e(TAG, "onBackPressed: "+getSupportFragmentManager().getBackStackEntryCount() );
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
            //getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else {
            backDialog();
        }
    }

    public void backDialog() {
        LogoutDialogBinding binding;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.logout_dialog, null, false);
        dialog.setContentView(binding.getRoot());
        binding.cat.setText("Do you want to exit?");
        binding.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        binding.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
               dialog.dismiss();
            }
        });
        dialog.show();
    }















    public String birthDateMatch(String dateofbirth){

        String asociateAnimal = "no";

        ArrayList<BirthdayData> birthdayDataArrayList = ZodaicActivity.birthDayData();

        Log.e(TAG+birthdayDataArrayList.size(), "birthDateMatch: "+dateofbirth );
        for (int i = 0; i <birthdayDataArrayList.size(); i++) {




            //=================================================
            Date strDobDate = null,strStartYear=null,strendYear=null;



            String birthdayData = birthdayDataArrayList.get(i).getYearEnd();

            Log.e(TAG, "birthDateMatch: 1111111   "+birthdayData );

            String date_1984_2043 = birthdayData;

            Log.e(TAG, "birthDateMatch: 333333 "+date_1984_2043 );


            //split date
            String[] datearray =date_1984_2043.split("-");

            Log.e(TAG, "birthDateMatch: datearray "+datearray.length );

            String startdate = datearray[0];
            String enddate = datearray[1];

            Log.e(TAG+dateofbirth, enddate+" split birthDateMatch: "+startdate );


            //convert dob to date object
            //birth date match  ///2021/02/10

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            try {
                strDobDate = sdf.parse(dateofbirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.e(TAG, startdate+"   :birthDateMatch: strdate dob object "+strDobDate );


















            //convert startdate to date object
            SimpleDateFormat sdfstart1 = new SimpleDateFormat("MMM dd yyyy");
            try {

                Log.e(TAG, "birthDateMatch: ssssssss "+startdate );
                strStartYear = sdfstart1.parse(startdate);
                Log.e(TAG, "birthDateMatch: ssssssss 1111   "+strStartYear );
            } catch (ParseException e) {

                Log.e(TAG, e.toString()+"   birthDateMatch: eeeeee "+e.getMessage() );
                e.printStackTrace();
            }
            Log.e(TAG, startdate+" birthDateMatch: strdate start object "+strStartYear );


            //convert end date to dateobject
            SimpleDateFormat sdfend = new SimpleDateFormat("MMM dd yyyy");
            try {
                strendYear = sdfend.parse(enddate);
            } catch (ParseException e) {
                Log.e(TAG, "birthDateMatch: eeeeee 11111 "+e.getMessage() );
                e.printStackTrace();
            }
            Log.e(TAG, "birthDateMatch: strdate end  object "+strendYear );
            Log.e(TAG, "birthDateMatch: last date object  dob       "+strDobDate );
            Log.e(TAG, "birthDateMatch: last date object 111 start  "+strStartYear );
            Log.e(TAG, "birthDateMatch: last date object 222 end    "+strendYear );























            try {


                if (strDobDate.compareTo(strStartYear) > 0 && strDobDate.compareTo(strendYear) < 0) {
                    ///System.out.println("Date 1 occurs after Date 2");
                    Log.e(TAG, i + " birthDateMatch: match111 yesssss");
                    asociateAnimal = birthdayDataArrayList.get(i).getAnimal();
                    break;
                } else {
                    Log.e(TAG, "birthDateMatch: match111  noooo");
                    //System.out.println("Date 1 occurs before Date 2");
                }
            }catch (Exception e){

            }
        }
        Log.e(TAG, "birthDateMatch: asociated animal "+asociateAnimal );

        return asociateAnimal;

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

            Log.e(TAG, city+" :getAddress: llaaaaaa  111111  "+obj.getCountryName() );


            country = obj.getCountryName();




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.e(TAG, "onConnected: " );
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        Log.e(TAG, "onConnected: location "+location );
        if (location != null) {
            getAddress(location);

            latt = location.getLatitude();
            longg = location.getLongitude();

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

        //Log.e(TAG, "onLocationChanged: "+location );
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


    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e(TAG, requestCode+" onActivityResult: "+resultCode );
        if(requestCode==1){

            if(resultCode==RESULT_OK){

                //Toast.makeText(this, "Gps opened", Toast.LENGTH_SHORT).show();
                //if user allows to open gps
                Log.e(TAG,"onActivityResult result ok"+data.toString());
                // Intent intent = new Intent(getApplicationContext(),CurrentAddAddressActivity.this);
                // LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

            }else if(resultCode==RESULT_CANCELED){

                // in case user back press or refuses to open gps
                Log.e(TAG,"onActivityResult result cancelled"+data.toString());
            }
        }
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








    private void permission() {
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = permissionsToRequest(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }
        // we build google api client
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
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
                            new AlertDialog.Builder(TabActivity.this).
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
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
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

    //========================================location












}