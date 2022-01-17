package com.alphaa.inzodiac.ui.activity.detailaboyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.BodyModel;

import java.util.ArrayList;


public class BodyAdapter extends  RecyclerView.Adapter<BodyAdapter.Viewhlder> {

    Context context;
    ArrayList<BodyModel>list;
    Listener2 listener2;
    public interface Listener2{
        void getpos(int pos);
    }
    @NonNull
    @Override
    public Viewhlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.ethnicity_layout, parent, false);
        return new Viewhlder(view);
    }

    public BodyAdapter(Context context, ArrayList<BodyModel> list,Listener2 listener2) {
        this.context = context;
        this.list = list;
        this.listener2 = listener2;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhlder holder, int position) {
        BodyModel s = list.get(position);
        holder.tvname.setText(s.name);
        if (s.status) {
            holder.tvname.setTextColor(context.getResources().getColor(R.color.white));
            holder.rl_back.setBackground(context.getResources().getDrawable(R.drawable.purple_btn_bg));

        } else {
            holder.tvname.setTextColor(context.getResources().getColor(R.color.black));
            holder.rl_back.setBackground(null);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Viewhlder extends RecyclerView.ViewHolder {

        TextView tvname;
        RelativeLayout rl_back;
        public Viewhlder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tvname);
            rl_back = itemView.findViewById(R.id.rl_back);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getAdapterPosition() != -1) {

                        BodyModel s = list.get(getAdapterPosition());
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).status = false;
                        }
                        listener2.getpos(getAdapterPosition());
                        s.status = true;
                        notifyDataSetChanged();


                    }


                }
            });


        }
    }


}