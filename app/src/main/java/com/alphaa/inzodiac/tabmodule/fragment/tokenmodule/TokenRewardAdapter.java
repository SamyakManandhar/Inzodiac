package com.alphaa.inzodiac.tabmodule.fragment.tokenmodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;

import java.util.ArrayList;

public class TokenRewardAdapter extends RecyclerView.Adapter<TokenRewardAdapter.MyViewHolder> {
    String TAG = this.getClass().getSimpleName();
    Context context;
    ArrayList<TokenModel.BanefitData> list;

    public TokenRewardAdapter(Context context, ArrayList<TokenModel.BanefitData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.token_reward_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TokenModel.BanefitData data = list.get(position);
        holder.tv_reward.setText(data.getBenifits());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_reward;
        public MyViewHolder(View view) {
            super(view);
            tv_reward = itemView.findViewById(R.id.tv_reward);

        }
    }
}