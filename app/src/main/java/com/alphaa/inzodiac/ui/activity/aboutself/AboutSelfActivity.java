package com.alphaa.inzodiac.ui.activity.aboutself;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.base.ImagePickerDialog;
import com.alphaa.inzodiac.databinding.ActivityAboutSelfBinding;
import com.alphaa.inzodiac.gallery.helpers.Constants;
import com.alphaa.inzodiac.gallery.models.Image;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.staticdata.BirthdayData;
import com.alphaa.inzodiac.staticdata.SetChineseWesternZodaic;
import com.alphaa.inzodiac.staticdata.ZodaicActivity;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.LookingSpinnerAdapter;
import com.alphaa.inzodiac.ui.activity.detailaboyou.DetailAboYouActivity;
import com.alphaa.inzodiac.utility.AppConstants;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Validation;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import id.zelory.compressor.Compressor;

import static com.alphaa.inzodiac.utility.Utility.getAge;

public class AboutSelfActivity extends BaseActivity implements View.OnClickListener, ApiCallBackInterFace.CategoryUpdateCallback {
    String TAG = getClass().getSimpleName();

    ActivityAboutSelfBinding binding;

    AppCompatSpinner sp_orientation, sp_lookingfor, sp_gender_nonbinary;
    private Uri temPhoto;
    private File userImageFile;
    private boolean bool1 = false, bool2 = false, bool3 = false, bool4 = false, boolcategory = false;
    private String strgender = "Female", strinterest = "Female", orientation = "", looking = "", date = "", category = "";
    private ArrayList<Image> imglist;
    private ImageAdapter adapter;
    private String Age = "";


    String userid;

    private static final int PICK_FROM_GALLERY = 109;
    Uri returnUri;
    List<String> imagelistpath;
    ImageAdapterString imageAdapter;

