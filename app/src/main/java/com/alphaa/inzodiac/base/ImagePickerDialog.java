package com.alphaa.inzodiac.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.gallery.activities.AlbumSelectActivity;
import com.alphaa.inzodiac.gallery.activities.ImageSelectActivity;
import com.alphaa.inzodiac.gallery.helpers.Constants;
import com.alphaa.inzodiac.gallery.models.Album;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ImagePickerDialog extends BaseBottomDialog implements View.OnClickListener {

    String TAG1 = getClass().getSimpleName();

    private ImagePickerDialog TAG = ImagePickerDialog.this;
    private ImagePickerCallBack callBack;
    private String imgfor = "";
    private TextView tv_camera,tv_gallery,cancel;
    private static ImagePickerDialog instance = null;
    private Context context;
    private SharedPreferences mypref;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "Go_Image";

    private ArrayList<Album> albums;
    private static final int PICK_FROM_GALLERY = 109;

    public ImagePickerDialog(Context context) {
        this.context = context;
       // setOnTextListener(getBaseActivity());
        mypref = this.context.getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
        editor = mypref.edit();
        editor.apply();

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.image_pick_dialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_camera = view.findViewById(R.id.tv_camera);
        tv_gallery = view.findViewById(R.id.tv_gallery);
        cancel = view.findViewById(R.id.cancel);
        setClicks(tv_camera,tv_gallery,cancel);
    }

    public static ImagePickerDialog getInstance(Context context) {
        if ((instance != null)) {
            return instance;
        }

        instance = new ImagePickerDialog(context);
        return instance;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, "TAG");
    }

    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }

   /* public void setOnTextListener(BaseActivity callBack) {
        this.callBack = callBack;
    }*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.tv_camera:{

                Log.e(TAG1, "onClick: camera click " );
                callBack.textOnClick("Camera");
                dismiss();
            }
            break;
            case R.id.tv_gallery:{

                openFolder();

//                Intent intent = new Intent(getActivity(), AlbumSelectActivity.class);
//                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10000);
//                startActivityForResult(intent, Constants.REQUEST_CODE);

                //callBack.textOnClick("Gallery");
               /* Matisse.from((Activity) context)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(5)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .showPreview(false) // Default is `true`
                        .forResult(AppConstants.MULTIPLE_IMAGE_REQUEST_CODE);
//                callBack.textOnClick("Gallery");*/
                dismiss();

            }
            break;
            case R.id.cancel:{
                dismiss();
            }
            break;

        }
    }

    public void intializeListener(ImagePickerCallBack callBack) {
        this.callBack = callBack;
    }

    public interface ImagePickerCallBack {
        void textOnClick(String type);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
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
}
