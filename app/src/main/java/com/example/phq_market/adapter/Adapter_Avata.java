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

import java.util.ArrayList;

public class Adapter_Avata extends RecyclerView.Adapter<Adapter_Avata.ViewHolder> {

    public final ArrayList<String> list;
    public final Context context;

    public Adapter_Avata(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_Avata.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_avata, parent, false);

    return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Avata.ViewHolder holder, int position) {
        int vitri = position;
        try {
            Glide.with(context)
                    .load(list.get(position))
                    .into(holder.avatars);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickProduct.clickproduct(vitri);
                }
            });
        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>>>>>>", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatars;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatars = itemView.findViewById(R.id.avatars);
        }
    }
    public interface OnClickImg {
        public void clickproduct(int ID);
    }
    public Adapter_Like.OnClickProduct onClickProduct;
    public void setOnClickProduct(Adapter_Like.OnClickProduct onClickProduct) {
        this.onClickProduct = onClickProduct;
    }
}
