package com.example.phq_market.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.model.ONLYIMAGE;

import java.util.ArrayList;

public class Adapter_Banner extends RecyclerView.Adapter<Adapter_Banner.ViewHolder> {

    public final ArrayList<ONLYIMAGE> list;
    public final Context context;
    public Adapter_Banner(ArrayList<ONLYIMAGE> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_Banner.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.banner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Banner.ViewHolder holder, int position) {
        String image = list.get(position).getIMAGE();
        try {
            Glide.with(context)
                    .load(image)
                    .into(holder.imageView);
        } catch (Exception e) {
            Log.d(">>>>>>>>>>", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
