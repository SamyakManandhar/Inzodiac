package com.alphaa.inzodiac.ui.activity.quizmodule;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityQuizInstructionBinding;
import com.alphaa.inzodiac.databinding.ActivityQuizchallangeBinding;
import com.alphaa.inzodiac.gallery.models.Image;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.utility.PDialog;

import java.util.ArrayList;

public class QuizChallengeActivity extends BaseActivity implements View.OnClickListener , ApiCallBackInterFace.AboutInfoCallback {
    String TAG = getClass().getSimpleName();

    ActivityQuizchallangeBinding binding;
    String height="",img="",strgender = "", strinterest = "", orientation = "", looking = "",date = "", ethnicityname = "", bodytype = "", haircolor = "", htype = "", married = "", child = "", smoke = "", drink = "", lang = "";
    ArrayList<Image> imglist;

    ArrayList<String> arrayListQuiz = new ArrayList<>();


    int count = 0;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quizchallange);

        //arrayListQuiz.addAll(Arrays.asList(arrayQuiz));

       // binding.tvQuestion.setText(arrayListQuiz.get(0));
        //countTimer();

        inItView();
    }

    private void inItView() {
        setClicks(binding.tvNext,binding.ivback);
    }

    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.tv_next: {

                Intent intent = new Intent(getApplicationContext(),QuizStareActivity.class);
                startActivity(intent);
                finish();

            }

            break;
            case R.id.ivback: {

                onBackPressed();
            }
            break;



        }
    }



    @Override
    public void onAboutInfoResponse(String s) {
        Intent intent = new Intent(QuizChallengeActivity.this, TabActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
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



    /*private void pressNext() {
        if (count<35) {
            count++;

            binding.tvQuestion.setText(arrayListQuiz.get(count));
            countDownTimer.cancel();
            countTimer();
        }else {
            countDownTimer.cancel();
            Toast.makeText(activity, "Questions limit completed", Toast.LENGTH_SHORT).show();
        }
        Log.e(TAG, "onClick: count next " + count);
    }

    private void pressPrevious() {
        if (count!=0){
            count--;
            binding.tvQuestion.setText(arrayListQuiz.get(count));

            countDownTimer.cancel();
            countTimer();
        }
        Log.e(TAG, "onClick: count next "+count );
    }*/

}

