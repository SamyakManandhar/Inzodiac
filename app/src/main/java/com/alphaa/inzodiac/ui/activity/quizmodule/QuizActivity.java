package com.alphaa.inzodiac.ui.activity.quizmodule;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityQuizBinding;
import com.alphaa.inzodiac.databinding.ActivityStepCompleteBinding;
import com.alphaa.inzodiac.gallery.models.Image;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.utility.PDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class QuizActivity extends BaseActivity implements View.OnClickListener , ApiCallBackInterFace.AboutInfoCallback {
    String TAG = getClass().getSimpleName();

    ActivityQuizBinding binding;
    String height="",img="",strgender = "", strinterest = "", orientation = "", looking = "",date = "", ethnicityname = "", bodytype = "", haircolor = "", htype = "", married = "", child = "", smoke = "", drink = "", lang = "";
    ArrayList<Image> imglist;

    ArrayList<String> arrayListQuiz = new ArrayList<>();
    String[] arrayQuiz = {"1. Given the choice of anyone in the world, whom would you want as a dinner guest?",
            "2. Would you like to be famous? In what way?",
            "3. Before making a telephone call, do you ever rehearse what you are going to say? Why?",
             "4. What would constitute a “perfect” day for you?",
            "5. When did you last sing to yourself? To someone else?",
            "6. If you were able to live to the age of 90 and retain either the mind or body of a 30-year-old for the last 60 years of your life, which would you want?",
            "7. Do you have a secret hunch about how you will die?",
            "8. Name three things you and your partner appear to have in common.",
            "9. For what in your life do you feel most grateful?",
            "10. If you could change anything about the way you were raised, what would it be?",
            "11. Take four minutes and tell your partner your life story in as much detail as possible.",
            "12. If you could wake up tomorrow having gained any one quality or ability, what would it be?" ,
            "13. If a crystal ball could tell you the truth about yourself, your life, the future or anything else, what would you want to know?" ,
            "14. Is there something that you’ve dreamed of doing for a long time? Why haven’t you done it?",
            "15. What is the greatest accomplishment of your life?" ,
            "16. What do you value most in a friendship?",
            "17. What is your most treasured memory?," ,
            "18. What is your most terrible memory?" ,
            "19. If you knew that in one year you would die suddenly, would you change anything about the way you are now living? Why?" ,
            "20. What does friendship mean to you?" ,
            "21. What roles do love and affection play in your life?",
            "22. Alternate sharing something you consider a positive characteristic of your partner. Share a total of five items." ,
            "23. How close and warm is your family? Do you feel your childhood was happier than most other people’s?" ,
            "24. How do you feel about your relationship with your mother?" ,
            "25. Make three true “we” statements each. For instance, “We are both in this room feeling ... “" ,
            "26. Complete this sentence: “I wish I had someone with whom I could share ... “",
            "27. If you were going to become a close friend with your partner, please share what would be important for him or her to know." ,
            "28. Tell your partner what you like about them; be very honest this time, saying things that you might not say to someone you’ve just met." ,
            "29. Share with your partner an embarrassing moment in your life." ,
            "30. When did you last cry in front of another person? By yourself?",
            "31. Tell your partner something that you like about them already.",
            "32. What, if anything, is too serious to be joked about?" ,
            "33. If you were to die this evening with no opportunity to communicate with anyone, what would you most regret not having told someone? Why haven’t you told them yet?" ,
            "34. Your house, containing everything you own, catches fire. After saving your loved ones and pets, you have time to safely make a final dash to save any one item. What would it be? Why?" ,
            "35. Of all the people in your family, whose death would you find most disturbing? Why?",
            "36. Share a personal problem and ask your partner’s advice on how he or she might handle it. Also, ask your partner to reflect back to you how you seem to be feeling about the problem you have chosen."
    };


    int count = 0;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);

        arrayListQuiz.addAll(Arrays.asList(arrayQuiz));

        binding.tvQuestion.setText(arrayListQuiz.get(0));
        //countTimer();

        inItView();
    }

    private void inItView() {
        setClicks(binding.rlnext,binding.rlprevius,binding.ivback);
    }

    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.rlprevius: {

                pressPrevious();
            }
            break;

            case R.id.rlnext: {

                pressNext();

            }
            break;
            case R.id.ivback: {

                if (countDownTimer!=null){
                    countDownTimer.cancel();
                }
                onBackPressed();
            }
            break;



        }
    }



    @Override
    public void onAboutInfoResponse(String s) {
        Intent intent = new Intent(QuizActivity.this, TabActivity.class);
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
        countDownTimer =  new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.tvTimer.setText("" + millisUntilFinished / 1000);

                Log.e(TAG, "onTick: " );
            }

            public void onFinish() {
                //binding.tvTimer.setText("done");
                pressNext();
            }
        };
        countDownTimer.start();
    }


    private void pressNext() {
        if (count<35) {
            count++;

            binding.tvQuestion.setText(arrayListQuiz.get(count));
            //countDownTimer.cancel();
            //countTimer();
        }else {
            //countDownTimer.cancel();
            //Toast.makeText(activity, "Questions limit completed", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),QuizChallengeActivity.class);
            startActivity(intent);
            finish();
        }
        Log.e(TAG, "onClick: count next " + count);
    }

    private void pressPrevious() {
        if (count!=0){
            count--;
            binding.tvQuestion.setText(arrayListQuiz.get(count));
            //countDownTimer.cancel();
            //countTimer();
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

