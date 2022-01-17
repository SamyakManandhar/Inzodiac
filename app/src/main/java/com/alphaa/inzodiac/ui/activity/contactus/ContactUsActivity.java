package com.alphaa.inzodiac.ui.activity.contactus;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.databinding.ActivityContactusBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.ui.activity.contactus.contactusmodel.ContactUsPojo;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.PDialog;
import com.google.android.datatransport.cct.internal.LogEvent;
import com.google.gson.Gson;

public class ContactUsActivity extends AppCompatActivity implements ApiCallBackInterFace.ContactUsCallback, View.OnClickListener {

    String TAG = getClass().getSimpleName();

    ActivityContactusBinding binding;

    String name ,phone,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_contactus);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contactus);

        name = AppSession.getStringPreferences(getApplicationContext(), Constants.USERNAME);
        email = AppSession.getStringPreferences(getApplicationContext(), Constants.USEREMAIL);
        phone = AppSession.getStringPreferences(getApplicationContext(), Constants.USERPHONE);

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initview();
            }
        });
    }

    public void  initview() {

        if (binding.etSubject.toString().isEmpty()) {
            Toast.makeText(this, "Please add Subject", Toast.LENGTH_SHORT).show();
        } else if (binding.etMessage.toString().isEmpty()) {
            Toast.makeText(this, "Please add Message", Toast.LENGTH_SHORT).show();
        } else {
            new ContactUsPresenter(ContactUsActivity.this, this).contactUs(name, phone, email, binding.etSubject.getText().toString(), binding.etMessage.getText().toString());
        }
    }


    @Override
    public void oncontactusInfoResponse(String s) {

        Log.e(TAG, "oncontactusInfoResponse: "+s );

        ContactUsPojo contactUsPojo = new Gson().fromJson(s,ContactUsPojo.class);
        Log.e(TAG, "oncontactusInfoResponse: 11111 "+contactUsPojo.getMessage() );
        Toast.makeText(this, ""+contactUsPojo.getMessage(), Toast.LENGTH_SHORT).show();

        binding.etMessage.setText("");
        binding.etSubject.setText("");

        finish();
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

    }

    @Override
    public void onClick(View v) {

    }
}