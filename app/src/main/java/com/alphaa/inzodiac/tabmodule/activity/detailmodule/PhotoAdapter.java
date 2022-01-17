package com.alphaa.inzodiac.tabmodule.activity.detailmodule;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

import static com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient.IMAGE_BASE_URL;


public class PhotoAdapter extends  RecyclerView.Adapter<PhotoAdapter.Viewhlder> {

    Context context;
    ArrayList<DetailModel.ProfilePic> list;
    @NonNull
    @Override
    public Viewhlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.photo_layout, parent, false);
        return new Viewhlder(view);
    }

    public PhotoAdapter(Context context,ArrayList<DetailModel.ProfilePic> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhlder holder, int position) {

        DetailModel.ProfilePic pic = list.get(position);
        Glide.with(context).load(IMAGE_BASE_URL+pic.getImage()).into(holder.img1);
       /* if (position == 2)
        {
            holder.rl_img.setVisibility(View.VISIBLE);
            holder.img1.setVisibility(View.GONE);
        }
        else {
            holder.rl_img.setVisibility(View.GONE);
            holder.img1.setVisibility(View.VISIBLE);

        }*/


        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openZoomImageDialog(context,IMAGE_BASE_URL+pic.getImage());

            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
    public class Viewhlder extends RecyclerView.ViewHolder {
        RelativeLayout rl_img;
        ImageView img1;
        public Viewhlder(@NonNull View itemView) {
            super(itemView);
            rl_img = itemView.findViewById(R.id.rl_img);
            img1 = itemView.findViewById(R.id.img1);

        }
    }




    public static void openZoomImageDialog(Context mContext,String image) {
        Dialog zoomImageDialog = new Dialog(mContext, R.style.MyAppTheme);
        zoomImageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        zoomImageDialog.setContentView(R.layout.dialog_zoom_image_view);



        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.copyFrom(zoomImageDialog.getWindow().getAttributes());
        windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
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



}