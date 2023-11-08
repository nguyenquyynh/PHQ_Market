package com.example.phq_market.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.model.NEWPRODUCT;

import java.util.ArrayList;

public class Adapter_SaleProduct extends RecyclerView.Adapter<Adapter_SaleProduct.ViewHolder> {
    public final ArrayList<NEWPRODUCT> list_product;
    public final Context context;
    public Adapter_SaleProduct(ArrayList<NEWPRODUCT> list_product, Context context) {
        this.list_product = list_product;
        this.context = context;
    }
    @NonNull
    @Override
    public Adapter_SaleProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_newproduct, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_SaleProduct.ViewHolder holder, int position) {
        NEWPRODUCT product = list_product.get(position);
        try {
            Glide.with(context)
                    .load(product.getIMG())
                    .into(holder.Img_imageproduct);
            holder.Txt_nameproduct.setText(product.getNAME());
            holder.Txt_evaluate.setText(String.format("%.3s",product.getEVALUATE()));
            holder.Txt_priceproduct.setText(String.valueOf(product.getPRICE()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickProduct.clickproduct(product.getID());
                }
            });
        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list_product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView Img_imageproduct;
        public TextView Txt_nameproduct, Txt_evaluate, Txt_priceproduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Img_imageproduct = itemView.findViewById(R.id.Img_imageproduct);
            Txt_nameproduct = itemView.findViewById(R.id.Txt_nameproduct);
            Txt_evaluate = itemView.findViewById(R.id.Txt_evaluate);
            Txt_priceproduct = itemView.findViewById(R.id.Txt_priceproduct);
        }
    }
    public interface OnClickProduct {
        public void clickproduct(int ID);
    }
    public Adapter_SaleProduct.OnClickProduct onClickProduct;
    public void setOnClickProduct(Adapter_SaleProduct.OnClickProduct onClickProduct) {
        this.onClickProduct = onClickProduct;
    }
}