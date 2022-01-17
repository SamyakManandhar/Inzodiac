package com.alphaa.inzodiac.gallery.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.gallery.models.Image;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Darshan on 4/14/2015.
 */
public class Photos_Adapter extends CustomGenericAdapter<Image> {
    public Photos_Adapter(Context context, ArrayList<Image> albums) {
        super(context, albums);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_view_item_album_select, null);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view_album_image);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text_view_album_name);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.imageView.getLayoutParams().width = size;
//        viewHolder.imageView.getLayoutParams().height = size;

        viewHolder.textView.setText(arrayList.get(position).name);
        try {
            Glide.with(context).load(arrayList.get(position).path).placeholder(R.drawable.image_placeholder).centerCrop().into(viewHolder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }
}
