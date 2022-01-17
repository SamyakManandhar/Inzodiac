package com.alphaa.inzodiac.tabmodule.fragment.updateprmodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;

import java.util.ArrayList;

public class UpdatePremAdapter extends RecyclerView.Adapter<UpdatePremAdapter.MyViewHolder> {
    String TAG = this.getClass().getSimpleName();
    Context context;
    ArrayList<PlanData.Datum> list;
    private int selectedPos = -1;
    onClick onclick;

    public UpdatePremAdapter(Context context, ArrayList<PlanData.Datum> list, onClick onclick) {
        this.context = context;
        this.list = list;
        this.onclick = onclick;
    }

    public interface onClick {
        void onEvent(View view, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_duration,tv_payment,tv_paymentsymbol_static,tv_perweek,tv_static_perweek;
        LinearLayout ll_main,ll_leftbox,ll_mostpopular;
        public MyViewHolder(View view) {
            super(view);
            tv_duration = itemView.findViewById(R.id.tv_duration);
            tv_payment = itemView.findViewById(R.id.tv_payment);
            tv_perweek = itemView.findViewById(R.id.tv_perweek);
            ll_main = itemView.findViewById(R.id.ll_main);
            ll_leftbox = itemView.findViewById(R.id.ll_leftbox);
            tv_static_perweek = itemView.findViewById(R.id.tv_static_perweek);
            tv_paymentsymbol_static = itemView.findViewById(R.id.tv_paymentsymbol_static);
            ll_mostpopular = itemView.findViewById(R.id.ll_mostpopular);

            ll_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        notifyItemChanged(selectedPos);
                        selectedPos = getAdapterPosition();
                        onclick.onEvent(v, position);
                        notifyItemChanged(selectedPos);
                    }
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_premium_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlanData.Datum  datum = list.get(position);
        holder.tv_duration.setText(datum.getName());
        holder.tv_perweek.setText(datum.getPer_week_price());
        holder.tv_payment.setText("Payment Of $"+datum.getPrice());

        if (datum.getName().toLowerCase().contains("lifetime")){
            holder.tv_perweek.setText(datum.getPrice());
            holder.tv_static_perweek.setText("Lifetime");
        }


        if (datum.getName().toLowerCase().contains("1 day")){
            holder.tv_perweek.setText(datum.getPrice());
            holder.tv_paymentsymbol_static.setText("P");
            holder.tv_static_perweek.setText("1 Day");
            holder.tv_payment.setText("Payment Of P"+datum.getPrice());
        }

        if (datum.getName().toLowerCase().contains("1 week")){
            holder.tv_perweek.setText(datum.getPrice());
            holder.tv_static_perweek.setText("1 week");
        }

//       isplan =  1=active ,0=deactive

        if(datum.getIs_plan().equals("1")){
            holder.ll_main.setBackground(context.getResources().getDrawable(R.drawable.all_cornor_purple_back));
        }
        else{
            holder.ll_main.setBackground(context.getResources().getDrawable(R.drawable.all_cornor_white_back));
        }


        if(selectedPos == position){
//            holder.ll_mostpopular.setVisibility(View.VISIBLE);
            holder.tv_duration.setTextColor(context.getResources().getColor(R.color.black));
            holder.tv_perweek.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_paymentsymbol_static.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_static_perweek.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_payment.setTextColor(context.getResources().getColor(R.color.black));
            holder.ll_leftbox.setBackground(context.getResources().getDrawable(R.drawable.purle_left_cornor_back));
        }
        else{
//            holder.ll_mostpopular.setVisibility(View.GONE);
            holder.tv_duration.setTextColor(context.getResources().getColor(R.color.colorGray1));
            holder.tv_perweek.setTextColor(context.getResources().getColor(R.color.colorGray1));
            holder.tv_paymentsymbol_static.setTextColor(context.getResources().getColor(R.color.colorGray1));
            holder.tv_static_perweek.setTextColor(context.getResources().getColor(R.color.colorGray1));
            holder.tv_payment.setTextColor(context.getResources().getColor(R.color.colorGray1));
            holder.ll_leftbox.setBackground(context.getResources().getDrawable(R.drawable.white_left_cornor_back));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}