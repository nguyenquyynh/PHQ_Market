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
import com.example.phq_market.model.NOTIFICATION;

import java.util.ArrayList;

public class Adapter_Notification extends RecyclerView.Adapter<Adapter_Notification.ViewHOlder> {
    private Context context;
    private ArrayList<NOTIFICATION> list;

    public Adapter_Notification(Context context, ArrayList<NOTIFICATION> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_Notification.ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_notification,parent,false);
        return new ViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Notification.ViewHOlder holder, int position) {
        NOTIFICATION noti = list.get(holder.getAdapterPosition());
        try{
            if(noti.getROLE() == 1)
                Glide.with(context)
                        .load(R.drawable.account)
                        .into(holder.Img_Notifi);
            else
                Glide.with(context)
                        .load(R.drawable.baseline_production_quantity_limits_24)
                        .into(holder.Img_Notifi);


            holder.Txt_Title.setText(noti.getTITLE()+"");
            holder.Txt_Content.setText(noti.getCONTENT());
            holder.Txt_Date.setText(noti.getDATE());
        }catch (Exception e){
            Log.e("--------->",e+"");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHOlder extends RecyclerView.ViewHolder {
        public ImageView Img_Notifi;
        public TextView Txt_Title,Txt_Content,Txt_Date;
        public ViewHOlder(@NonNull View itemView) {
            super(itemView);
            Img_Notifi = itemView.findViewById(R.id.Img_Notifi);
            Txt_Title = itemView.findViewById(R.id.Txt_Title);
            Txt_Content = itemView.findViewById(R.id.Txt_Content);
            Txt_Date = itemView.findViewById(R.id.Txt_Date);

        }
    }
}
