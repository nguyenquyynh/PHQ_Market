package com.example.phq_market.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.model.CATALOGSHOW;

import java.util.ArrayList;

public class Adapter_Catalog extends RecyclerView.Adapter<Adapter_Catalog.ViewHolder> {

    public final ArrayList<CATALOGSHOW> list_catalog;
    public final Context context;

    public Adapter_Catalog(ArrayList<CATALOGSHOW> list_catalog, Context context) {
        this.list_catalog = list_catalog;
        this.context = context;
    }


    @NonNull
    @Override
    public Adapter_Catalog.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_catalog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Catalog.ViewHolder holder, int position) {
        CATALOGSHOW catalog = list_catalog.get(position);
        try {
            Glide.with(context)
                    .load(catalog.getIMG())
                    .into(holder.Img_imgcatalog);
            holder.Txt_namecatalog.setText(catalog.getNAME());
            holder.Txt_countcatalog.setText(String.valueOf(catalog.getQUANTITY()));
        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>>", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return list_catalog.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView Img_imgcatalog;
        public TextView Txt_namecatalog,Txt_countcatalog;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Img_imgcatalog = itemView.findViewById(R.id.Img_imgcatalog);
            Txt_namecatalog = itemView.findViewById(R.id.Txt_namecatalog);
            Txt_countcatalog = itemView.findViewById(R.id.Txt_countcatalog);
        }
    }
}
