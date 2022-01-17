package com.alphaa.inzodiac.lucky_unlucky_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;

import java.util.List;


public class UnLuckyNumberAdapter extends  RecyclerView.Adapter<UnLuckyNumberAdapter.Viewhlder> {

    Context context;
    List<String>list;
    Listener2 listener2;
    public interface Listener2{
        void getpos(int pos);
    }
    @NonNull
    @Override
    public Viewhlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_unlucky_number, parent, false);
        return new Viewhlder(view);
    }

    public UnLuckyNumberAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        //this.listener2 = listener2;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhlder holder, int position) {

        holder.tv_number.setText(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Viewhlder extends RecyclerView.ViewHolder {

        TextView tv_number;

        public Viewhlder(@NonNull View itemView) {
            super(itemView);
            tv_number = itemView.findViewById(R.id.tv_number);

        }
    }


}