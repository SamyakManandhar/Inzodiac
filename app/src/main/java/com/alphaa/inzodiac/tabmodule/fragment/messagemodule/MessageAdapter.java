package com.alphaa.inzodiac.tabmodule.fragment.messagemodule;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.app.prefs.PrefHelper;
import com.alphaa.inzodiac.tabmodule.activity.chatmodule.Chat;
import com.alphaa.inzodiac.tabmodule.activity.chatmodule.ChatActivity;
import com.alphaa.inzodiac.ui.activity.signupmodule.UserdatailModel;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.alphaa.inzodiac.utility.Utility.printDifference;
import static com.facebook.FacebookSdk.getApplicationContext;


public class MessageAdapter extends  RecyclerView.Adapter<MessageAdapter.Viewhlder> {

    String TAG = this.getClass().getSimpleName();
    Context context;
    List<Chat> chatList;
    @NonNull
    @Override
    public Viewhlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.message_layout, parent, false);
        return new Viewhlder(view);
    }

    public MessageAdapter(Context context,List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhlder holder, int position) {
        Chat chat = chatList.get(position);

        Log.e(TAG, chat.getUid()+"  onBindViewHolder: subscr "+chat.getSubscription() );

        holder.tv_username.setText(chat.getName());
        if (chat.getIsImage() == 1) {
            holder.tv_msg.setText("Image");
        } else {
            holder.tv_msg.setText(chat.getMessage());
        }

        if (chat.onoffstatus.equals("online")){
            holder.onoffstatus.setVisibility(View.VISIBLE);
        }else {
            holder.onoffstatus.setVisibility(View.GONE);

        }
        holder.onoffstatus.setVisibility(View.GONE);
        Log.e(TAG, "onBindViewHolder: image url "+chat.getImageUrl() );

        if (chat.getImageUrl()!=null&& !chat.getImageUrl().isEmpty()) {
            Glide.with(context).load(chat.getImageUrl()).into(holder.img);
        }

        try {
            Date date = new Date((Long) chat.getTimestamp());
            Date cur_date = Calendar.getInstance().getTime();
            printDifference(context, date, cur_date, holder.tvtime);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return chatList.size();
    }


    public class Viewhlder extends RecyclerView.ViewHolder {
        TextView tv_username,tv_msg,tvtime;
        ImageView onoffstatus;

        RoundedImageView img;
        public Viewhlder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_msg = itemView.findViewById(R.id.tv_msg);
            tvtime = itemView.findViewById(R.id.tvtime);
            onoffstatus = itemView.findViewById(R.id.onoffstatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Chat chat = chatList.get(getAdapterPosition());


                    Log.e(TAG, AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE)+"  onClick: "+chat.getSubscription() );

                    //get subscription

                    /*if (AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE)==null&&chat.getSubscription().toLowerCase().matches("yes")){
                    Toast.makeText(context, "Please Activate Premium Subscription", Toast.LENGTH_SHORT).show();

                    return;
                }

                    else if (AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE).isEmpty()&&chat.getSubscription().toLowerCase().matches("yes")){
                        Toast.makeText(context, "Please Activate Premium Subscription", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    else if (AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE).toLowerCase().matches("no")&&chat.getSubscription().toLowerCase().matches("yes")){
                        Toast.makeText(context, "Please Activate Premium Subscription", Toast.LENGTH_SHORT).show();

                        return;
                    }*/


                   PrefHelper prefHelper = new PrefHelper(context);
                   prefHelper.setotherid(chat.uid);
                    Intent  intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("otherid",chat.getUid());
                    intent.putExtra("otherName",chat.getName());
                    intent.putExtra("otherProfile",chat.getProfilePic());
                    context.startActivity(intent);
                    chatMassage(getAdapterPosition());
                }
            });

        }
    }

    private void chatMassage(int pos) {
        Chat chat = chatList.get(pos);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String device_token = FirebaseInstanceId.getInstance().getToken();
        String firebaseToken = FirebaseInstanceId.getInstance().getToken();
        PrefHelper prefHelper = new PrefHelper(context);
        UserdatailModel userdatailModel = new Gson().fromJson(prefHelper.getUserDataModel(), UserdatailModel.class);
        String userName = userdatailModel.getData().getName();
        String userid = userdatailModel.getData().getId();

        // user chat
        Chat mychat = new Chat();
        mychat.firebaseToken = device_token;
        mychat.message = chat.message;
        mychat.name = userName;
        mychat.isImage = chat.isImage;
        mychat.timestamp = chat.timestamp;
        mychat.uid = userid;
        mychat.lastMsg = "";
        mychat.firebaseToken = firebaseToken;
        mychat.readstatus = true;


        mychat.onoffstatus = "online";

        //other Userchat
        Chat otherchat = new Chat();
        otherchat.firebaseToken = device_token;
        otherchat.message = chat.message;
        otherchat.name = userName;
        otherchat.isImage = chat.isImage;
        otherchat.timestamp = chat.timestamp;
        otherchat.uid = chat.uid;
        otherchat.lastMsg = "";
        otherchat.firebaseToken = firebaseToken;
        otherchat.readstatus = false;
        otherchat.onoffstatus = "online";


        database.child(Constants.CHAT_HISTORY_TABLE).child(userid).child(chat.uid).setValue(otherchat);


    }




   /* private void onlineOfflinStatus() {
        PrefHelper prefHelper = new PrefHelper(context);
        UserdatailModel userdatailModel = new Gson().fromJson(prefHelper.getUserDataModel(), UserdatailModel.class);
        String userName = userdatailModel.getData().getName();
        String userid = userdatailModel.getData().getId();
        String useremail = userdatailModel.getData().getEmail();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseUserModel userModel = new FirebaseUserModel();
        userModel.name = userName;
        userModel.email =useremail;
        userModel.profilePic = "";
        userModel.uid = userid;
        userModel.readstatus = false;
        userModel.onoffstatus = "online";
        database.child(Constants.USER_TABLE).child(userid).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }*/


}