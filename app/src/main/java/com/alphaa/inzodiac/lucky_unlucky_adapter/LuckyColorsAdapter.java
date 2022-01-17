package com.alphaa.inzodiac.lucky_unlucky_adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;

import java.util.HashMap;
import java.util.List;


public class LuckyColorsAdapter extends  RecyclerView.Adapter<LuckyColorsAdapter.Viewhlder> {

    String TAG = getClass().getSimpleName();
    Context context;
    List<String>list;
    Listener2 listener2;
    public interface Listener2{
        void getpos(int pos);
    }
    @NonNull
    @Override
    public Viewhlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_lucky_color, parent, false);
        return new Viewhlder(view);
    }

    public LuckyColorsAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        //this.listener2 = listener2;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhlder holder, int position) {

        holder.tv_color.setText(list.get(position));

       // Log.e(TAG, "onBindViewHolder: color "+list.get(position) );
        String c= colorData(list.get(position));

        Log.e(TAG, "onBindViewHolder: cccccccc  "+c );

        if (c!=null)
            holder.rl_color.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(c)));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Viewhlder extends RecyclerView.ViewHolder {

        TextView tv_color;

        RelativeLayout rl_color;

        public Viewhlder(@NonNull View itemView) {
            super(itemView);
            tv_color = itemView.findViewById(R.id.tv_color);
            rl_color = itemView.findViewById(R.id.rl_color);

        }
    }

    public String colorData(String color){

        String colorcode = "#0000ff";

        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("Blue", "#0000ff");
        hmap.put("Gold", "#ffd700");
        hmap.put("Green", "#008000");
        hmap.put("White", "#ffffff");
        hmap.put("Yellow", "#ffff00");
        hmap.put("Grey", "#808080");
        hmap.put("Orange", "#FFA500");
        hmap.put("Pink", "#ffc0cb");
        hmap.put("Red", "#ff0000");
        hmap.put("Purple", "#800080");
        hmap.put("Silver", "#c0c0c0");
        hmap.put("Greyish", "#808080");
        hmap.put("Black", "#111111");
        hmap.put("Brown", "#A52A2A");

        hmap.put("Light-Green", "#90EE90");
        hmap.put("Light-Blue", "#ADD8E6");
        hmap.put("Mauve", "#e0b0ff");
        hmap.put("Lilac", "#C8A2C8");
        hmap.put("Violet", "#EE82EE");
        hmap.put("Sea green", "#2e8b57");
        hmap.put("Violet", "#111111");
        hmap.put("Beige", "#F5F5DC");
        hmap.put("Pale-Yellow", "#FFFFBF");
        hmap.put("Scarlet", "#FF2400");
        hmap.put("Rust", "#b7410e");

        colorcode = hmap.get(color);
        //Log.e(TAG, "colorData: color "+colorcode );

        return colorcode;
    }

}