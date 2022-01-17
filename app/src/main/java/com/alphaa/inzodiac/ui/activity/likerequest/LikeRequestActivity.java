package com.alphaa.inzodiac.ui.activity.likerequest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityQuizAcceptRegectBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.ui.activity.quizmodule.presenter.QuizAcceptRegectPresenter;
import com.alphaa.inzodiac.utility.CheckNetwork;
import com.alphaa.inzodiac.utility.PDialog;

public class LikeRequestActivity extends BaseActivity implements View.OnClickListener , ApiCallBackInterFace.LikeRequestCallback {
    String TAG = getClass().getSimpleName();

    com.alphaa.inzodiac.databinding.ActivityLikeRequestBinding binding;
    String comesfrom,m_userid,m_otheruserid,m_status,m_username,m_request_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_like_request);

      //  binding.tvQuestion.setText(arrayListQuiz.get(0));
       // countTimer();

        inItView();



        Intent intent = getIntent();
        comesfrom = intent.getStringExtra("comesfrom");
        //m_userid = intent.getStringExtra("m_userid");
        m_otheruserid = intent.getStringExtra("otherid");
        //m_status = intent.getStringExtra("m_status");
        m_username = intent.getStringExtra("otherName");
        m_request_id = intent.getStringExtra("request_id");


        Log.e(TAG, m_otheruserid+"   :onCreate: 1111 " );
        Log.e(TAG, m_username+"   :onCreate: 2222 "+m_request_id );
        Log.e(TAG, comesfrom+"   :onCreate: 3333 " );


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

                    new LikeRequestPresenter(this, this).quizRequestAcceptRegect(m_otheruserid,"1",m_request_id);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.tv_reject:

                Log.e(TAG, m_request_id+"  onClick: userid "+m_userid );

                if (CheckNetwork.isNetAvailable(getApplicationContext())) {

                    new LikeRequestPresenter(this, this).quizRequestAcceptRegect(m_otheruserid,"2",m_request_id);
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
    public void onLikeRequestResponse(String s) {

        finish();
        Log.e(TAG, "onLikeRequestResponse: "+s );

    }
}

