package com.alphaa.inzodiac.tabmodule.fragment.horomonthmodule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseFragment;
import com.alphaa.inzodiac.databinding.ActivityHoroMonthBinding;
import com.alphaa.inzodiac.databinding.DialogMonthlyHoroscopeBinding;
import com.alphaa.inzodiac.databinding.LogoutDialogBinding;
import com.alphaa.inzodiac.tabmodule.fragment.tokenmodule.TokenFragment;
import com.alphaa.inzodiac.tabmodule.fragment.updateprmodule.UpdatePremiumFragment;

import java.util.Objects;

public class HoroMonthActivity extends BaseFragment {

    private static final String TAG = "HoroMonthActivity";

    ActivityHoroMonthBinding bindingmain;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static HoroMonthActivity newInstance(String param1, String param2) {
        HoroMonthActivity fragment = new HoroMonthActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bindingmain = DataBindingUtil.inflate(inflater, R.layout.activity_horo_month , container, false);
        return bindingmain.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e(TAG, "onViewCreated: " );

        bindingmain.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getContext(), PaymentDetailActivity.class);

                dialogHoroScope();
            }
        });
    }





    public void dialogHoroScope() {
        DialogMonthlyHoroscopeBinding binding;
        final Dialog dialog = new Dialog(getContext(),R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_monthly_horoscope, null, false);
        dialog.setContentView(binding.getRoot());
        //binding.cat.setText("Do you want to exit?");
        binding.ivPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                bindingmain.frame1.setVisibility(View.VISIBLE);
                UpdatePremiumFragment filterFragment = UpdatePremiumFragment.newInstance("", "");
                replaceFragment(filterFragment, bindingmain.frame1.getId(), true);

            }
        });


        binding.ivToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                dialog.dismiss();

                TokenFragment profileFragment = TokenFragment.newInstance("", "");
                replaceFragment(profileFragment, bindingmain.frame1.getId(), false);

            }
        });
        dialog.show();
    }






}