    //get chinese and weastern
    public Date strDobDate, strStartYear, strendYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_self);
        imglist = new ArrayList<>();
        inItView();
        //setAdapter();
        userid = AppSession.getStringPreferences(getApplicationContext(), com.alphaa.inzodiac.utility.Constants.USEREIDSIGNUP);

        Log.e(TAG, "onCreate: " + userid);


        imagelistpath = new ArrayList<>();


        setAdapterStrinpath();
        checkPermission();


        ArrayList<String> arrayList = new ArrayList<>();
       /* arrayList.add("Orientation");
        arrayList.add("Portrait");
        arrayList.add("Landscap");
*/
        arrayList.add("Orientation");
        arrayList.add("Straight");
        arrayList.add("Gay");

        arrayList.add("Lesbian");
        arrayList.add("Bisexual");
        arrayList.add("Asexual");
        arrayList.add("Demisexual");
        arrayList.add("Pansexual");
        arrayList.add("Queer");
        arrayList.add("Questioning");
        sp_orientation = findViewById(R.id.sp_orientation);
        LookingSpinnerAdapter adapter = new LookingSpinnerAdapter(this, arrayList);
        sp_orientation.setAdapter(adapter);


        binding.spOrientation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (bool1 && position != 0) {
                    orientation = arrayList.get(position);
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


        //--------------------------------------------------starts
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("Looking For");
        arrayList1.add("Don't know");
        arrayList1.add("Chat");
        arrayList1.add("Business relationship");
        arrayList1.add("Friends");
        arrayList1.add("Casual");
        arrayList1.add("Serious relationship");
        //arrayList.add("Both");
       /* arrayList1.add("Looking For");
        arrayList1.add("More Understanding");
        arrayList1.add("Passion Potential");
        arrayList1.add("Friends");
        arrayList1.add("Business");
        arrayList1.add("Wild Card");
        arrayList1.add("Our Pick");
        arrayList1.add("Opposites Attract");
        arrayList1.add("Classic Compatible");*/


        sp_lookingfor = findViewById(R.id.sp_lookingfor);
        LookingSpinnerAdapter adapter1 = new LookingSpinnerAdapter(this, arrayList1);
        sp_lookingfor.setAdapter(adapter1);

        binding.spLookingfor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e(TAG, "onItemSelected: spLookingfor pos " + position);
                if (bool2 && position != 0) {
                    looking = arrayList1.get(position);
                    binding.tvlook.setText(looking);
                }
                bool2 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }

                if (position != 0) {
                    //new AboutSelfCategoryPresenter(getActivity(), AboutSelfActivity.this).setCategory(userid,"3");
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //===========================================ends


        //------------------------gender non binary--------------------------starts
        ArrayList<String> arrayList3 = new ArrayList<>();
        arrayList3.add("Non Binary");
        arrayList3.add("Agender");
        arrayList3.add("Androgyne");
        arrayList3.add("Androgynous");


        arrayList3.add("Bigender");
        arrayList3.add("Cis");
        arrayList3.add("Cisgender");

        arrayList3.add("Cis Female");
        arrayList3.add("Cis Male");
        arrayList3.add("Cis Man");
        arrayList3.add("Cis Woman");
        arrayList3.add("Cisgender Female");
        arrayList3.add("Cisgender Male");
        arrayList3.add("Cisgender Man");
        arrayList3.add("Cisgender Woman");
        arrayList3.add("Female to Male");
        arrayList3.add("FTM");
        arrayList3.add("Gender Fluid");
        arrayList3.add("Gender Nonconforming");
        arrayList3.add("Gender Questioning");
        arrayList3.add("Gender Variant");
        arrayList3.add("Genderqueer");


        arrayList3.add("Intersex");
        arrayList3.add("MTF");
        arrayList3.add("Neither");
        arrayList3.add("Neutrois");
        arrayList3.add("Non-binary");
        arrayList3.add("Other");


        arrayList3.add("Pangender");
        arrayList3.add("Trans");
        arrayList3.add("Trans Female");
        arrayList3.add(" Trans*");
        arrayList3.add("Trans* Female");
        arrayList3.add("Trans Male");

        arrayList3.add("Trans* Male");
        arrayList3.add("Trans Man");
        arrayList3.add("Trans* Man");
        arrayList3.add("Trans Person");
        arrayList3.add("Trans* Person");
        arrayList3.add("Trans Woman");


        arrayList3.add("Trans* Woman");
        arrayList3.add("Transfeminine");
        arrayList3.add("Transgender");
        arrayList3.add("Transgender Female");
        arrayList3.add("Transgender Male");
        arrayList3.add("Transgender Man");

        arrayList3.add("Transgender Person");
        arrayList3.add("Transgender Woman");
        arrayList3.add("Transmasculine");
        arrayList3.add("Transsexual");
        arrayList3.add("Transsexual Female");
        arrayList3.add("Transsexual Male");

        arrayList3.add("Transsexual Man");
        arrayList3.add("Transsexual Person");
        arrayList3.add("Transsexual Woman");
        arrayList3.add("Two-Spirit");


//        sp_gender_nonbinary = findViewById(R.id.sp_gender_nonbinary);
        LookingSpinnerAdapter adapter3 = new LookingSpinnerAdapter(this, arrayList3);
        binding.spGenderNonbinary.setAdapter(adapter3);

        binding.spGenderNonbinary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e(TAG, bool3 + "  onItemSelected: spGenderNonbinary pos " + position);
                if (bool3) {
                    strgender = arrayList3.get(position);
                    binding.tvGenderNonbinary.setText(strgender);


                    binding.male.setTextColor(activity.getResources().getColor(R.color.black));
                    binding.rlmale.setBackground(null);
                    binding.female.setTextColor(getResources().getColor(R.color.black));
                    binding.rlfemale.setBackground(null);


                }
                bool3 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //======================gender non binary=====================ends


        //------------------------interest non binary--------------------------starts
        ArrayList<String> arrayList4 = new ArrayList<>();
        arrayList4.add("Non Binary");
        arrayList4.add("Agender");
        arrayList4.add("Androgyne");
        arrayList4.add("Androgynous");


        arrayList4.add("Bigender");
        arrayList4.add("Cis");
        arrayList4.add("Cisgender");

        arrayList4.add("Cis Female");
        arrayList4.add("Cis Male");
        arrayList4.add("Cis Man");
        arrayList4.add("Cis Woman");
        arrayList4.add("Cisgender Female");
        arrayList4.add("Cisgender Male");
        arrayList4.add("Cisgender Man");
        arrayList4.add("Cisgender Woman");
        arrayList4.add("Female to Male");
        arrayList4.add("FTM");
        arrayList4.add("Gender Fluid");
        arrayList4.add("Gender Nonconforming");
        arrayList4.add("Gender Questioning");
        arrayList4.add("Gender Variant");
        arrayList4.add("Genderqueer");


        arrayList4.add("Intersex");
        arrayList4.add("MTF");
        arrayList4.add("Neither");
        arrayList4.add("Neutrois");
        arrayList4.add("Non-binary");
        arrayList4.add("Other");


        arrayList4.add("Pangender");
        arrayList4.add("Trans");
        arrayList4.add("Trans Female");
        arrayList4.add(" Trans*");
        arrayList4.add("Trans* Female");
        arrayList4.add("Trans Male");

        arrayList4.add("Trans* Male");
        arrayList4.add("Trans Man");
        arrayList4.add("Trans* Man");
        arrayList4.add("Trans Person");
        arrayList4.add("Trans* Person");
        arrayList4.add("Trans Woman");


        arrayList4.add("Trans* Woman");
        arrayList4.add("Transfeminine");
        arrayList4.add("Transgender");
        arrayList4.add("Transgender Female");
        arrayList4.add("Transgender Male");
        arrayList4.add("Transgender Man");

        arrayList4.add("Transgender Person");
        arrayList4.add("Transgender Woman");
        arrayList4.add("Transmasculine");
        arrayList4.add("Transsexual");
        arrayList4.add("Transsexual Female");
        arrayList4.add("Transsexual Male");

        arrayList4.add("Transsexual Man");
        arrayList4.add("Transsexual Person");
        arrayList4.add("Transsexual Woman");
        arrayList4.add("Two-Spirit");


//        sp_gender_nonbinary = findViewById(R.id.sp_gender_nonbinary);
        LookingSpinnerAdapter adapter4 = new LookingSpinnerAdapter(this, arrayList4);
        binding.spInterestedinNonbinary.setAdapter(adapter4);

        binding.spInterestedinNonbinary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e(TAG, bool4 + "  onItemSelected: spGenderNonbinary pos " + position);
                if (bool4) {
                    strinterest = arrayList4.get(position);
                    binding.tvInterestedinNonbinary.setText(strinterest);


                    //binding.nonbinary1.setTextColor(getResources().getColor(R.color.white));
                    //binding.rlnonbinary1.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));
                    binding.male1.setTextColor(activity.getResources().getColor(R.color.black));
                    binding.rlmale1.setBackground(null);
                    binding.female1.setTextColor(getResources().getColor(R.color.black));
                    binding.rlfemale1.setBackground(null);


                }
                bool4 = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //======================gender non binary=====================ends


        //////////////////==============category=======================
        //--------------------------------------------------starts
        ArrayList<String> arrayList7 = new ArrayList<>();

      /*  arrayList7.add("Category");
        arrayList7.add("More Understanding");
        arrayList7.add("Passion Potential");
        arrayList7.add("Friends");
        arrayList7.add("Business");
        arrayList7.add("Wild Card");
        arrayList7.add("Our Pick");
        arrayList7.add("Opposites Attract");
        arrayList7.add("Classic Compatible");*/

        arrayList7.add("Category");
        arrayList7.add("Dating");//1,2,5,6,7,8
        arrayList7.add("Casual");//2
        arrayList7.add("Friends");//3
        arrayList7.add("Business");//4


        LookingSpinnerAdapter adapter7 = new LookingSpinnerAdapter(this, arrayList7);
        binding.spCategory.setAdapter(adapter7);

        binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String catid = "";
                Log.e(TAG, catid + "  onItemSelected: spCategory pos " + position);
                if (boolcategory && position != 0) {
                    category = arrayList7.get(position);
                    binding.tvcategory.setText(category);


                    /*if (position==1){
                        catid = "1,2,5,6,7,8";
                    }
                    if (position==2){
                        catid = "2";
                    }

                    if (position==3){
                        catid = "3";
                    }

                    if (position==4){
                        catid = "4";
                    }*/


                    if (position == 1) {
                        catid = "1,2,3,4,5,6,7,8";
                    }
                    if (position == 2) {
                        catid = "1,2,3,4,5,6,7,8";
                    }

                    if (position == 3) {
                        catid = "1,2,3,4,5,6,7,8";
                    }

                    if (position == 4) {
                        catid = "1,2,3,4,5,6,7,8";
                    }
                }
                boolcategory = true;
                if (view != null) {
                    view.setVisibility(View.GONE);
                }

                if (position != 0) {

                    Log.e(TAG, "onItemSelected: catidddddd " + catid);
                    new AboutSelfCategoryPresenter(getActivity(), AboutSelfActivity.this).setCategory(userid, catid);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //===========================================category


    }

    private void setAdapterStrinpath() {
        imageAdapter = new ImageAdapterString(getApplicationContext(), imagelistpath);
        LinearLayoutManager manager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(imageAdapter);
        if (imagelistpath.size() == 0) {
            binding.ivimage.setVisibility(View.VISIBLE);
        } else {
            binding.ivimage.setVisibility(View.GONE);

        }
    }

    private void getDataIntent() {
        if (getIntent().getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES) != null && getIntent().getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES).size() != 0) {
            ArrayList<Image> images = getIntent().getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0, l = images.size(); i < l; i++) {
                stringBuffer.append(images.get(i).path + "\n");
                if (imglist.size() < 5) {
                    imglist.add(images.get(i));
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(activity, "You can choos only five image", Toast.LENGTH_SHORT).show();
                    break;
                }
            }

        }
        if (imglist.size() == 0) {
            binding.ivimage.setVisibility(View.VISIBLE);
        } else {
            binding.ivimage.setVisibility(View.GONE);

        }
    }


    private void inItView() {
        setClicks(binding.etDate, binding.ivSelect, binding.llImg, binding.rlfemale, binding.rlmale, binding.next, binding.skip, binding.ivback,
                binding.nonbinary, binding.nonbinary1, binding.rlmale1, binding.rlfemale1, binding.ivSelect);
    }

    private void setAdapter() {
        adapter = new ImageAdapter(activity, imglist);
        LinearLayoutManager manager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
        getDataIntent();

    }

    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rlmale: {
                binding.male.setTextColor(activity.getResources().getColor(R.color.white));
                binding.rlmale.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));
                binding.female.setTextColor(getResources().getColor(R.color.black));
                binding.rlfemale.setBackground(null);
                binding.nonbinary.setTextColor(getResources().getColor(R.color.black));
                binding.rlnonbinary.setBackground(null);
                strgender = "male";


                binding.tvGenderNonbinary.setText("");
            }
            break;
            case R.id.rlfemale: {
                binding.male.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlmale.setBackground(null);
                binding.female.setTextColor(getResources().getColor(R.color.white));
                binding.rlfemale.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));
                binding.nonbinary.setTextColor(getResources().getColor(R.color.black));
                binding.rlnonbinary.setBackground(null);
                strgender = "female";

                binding.tvGenderNonbinary.setText("");
            }
            break;
            case R.id.nonbinary: {
                binding.nonbinary.setTextColor(getResources().getColor(R.color.white));
                binding.rlnonbinary.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));
                binding.male.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlmale.setBackground(null);
                binding.female.setTextColor(getResources().getColor(R.color.black));
                binding.rlfemale.setBackground(null);
                strgender = "nonbinary";
            }

            break;

            case R.id.ll_img: {
                if (imglist.size() < 5) {
                    ImagePickerDialog imagePickerDialog = ImagePickerDialog.getInstance(activity);
                    imagePickerDialog.show(getSupportFragmentManager());
                    imagePickerDialog.intializeListener(this);
                } else {
                    Toast.makeText(activity, "You can choose only five image", Toast.LENGTH_SHORT).show();

                }
            }
            break;
            case R.id.iv_select: {

                if (ContextCompat.checkSelfPermission(AboutSelfActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Please turn on storage permission from settings", Toast.LENGTH_SHORT).show();
                } else {

                    ImagePickerDialog imagePickerDialog = ImagePickerDialog.getInstance(activity);
                    imagePickerDialog.show(getSupportFragmentManager());
                    imagePickerDialog.intializeListener(this);

                }

//                openFolder();
            }

            break;
            case R.id.et_date: {
                openDatePicker();
            }

            break;


            case R.id.rlmale1: {

                Log.e("TAG", "onClick: rl ll");
                binding.male1.setTextColor(activity.getResources().getColor(R.color.white));
                binding.rlmale1.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));
                binding.female1.setTextColor(getResources().getColor(R.color.black));
                binding.rlfemale1.setBackground(null);
                binding.nonbinary1.setTextColor(getResources().getColor(R.color.black));
                binding.rlnonbinary1.setBackground(null);
                strinterest = "Male";

                binding.tvInterestedinNonbinary.setText("");

            }
            break;
            case R.id.rlfemale1: {
                binding.male1.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlmale1.setBackground(null);
                binding.female1.setTextColor(getResources().getColor(R.color.white));
                binding.rlfemale1.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));
                binding.nonbinary1.setTextColor(getResources().getColor(R.color.black));
                binding.rlnonbinary1.setBackground(null);
                strinterest = "Female";

                binding.tvInterestedinNonbinary.setText("");
            }
            break;
            case R.id.nonbinary1: {
                binding.nonbinary1.setTextColor(getResources().getColor(R.color.white));
                binding.rlnonbinary1.setBackground(getResources().getDrawable(R.drawable.purple_btn_bg));
                binding.male1.setTextColor(activity.getResources().getColor(R.color.black));
                binding.rlmale1.setBackground(null);
                binding.female1.setTextColor(getResources().getColor(R.color.black));
                binding.rlfemale1.setBackground(null);
                strinterest = "nonbinary";
            }
            break;

            case R.id.next: {
                Validation validation = new Validation(this);
                //if (validation.isoriValid(orientation) && validation.islookValid(looking) && validation.isdateValid(binding.etDate)  ) {

                Log.e(TAG, imglist.size() + "   onClick: dateee  " + strinterest);
                Log.e(TAG, strgender + "   onClick: orientation  " + orientation);
                if (imglist.size() != 0) {

                    if (orientation.isEmpty()) {
                        Toast.makeText(this, "Please select Orientation", Toast.LENGTH_SHORT).show();

                    } else if (looking.isEmpty()) {
                        Toast.makeText(this, "Please select Looking For", Toast.LENGTH_SHORT).show();

                    } else if (category.isEmpty()) {
                        Toast.makeText(this, "Please select Category", Toast.LENGTH_SHORT).show();

                    } else if (date.isEmpty()) {
                        Toast.makeText(this, "Please select Date", Toast.LENGTH_SHORT).show();

                    } else {


                        Intent intent = new Intent(AboutSelfActivity.this, DetailAboYouActivity.class);
                        intent.putExtra("img", "");
                        intent.putExtra("strgender", strgender);
                        intent.putExtra("strinterest", strinterest);
                        intent.putExtra("date", date);
                        intent.putExtra("orientation", orientation);
                        intent.putExtra("looking", looking);
                        intent.putParcelableArrayListExtra("imglist", imglist);
                        intent.putExtra("skip", "skip1");
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please select profile picture", Toast.LENGTH_SHORT).show();
                }
                //}
            }
            break;

            case R.id.skip: {
                Intent intent1 = new Intent(AboutSelfActivity.this, DetailAboYouActivity.class);
                intent1.putExtra("skip", "skip");
                startActivity(intent1);
            }
            break;
            case R.id.ivback: {
                onBackPressed();
            }
            break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.e(TAG, requestCode + " onActivityResult: " + resultCode);
        if (resultCode != 0) {
            switch (requestCode) {
              /*  case Constants.REQUEST_CODE: {

               if (imglist.size() < 5) {
                        List<Uri> mSelected = Matisse.obtainResult(data);
                        for (int i = 0; i < mSelected.size(); i++) {
                            imglist.add(mSelected.get(i));
                        }
                        adapter.notifyDataSetChanged();
                        Log.e("ff", mSelected.toString());
                    } else {
                        Toast.makeText(activity, "You can chooes only five image", Toast.LENGTH_SHORT).show();
 break;
                    }


            }*/


                /*case AppConstants.RESULT_LOAD: {

                    temPhoto = data.getData();
                    if (temPhoto != null) {
                        try {
                            long time = System.currentTimeMillis();
                            String str = "" + time;
                            String destinatiomPath = str + "outloud.jpg";
                            UCrop.Options options = new UCrop.Options();
                            options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
                            options.setToolbarWidgetColor(getResources().getColor(R.color.colorWhite));
                            options.setToolbarColor(getResources().getColor(R.color.colorPurple));
                            options.setStatusBarColor(getResources().getColor(R.color.colorPurple));
                            UCrop.of(temPhoto, Uri.fromFile(new File(getCacheDir(), destinatiomPath)))
                                    .withAspectRatio(1f, 1f)
                                    .withOptions(options)
                                    .start(this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                break;*/

                case AppConstants.REQUEST_CAMERA: {
// New Code
                    temPhoto = Uri.fromFile(new File(BaseActivity.mCurrentPhotoPath));

                    if (temPhoto != null) {

                        try {
                            long time = System.currentTimeMillis();
                            String str = "" + time;
                            String destinatiomPath = str + "outloud.jpg";
                            UCrop.Options options1 = new UCrop.Options();
                            options1.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
                            options1.setToolbarWidgetColor(getResources().getColor(R.color.colorWhite));
                            options1.setToolbarColor(getResources().getColor(R.color.colorPurple));
                            options1.setStatusBarColor(getResources().getColor(R.color.colorPurple));
                            UCrop.of(temPhoto, Uri.fromFile(new File(activity.getCacheDir(), destinatiomPath)))
                                    .withAspectRatio(1f, 1f)
                                    .withOptions(options1)
                                    .start(this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

                case UCrop.REQUEST_CROP:

                    if (data != null) {
                        handleCropResult(data);
                    }

                    break;

                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }

            if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                StringBuffer stringBuffer = new StringBuffer();
                Log.e(TAG, "onActivityResult: images  " + images);
                if (imglist.size() < 5) {
                    for (int i = 0, l = images.size(); i < l; i++) {
                        stringBuffer.append(images.get(i).path + "\n");
                    }
                    imglist.addAll(images);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(activity, "You can chooes only five image", Toast.LENGTH_SHORT).show();

                }
            }


            //////////choose from gallary

            Log.e(TAG, requestCode + "  onActivityResult: pickekkkkkkkkk " + PICK_FROM_GALLERY);
            if (requestCode == 65645 || requestCode == 131181 || requestCode == 196717 || requestCode == 262253 || requestCode == 327789) {
                if (resultCode == Activity.RESULT_OK) {

                    Log.e(TAG, data.getClipData() + "  onActivityResult: pickekkkkkkkkk 11 " + PICK_FROM_GALLERY);
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                        Log.e(TAG, requestCode + "  onActivityResult: pickekkkkkkkkk 22 " + PICK_FROM_GALLERY);


                        for (int i = 0; i < count; i++) {
                            returnUri = data.getClipData().getItemAt(i).getUri();


                            FileUtilsPath fileUtils = new FileUtilsPath(getApplicationContext());


                            //compress lib
                            File compressedImage = null;
                            try {
                                compressedImage = new Compressor(this)
                                        .setMaxWidth(640)
                                        .setMaxHeight(480)
                                        .setQuality(75)
                                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                        .compressToFile(new File(fileUtils.getPath(returnUri)));
                            } catch (IOException e) {
                                e.printStackTrace();


                                Log.e(TAG, "onActivityResult: exce " + e.getMessage());
                            }

                            Log.e(TAG, "onActivityResult: compress " + compressedImage);
                            imagelistpath.add(fileUtils.getPath(returnUri));
//                        imagelistpath.add(String.valueOf(compressedImage));

                       /* Image image = new Image();
                        image.setPath(fileUtils.getPath(returnUri));
//                        image.setPath(fileUtils.getPath(returnUri));
//                        image.setPath(String.valueOf(compressedImage));
                        imglist.add(image);*/


                            String newpath = compressImage(String.valueOf(returnUri));

                            Image image = new Image();
                            image.setPath(newpath);
                            imglist.add(image);


                            Log.e(TAG, imagelistpath + "  onActivityResult: uri " + fileUtils.getPath(returnUri));


                            Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
                            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                            returnCursor.moveToFirst();
                            System.out.println("PIYUSH NAME IS" + returnCursor.getString(nameIndex));
                            System.out.println("PIYUSH SIZE IS" + returnCursor.getLong(sizeIndex));

                            Log.e("rahul ", "iiiii " + returnCursor.getString(nameIndex));

                            Log.e("rahul ", "SSSS " + returnCursor.getLong(sizeIndex));

                        /*AttachmentListData attachmentListData = new AttachmentListData();
                        attachmentListData.setImageName(returnCursor.getString(nameIndex));
                        attachmentListData.setImageID(returnUri.toString());
                        newAttachmentList.add(attachmentListData);*/
                        }

                        Log.e(TAG, "onActivityResult: imgelisting  " + imagelistpath.size());

                        setAdapterStrinpath();
                    } else if (data.getData() != null) {

                        Log.e(TAG, "onActivityResult: data get data");
                        returnUri = data.getData();


                        /////set arraylist
                        FileUtilsPath fileUtils = new FileUtilsPath(getApplicationContext());


                        imagelistpath.add(fileUtils.getPath(returnUri));
                    /*Image image = new Image();
                    image.setPath(fileUtils.getPath(returnUri));
                    imglist.add(image);*/


                        String newpath = compressImage(String.valueOf(returnUri));

                        Image image = new Image();
                        image.setPath(newpath);
                        imglist.add(image);


                        setAdapterStrinpath();

                        Cursor returnCursor =
                                getContentResolver().query(returnUri, null, null, null, null);
                        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                        returnCursor.moveToFirst();
                        System.out.println("PIYUSH NAME IS" + returnCursor.getString(nameIndex));
                        System.out.println("PIYUSH SIZE IS" + returnCursor.getLong(sizeIndex));

                        Log.e("rahul ", "nnn " + returnCursor.getString(nameIndex));

                        Log.e("rahul ", "Size " + returnCursor.getLong(sizeIndex));

                   /* AttachmentListData attachmentListData = new AttachmentListData();
                    attachmentListData.setImageName(returnCursor.getString(nameIndex));
                    attachmentListData.setImageID(returnUri.toString());
                    newAttachmentList.add(attachmentListData);*/
                    }
                    //generateNewAttachmentList(newAttachmentList);


                    //imageAdapter.notifyDataSetChanged();
                }
            }

        }

    }

    private void handleCropResult(Intent result) {
        Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
//            Glide.with(this).load(resultUri).into(binding.ivimage);
            if (imglist.size() < 5) {
                //  imglist.add(new Image(0, "cameraimage" , resultUri.getPath(), true));
                //adapter.notifyDataSetChanged();


                imagelistpath.add(resultUri.getPath());
                Image image = new Image();
                image.setPath(resultUri.getPath());
                imglist.add(image);
                setAdapterStrinpath();

                Log.e(TAG, imglist.size() + "   handleCropResult:  hffffff " + imagelistpath.size());


                userImageFile = new File(Objects.requireNonNull(resultUri.getPath()));

            } else {
                Toast.makeText(activity, "You can chooes only five image", Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(activity, "cannot_retrieve_cropped_image", Toast.LENGTH_SHORT).show();
        }
        if (imglist.size() == 0) {
            binding.ivimage.setVisibility(View.VISIBLE);
        } else {
            binding.ivimage.setVisibility(View.GONE);

        }
    }

    public void openDatePicker() {
        DatePickerDialog picker;
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
// date picker dialog
        picker = new DatePickerDialog(AboutSelfActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String month;
                        String day;
                        if (monthOfYear + 1 < 10) {
                            month = "0" + String.valueOf(monthOfYear + 1);
                        } else {
                            month = String.valueOf(monthOfYear + 1);
                        }
                        if (dayOfMonth < 10) {
                            day = "0" + String.valueOf(dayOfMonth);
                        } else {
                            day = String.valueOf(dayOfMonth);
                        }
                        date = (year + "/" + month + "/" + day);
                        Age = getAge(year, monthOfYear, dayOfMonth);
                        Date cur_date = Calendar.getInstance().getTime();


                        String getage = getAge(year, monthOfYear, dayOfMonth);
                        Log.e(TAG, "onDateSet: getdate " + getage);


                        if (Integer.parseInt(getage) >= 18) {
                            binding.etDate.setText(date);

                            Log.e(TAG, "onDateSet: " + date);
//                            2001/04/29

                            /*String inputPattern = "MM-dd-yyyy";
                            String outputPattern = "yyyy/MM/dd";
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                            Date date1 = null;
                            String newdate = null;

                            try {
                                date1 = inputFormat.parse(date);
                                newdate = outputFormat.format(date1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
*/


                            String strwesternzodaic = SetChineseWesternZodaic.birthDateMatchWithWestern(date);
                            String strchinesezodaic = chineseZodaicSign(date);

                            if (strwesternzodaic.equalsIgnoreCase("no")) {
                                strwesternzodaic = "CAPRICORN";
                            }
                            Log.e(TAG, strwesternzodaic + " onDateSet: weaster and chinse " + strchinesezodaic);


                            AppSession.setStringPreferences(getApplicationContext(), com.alphaa.inzodiac.utility.Constants.WESTERNZODAICSIGN, strwesternzodaic);
                            AppSession.setStringPreferences(getApplicationContext(), com.alphaa.inzodiac.utility.Constants.CHINESEZODAICSIGN, strchinesezodaic);


                        } else {
                            Toast.makeText(AboutSelfActivity.this, "Age should be greater than 18", Toast.LENGTH_SHORT).show();
                        }


                        //Log.e(TAG, "onDateSet: date "+date );


                       /* SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String formattedDate = df.format(cur_date);
                        String[] separated = formattedDate.split("-");
                        int d = Integer.parseInt(separated[0]);
                        int m = Integer.parseInt(separated[1]);
                        int y = Integer.parseInt(separated[2]);
                        String Age1 = getAge(y,m,d);

                        if (Integer.parseInt(Age1) >= 0){
                        }else {
                            Toast.makeText(AboutSelfActivity.this, "Don't Select future date", Toast.LENGTH_SHORT).show();
                        }*/

                    }
                }, year, month, day);
        picker.show();
        picker.getDatePicker().setMaxDate(System.currentTimeMillis());
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

        Log.e(TAG, "onResume: 1111 " + binding.tvori.getText().toString());
        super.onResume();
    }

    @Override
    protected void onPause() {

        Log.e(TAG, "onPause: 1111 " + binding.tvori.getText().toString());
        super.onPause();
    }


    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


    public void openFolder() {
        Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_GALLERY);
    }


    ///////////////////////compress image
    public String compressImage(String imageUri) {

        //String filePath = getRealPathFromURI(Uri.parse(imageUri));

        FileUtilsPath fileUtilsPath = new FileUtilsPath(getApplicationContext());
        String filePath = fileUtilsPath.getPath(Uri.parse(imageUri));

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


    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //if (!Settings.System.canWrite(this)) {


            if (ContextCompat.checkSelfPermission(AboutSelfActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 2909);
            }
            // } else {
            // continue with your code
            //}
        } else {
            // continue with your code
        }
    }


    ///////sssseeeeeeeeettttttttt weastern and chinese zodaic

    public String chineseZodaicSign(String dateofbirth) {

        String asociateAnimal = "no";

        ArrayList<BirthdayData> birthdayDataArrayList = ZodaicActivity.birthDayData();

        for (int i = 0; i < birthdayDataArrayList.size(); i++) {

            String birthdayData = birthdayDataArrayList.get(i).getYearEnd();


            String date_1984_2043 = birthdayData;


            //split date
            String[] datearray = date_1984_2043.split("-");

            String startdate = datearray[0];
            String enddate = datearray[1];

            //convert dob to date object
            //birth date match  ///2021/02/10


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            try {
                strDobDate = sdf.parse(dateofbirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            //convert startdate to date object
            SimpleDateFormat sdfstart1 = new SimpleDateFormat("MMM dd yyyy");
            try {

                strStartYear = sdfstart1.parse(startdate);

            } catch (ParseException e) {

                e.printStackTrace();
            }

            //convert end date to dateobject
            SimpleDateFormat sdfend = new SimpleDateFormat("MMM dd yyyy");
            try {
                strendYear = sdfend.parse(enddate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                if (
                        (strDobDate.compareTo(strStartYear) == 0 && strDobDate.compareTo(strendYear) < 0) ||
                                (strDobDate.compareTo(strStartYear) > 0 && strDobDate.compareTo(strendYear) == 0) ||
                                (strDobDate.compareTo(strStartYear) > 0 && strDobDate.compareTo(strendYear) < 0)
                ) {
                    asociateAnimal = birthdayDataArrayList.get(i).getAnimal();
                    break;
                } else {
                }

            } catch (Exception e) {

            }
        }

        return asociateAnimal;

    }


}