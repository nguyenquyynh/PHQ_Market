package com.example.phq_market.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phq_market.R;
import com.example.phq_market.model.ORDERANDFEEDBACK;
import com.example.phq_market.model.ORDERCONFIRM;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Order_Shipping extends RecyclerView.Adapter<Adapter_Order_Shipping.ViewHolder> {
    private Context context;
    private ArrayList<ORDERCONFIRM> listOrder;

    public Adapter_Order_Shipping(Context context, ArrayList<ORDERCONFIRM> listOrder) {
        this.context = context;
        this.listOrder = listOrder;
    }
    private Adapter_Detail_Order_Confirm adapter;
    private ArrayList<ORDERANDFEEDBACK> listdetail;
    @NonNull
    @Override
    public Adapter_Order_Shipping.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_confirm_order,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Order_Shipping.ViewHolder holder, int position) {
        ORDERCONFIRM orderProdcut = listOrder.get(holder.getAdapterPosition());
        DecimalFormat formatter = new DecimalFormat("#,###");

        try{
            holder.Btn_Cancel.setVisibility(View.GONE);

            holder.Txt_code.setText("" + orderProdcut.getCODEORDER());
            holder.Txt_payment.setText("Payment: "+(orderProdcut.getPAYMENT() == 0? "Online": "Direct"));
            holder.Txt_date.setText("Date: " + orderProdcut.getDATE());
            holder.Txt_price.setText(formatter.format(orderProdcut.getPAY()));
            holder.Txt_address.setText("Address: "+orderProdcut.getADRESS());

            listdetail = orderProdcut.getListdetail();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            holder.Rcv_img.setLayoutManager(linearLayoutManager);
            adapter = new Adapter_Detail_Order_Confirm(context,listdetail);
            holder.Rcv_img.setAdapter(adapter);

        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Txt_code,Txt_payment,Txt_price,Txt_date,Txt_address;
        public Button Btn_Cancel;
        public RecyclerView Rcv_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Txt_code = itemView.findViewById(R.id.Txt_code);
            Txt_payment = itemView.findViewById(R.id.Txt_payment);
            Txt_price = itemView.findViewById(R.id.Txt_price);
            Txt_date = itemView.findViewById(R.id.Txt_date);
            Txt_address = itemView.findViewById(R.id.Txt_address);
            Btn_Cancel = itemView.findViewById(R.id.Btn_Cancel);
            Rcv_img = itemView.findViewById(R.id.Rcv_img);
        }
    }
}
