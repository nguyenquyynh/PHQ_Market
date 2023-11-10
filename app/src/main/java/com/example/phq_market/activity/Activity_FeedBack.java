package com.example.phq_market.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phq_market.R;

public class Activity_FeedBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        Intent intent = getIntent();
        if (intent!=null){
            Toast.makeText(this, ""+intent.getIntExtra("ID",0), Toast.LENGTH_SHORT).show();
        }

        ImageView IMG_Back = findViewById(R.id.IMG_Back);
        IMG_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}