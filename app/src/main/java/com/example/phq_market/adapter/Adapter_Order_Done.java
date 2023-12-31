package com.example.phq_market.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.phq_market.activity.Activity_FeedBack;
import com.example.phq_market.model.ORDERANDFEEDBACK;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Order_Done extends RecyclerView.Adapter<Adapter_Order_Done.ViewHolder> {
    private Context context;
    private ArrayList<ORDERANDFEEDBACK> listOrder;

    public Adapter_Order_Done(Context context, ArrayList<ORDERANDFEEDBACK> listOrder) {
        this.context = context;
        this.listOrder = listOrder;
    }

    @NonNull
    @Override
    public Adapter_Order_Done.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_order_feedback,null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Order_Done.ViewHolder holder, int position) {
        ORDERANDFEEDBACK orderProdcut = listOrder.get(holder.getAdapterPosition());
        DecimalFormat formatter = new DecimalFormat("#,###");
            if(orderProdcut.getCheckFeedBack() == null){
                holder.Btn_Feedback.setVisibility(View.VISIBLE);
            }else {
                holder.Btn_Feedback.setVisibility(View.GONE);
            }

            try{
                Glide.with(context)
                        .load(orderProdcut.getIMG())
                        .into(holder.Img_imageproduct);
                holder.Txt_nameproduct.setText(orderProdcut.getNAME());
                holder.Txt_evaluate.setText(orderProdcut.getQUANTITY()+"");
                holder.Txt_priceproduct.setText(formatter.format(orderProdcut.getPAY()));
                Log.e("--------->",orderProdcut.getCheckFeedBack()+"");
            }catch (Exception e){

            }

        holder.Btn_Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_FeedBack.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ITEM",listOrder.get(holder.getAdapterPosition()));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
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
