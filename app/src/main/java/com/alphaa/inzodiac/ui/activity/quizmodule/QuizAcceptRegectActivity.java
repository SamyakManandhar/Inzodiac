package com.alphaa.inzodiac.ui.activity.quizmodule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityQuizAcceptRegectBinding;
import com.alphaa.inzodiac.databinding.ActivityStareQuizBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.ui.activity.quizmodule.presenter.QuizAcceptRegectPresenter;
import com.alphaa.inzodiac.utility.CheckNetwork;
import com.alphaa.inzodiac.utility.PDialog;

public class QuizAcceptRegectActivity extends BaseActivity implements View.OnClickListener , ApiCallBackInterFace.QuizRequestAcceptRejectCallback {
    String TAG = getClass().getSimpleName();

    ActivityQuizAcceptRegectBinding binding;
    String comesfrom,m_userid,m_otheruserid,m_status,m_username,m_request_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_accept_regect);

      //  binding.tvQuestion.setText(arrayListQuiz.get(0));
       // countTimer();

        inItView();



        Intent intent = getIntent();
        comesfrom = intent.getStringExtra("comesfrom");//
        m_userid = intent.getStringExtra("m_userid");//
        m_otheruserid = intent.getStringExtra("m_otheruserid");
        m_status = intent.getStringExtra("m_status");//
        m_username = intent.getStringExtra("m_username");
        m_request_id = intent.getStringExtra("m_request_id");

        binding.tvRequest.setText("You have request from user "+m_username);
    }

    private void inItView() {
        setClicks(binding.ivback,binding.tvAccept,binding.tvReject);
    }

    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.ivback:
                onBackPressed();

                 break;

            case R.id.tv_accept:


                Log.e(TAG, m_request_id+"  onClick: userid "+m_userid );
                if (CheckNetwork.isNetAvailable(getApplicationContext())) {

                    new QuizAcceptRegectPresenter(this, this).quizRequestAcceptRegect(m_otheruserid,"Accept",m_request_id);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.tv_reject:

                Log.e(TAG, m_request_id+"  onClick: userid "+m_userid );

                if (CheckNetwork.isNetAvailable(getApplicationContext())) {

                    new QuizAcceptRegectPresenter(this, this).quizRequestAcceptRegect(m_otheruserid,"Decline",m_request_id);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), Toast.LENGTH_LONG).show();
                }


                break;



        }
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


    @Override
    public void onAcceptRejectRequesr(String s) {

        Log.e(TAG, "onAcceptRejectRequesr: sssss "+s);
        Toast.makeText(activity, ""+s, Toast.LENGTH_SHORT).show();
        finish();

    }
}

