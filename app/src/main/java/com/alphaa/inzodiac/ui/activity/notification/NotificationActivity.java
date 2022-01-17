package com.alphaa.inzodiac.ui.activity.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.SendGameRequest;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.SendQuizRequest;
import com.alphaa.inzodiac.ui.activity.likerequest.LikeRequestActivityNew;
import com.alphaa.inzodiac.ui.activity.notification.notificationmodel.NotificationDatum;
import com.alphaa.inzodiac.ui.activity.notification.notificationmodel.NotificationModel;
import com.alphaa.inzodiac.ui.activity.quizmodule.QuizAcceptRegectActivity;
import com.alphaa.inzodiac.ui.activity.quizmodule.QuizInstructionActivity;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    String TAG = this.getClass().getSimpleName();
    List<NotificationDatum> notificationList;
    RecyclerView rv_notification;
    ProgressBar progressbar;
    TextView tv_notfound;

    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //TextView header1 = findViewById(R.id.header1);
        //header1.setText(getIntent().getStringExtra("name"));

        uid = AppSession.getStringPreferences(getApplicationContext(), Constants.USEREId);
        Log.e(TAG, "onCreate: uid "+uid );
        tv_notfound = findViewById(R.id.tv_notfound);
        rv_notification = findViewById(R.id.rv_notification);
        progressbar = findViewById(R.id.progressbar);

        getNotification(uid);
    }




    public void getNotification(String id) {

        progressbar.setVisibility(View.VISIBLE);

        Call<NotificationModel> call = RetrofitClient.getInstance()
                .getApi().getNotification(id);
        call.enqueue(new Callback<NotificationModel>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<NotificationModel> call, @NonNull retrofit2.Response<NotificationModel> response) {


                Log.e(TAG, "  onResponse: "+new Gson().toJson(response.body()));

                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    NotificationModel notificationModel = response.body();

                    if (notificationModel.getStatus()){
                        notificationList = notificationModel.getData();
                        Log.e(TAG, "onResponse: notificationlist "+notificationList.size() );
                        Collections.reverse(notificationList);
                        NotificationAdapter searchAdapter = new NotificationAdapter(getApplicationContext(), notificationList);


                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        rv_notification.setLayoutManager(layoutManager);


                        rv_notification.setAdapter(searchAdapter);
                        tv_notfound.setVisibility(View.GONE);
                    }else {
                        tv_notfound.setVisibility(View.VISIBLE);
                    }

                }else {
                    tv_notfound.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {
                progressbar.setVisibility(View.VISIBLE);
                tv_notfound.setVisibility(View.VISIBLE);
            }
        });

    }


    public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
        public List<NotificationDatum> searchList;
        Context context;
        private LayoutInflater mInflater;

        public NotificationAdapter(Context activity, List<NotificationDatum> List) {
            searchList = List;
            context = activity;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_time, id_username, tv_status, tv_tokenno, tvRupees, tv_invoice;
            ImageView id_userpic;

            public MyViewHolder(View view) {
                super(view);
                id_username = view.findViewById(R.id.id_username);
                tv_time = view.findViewById(R.id.tv_time);
                tvRupees = view.findViewById(R.id.tvRupeesId);
                tv_invoice = view.findViewById(R.id.tv_invoiceId);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.e(TAG, "onClick: 1111111" );

                        if (notificationList.get(getAdapterPosition()).getMessage().endsWith("profile")){
                            Intent intent = new Intent(context, LikeRequestActivityNew.class);
                            //  }
                            intent.putExtra("comesfrom", "pending_intent");
                            intent.putExtra("otherName", "username");
                            intent.putExtra("otherid", notificationList.get(getAdapterPosition()).getSenderId());

                            intent.putExtra("request_id", notificationList.get(getAdapterPosition()).getTypesId());

                            startActivity(intent);
                        }else if (notificationList.get(getAdapterPosition()).getMessage().startsWith("Would".toLowerCase())){
                            Intent intent = new Intent(context, QuizAcceptRegectActivity.class);
                            Log.e(TAG, "sendNotification1: QuizAcceptRegectActivity " );
                            intent.putExtra("comesfrom", "pending_intent");
                            intent.putExtra("otherName", "username");
                            intent.putExtra("m_otheruserid", notificationList.get(getAdapterPosition()).getSenderId());
                            intent.putExtra("m_userid", notificationList.get(getAdapterPosition()).getRecepientId());

                            intent.putExtra("request_id", notificationList.get(getAdapterPosition()).getTypesId());

                            startActivity(intent);

                        }else  if (notificationList.get(getAdapterPosition()).getMessage().toLowerCase().startsWith("Please".toLowerCase())){
                            Log.e(TAG, "sendNotification1: QuizActivity " );
                            Intent intent = new Intent(context, QuizInstructionActivity.class);
                            startActivity(intent);
                        }

                    }
                });
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final NotificationDatum detail = searchList.get(position);

          String date =  getDate(Long.parseLong(detail.getCreateDt()), "dd/MM/yyyy hh:mm");

            Log.e(TAG, "onBindViewHolder: date "+date );
            holder.id_username.setText(detail.getMessage());
            holder.tv_time.setText(date);
            //holder.tvRupees.setText("Your Invoice Number is " + detail.getInvoiceId());
        }

        @Override
        public int getItemCount() {
            return searchList.size();
        }
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}