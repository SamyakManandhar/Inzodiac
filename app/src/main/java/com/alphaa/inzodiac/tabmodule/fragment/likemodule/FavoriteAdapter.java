package com.alphaa.inzodiac.tabmodule.fragment.likemodule;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.tabmodule.activity.chatmodule.ChatActivity;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailActivity;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailPresenter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class FavoriteAdapter extends  RecyclerView.Adapter<FavoriteAdapter.Viewhlder> {

    String TAG = this.getClass().getSimpleName();
    Context context;
    ArrayList<FavoriteModel.Datum> list;

    OnClick onClick;
    interface  OnClick{
        void dislikeClick(String otheruserid);
    }
    @NonNull
    @Override
    public Viewhlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.zodiac_layoutwithdislike, parent, false);
        return new Viewhlder(view);
    }

    public FavoriteAdapter(Context context, ArrayList<FavoriteModel.Datum> list,OnClick onClick) {
        this.context = context;
        this.list = list;
        this.onClick= onClick;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhlder holder, int position) {
        FavoriteModel.Datum  datum = list.get(position);
        String age = datum.getAge();
        if (age.isEmpty()){
            holder.tvname.setText(datum.getName());
        }else {
            holder.tvname.setText(datum.getName()+" , "+age);
        }
        holder.tv_address.setText(datum.getCountry());
        if (datum.getProfilePic().size() != 0) {
            if (!datum.getProfilePic().get(0).getImage().isEmpty()) {
                Glide.with(context).load(datum.getImageUrl() + datum.getProfilePic().get(0).getImage()).into(holder.img_user);
            }
        }
        holder.img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: image" );
                FavoriteModel.Datum  datum = list.get(position);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("uid",datum.getId());
                intent.putExtra("comesfrom","favourite");
                context.startActivity(intent);
            }
        });
         holder.iv_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: chat" );

                //getPrefHelper().setotherid(otherid);
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("otherid",list.get(position).getId());
                intent.putExtra("otherName",list.get(position).getName());
                intent.putExtra("otherProfile",list.get(position).getProfilePic().get(0).getImage());
                context.startActivity(intent);
                //finish();
            }
        });
         holder.iv_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: dislike" );
               onClick.dislikeClick(list.get(position).getId());

            }
        });



    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Viewhlder extends RecyclerView.ViewHolder {
        TextView tvname,tv_address;
        ImageView img_user,iv_ch,iv_dislike;
        public Viewhlder(@NonNull View itemView) {
            super(itemView);
            iv_ch = itemView.findViewById(R.id.iv_ch);
            iv_dislike = itemView.findViewById(R.id.iv_dislike);
            tvname = itemView.findViewById(R.id.tvname);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_address = itemView.findViewById(R.id.tv_address);
            img_user = itemView.findViewById(R.id.img_user);
            /*itemView.setOnClickListener(v -> {
                FavoriteModel.Datum  datum = list.get(getAdapterPosition());
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("uid",datum.getId());
                intent.putExtra("comesfrom","favourite");
                context.startActivity(intent);
            });*/

        }
    }


}