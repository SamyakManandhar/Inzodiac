package com.alphaa.inzodiac.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.alphaa.inzodiac.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;

import org.jetbrains.annotations.NotNull;


public class HeightSliderDialog extends BaseBottomDialog implements View.OnClickListener {

    private HeightSliderDialog TAG = HeightSliderDialog.this;
    private HeightSliderCallBack callBack;
    private String imgfor = "";
    private static HeightSliderDialog instance = null;
    private Context context;
    private SharedPreferences mypref;
    private SharedPreferences.Editor editor;
    private RangeSeekBar sb_range_age;
    private RelativeLayout cancel;
    private static final String PREF_NAME = "Go_Image";


    public HeightSliderDialog(Context context) {
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
        View view = inflater.inflate(R.layout.height_slide_dialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sb_range_age = view.findViewById(R.id.sb_range_age);
        cancel = view.findViewById(R.id.cancel);
        heightSlider();
        setClicks(cancel);
    }

    private void heightSlider(){
        sb_range_age.getLeftSeekBar().setIndicatorText("150cm");
        sb_range_age.getRightSeekBar().setIndicatorText("219cm");
        sb_range_age.setProgress(150F,219F);

        sb_range_age.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float sb_range_3, float rightValue, boolean isFromUser) {
                int intvalue = (int)Math.round(sb_range_3);
                String mytext=String.valueOf(intvalue);
                sb_range_age.getLeftSeekBar().setIndicatorText(mytext+"cm");


                int intvalue1 = (int)Math.round(rightValue);
                String mytext1=String.valueOf(intvalue1);
                sb_range_age.getRightSeekBar().setIndicatorText(mytext1+"cm");
                callBack.heighttextOnClick(mytext,mytext1);

            }
            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }
        });
    }
    
    public static HeightSliderDialog getInstance(Context context) {
        if ((instance != null)) {
            return instance;
        }

        instance = new HeightSliderDialog(context);
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
            
            case R.id.cancel:{
                dismiss();
            }
            break;

        }
    }

    public void intializeHeightListener(HeightSliderCallBack callBack) {
        this.callBack = callBack;
    }

    public interface HeightSliderCallBack {
        void heighttextOnClick(String left,String Right);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
    }
}
