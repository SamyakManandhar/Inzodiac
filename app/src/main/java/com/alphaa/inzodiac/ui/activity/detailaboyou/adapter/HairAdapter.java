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
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.HairModel;

import java.util.ArrayList;


public class HairAdapter extends  RecyclerView.Adapter<HairAdapter.Viewhlder> {

    Context context;
    ArrayList<HairModel>list;
    Listener3 listener3;
    public interface Listener3{
        void getpos(int pos);
    }
    @NonNull
    @Override
    public Viewhlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.ethnicity_layout, parent, false);
        return new Viewhlder(view);
    }

    public HairAdapter(Context context, ArrayList<HairModel> list,Listener3 listener3) {
        this.context = context;
        this.list = list;
        this.listener3 = listener3;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhlder holder, int position) {
        HairModel s = list.get(position);
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

                        HairModel s = list.get(getAdapterPosition());
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).status = false;
                        }
                        s.status = true;
                        listener3.getpos(getAdapterPosition());
                        notifyDataSetChanged();


                    }


                }
            });


        }
    }


}