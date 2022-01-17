package com.alphaa.inzodiac.tabmodule.fragment.westernmodule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class WesternAdapter extends  RecyclerView.Adapter<WesternAdapter.Viewhlder> {

    Context context;
    ArrayList<WesternModel.Datum> list;
    WesternListener westernListener;

    interface WesternListener{
        public void getpos(int pos);
    }
    @NonNull
    @Override
    public Viewhlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.westernchines_layout, parent, false);
        return new Viewhlder(view);
    }

    public WesternAdapter(Context context, ArrayList<WesternModel.Datum> list,WesternListener westernListener) {
        this.context = context;
        this.list = list;
        this.westernListener = westernListener;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull Viewhlder holder, int position) {
        WesternModel.Datum dataItem = list.get(position);
        holder.txt.setText(dataItem.getName());
        Glide.with(context).load(dataItem.getImage()).into(holder.img);
        if (dataItem.statusbool) {
            holder.txt.setTextColor(context.getResources().getColor(R.color.white));
            holder.img.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white));
            holder.ll.setBackground(context.getResources().getDrawable(R.drawable.purple_btn_bg));

        } else {
            holder.txt.setTextColor(context.getResources().getColor(R.color.colorGray1));
            holder.img.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPurple));
            holder.ll.setBackground(null);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Viewhlder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;
        LinearLayout ll;
        public Viewhlder(@NonNull View itemView) {
            super(itemView);

            img= itemView.findViewById(R.id.img);
            txt= itemView.findViewById(R.id.txt);
            ll= itemView.findViewById(R.id.ll);

            itemView.setOnClickListener(v -> {
                /*Intent intent = new Intent(context, TabActivity.class);
                context.startActivity(intent);*/


                if (getAdapterPosition() != -1) {

                    WesternModel.Datum dataItem = list.get(getAdapterPosition());
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).statusbool = false;
                    }
                    westernListener.getpos(getAdapterPosition());
                    dataItem.statusbool = true;
                    notifyDataSetChanged();


                }



              /*  MenuFragment menuFragment = MenuFragment.newInstance(dataItem.getId(), "");


                try {
                    FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                    String fragmentName = menuFragment.getClass().getName();
                    fm.beginTransaction().replace(R.id.frame, menuFragment, fragmentName).commit();
                    //if (addToBackStack) stackSet.push(fragment);
                    //hideKeyboard();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }*/
            });

        }
    }


}