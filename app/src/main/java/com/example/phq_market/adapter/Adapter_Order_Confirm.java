package com.example.phq_market.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_Status;
import com.example.phq_market.api.api;
import com.example.phq_market.model.ORDERANDFEEDBACK;
import com.example.phq_market.model.ORDERCONFIRM;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_Order_Confirm extends RecyclerView.Adapter<Adapter_Order_Confirm.ViewHolder> {
    private Context context;
    private ArrayList<ORDERCONFIRM> listOrder;
    private String url = api.url;

    public Adapter_Order_Confirm(Context context, ArrayList<ORDERCONFIRM> listOrder) {
        this.context = context;
        this.listOrder = listOrder;
    }
    private Adapter_Detail_Order_Confirm adapter;
    private ArrayList<ORDERANDFEEDBACK> listdetail;
    @NonNull
    @Override
    public Adapter_Order_Confirm.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_confirm_order,null);
        return new Adapter_Order_Confirm.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Order_Confirm.ViewHolder holder, int position) {
        ORDERCONFIRM orderProdcut = listOrder.get(holder.getAdapterPosition());
        DecimalFormat formatter = new DecimalFormat("#,###");

        try{
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

        holder.Btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Warning");
                builder.setMessage("Are you sure you want to canel your order "+ listOrder.get(holder.getAdapterPosition()).getCODEORDER()+" order ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateStatus(listOrder.get(holder.getAdapterPosition()).getID());
                        context.startActivity(new Intent(context, Activity_Status.class));
                    }
                });
                builder.setNegativeButton("I don't want", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void updateStatus(int ID){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api Api = retrofit.create(api.class);
        Call<String> call = Api.update_Status(ID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Toast.makeText(context, "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
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
