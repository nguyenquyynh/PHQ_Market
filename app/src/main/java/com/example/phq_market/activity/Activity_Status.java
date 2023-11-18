package com.example.phq_market.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phq_market.R;

public class Activity_Status extends AppCompatActivity {
    private ImageView Img_back;
    private ImageView Img_notification;
    private TextView Tv_Submiting;
    private TextView Tv_Shipping;
    private TextView Tv_Done;
    private TextView Tv_Canceled;
    private RecyclerView Rcv_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Img_back = findViewById(R.id.Img_back);
        Img_notification = findViewById(R.id.Img_notification);
        Tv_Submiting = findViewById(R.id.Tv_Submiting);
        Tv_Shipping = findViewById(R.id.Tv_Shipping);
        Tv_Done = findViewById(R.id.Tv_Done);
        Tv_Canceled = findViewById(R.id.Tv_Canceled);
        Rcv_status = findViewById(R.id.Rcv_status);

        Img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}