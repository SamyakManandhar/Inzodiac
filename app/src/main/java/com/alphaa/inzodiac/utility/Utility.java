package com.alphaa.inzodiac.utility;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.databinding.LogoutDialogBinding;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uk.co.senab.photoview.PhotoView;

public class Utility {

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    public static String getDateBanner(Context context,Object timeStamp) {
        String banner_date = "";
        SimpleDateFormat sim = new SimpleDateFormat("d MMMM yyyy", Locale.US);
        try {
            String date_str = sim.format(new Date((Long) timeStamp)).trim();
            String currentDate = sim.format(Calendar.getInstance().getTime()).trim();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            String yesterdayDate = sim.format(calendar.getTime()).trim();

            if (date_str.equals(currentDate)) {
                banner_date = context.getString(R.string.dummy_time).trim();
            } else if (date_str.equals(yesterdayDate)) {
                banner_date = context.getString(R.string.yesterday).trim();
            } else {
                banner_date = date_str.trim();
            }

            return banner_date;
        } catch (Exception e) {
            e.printStackTrace();
            return banner_date;
        }
    }


    public static String SimpleTimeFormat(Object stamp){
        String time="";
        SimpleDateFormat sd = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            String time_str = sd.format(new Date((Long) stamp));
            time = time_str.replace("am", "AM").replace("pm", "PM");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String SimpleDateTimeFormat(Object stamp){
        String time_str="";
        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yy 'at' HH:mm ", Locale.US);
        try {
             time_str = sd.format(new Date((Long) stamp));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return time_str;
    }

    public static void printDifference(Context mContext, Date startDate, Date endDate, TextView textView) {
        long different;

        //milliseconds
        if (endDate.getTime() > startDate.getTime()) {
            different = endDate.getTime() - startDate.getTime();
        } else {
            different = startDate.getTime() - endDate.getTime();
        }
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long weeksInMilli = daysInMilli * 7;
        long monthInMilli = weeksInMilli * 30;
        long yearInMilli = monthInMilli * 12;
        long elapsedYears = different / yearInMilli;
        different = different % yearInMilli;
        long elapsedMonths = different / monthInMilli;
        different = different % monthInMilli;
        long elapsedWeeks = different / weeksInMilli;
        different = different % weeksInMilli;
        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        long elapsedSeconds = different / secondsInMilli;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sd = new SimpleDateFormat("hh:mm a");
        String time_str = sd.format(different);
        String time = time_str.replace("am", "AM").replace("pm", "PM");
        if (elapsedYears != 0) {
            if (elapsedYears == 1) {
                textView.setText(String.format("%s %s", String.valueOf(elapsedYears), mContext.getResources().getString(R.string.year_ago)));
            } else {
                textView.setText(String.format("%s %s", String.valueOf(elapsedYears), mContext.getResources().getString(R.string.years_ago)));
            }
        } else if (elapsedMonths != 0) {
            if (elapsedMonths == 1) {
                textView.setText(String.format("%s %s", String.valueOf(elapsedMonths), mContext.getResources().getString(R.string.month_ago)));
            } else {
                textView.setText(String.format("%s %s", String.valueOf(elapsedMonths), mContext.getResources().getString(R.string.months_ago)));
            }
        } else if (elapsedWeeks != 0) {
            if (elapsedWeeks == 1) {
                textView.setText(String.format("%s %s", String.valueOf(elapsedWeeks), mContext.getResources().getString(R.string.week_ago)));
            } else {
                textView.setText(String.format("%s %s", String.valueOf(elapsedWeeks), mContext.getResources().getString(R.string.weeks_ago)));
            }
        } else if (elapsedDays != 0) {
            if (elapsedDays == 1) {
                textView.setText(String.format("%s %s", String.valueOf(elapsedDays), mContext.getResources().getString(R.string.day_ago)));
            } else {
                textView.setText(String.format("%s %s", String.valueOf(elapsedDays), mContext.getResources().getString(R.string.days_ago)));
            }
        } else if (elapsedHours != 0) {
            if (elapsedHours == 1) {
                textView.setText(String.format("%s %s", String.valueOf(elapsedHours), mContext.getResources().getString(R.string.hour_ago)));
            } else {
                textView.setText(String.format("%s %s", String.valueOf(elapsedHours), mContext.getResources().getString(R.string.hours_ago)));
            }

        } else if (elapsedMinutes != 0) {
            if (elapsedMinutes == 1) {
                textView.setText(String.format("%s %s", String.valueOf(elapsedMinutes), mContext.getResources().getString(R.string.minute_ago)));
            } else {
                textView.setText(String.format("%s %s", String.valueOf(elapsedMinutes), mContext.getResources().getString(R.string.minutes_ago)));
            }
        } else if (elapsedSeconds != 0) {
            if (elapsedSeconds == 1) {
                textView.setText(String.format("%s %s", String.valueOf(elapsedSeconds), mContext.getResources().getString(R.string.second_ago)));
            } else {
                textView.setText(String.format("%s %s", String.valueOf(elapsedSeconds), mContext.getResources().getString(R.string.seconds_ago)));
            }

        }
    }


    public static void openZoomImageDialog(Context mContext,String image) {
        Dialog zoomImageDialog = new Dialog(mContext, R.style.MyAppTheme);
        zoomImageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        zoomImageDialog.setContentView(R.layout.dialog_zoom_image_view);



        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.copyFrom(zoomImageDialog.getWindow().getAttributes());
        windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        zoomImageDialog.getWindow().setAttributes(windowParams);

        PhotoView mphoto_view = zoomImageDialog.findViewById(R.id.img_enlarge);

        ImageView iv_back = zoomImageDialog.findViewById(R.id.iv_back);

        Glide.with(mContext).load(image).into(mphoto_view);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageDialog.dismiss();
            }
        });

        zoomImageDialog.getWindow().setGravity(Gravity.CENTER);
        zoomImageDialog.show();
    }


    public static String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


}
