package com.example.phq_market.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.model.CHECKOUT;

import java.util.ArrayList;

public class Adapter_Checkout extends RecyclerView.Adapter<Adapter_Checkout.ViewHolder> {

    private Context context;
    private ArrayList<CHECKOUT> listCheckout;

    public Adapter_Checkout(Context context, ArrayList<CHECKOUT> listCheckout) {
        this.context = context;
        this.listCheckout = listCheckout;
    }

    @NonNull
    @Override
    public Adapter_Checkout.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_checkout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Checkout.ViewHolder holder, int position) {
        CHECKOUT chou = listCheckout.get(holder.getAdapterPosition());
        try{
            Glide.with(context)
                    .load(chou.getIMG())
                    .into(holder.Img_Anh);
            holder.Txt_name.setText("Name: "+chou.getNAME());
            holder.Txt_cost.setText("Price: "+chou.getPRICE());
            holder.Txt_quantity.setText("x"+chou.getQUANTITY());
        }catch (Exception e){

        }

        holder.Img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listCheckout.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView Img_Anh,Img_cancel;
        public TextView Txt_name,Txt_cost,Txt_quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Img_Anh = itemView.findViewById(R.id.Img_Anh);
            Img_cancel = itemView.findViewById(R.id.Img_cancel);
            Txt_name = itemView.findViewById(R.id.Txt_name);
            Txt_cost = itemView.findViewById(R.id.Txt_cost);
            Txt_quantity = itemView.findViewById(R.id.Txt_quantity);
        }
    }
}
