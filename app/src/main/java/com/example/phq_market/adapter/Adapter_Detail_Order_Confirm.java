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
import com.example.phq_market.model.ORDERANDFEEDBACK;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Detail_Order_Confirm extends RecyclerView.Adapter<Adapter_Detail_Order_Confirm.ViewHolder> {
    private Context context;
    private ArrayList<ORDERANDFEEDBACK> list;

    public Adapter_Detail_Order_Confirm(Context context, ArrayList<ORDERANDFEEDBACK> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_Detail_Order_Confirm.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_detail_confirm_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Detail_Order_Confirm.ViewHolder holder, int position) {
        ORDERANDFEEDBACK orderProdcut = list.get(holder.getAdapterPosition());
        DecimalFormat formatter = new DecimalFormat("#,###");

        try{
            Glide.with(context)
                    .load(orderProdcut.getIMG())
                    .into(holder.Img_imageproduct);
            holder.Txt_nameproduct.setText(orderProdcut.getNAME());
            holder.Txt_evaluate.setText(orderProdcut.getQUANTITY()+"");
            holder.Txt_priceproduct.setText(formatter.format(orderProdcut.getPAY()));
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        try{
            return list.size();
        }catch (Exception e){
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView Img_imageproduct;
        public TextView Txt_nameproduct,Txt_evaluate,Txt_priceproduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Img_imageproduct = itemView.findViewById(R.id.Img_imageproduct);
            Txt_nameproduct = itemView.findViewById(R.id.Txt_nameproduct);
            Txt_evaluate = itemView.findViewById(R.id.Txt_evaluate);
            Txt_priceproduct = itemView.findViewById(R.id.Txt_priceproduct);
        }
    }
}
