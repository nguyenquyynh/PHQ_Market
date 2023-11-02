package com.example.phq_market.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.model.PRODUCT;

import java.util.ArrayList;

public class Adapter_NewProduct extends RecyclerView.Adapter<Adapter_NewProduct.ViewHolder> {
    public final ArrayList<PRODUCT> list_product;
    public final Context context;

    public Adapter_NewProduct(ArrayList<PRODUCT> list_product, Context context) {
        this.list_product = list_product;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_NewProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_newproduct, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_NewProduct.ViewHolder holder, int position) {
        PRODUCT product = list_product.get(position);
        try {
            //Load Image
            holder.Txt_nameproduct.setText(product.getNAME());
            holder.Txt_evaluate.setText(String.valueOf(4.5));
            holder.Txt_priceproduct.setText(String.valueOf(product.getPRICE()));
        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>", e.getMessage());
        }
        holder.Btn_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Export ID of product
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView Img_imageproduct;
        public TextView Txt_nameproduct, Txt_evaluate, Txt_priceproduct;
        public Button Btn_buynow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Img_imageproduct = itemView.findViewById(R.id.Img_imageproduct);
            Txt_nameproduct = itemView.findViewById(R.id.Txt_nameproduct);
            Txt_evaluate = itemView.findViewById(R.id.Txt_evaluate);
            Txt_priceproduct = itemView.findViewById(R.id.Txt_priceproduct);
            Btn_buynow = itemView.findViewById(R.id.Btn_buynow);
        }
    }
}
