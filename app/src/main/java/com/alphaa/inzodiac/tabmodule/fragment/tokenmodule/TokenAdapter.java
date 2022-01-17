package com.alphaa.inzodiac.tabmodule.fragment.tokenmodule;

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

public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.MyViewHolder> {
    String TAG = this.getClass().getSimpleName();
    Context context;
    ArrayList<TokenModel.Datum> list;
    private int selectedPos = 0;
    onClick onclick;

    public TokenAdapter(int position, Context context, ArrayList<TokenModel.Datum> list, onClick onclick) {
        this.context = context;
        this.list = list;
        this.selectedPos = position;
        this.onclick = onclick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.token_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TokenModel.Datum datum = list.get(position);
        holder.tokenname.setText(datum.getTokenName());
        holder.tv_tokenprice.setText(datum.getPrice());

        if(selectedPos == position){
            holder.tv_pricesymbol.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_tokenprice.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_token.setTextColor(context.getResources().getColor(R.color.black));
            holder.tokenname.setTextColor(context.getResources().getColor(R.color.black));
            holder.ll_whole.setBackground(context.getResources().getDrawable(R.drawable.ic_half_circle__blue_token));
        }
        else{
            holder.tv_pricesymbol.setTextColor(context.getResources().getColor(R.color.colorGray1));
            holder.tv_tokenprice.setTextColor(context.getResources().getColor(R.color.colorGray1));
            holder.tv_token.setTextColor(context.getResources().getColor(R.color.colorGray1));
            holder.tokenname.setTextColor(context.getResources().getColor(R.color.colorGray1));
            holder.ll_whole.setBackground(context.getResources().getDrawable(R.drawable.ic_half_circle__white_token));
        }
    }

    public interface onClick {
        void onEvent(View view, int pos);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tokenprice,tv_pricesymbol, tokenname,tv_token;
        LinearLayout ll_whole;
        public MyViewHolder(View view) {
            super(view);
            tokenname = itemView.findViewById(R.id.tokenname);
            tv_tokenprice = itemView.findViewById(R.id.tv_tokenprice);
            ll_whole = itemView.findViewById(R.id.ll_whole);
            tv_token = itemView.findViewById(R.id.tv_token);
            tv_pricesymbol = itemView.findViewById(R.id.tv_pricesymbol);


            ll_whole.setOnClickListener(new View.OnClickListener() {
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
}