package com.alphaa.inzodiac.ui.activity.aboutself;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.gallery.models.Image;
import com.bumptech.glide.Glide;

import java.util.List;


public class ImageAdapter extends  RecyclerView.Adapter<ImageAdapter.Viewhlder> {

    String TAG = this.getClass().getSimpleName();
    Context context;
    List<Image> list;
    @NonNull
    @Override
    public Viewhlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.multiimage_layout, parent, false);
        return new Viewhlder(view);
    }

    public ImageAdapter(Context context, List<Image> list) {
        this.context = context;
        this.list = list;


    }

    @Override
    public void onBindViewHolder(@NonNull Viewhlder holder, int position) {

        Log.e(TAG, "onBindViewHolder: "+list.get(position).path );
        Glide.with(context).load(list.get(position).path).into(holder.ivimage);
    }


    @Override
    public int getItemCount() {
        Log.e(TAG, "ImageAdapter: listsize "+list.size() );
        return list.size();
    }


    public class Viewhlder extends RecyclerView.ViewHolder {
        ImageView ivimage;
        public Viewhlder(@NonNull View itemView) {
            super(itemView);
            ivimage = itemView.findViewById(R.id.ivimage);
        }
    }


}