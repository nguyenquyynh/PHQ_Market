package com.example.phq_market.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.model.ONLYIMAGE;

import java.util.List;

public class Adapter_Image_Slide_ItemDetail extends RecyclerView.Adapter<Adapter_Image_Slide_ItemDetail.ViewHolder> {
    private final List<ONLYIMAGE> imageList;
    private final Context context;

    public Adapter_Image_Slide_ItemDetail(List<ONLYIMAGE> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String img = imageList.get(position).getIMAGE();
        try {
            Glide.with(context)
                    .load(img)
                    .into(holder.imageView);
        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>>>>>>", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
        }
    }
}
