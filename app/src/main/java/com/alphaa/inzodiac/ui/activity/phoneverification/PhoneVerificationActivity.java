package com.alphaa.inzodiac.ui.activity.phoneverification;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityPhoneVerificationBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.ui.activity.aboutself.AboutSelfActivity;
import com.alphaa.inzodiac.ui.activity.editphonemodule.UpdatePhonePresenter;
import com.alphaa.inzodiac.ui.activity.loginmodule.LoginActivity;
import com.alphaa.inzodiac.ui.activity.signupmodule.SignupActivity;
import com.alphaa.inzodiac.ui.activity.signupmodule.SignupPresenter;
import com.alphaa.inzodiac.ui.activity.signupmodule.UserdatailModel;
import com.alphaa.inzodiac.ui.signupmodel.SignupDatum;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.CheckNetwork;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.FirebaseUserModel;
import com.alphaa.inzodiac.utility.PDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.concurrent.TimeUnit;

public class PhoneVerificationActivity extends BaseActivity implements View.OnClickListener, ApiCallBackInterFace.signupInfoCallback, ApiCallBackInterFace.updatePhoneNumberCallback {
    String TAG = getClass().getSimpleName();

    private ActivityPhoneVerificationBinding binding;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;

    UserdatailModel.SignupDatum userDataModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_verification);

        if (getIntent().hasExtra("user_data")) {
            userDataModal = (UserdatailModel.SignupDatum) getIntent().getSerializableExtra("user_data");
        }

        inItView();
    }

    private void inItView() {
        setClicks(binding.resendBtn, binding.verifyBtn, binding.ivback);

        binding.verifyBtn.setEnabled(false);
        binding.resendView.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:$credential");
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    new AlertDialog
                            .Builder(PhoneVerificationActivity.this)
                            .setMessage("Invalid phone number !")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    new AlertDialog
                            .Builder(PhoneVerificationActivity.this)
                            .setMessage("Too Many Requests. Quota exceeded.")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                startCountDownTimer();
                binding.verifyBtn.setEnabled(true);
                binding.resendView.setVisibility(View.GONE);
            }
        };

        startPhoneNumberVerification(userDataModal.getPhone());
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        Log.e("start PhoneNumber", phoneNumber);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(Constants.PHONE_COUNTRY_CODE + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(Constants.PHONE_COUNTRY_CODE + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(token)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            // FirebaseUser user = task.getResult().getUser();

                            String auth_flow = AppSession.getStringPreferences(getApplicationContext(), Constants.AUTH_FLOW);
                            String auth_type = AppSession.getStringPreferences(getApplicationContext(), Constants.AUTH_TYPE);
                            String is_profile_completed = AppSession.getStringPreferences(getApplicationContext(), Constants.IS_COMPLETED_PROFILE);

                            if (auth_flow.equals("login")) {
                                if (auth_type.equals("phone")) {
                                    if (is_profile_completed.equals("0")) {
                                        goAboutSelf();
                                    }
                                    else {
                                        goHome();
                                    }
                                }
                                else {
                                    doSavePhone();
                                }
                            } else {
                                doSignup();
                            }

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(PhoneVerificationActivity.this, "Invalid Code!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }


    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verifyBtn: {
                String code = binding.etCode.getText().toString();
                if (!code.isEmpty()) {
                    verifyPhoneNumberWithCode(mVerificationId, code);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter code!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.resendBtn: {
                resendVerificationCode(userDataModal.getPhone(), mResendToken);
            }
            break;
            case R.id.ivback: {
                onBackPressed();
            }
            break;
            default:
                break;
        }
    }

    private void doSavePhone() {
        // update phone number
        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            String userid = AppSession.getStringPreferences(getApplicationContext(), com.alphaa.inzodiac.utility.Constants.USEREIDSIGNUP);
            new UpdatePhonePresenter(this, this).updatePhoneNumber(userDataModal.getPhone(), userid);
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), Toast.LENGTH_LONG).show();
        }
    }

    private void doSignup() {
        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            new SignupPresenter(this, this).signupData(userDataModal.getEmail(), userDataModal.getPassword(), "", userDataModal.getPhone()
                    , userDataModal.getName(), userDataModal.getLat(), userDataModal.getLong(), userDataModal.getFirebase_token());

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), Toast.LENGTH_LONG).show();
        }
    }

    private void goAboutSelf() {
        Intent intent = new Intent(PhoneVerificationActivity.this, AboutSelfActivity.class);
        navigateTo(intent, true);
    }

    private void goHome() {
        getPrefHelper().issetLoggedIn();

        Intent intent = new Intent(PhoneVerificationActivity.this, TabActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onsignupInfoResponse(String s) {

        Log.e(TAG, "onsignupInfoResponse: " + s);

        UserdatailModel userdatailModel = null;
        try {
            String s1 = s;//.replace("null", "\"\"");

            Log.e(TAG, "onsignupInfoResponse: null " + s1);
            userdatailModel = new Gson().fromJson(s1, UserdatailModel.class);

            Log.e(TAG, "onsignupInfoResponse: user " + userdatailModel.getData().getId());
            getPrefHelper().setUserId(userdatailModel.getData().getId());
            getPrefHelper().setUserDataModel(s1);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.e(TAG, "onsignupInfoResponse: exception " + e.getMessage());
        }
        //getPrefHelper().issetLoggedIn();

        AppSession.setStringPreferences(getApplicationContext(), Constants.USEREIDSIGNUP, userdatailModel.getData().getId());
        AppSession.setStringPreferences(getApplicationContext(), Constants.USER_EMAIL_SIGNUP, userDataModal.getEmail());
        AppSession.setStringPreferences(getApplicationContext(), Constants.USER_PASS_SIGNUP, userDataModal.getPassword());
        AppSession.setStringPreferences(getApplicationContext(), Constants.USER_FB_TOKEN_SIGNUP, userDataModal.getFirebase_token());

        addUserFirebaseDatabase(userdatailModel.getData().getName(), userdatailModel.getData().getEmail(), userdatailModel.getData().getId());

        goAboutSelf();
    }

    private void addUserFirebaseDatabase(String name, String email, String uId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseUserModel userModel = new FirebaseUserModel();
        userModel.name = name;
        userModel.email = email;
        userModel.profilePic = "";
        userModel.uid = uId;
        database.child(Constants.USER_TABLE).child(uId).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
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

    void startCountDownTimer() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.tvTime.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                binding.verifyBtn.setEnabled(false);
                binding.resendView.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    @Override
    public void onUpdatePhoneResponse(Boolean b) {
        if(b) {
            Intent intent = new Intent(PhoneVerificationActivity.this, AboutSelfActivity.class);
            navigateTo(intent, true);
        }
    }


}