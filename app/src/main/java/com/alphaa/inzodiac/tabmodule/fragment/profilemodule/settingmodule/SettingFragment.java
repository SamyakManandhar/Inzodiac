package com.alphaa.inzodiac.tabmodule.fragment.profilemodule.settingmodule;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseFragment;
import com.alphaa.inzodiac.databinding.FragmentProfileBinding;
import com.alphaa.inzodiac.databinding.LogoutDialogBinding;
import com.alphaa.inzodiac.tabmodule.activity.aboutmodule.AboutActivity;
import com.alphaa.inzodiac.tabmodule.fragment.updateprmodule.UpdatePremiumFragment;
import com.alphaa.inzodiac.ui.activity.SafetyActivity;
import com.alphaa.inzodiac.ui.activity.contactus.ContactUsActivity;
import com.alphaa.inzodiac.ui.activity.notification.NotificationActivity;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentProfileBinding binding;

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rlabo.setOnClickListener(v -> {
            Intent intent = new Intent(activity, AboutActivity.class);
            intent.putExtra("name", "About");
            startActivity(intent);
        });

        binding.rlpp.setOnClickListener(v -> {
            Intent intent = new Intent(activity, AboutActivity.class);
            intent.putExtra("name", "Privacy Policy");
            startActivity(intent);
        });

        binding.llinpp.setOnClickListener(v -> {
            UpdatePremiumFragment filterFragment = UpdatePremiumFragment.newInstance("", "");
            replaceFragment(filterFragment, binding.frame.getId(), true);
            binding.frame.setVisibility(View.VISIBLE);
            binding.ll.setVisibility(View.GONE);
        });

        binding.rltc.setOnClickListener(v -> {
            Intent intent = new Intent(activity, AboutActivity.class);
            intent.putExtra("name", "Terms & Conditions");
            startActivity(intent);
        });

        binding.rllog.setOnClickListener(v -> {
            workInfoDialog();
        });

        binding.llSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SafetyActivity.class);
                //intent.putExtra("name", "Terms & Conditions");
                startActivity(intent);
            }
        });

        binding.llRateapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("market://details?id=" + "getPackageName"));
                startActivity(i);

            }
        });

        binding.llContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ContactUsActivity.class);
                //intent.putExtra("name", "Terms & Conditions");
                startActivity(intent);
            }
        });
        binding.llnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NotificationActivity.class);
                //intent.putExtra("name", "Terms & Conditions");
                startActivity(intent);
            }
        });
    }

    public void workInfoDialog() {
        LogoutDialogBinding binding;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.logout_dialog, null, false);
        dialog.setContentView(binding.getRoot());


        binding.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        binding.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getPrefHelper().logout(activity);

            }
        });
        dialog.show();
    }


}