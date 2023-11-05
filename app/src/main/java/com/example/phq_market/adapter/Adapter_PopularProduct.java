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
import com.example.phq_market.model.NEWPRODUCT;
import com.example.phq_market.model.PRODUCT;

import java.util.ArrayList;

public class Adapter_PopularProduct extends RecyclerView.Adapter<Adapter_PopularProduct.ViewHolder> {

    public final ArrayList<NEWPRODUCT> list_product;
    public final Context context;

    public Adapter_PopularProduct(ArrayList<NEWPRODUCT> list_product, Context context) {
        this.list_product = list_product;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_PopularProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_popularproduct, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_PopularProduct.ViewHolder holder, int position) {
        NEWPRODUCT product = list_product.get(position);
        try {
            Glide.with(context)
                    .load(product.getIMG())
                    .into(holder.Img_imageproduct);
            holder.Txt_nameproduct.setText(product.getNAME());
            holder.Txt_priceproduct.setText(String.valueOf(product.getPRICE()));
            holder.Txt_evaluate.setText(String.valueOf(product.getEVALUATE()));
        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>", e.getMessage());
        }
        holder.Img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Truyền đi ID của product
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView Img_like, Img_imageproduct;
        public TextView Txt_nameproduct, Txt_priceproduct, Txt_evaluate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Img_like = itemView.findViewById(R.id.Img_like);
            Img_imageproduct = itemView.findViewById(R.id.Img_imageproduct);
            Txt_nameproduct = itemView.findViewById(R.id.Txt_nameproduct);
            Txt_priceproduct = itemView.findViewById(R.id.Txt_priceproduct);
            Txt_evaluate = itemView.findViewById(R.id.Txt_evaluate);
        }
    }
}
