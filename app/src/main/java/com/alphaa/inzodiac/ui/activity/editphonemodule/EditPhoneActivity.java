package com.alphaa.inzodiac.ui.activity.editphonemodule;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityEditPhoneBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.ui.activity.phoneverification.PhoneVerificationActivity;
import com.alphaa.inzodiac.ui.activity.phoneverification.VerificationPresenter;
import com.alphaa.inzodiac.ui.activity.signupmodule.UserdatailModel;
import com.alphaa.inzodiac.utility.CheckNetwork;
import com.alphaa.inzodiac.utility.PDialog;
import com.alphaa.inzodiac.utility.Validation;

public class EditPhoneActivity extends BaseActivity implements View.OnClickListener,  ApiCallBackInterFace.phoneVerifyInfoCallback {
    String TAG = getClass().getSimpleName();

    private ActivityEditPhoneBinding binding;
    private Validation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_phone);

        setClicks(binding.submitBtn);
    }

    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
        validation = new Validation(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn: {
                doSave();
            }
            break;
            default:
                break;
        }
    }

    private void doSave() {
        if (validation.isMobileNoValid(binding.phoneNum)) {
            if (CheckNetwork.isNetAvailable(getApplicationContext())) {
                new VerificationPresenter(this, this).checkSamePhoneNumber(binding.phoneNum.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), Toast.LENGTH_LONG).show();
            }
        }
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
        Log.e(TAG, "onApiErrorResponse: " + Errorresponse);
        Toast.makeText(activity, "" + Errorresponse, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCheckSamePhoneResponse(Boolean b) {
        UserdatailModel.SignupDatum data = new UserdatailModel.SignupDatum();
        data.setPhone(binding.phoneNum.getText().toString());

        Intent intent = new Intent(activity, PhoneVerificationActivity.class);
        intent.putExtra("user_data", data);
        startActivity(intent);
    }
}