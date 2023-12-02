package com.example.phq_market.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.phq_market.activity.Activity_Status;
import com.example.phq_market.api.api;
import com.example.phq_market.model.ORDERANDFEEDBACK;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_Order_Confirm extends RecyclerView.Adapter<Adapter_Order_Confirm.ViewHolder> {
    private Context context;
    private ArrayList<ORDERANDFEEDBACK> listOrder;
    private String url = api.url;

    public Adapter_Order_Confirm(Context context, ArrayList<ORDERANDFEEDBACK> listOrder) {
        this.context = context;
        this.listOrder = listOrder;
    }
    @NonNull
    @Override
    public Adapter_Order_Confirm.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_order_feedback,null);
        return new Adapter_Order_Confirm.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Order_Confirm.ViewHolder holder, int position) {
        ORDERANDFEEDBACK orderProdcut = listOrder.get(holder.getAdapterPosition());
        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.Btn_Feedback.setText("Cancel");

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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Warning");
                builder.setMessage("Are you sure you want to canel your "+ listOrder.get(holder.getAdapterPosition()).getNAME()+" order ?");
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
