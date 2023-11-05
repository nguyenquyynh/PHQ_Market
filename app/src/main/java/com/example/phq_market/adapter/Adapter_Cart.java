package com.example.phq_market.adapter;

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
import com.example.phq_market.model.CART;
import com.example.phq_market.model.CATALOGSHOW;

import java.util.ArrayList;

public class Adapter_Cart extends RecyclerView.Adapter<Adapter_Cart.ViewHolder> {
    private Context context;
    private ArrayList<CART> list_CART;

    public Adapter_Cart(Context context, ArrayList<CART> list_CART) {
        this.context = context;
        this.list_CART = list_CART;
    }

    @NonNull
    @Override
    public Adapter_Cart.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_cart,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Cart.ViewHolder holder, int position) {
        CART cart = list_CART.get(position);
        try {
            Glide.with(context)
                    .load(cart.getIMG())
                    .into(holder.imgAnh);
            holder.tvName.setText(cart.getNAME());
            holder.tvquantity.setText(String.valueOf(cart.getQUANTITY()));
            holder.tvCost.setText(String.valueOf(cart.getPRICE()));
        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>>", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list_CART.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnh;
        TextView tvName,tvCost,tvquantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            tvName = itemView.findViewById(R.id.tvName);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvquantity = itemView.findViewById(R.id.tvquantity);
        }
    }
}
