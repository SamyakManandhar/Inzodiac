package com.alphaa.inzodiac.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alphaa.inzodiac.app.prefs.Constants;
import com.alphaa.inzodiac.app.prefs.PrefHelper;
import com.alphaa.inzodiac.app.utils.KeyboardUtil;
import com.alphaa.inzodiac.app.utils.StackSet;
import com.alphaa.inzodiac.utility.AppConstants;
import com.alphaa.inzodiac.utility.PDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.alphaa.inzodiac.utility.AppConstants.MY_PERMISSIONS_REQUEST_CAMERA;


public class BaseActivity extends AppCompatActivity implements ImagePickerDialog.ImagePickerCallBack {

    String TAG = getClass().getSimpleName();

    public static String mCurrentPhotoPath = "";
    public Activity activity = this;
    protected PrefHelper prefHelper;
    private PDialog pDialog1;
    private StackSet<Fragment> stackSet = new StackSet<>();

    protected Activity getActivity() {
        return this;
    }

    protected void hideKeyboard() {
        if (getCurrentFocus() != null)
            KeyboardUtil.hideKeyboard(this, getCurrentFocus());

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pDialog1 = new PDialog();
        prefHelper = new PrefHelper(this);

    }


    public  PrefHelper getPrefHelper() {
        return prefHelper;
    }



    protected final StackSet<Fragment> getBackStack() {
        return stackSet;
    }

    protected final Fragment getCurrentFragment() {
        return stackSet.peek();
    }

    protected final void replaceFragment(@NonNull Fragment fragment, @IdRes int containerId, boolean addToBackStack) {
        try {
            FragmentManager fm = getSupportFragmentManager();
            String fragmentName = fragment.getClass().getName();
            fm.beginTransaction().replace(containerId, fragment, fragmentName).addToBackStack("abc").commit();
            //if (addToBackStack) stackSet.push(fragment);
            hideKeyboard();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    protected final void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }


    protected final <T> void navigateTo(Class<T> destination, Bundle bundle) {
        Intent intent = new Intent(this, destination);
        if (bundle != null) {
            intent.putExtra(Constants.KEY_BUNDLE_PARAM, bundle);
        }
        startActivity(intent);
    }

    protected final <T> void navigateTo(Class<T> destination, Bundle bundle, boolean isFinishing) {
        Intent intent = new Intent(this, destination);
        if (bundle != null) {
            intent.putExtra(Constants.KEY_BUNDLE_PARAM, bundle);
        }
        startActivity(intent);
        if (isFinishing) finish();
    }


    protected final void navigateTo(Intent intent, boolean isFinishing) {
        startActivity(intent);
        if (isFinishing) finish();
    }

    public void onStartActivityWithClearStack(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void alertDialog(String msg, Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Alert")
                .setMessage(msg)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .show();

    }


    @Override
    public void textOnClick(String type) {
        Log.e(TAG, "textOnClick: 11111111" );
        if (type.equals("Camera")) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    callIntent(MY_PERMISSIONS_REQUEST_CAMERA);
                } else {
                    dispatchTakePictureIntent();
                }
            } else {
                dispatchTakePictureIntent();
            }

        } else if (type.equals("Gallery")) {

            if (Build.VERSION.SDK_INT >= 23) {
                if (activity.checkSelfPermission(READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    callIntent(AppConstants.MY_PERMISSIONS_REQUEST_EXTERNAL);
                } else {
                    callIntent(AppConstants.RESULT_LOAD);
                }
            } else {
                callIntent(AppConstants.RESULT_LOAD);
            }
        }
    }

    public void callIntent(int caseId) {

        switch (caseId) {
            case AppConstants.INTENT_CAMERA:
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, AppConstants.REQUEST_CAMERA);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;



            case AppConstants.RESULT_LOAD:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoPickerIntent, AppConstants.RESULT_LOAD);
                break;

            case AppConstants.REQUEST_CAMERA:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
                break;

            case AppConstants.MY_PERMISSIONS_REQUEST_EXTERNAL:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            AppConstants.MY_PERMISSIONS_REQUEST_EXTERNAL);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
                break;
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.alphaa.inzodiac.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, AppConstants.REQUEST_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File storageDir = getCacheDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                dispatchTakePictureIntent();
                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


}
