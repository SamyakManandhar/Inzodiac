package com.alphaa.inzodiac.ui.activity.letstart;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityLetStartBinding;
import com.alphaa.inzodiac.databinding.ActivityLoginBinding;
import com.alphaa.inzodiac.ui.activity.loginmodule.LoginActivity;
import com.alphaa.inzodiac.ui.activity.signupmodule.SignupActivity;

public class LetStartActivity extends BaseActivity implements View.OnClickListener {

    ActivityLetStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_let_start);
        inItView();
    }

    private void inItView() {
        setClicks(binding.login,binding.register );

    }

    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login: {
                Intent intent = new Intent(LetStartActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.ivback: {
               onBackPressed();
            }
            break;

            case R.id.register: {
                Intent intent = new Intent(LetStartActivity.this, SignupActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
}