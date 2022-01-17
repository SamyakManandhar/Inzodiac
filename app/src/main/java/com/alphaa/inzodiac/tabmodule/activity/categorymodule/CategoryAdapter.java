package com.alphaa.inzodiac.tabmodule.activity.categorymodule;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.app.prefs.PrefHelper;
import com.alphaa.inzodiac.tabmodule.fragment.menumodule.MenuFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class CategoryAdapter extends  RecyclerView.Adapter<CategoryAdapter.Viewhlder> {

    String TAG = getClass().getSimpleName();

    Context context;
    ArrayList<CategoryResponse.DataItem> list;
    @NonNull
    @Override
    public Viewhlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.western_layout, parent, false);
        return new Viewhlder(view);
    }

    public CategoryAdapter(Context context, ArrayList<CategoryResponse.DataItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhlder holder, int position) {
        CategoryResponse.DataItem dataItem = list.get(position);
        holder.txt.setText(dataItem.getName());
        Glide.with(context).load(dataItem.getImages()).into(holder.img);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Viewhlder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;
        public Viewhlder(@NonNull View itemView) {
            super(itemView);

            img= itemView.findViewById(R.id.img);
            txt= itemView.findViewById(R.id.txt);

            itemView.setOnClickListener(v -> {


                Log.e(TAG, "Viewhlder: " );
                /*Intent intent = new Intent(context, TabActivity.class);
                context.startActivity(intent);*/
                CategoryResponse.DataItem dataItem = list.get(getAdapterPosition());
                PrefHelper prefHelper = new PrefHelper(context);
                prefHelper.setCatId(dataItem.getId());
                MenuFragment menuFragment = MenuFragment.newInstance(dataItem.getId(), "");


                try {
                    FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                    String fragmentName = menuFragment.getClass().getName();
                    fm.beginTransaction().replace(R.id.frame, menuFragment, fragmentName).addToBackStack(null).commit();
                    //if (addToBackStack) stackSet.push(fragment);
                    //hideKeyboard();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            });

        }
    }


}