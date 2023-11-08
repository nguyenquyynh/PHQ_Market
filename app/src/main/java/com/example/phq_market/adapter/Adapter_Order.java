package com.example.phq_market.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.phq_market.activity.Activity_FeedBack;
import com.example.phq_market.model.NEWPRODUCT;
import com.example.phq_market.model.PRODUCTDETAIL;

import java.util.ArrayList;

public class Adapter_Order extends RecyclerView.Adapter<Adapter_Order.ViewHolder> {
    private Context context;
    private ArrayList<NEWPRODUCT> list;

    public Adapter_Order(Context context, ArrayList<NEWPRODUCT> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_Order.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_order_feedback,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Order.ViewHolder holder, int position) {
        NEWPRODUCT product= list.get(holder.getAdapterPosition());
        try{
            Glide.with(context)
                    .load(product.getIMG())
                    .into(holder.Img_imageproduct);
            holder.Txt_nameproduct.setText(product.getNAME());
            holder.Txt_evaluate.setText(""+product.getEVALUATE());
            holder.Txt_priceproduct.setText(""+product.getPRICE());
        }catch (Exception e){

        }


        holder.Btn_Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_FeedBack.class);
                intent.putExtra("IDPRODUCT",list.get(holder.getAdapterPosition()).getID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView Img_imageproduct;
        public TextView Txt_nameproduct,Txt_evaluate,Txt_priceproduct;
        public Button Btn_Feedback;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Img_imageproduct = itemView.findViewById(R.id.Img_imageproduct);
            Txt_nameproduct = itemView.findViewById(R.id.Txt_nameproduct);
            Txt_evaluate = itemView.findViewById(R.id.Txt_evaluate);
            Txt_priceproduct = itemView.findViewById(R.id.Txt_priceproduct);
            Btn_Feedback = itemView.findViewById(R.id.Btn_Feedback);
        }
    }
}
