package com.alphaa.inzodiac.ui.activity.editprofile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityEditProfileBinding;
import com.alphaa.inzodiac.databinding.LogoutDialogBinding;
import com.alphaa.inzodiac.gallery.models.Image;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailModel;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.LookingSpinnerAdapter;
import com.alphaa.inzodiac.tabmodule.fragment.profilemodule.profiledetailmodule.ProfileDetailPresenter;
import com.alphaa.inzodiac.ui.activity.aboutself.AboutSelfPresenter;
import com.alphaa.inzodiac.ui.activity.aboutself.ImageAdapter;
import com.alphaa.inzodiac.ui.activity.stepcomplete.StepCompleteActivity;
import com.alphaa.inzodiac.utility.AppConstants;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.PDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient.IMAGE_BASE_URL;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener, ApiCallBackInterFace.AboutInfoCallback, ApiCallBackInterFace.CategoryUpdateCallback {
    String TAG = getClass().getSimpleName();

    ActivityEditProfileBinding binding;

    AppCompatSpinner sp_orientation, sp_lookingfor;
    DetailModel detailModel;
    String userid;
    private Uri temPhoto;
    private File userImageFile;
    private boolean bool1 = false, bool2 = false, bool3 = false, bool4 = false, bool5 = false, bool6 = false, bool7 = false, bool8 = false, bool9 = false, bool10 = false;
    private String sdrink = "", srelationship = "", swantchild = "", ssmoke = "", shoroscope = "", sexercise = "", sethenicity = "", strinterest = "", orientation = "", looking = "", date = "";
    private ArrayList<Image> imglist;
    private ImageAdapter adapter;
    private String Age = "",profilImgPath, profileData,vimage_type;
    ArrayList<DetailModel.ProfilePic> userprofileList;
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 100;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        imglist = new ArrayList<>();
        userprofileList = new ArrayList<>();
        userid = AppSession.getStringPreferences(getApplicationContext(), com.alphaa.inzodiac.utility.Constants.USEREId);

        profileData = getIntent().getStringExtra("profiledata");
        Log.e(TAG, "onCreate: profile data "+profileData );
        if (profileData != null) {
            detailModel = new Gson().fromJson(profileData, DetailModel.class);
        }


        /////////////orientation==============================================start
        ArrayList<String> arrayListOrientation = new ArrayList<>();
        arrayListOrientation.add("Orientation");
        arrayListOrientation.add("Straight");
        arrayListOrientation.add("Gay");
        arrayListOrientation.add("Lesbian");
        arrayListOrientation.add("Bisexual");
        arrayListOrientation.add("Asexual");
        arrayListOrientation.add("Demisexual");
        arrayListOrientation.add("Pansexual");
        arrayListOrientation.add("Queer");
        arrayListOrientation.add("Questioning");
        sp_orientation = findViewById(R.id.sp_orientation);
        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(this, arrayListOrientation);
        sp_orientation.setAdapter(adapter);

        //get index from arraylist
        int index = arrayListOrientation.indexOf(detailModel.getData().getOrientation());
        Log.e(TAG, "onCreate: index "+index );

        try {
            binding.tvori.setText(arrayListOrientation.get(index));
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.spOrientation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (bool1 && position != 0) {
                    orientation = arrayListOrientation.get(position);
                    binding.tvori.setText(orientation);
                }
                bool1 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /////////////orientation==============================================end

        /////////////looking for==============================================start
        ArrayList<String> arrayListlookingfor = new ArrayList<>();
        arrayListlookingfor.add("Looking For");
        arrayListlookingfor.add("Don't know");
        arrayListlookingfor.add("Chat");
        arrayListlookingfor.add("Business relationship");
        arrayListlookingfor.add("Friends");
        arrayListlookingfor.add("Casual");
        arrayListlookingfor.add("Serious relationship");


        sp_lookingfor = findViewById(R.id.sp_lookingfor);
        LookingSpinnerAdapter adapter1 = new LookingSpinnerAdapter(this, arrayListlookingfor);
        sp_lookingfor.setAdapter(adapter1);

        int index1 = arrayListlookingfor.indexOf(detailModel.getData().getLookingFor());
        binding.tvlook.setText(arrayListlookingfor.get(index1));

        binding.spLookingfor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (bool2 && position != 0) {
                    looking = arrayListlookingfor.get(position);
                    binding.tvlook.setText(looking);
                }
                bool2 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /////////////looking for==============================================ends

        /////////////intrested  for==============================================start
        ArrayList<String> arrayListintest = new ArrayList<>();
        arrayListintest.add("InteresteIn");
        arrayListintest.add("Male");
        arrayListintest.add("Female");
        //arrayListintest.add("Non-Binary");


        arrayListintest.add("Agender");
        arrayListintest.add("Androgyne");
        arrayListintest.add("Androgynous");


        arrayListintest.add("Bigender");
        arrayListintest.add("Cis");
        arrayListintest.add("Cisgender");

        arrayListintest.add("Cis Female");
        arrayListintest.add("Cis Male");
        arrayListintest.add("Cis Man");
        arrayListintest.add("Cis Woman");
        arrayListintest.add("Cisgender Female");
        arrayListintest.add("Cisgender Male");
        arrayListintest.add("Cisgender Man");
        arrayListintest.add("Cisgender Woman");
        arrayListintest.add("Female to Male");
        arrayListintest.add("FTM");
        arrayListintest.add("Gender Fluid");
        arrayListintest.add("Gender Nonconforming");
        arrayListintest.add("Gender Questioning");
        arrayListintest.add("Gender Variant");
        arrayListintest.add("Genderqueer");


        arrayListintest.add("Intersex");
        arrayListintest.add("MTF");
        arrayListintest.add("Neither");
        arrayListintest.add("Neutrois");
        arrayListintest.add("Non-binary");
        arrayListintest.add("Other");


        arrayListintest.add("Pangender");
        arrayListintest.add("Trans");
        arrayListintest.add("Trans Female");
        arrayListintest.add(" Trans*");
        arrayListintest.add("Trans* Female");
        arrayListintest.add("Trans Male");

        arrayListintest.add("Trans* Male");
        arrayListintest.add("Trans Man");
        arrayListintest.add("Trans* Man");
        arrayListintest.add("Trans Person");
        arrayListintest.add("Trans* Person");
        arrayListintest.add("Trans Woman");



        arrayListintest.add("Trans* Woman");
        arrayListintest.add("Transfeminine");
        arrayListintest.add("Transgender");
        arrayListintest.add("Transgender Female");
        arrayListintest.add("Transgender Male");
        arrayListintest.add("Transgender Man");

        arrayListintest.add("Transgender Person");
        arrayListintest.add("Transgender Woman");
        arrayListintest.add("Transmasculine");
        arrayListintest.add("Transsexual");
        arrayListintest.add("Transsexual Female");
        arrayListintest.add("Transsexual Male");

        arrayListintest.add("Transsexual Man");
        arrayListintest.add("Transsexual Person");
        arrayListintest.add("Transsexual Woman");
        arrayListintest.add("Two-Spirit");







        LookingSpinnerAdapter adapter11 = new LookingSpinnerAdapter(this, arrayListintest);
        binding.spIntrestedin.setAdapter(adapter11);

        Log.e(TAG, "onCreate: detailModel.getData().getIntrestedIn() "+detailModel.getData().getIntrestedIn() );

        int index2 = arrayListintest.indexOf(detailModel.getData().getIntrestedIn());
        try {
            binding.tvIntrestedin.setText(arrayListintest.get(index2));
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.spIntrestedin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (bool3 && position != 0) {
                    strinterest = arrayListintest.get(position);
                    binding.tvIntrestedin.setText(strinterest);
                }
                bool3 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /////////////intrested for==============================================ends


//        \\\ ethenicity ------
        ArrayList<String> arrayethenicity = new ArrayList<>();
        arrayethenicity.add("Native American");
        arrayethenicity.add("Black African");
        arrayethenicity.add("Black West Indian");
        arrayethenicity.add("East Asian");
        arrayethenicity.add("Hispanic/Latino");
        arrayethenicity.add("Midle Eastern");
        arrayethenicity.add("Pacific Islander");
        arrayethenicity.add("South Asian");
        arrayethenicity.add("Mixed Race");
        arrayethenicity.add("White/Caucasian");
        arrayethenicity.add("Other");

        LookingSpinnerAdapter adapter12 = new LookingSpinnerAdapter(this, arrayethenicity);
        binding.spEthenicity.setAdapter(adapter12);

        int index3 = arrayethenicity.indexOf(detailModel.getData().getEthnicity().trim());
        try {
            binding.tvEthenicity.setText(arrayethenicity.get(index3));
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.spEthenicity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (bool4 && position != 0) {
                    sethenicity = arrayethenicity.get(position);
                    binding.tvEthenicity.setText(sethenicity);
                }
                bool4 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

//          \\\ excercise------
        ArrayList<String> arrayexercise = new ArrayList<>();
        arrayexercise.add("Regularly");
        arrayexercise.add("Every now & then");
        arrayexercise.add("I will if you will");
        arrayexercise.add("I'm unable to exercise");
        arrayexercise.add("No thanks");
        arrayexercise.add("N/A");


        LookingSpinnerAdapter adapter13 = new LookingSpinnerAdapter(this, arrayexercise);
        binding.spExercise.setAdapter(adapter13);

        int index4 = arrayexercise.indexOf(detailModel.getData().getBodytype().trim());
        try {
            binding.tvExercise.setText(arrayexercise.get(index4));
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.spExercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (bool5 && position != 0) {
                    sexercise = arrayexercise.get(position);
                    binding.tvExercise.setText(sexercise);
                }
                bool5 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ///////////// preferred horoscope==============================================start
        ArrayList<String> arrayhoroscope = new ArrayList<>();
        arrayhoroscope.add("Chinese");
        arrayhoroscope.add("Western");
        arrayhoroscope.add("Both");

        LookingSpinnerAdapter adapter14 = new LookingSpinnerAdapter(this, arrayhoroscope);
        binding.spHoroscope.setAdapter(adapter14);

        int index5 = arrayhoroscope.indexOf(detailModel.getData().getHoroscopetype().trim());
        try {
            binding.tvHoroscope.setText(arrayhoroscope.get(index5));
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.spHoroscope.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (bool6 && position != 0) {
                    shoroscope = arrayhoroscope.get(position);
                    binding.tvHoroscope.setText(shoroscope);
                }
                bool6 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        /////////////  relationship==============================================start
        ArrayList<String> arrayrelation = new ArrayList<>();
        arrayrelation.add("Single");
        arrayrelation.add("Complicated");
        arrayrelation.add("Partnered");
        arrayrelation.add("Married");
        arrayrelation.add("N/A");

        LookingSpinnerAdapter adapter15 = new LookingSpinnerAdapter(this, arrayrelation);
        binding.spRelationship.setAdapter(adapter15);

        int index6 = arrayrelation.indexOf(detailModel.getData().getMarried().trim());
        try {
            binding.tvRelationship.setText(arrayrelation.get(index6));
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.spRelationship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (bool7 && position != 0) {
                    srelationship = arrayrelation.get(position);
                    binding.tvRelationship.setText(srelationship);
                }
                bool7 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        /////////////  want child=============================================start
        ArrayList<String> arraywantchild = new ArrayList<>();
        arraywantchild.add("No-Never");
        arraywantchild.add("Maybe someday");
        arraywantchild.add("Already Have");
        arraywantchild.add("All Grown Up");
        arraywantchild.add("N/A");

        LookingSpinnerAdapter adapter16 = new LookingSpinnerAdapter(this, arraywantchild);
        binding.spWantchild.setAdapter(adapter16);

        int index7 = arraywantchild.indexOf(detailModel.getData().getChildren().trim());
        try {
            binding.tvWantchild.setText(arraywantchild.get(index7));
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.spWantchild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (bool8 && position != 0) {
                    swantchild = arraywantchild.get(position);
                    binding.tvWantchild.setText(swantchild);
                }
                bool8 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ///////////// do you smoke =============================================start
        ArrayList<String> arraydoyousmoke = new ArrayList<>();
        arraydoyousmoke.add("Never");
        arraydoyousmoke.add("Socially");
        arraydoyousmoke.add("Regularly");
        arraydoyousmoke.add("N/A");

        LookingSpinnerAdapter adapter17 = new LookingSpinnerAdapter(this, arraydoyousmoke);
        binding.spDoyousmoke.setAdapter(adapter17);

        int index8 = arraydoyousmoke.indexOf(detailModel.getData().getSmoke().trim());
        binding.tvDoyousmoke.setText(arraydoyousmoke.get(index8));

        binding.spDoyousmoke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (bool9 && position != 0) {
                    ssmoke = arraydoyousmoke.get(position);
                    binding.tvDoyousmoke.setText(ssmoke);
                }
                bool9 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ///////////// do you smoke =============================================start
        ArrayList<String> arraydoyoudrink = new ArrayList<>();
        arraydoyoudrink.add("Never");
        arraydoyoudrink.add("Socially");
        arraydoyoudrink.add("Regularly");
        arraydoyoudrink.add("N/A");

        LookingSpinnerAdapter adapter18 = new LookingSpinnerAdapter(this, arraydoyoudrink);
        binding.spDoyoudrink.setAdapter(adapter18);

        int index9 = arraydoyoudrink.indexOf(detailModel.getData().getDrink().trim());
        try {
            binding.tvDoyoudrink.setText(arraydoyoudrink.get(index9));
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.spDoyoudrink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (bool10 && position != 0) {
                    sdrink = arraydoyoudrink.get(position);
                    binding.tvDoyoudrink.setText(sdrink);
                }
                bool10 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        binding.tvAbout.setText(detailModel.getData().getAbout());

//        ====== set profile image ==
        System.out.println("usergallery_imglist size=====" + detailModel.getData().getProfilePic().size());

        userprofileList = detailModel.getData().getProfilePic();

        userID = getPrefHelper().getUserId();
        setClicks(binding.btnUpdate, binding.ivback, binding.imgvOne,binding.imgvOneClose,binding.imgvTwoClose,binding.imgvThreeClose,binding.imgvFourClose,binding.imgvFiveClose,binding.imgvSixClose, binding.imgvTwo, binding.imgvThree, binding.imgvFour,
                binding.imgvFive, binding.imgvSix);
}


public void setUserIamge(ArrayList<DetailModel.ProfilePic> userprofileListnew){

    Log.e(TAG, "setUserIamge: 1111111111  "+userprofileList.size() );

    if (userprofileListnew.size()==0){

        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvOne);

        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvTwo);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvThree);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvFour);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvFive);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvSix);


    }
    if (userprofileListnew.size() == 1) {
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(0).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvOne);

        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvTwo);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvThree);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvFour);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvFive);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvSix);



    }
    if (userprofileListnew.size() == 2) {
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(0).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvOne);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(1).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvTwo);

        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvThree);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvFour);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvFive);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvSix);

    }
    if (userprofileListnew.size() == 3) {
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(0).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvOne);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(1).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvTwo);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(2).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvThree);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvFour);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvFive);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvSix);
    }
    if (userprofileListnew.size() == 4) {
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(0).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvOne);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(1).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvTwo);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(2).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvThree);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(3).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvFour);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvFive);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvSix);
    }
    if (userprofileListnew.size() == 5) {
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(0).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvOne);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(1).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvTwo);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(2).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvThree);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(3).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvFour);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(4).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvFive);
        Glide.with(activity).load("").placeholder(R.drawable.photo_bg).into(binding.imgvSix);
    }
    if (userprofileListnew.size() == 6) {
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(0).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvOne);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(1).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvTwo);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(2).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvThree);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(3).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvFour);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(4).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvFive);
        Glide.with(activity).load(IMAGE_BASE_URL + userprofileListnew.get(5).getImage()).placeholder(R.drawable.photo_bg).into(binding.imgvSix);
    }
}
    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update: {

                System.out.println("profile edit update uid=="+userID);
                new AboutSelfPresenter(this,this).aboutSelfData( userID,binding.tvAbout.getText().toString(),"","","", strinterest, orientation,
                        looking, "", sethenicity, sexercise , "", shoroscope , srelationship, swantchild , ssmoke , sdrink ,
                        "",imglist,"","","","",
                        "","","","","");
            }
            break;

            case R.id.ivback:{
                onBackPressed();
            }
            break;

            case R.id.imgv_one:
                vimage_type = "1";
                askStoragePermission();
                break;
            case R.id.imgv_two:
                vimage_type = "2";
                askStoragePermission();
                break;
            case R.id.imgv_three:
                vimage_type = "3";
                askStoragePermission();
                break;
            case R.id.imgv_four:
                vimage_type = "4";
                askStoragePermission();
                break;
            case R.id.imgv_five:
                vimage_type = "5";
                askStoragePermission();
                break;
            case R.id.imgv_six:
                vimage_type = "6";
                askStoragePermission();
                break;

            case R.id.imgv_one_close:
              deleteUserProfile(0);
                break;
            case R.id.imgv_two_close:
                deleteUserProfile(1);
                break;
            case R.id.imgv_three_close:
                deleteUserProfile(2);
                break;
            case R.id.imgv_four_close:
                deleteUserProfile(3);
                break;
            case R.id.imgv_five_close:
                deleteUserProfile(4);
                break;
            case R.id.imgv_six_close:
                deleteUserProfile(5);
                break;
        }
    }

    private void deleteUserProfile(int i) {


        Log.e(TAG, userprofileList.size()+"  deleteUserProfile: iiiiii "+i );
        try {
            if (userprofileList.get(i).getImage() != null) {
                System.out.println(i+"==image path======" + userprofileList.get(i).getImage());
                deleteUserProfile(userprofileList.get(i).getImage(), i);
            }
        }catch (Exception e){e.printStackTrace();}

    }


    private void deleteUserProfile(String path, int position) {
        JsonObject jsonObject = new JsonObject();

        System.out.println("posiiton======"+position);
        try {
            jsonObject.addProperty("user_id",userID);
            jsonObject.addProperty("user_image",path);

        }catch (Exception e){e.printStackTrace();}

        PDialog.pdialog(activity);

        Log.e("TAG", "delete image request : " + new Gson().toJson(jsonObject));
        Call<DetailModel> call = RetrofitClient.getInstance().getApi().deleteUserProfile(jsonObject);

        call.enqueue(new Callback<DetailModel>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<DetailModel> call, @NonNull retrofit2.Response<DetailModel> response) {
                Log.e(TAG, "delete image response: "+new Gson().toJson(response.body()));

                PDialog.hideDialog();
                try {
                    switch (response.code()) {
                        case 200: {
                           /* String stresult = new Gson().toJson(response.body(), DetailModel.class);
                            Log.e(TAG,"delete image response1111"+ stresult);

                            DetailModel detailModel = new Gson().fromJson(stresult,DetailModel.class);
                            Toast.makeText(EditProfileActivity.this, detailModel.getMessage(), Toast.LENGTH_SHORT).show();


*/
                            DetailModel detailModel = response.body();
                            Log.e(TAG,"delete image response mesage "+ detailModel.getMessage());
                            Log.e(TAG, "delete image response size  "+detailModel.getData().getProfilePic().size() );

                          /*  if(position ==0){
                                Glide.with(activity).load(R.drawable.photo_bg).placeholder(R.drawable.photo_bg).into(binding.imgvOne); }
                            else if(position ==1){
                                Glide.with(activity).load(R.drawable.photo_bg).placeholder(R.drawable.photo_bg).into(binding.imgvTwo); }
                            else if(position ==2){
                                Glide.with(activity).load(R.drawable.photo_bg).placeholder(R.drawable.photo_bg).into(binding.imgvThree);
                            } else if(position ==3){
                                Glide.with(activity).load(R.drawable.photo_bg).placeholder(R.drawable.photo_bg).into(binding.imgvFour);
                            }else if(position ==4){
                                Glide.with(activity).load(R.drawable.photo_bg).placeholder(R.drawable.photo_bg).into(binding.imgvFive);
                            }else if(position ==5){
                                Glide.with(activity).load(R.drawable.photo_bg).placeholder(R.drawable.photo_bg).into(binding.imgvSix);
                            }else{

                            }
//*/

                            setUserIamge(detailModel.getData().getProfilePic());
                            userprofileList = detailModel.getData().getProfilePic();


                            break;
                        }
                        case 400:
                            Log.e(TAG, "onResponse: errr 11111 "+new Gson().toJson(response.errorBody()) );
                        case 401:
                        case 500:
                        case 405:
                        {
                            Log.e(TAG, "onResponse: errr 2222 "+new Gson().toJson(response.errorBody()) );
                            String result = Objects.requireNonNull(response.errorBody()).string();
                            Log.d("response400", result);
                            JSONObject jsonObject = new JSONObject(result);
                            String statusCode = jsonObject.optString("status");
                            String msg = jsonObject.optString("message");
                            break;
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailModel> call, @NonNull Throwable t) {

            }
        });

    }

    //    =================== image upload ======

    private void askStoragePermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_READ_EXTERNAL_STORAGE);
            }
        } else {
            chooseFromGallery();
        }
    }

    private void chooseFromGallery() {
        CropImage.activity().start(EditProfileActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseFromGallery();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.permission_denied), Toast.LENGTH_LONG).show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void resultCrop(Uri resultUri) {
        profilImgPath = getRealPathFromURI(resultUri);
        profilImgPath = compressImage(String.valueOf(resultUri));
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(Uri.parse(imageUri));
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float maxHeight = 900.0f;
        float maxWidth = 670.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;

        //      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[8 * 512];

        try {
            //          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        //      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);
            File file = new File(filename);

            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

           /* try {
                if (vimage_type.equals("1")) {
                    Glide.with(this).load(file.getPath()).placeholder(R.drawable.photo_bg).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(binding.imgvOne);
                    updateUser(file.getPath());
                } else if (vimage_type.equals("2")) {
                    Glide.with(this).load(file.getPath()).placeholder(R.drawable.photo_bg).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(binding.imgvTwo);
                    updateUser(file.getPath());
                } else if (vimage_type.equals("3")) {
                    Glide.with(this).load(file.getPath()).placeholder(R.drawable.photo_bg).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(binding.imgvThree);
                    updateUser(file.getPath());
                } else if (vimage_type.equals("4")) {
                    Glide.with(this).load(file.getPath()).placeholder(R.drawable.photo_bg).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(binding.imgvFour);
                    updateUser(file.getPath());
                } else if (vimage_type.equals("5")) {
                    Glide.with(this).load(file.getPath()).placeholder(R.drawable.photo_bg).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(binding.imgvFive);
                    updateUser(file.getPath());
                } else if (vimage_type.equals("6")) {
                    Glide.with(this).load(file.getPath()).placeholder(R.drawable.photo_bg).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(binding.imgvSix);
                    updateUser(file.getPath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            updateUser(file.getPath());

            // civProfilId.setImageBitmap(decoded);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return filename;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), getString(R.string.app_name) + "/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getApplicationContext().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        System.out.println("Path is file get real path se====" + result);
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                resultCrop(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "Cropping failed: " + error, Toast.LENGTH_LONG).show();
            }
        }
    }

//    =========================

    private void updateUser(String path) {
        MultipartBody.Part imgFileStation = null;
        if (path == null) {
        } else {
            File fileForImage = new File(path);
            Log.e(TAG, "updateUser: prof file "+fileForImage );
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("multipart/form-data"), fileForImage);
            imgFileStation = MultipartBody.Part.createFormData("profile_pic", fileForImage.getName(), requestFileOne);
        }

        MultipartBody.Part user_id = MultipartBody.Part.createFormData("user_id", userID);

        System.out.println("edit profile request===userID===="+userID);
        System.out.println("edit profile request==path===="+path);

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().uploadProfileImage(user_id,imgFileStation);


        PDialog.pdialog(activity);

        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                PDialog.hideDialog();

                Log.e(TAG, "profile upload response: "+new Gson().toJson(response.body()));
                try {
                    switch (response.code()) {
                        case 200: {
                            String stresult = Objects.requireNonNull(response.body()).string();
                            Log.e("profile response", stresult);


                            DetailModel detailModel = new Gson().fromJson(stresult,DetailModel.class);

                            Log.e(TAG, "onResponse: upload "+detailModel.getData().getProfilePic().size() );
                            setUserIamge(detailModel.getData().getProfilePic());
                            userprofileList = detailModel.getData().getProfilePic();

                            break;
                        }
                        case 400:
                            Log.e(TAG, "onResponse: errr 11111 "+new Gson().toJson(response.errorBody()) );
                        case 401:
                        case 500:
                        case 405:
                        {
                            Log.e(TAG, "onResponse: errr 2222 "+new Gson().toJson(response.errorBody()) );
                            String result = Objects.requireNonNull(response.errorBody()).string();
                            Log.d("response400", result);
                            JSONObject jsonObject = new JSONObject(result);
                            String statusCode = jsonObject.optString("status");
                            String msg = jsonObject.optString("message");
                            break;
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                PDialog.hideDialog();
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });


    }

    @Override
    public void onCategoryUpdateResponse(String s) {

    }

    @Override
    public void showLoaderProcess() {
    }

    @Override
    public void hideLoaderProcess() {
    }

    @Override
    public void onApiErrorResponse(String Errorresponse, String error_type) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserIamge(userprofileList);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onAboutInfoResponse(String s) {

        Log.e(TAG, "onAboutInfoResponse: ssssssss "+s );
        Toast.makeText(this, "Success update", Toast.LENGTH_SHORT).show();
        finish();
    }
}