package com.example.phq_market.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phq_market.R;

import java.util.ArrayList;

public class Adapter_Recycleview extends RecyclerView.Adapter<Adapter_Recycleview.ViewHolder> {
    private Context context;
    private ArrayList<String> list;

    public Adapter_Recycleview(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }
    public interface GetIDListener{
        void id(int posotion,String name);
    }
    private GetIDListener getIDListener;
    public void onGetIDListener(GetIDListener getIDListener){
        this.getIDListener = getIDListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_adress,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv.setText(list.get(holder.getAdapterPosition()));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIDListener.id(holder.getAdapterPosition(), list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.Tv_tp);
        }
    }
}
