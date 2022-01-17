package com.alphaa.inzodiac.tabmodule.fragment.menumodule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class MenuAdapter extends  RecyclerView.Adapter<MenuAdapter.Viewhlder> {

    Context context;
    ArrayList<UserMenuModel.Datum> list;
    @NonNull
    @Override
    public Viewhlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.zodiac_layout, parent, false);
        return new Viewhlder(view);
    }

    public MenuAdapter(Context context, ArrayList<UserMenuModel.Datum> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhlder holder, int position) {
        UserMenuModel.Datum  datum = list.get(position);
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

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Viewhlder extends RecyclerView.ViewHolder {
        TextView tvname,tv_address;
        ImageView img_user;
        public Viewhlder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tvname);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_address = itemView.findViewById(R.id.tv_address);
            img_user = itemView.findViewById(R.id.img_user);
            itemView.setOnClickListener(v -> {
                UserMenuModel.Datum  datum = list.get(getAdapterPosition());
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("uid",datum.getId());
//                intent.putExtra("comesfrom","menufragment");
                context.startActivity(intent);
            });

        }
    }


}