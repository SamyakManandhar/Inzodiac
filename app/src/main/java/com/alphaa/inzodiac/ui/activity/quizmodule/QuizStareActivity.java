package com.alphaa.inzodiac.ui.activity.quizmodule;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityQuizBinding;
import com.alphaa.inzodiac.databinding.ActivityStareQuizBinding;
import com.alphaa.inzodiac.gallery.models.Image;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.utility.PDialog;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class QuizStareActivity extends BaseActivity implements View.OnClickListener , ApiCallBackInterFace.AboutInfoCallback {
    String TAG = getClass().getSimpleName();

    ActivityStareQuizBinding binding;
    String height="",img="",strgender = "", strinterest = "", orientation = "", looking = "",date = "", ethnicityname = "", bodytype = "", haircolor = "", htype = "", married = "", child = "", smoke = "", drink = "", lang = "";
    ArrayList<Image> imglist;

    ArrayList<String> arrayListQuiz = new ArrayList<>();


    int count = 0;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stare_quiz);

        //arrayListQuiz.addAll(Arrays.asList(arrayQuiz));

      //  binding.tvQuestion.setText(arrayListQuiz.get(0));
        countTimer();

        inItView();
    }

    private void inItView() {
        setClicks(binding.ivback);
    }

    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


           /* case R.id.rlprevius: {

                pressPrevious();
            }
            break;

            case R.id.rlnext: {

                pressNext();

            }
            break;*/
            case R.id.ivback: {


                onBackPressed();
                countDownTimer.cancel();
            }
            break;



        }
    }



    @Override
    public void onAboutInfoResponse(String s) {
        Intent intent = new Intent(QuizStareActivity.this, TabActivity.class);
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

    public void countTimer(){
        countDownTimer =  new CountDownTimer(1000*60*4, 1000) {

            public void onTick(long millisUntilFinished) {
               // binding.tvTimer.setText("" + millisUntilFinished / 1000);

                binding.tvTimer.setText(""+String.format("%d : %02d ",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));



                Log.e(TAG, "onTick: " );
            }

            public void onFinish() {
                //binding.tvTimer.setText("done");
                //pressNext();

                binding.tvQuestion.setVisibility(View.VISIBLE);
                binding.tvTimer.setVisibility(View.GONE);

                //play sound
                MediaPlayer mediaPlayer= MediaPlayer.create(getApplicationContext(),R.raw.beep);
                mediaPlayer.start();


                //vibrate mobile
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 2000 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(2000);
                }
            }
        };
        countDownTimer.start();
    }


    private void pressNext() {
        if (count<35) {
            count++;

            binding.tvQuestion.setText(arrayListQuiz.get(count));
            countDownTimer.cancel();
            countTimer();
        }else {
            countDownTimer.cancel();
            //Toast.makeText(activity, "Questions limit completed", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),QuizChallengeActivity.class);
            startActivity(intent);
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
    }
}

