package com.example.phq_market.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.model.ORDERANDFEEDBACK;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Order_Shipping extends RecyclerView.Adapter<Adapter_Order_Shipping.ViewHolder> {
    private Context context;
    private ArrayList<ORDERANDFEEDBACK> listOrder;

    public Adapter_Order_Shipping(Context context, ArrayList<ORDERANDFEEDBACK> listOrder) {
        this.context = context;
        this.listOrder = listOrder;
    }
    @NonNull
    @Override
    public Adapter_Order_Shipping.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_order_feedback,null);
        return new Adapter_Order_Shipping.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Order_Shipping.ViewHolder holder, int position) {
        ORDERANDFEEDBACK orderProdcut = listOrder.get(holder.getAdapterPosition());
        DecimalFormat formatter = new DecimalFormat("#,###");

        holder.Btn_Feedback.setVisibility(View.GONE);

        try{
            Glide.with(context)
                    .load(orderProdcut.getIMG())
                    .into(holder.Img_imageproduct);
            holder.Txt_nameproduct.setText(orderProdcut.getNAME());
            holder.Txt_evaluate.setText(orderProdcut.getQUANTITY()+"");
            holder.Txt_priceproduct.setText(formatter.format(orderProdcut.getPAY()));
        }catch (Exception e){

        }

        holder.Btn_Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
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
