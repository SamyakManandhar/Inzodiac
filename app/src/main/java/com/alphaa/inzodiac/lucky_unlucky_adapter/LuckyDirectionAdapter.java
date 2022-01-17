package com.alphaa.inzodiac.lucky_unlucky_adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;

import java.util.HashMap;
import java.util.List;


public class LuckyDirectionAdapter extends  RecyclerView.Adapter<LuckyDirectionAdapter.Viewhlder> {

    String TAG = getClass().getSimpleName();
    Context context;
    List<String>list;
    Listener2 listener2;

    HashMap<String, Integer> hmap;

    public interface Listener2{
        void getpos(int pos);
    }
    @NonNull
    @Override
    public Viewhlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_lucky_direction, parent, false);
        return new Viewhlder(view);
    }

    public LuckyDirectionAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        //this.listener2 = listener2;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhlder holder, int position) {

        Log.e(TAG, "onBindViewHolder: "+ list.get(position));
        holder.tv_direction.setText(list.get(position));

       directionIconData(list.get(position));
        //Log.e(TAG, "onBindViewHolder: index "+index );


        try {
            holder.iv_direction.setBackgroundResource(hmap.get(list.get(position)));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Viewhlder extends RecyclerView.ViewHolder {

        TextView tv_direction;
        ImageView iv_direction;

        public Viewhlder(@NonNull View itemView) {
            super(itemView);
            tv_direction = itemView.findViewById(R.id.tv_direction);
            iv_direction=  itemView.findViewById(R.id.iv_direction);

        }
    }


    public void directionIconData(String direction){

        //int drawableint;

        hmap = new HashMap<String, Integer>();
        hmap.put("North", R.drawable.ic_arrow_north);
        hmap.put("South",  R.drawable.ic_arrow_south);
        hmap.put("East",  R.drawable.ic_arrow_east);
        hmap.put("West",  R.drawable.ic_arrow_west);

        hmap.put("Southwest",  R.drawable.ic_arrow_southwest);
        hmap.put("Northwest",  R.drawable.ic_arrow_northwest);
        hmap.put("Northeast",  R.drawable.ic_arrow_northeast);
        hmap.put("Southeast",  R.drawable.ic_arrow_southwest);



        // drawableint =  hmap.get(direction);

        //Log.e(TAG, "directionIconData: drawable "+drawableint );


       // List<String> indexes = new ArrayList<String>(hmap.keySet()); // <== Set to List

       // drawableint = indexes.indexOf(direction);

       // return drawableint;
    }

}