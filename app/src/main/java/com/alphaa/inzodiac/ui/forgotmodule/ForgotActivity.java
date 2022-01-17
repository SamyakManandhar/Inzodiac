package com.alphaa.inzodiac.ui.forgotmodule;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityForgotBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.utility.PDialog;
import com.alphaa.inzodiac.utility.Validation;

public class ForgotActivity extends BaseActivity implements View.OnClickListener, ApiCallBackInterFace.forgetPasswordInfoCallback {
    private ActivityForgotBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot);
        inItView();
    }

    private void inItView() {
        setClicks(binding.send,binding.ivback);
    }

    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivback: {
                onBackPressed();
            }
            break;
            case R.id.send: {
                Validation validation = new Validation(this);
                if (validation.isEmailValid(binding.etEmail)) {
                    new ForgotPresenter(this, this).forgotData(binding.etEmail.getText().toString());
                }
            }
            break;
        }
    }

    @Override
    public void onforgetPasswordInfoResponse(String s) {


    }

    @Override
    public void showLoaderProcess() {
        PDialog.pdialog(activity);
    }

    @Override
    public void hideLoaderProcess() {
        PDialog.hideDialog();
    }

    @Override
    public void onApiErrorResponse(String Errorresponse, String error_type) {

    }
}