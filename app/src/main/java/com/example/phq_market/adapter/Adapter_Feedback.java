package com.example.phq_market.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phq_market.R;
import com.example.phq_market.model.FEEDBACKSHOW;

import java.util.ArrayList;

public class Adapter_Feedback extends RecyclerView.Adapter<Adapter_Feedback.ViewHolder> {
    public final ArrayList<FEEDBACKSHOW> list;
    public final Context context;

    public Adapter_Feedback(ArrayList<FEEDBACKSHOW> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_Feedback.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Feedback.ViewHolder holder, int position) {
        FEEDBACKSHOW feedback = list.get(position);
        try {
            holder.Txt_nameuser.setText(feedback.getCUSTOMER());
            holder.Txt_content.setText(feedback.getCONTENT());
            holder.Txt_evaluate.setText(String.valueOf(feedback.getEVALUATE()));
        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>>>>" , e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Txt_nameuser, Txt_content, Txt_evaluate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Txt_nameuser = itemView.findViewById(R.id.Txt_nameuser);
            Txt_content = itemView.findViewById(R.id.Txt_content);
            Txt_evaluate = itemView.findViewById(R.id.Txt_evaluate);
        }
    }
}
