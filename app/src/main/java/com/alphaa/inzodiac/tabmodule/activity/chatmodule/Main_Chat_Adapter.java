package com.alphaa.inzodiac.tabmodule.activity.chatmodule;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main_Chat_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Chat> chatList;
    private String myUId;
    private AdapterPositionListener listener;
    private String time = "";

    String otherimagepath;

    public Main_Chat_Adapter(Context mContext, ArrayList<Chat> chatList, String myUId, AdapterPositionListener listener) {
        this.mContext = mContext;
        this.chatList = chatList;
        this.myUId = myUId;
        this.listener = listener;


        Log.e("TAG", "Main_Chat_Adapter: size "+chatList.size() );

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view;

        //Log.e("TAG", "onCreateViewHolder:  size "+chatList.size() );
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_right_side_view, parent, false);
                holder = new MyChatViewHolder(view);
                return holder;

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_left_side_view, parent, false);
                holder = new OtherChatViewHolder(view);
                return holder;

            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_right_side_view, parent, false);
                holder = new MyChatViewHolder(view);
                return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder h, int position) {
        //  listener.getInterest(chatList.get(position).banner_date);

        int pos = position - 1;
        int tempPos = (pos == -1) ? pos + 1 : pos;
        Chat chat = chatList.get(position);

        if (myUId.equals(chatList.get(position).uid)) {
            ((MyChatViewHolder) h).myBindData(chat, position, tempPos);
        } else {
            ((OtherChatViewHolder) h).otherBindData(chat, position, tempPos);
        }

        listener.getPosition(position);

    }

    @Override
    public int getItemViewType(int position) {
        if (myUId.equals(chatList.get(position).uid)) {
            return 0;
        } else {
            return 1;
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public interface AdapterPositionListener {
        void getPosition(int position);
    }

    public class MyChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // My Chat Text Msg
        TextView right_tvtime, right_tvmsg;
        RelativeLayout right_rlimage;
        ImageView right_ivimage;
        ProgressBar right_progress;

        CircleImageView right_ivuserimage;

        MyChatViewHolder(View itemView) {
            super(itemView);
            right_tvtime = itemView.findViewById(R.id.right_tvtime);
            right_tvmsg = itemView.findViewById(R.id.right_tvmsg);
            right_rlimage = itemView.findViewById(R.id.right_rlimage);
            right_ivimage = itemView.findViewById(R.id.right_ivimage);
            right_progress = itemView.findViewById(R.id.right_progress);
            right_ivuserimage = itemView.findViewById(R.id.right_ivuserimage);
            right_ivimage.setOnClickListener(this);

        }

        void myBindData(Chat chat, int pos, int tempPos) {

            Log.e("TAG", AppSession.getStringPreferences(mContext, Constants.USERIMAGE)+" myBindData: right image "+chat.getIsImage() );

            Glide.with(mContext)
                                .load(AppSession.getStringPreferences(mContext, Constants.USERIMAGE))
                                //.placeholder(R.drawable.placeholder)
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .dontTransform()
                                .into(right_ivuserimage);

            //right_ivimage.setBackgroundResource(R.drawable.ic_check);

            right_ivimage.setVisibility(View.VISIBLE);
            if (chatList.get(pos).getIsImage() == 1) {
                right_progress.setVisibility(View.GONE);
                Glide.with(mContext).load(chat.imageUrl).apply(new RequestOptions().placeholder(R.drawable.back)).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                        right_progress.setVisibility(View.GONE);
                        return false;

                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        right_progress.setVisibility(View.GONE);
                        return false;
                    }
                }).into(right_ivimage);
                right_tvtime.setText(Utility.SimpleTimeFormat(chat.getTimestamp()));
                right_tvmsg.setVisibility(View.GONE);
                right_rlimage.setVisibility(View.VISIBLE);
            } else if (chat.getIsImage() == 0) {
                right_tvmsg.setVisibility(View.VISIBLE);
                right_tvmsg.setText(chat.message);
                //right_rlimage.setVisibility(View.GONE);
                right_tvtime.setText(Utility.SimpleTimeFormat(chat.getTimestamp()));

            }


            right_tvtime.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.right_ivimage: {
                    //Utility.openZoomImageDialog(mContext, chatList.get(getAdapterPosition()).imageUrl);
                }
            }
        }
    }

    public class OtherChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView left_tvmsg, left_tvtime;
        ImageView left_ivimage;
        RelativeLayout left_rlimage;
        ProgressBar left_progress;
        CircleImageView left_ivuserimage;

        OtherChatViewHolder(View itemView) {
            super(itemView);
            left_tvmsg = itemView.findViewById(R.id.left_tvmsg);
            left_tvtime = itemView.findViewById(R.id.left_tvtime);
            left_ivimage = itemView.findViewById(R.id.left_ivimage);
            left_ivuserimage = itemView.findViewById(R.id.left_ivuserimage);
            left_ivuserimage = itemView.findViewById(R.id.left_ivuserimage);
            left_rlimage = itemView.findViewById(R.id.left_rlimage);
            left_progress = itemView.findViewById(R.id.left_progress);
            left_ivuserimage = itemView.findViewById(R.id.left_ivuserimage);
            left_ivimage.setOnClickListener(this);
        }

        void otherBindData(Chat chat, int pos, int tempPos) {

            Log.e("TAG", otherimagepath+"  otherBindData: left side user id "+chat.getUid() );


            //if (chatList!=null&&chatList.size()!=0) {
//                String otheruid = chatList.get(0).uid;
            String otheruid = chat.getUid();

                Log.e("TAG", "Main_Chat_Adapter: otheruid " + otheruid);

                FirebaseDatabase firebaseDatabase;
                DatabaseReference databaseReference;

                firebaseDatabase = FirebaseDatabase.getInstance();

                databaseReference = firebaseDatabase.getReference("users").child(otheruid);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //String value = snapshot.getValue(String.class);
                        //Log.e("TAG", "onDataChange: left image " + snapshot.getValue());
                        Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();

                        Log.e("TAG", "onDataChange: left " + newPost.get("profilePic"));

                        otherimagepath = String.valueOf(newPost.get("profilePic"));

                        try {
                            Glide.with(mContext)
                                    .load(otherimagepath)
                                    //.placeholder(R.drawable.placeholder)
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .dontTransform()
                                    .into(left_ivuserimage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            //}





            //left_ivuserimage.setVisibility(View.VISIBLE);



            if (chatList.get(pos).getIsImage() == 1) {
                left_progress.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(chat.imageUrl).apply(new RequestOptions().placeholder(R.drawable.back)).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                        left_progress.setVisibility(View.GONE);
                        return false;

                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        left_progress.setVisibility(View.GONE);

                        return false;
                    }
                }).into(left_ivimage);

                left_tvtime.setVisibility(View.VISIBLE);
                left_tvtime.setText(Utility.SimpleTimeFormat(chat.getTimestamp()));
                left_ivimage.setVisibility(View.VISIBLE);
                left_tvmsg.setVisibility(View.GONE);
                left_rlimage.setVisibility(View.VISIBLE);
            } else if (chat.getIsImage() == 0) {
                //gone
                left_tvmsg.setText(chat.getMessage());
                left_tvmsg.setVisibility(View.VISIBLE);
                left_rlimage.setVisibility(View.GONE);
                left_tvtime.setText(Utility.SimpleTimeFormat(chat.getTimestamp()));
            }

            left_tvtime.setVisibility(View.INVISIBLE);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_ivimage: {
                    //Utility.openZoomImageDialog(mContext, chatList.get(getAdapterPosition()).imageUrl);
                }
            }
        }
    }
}
