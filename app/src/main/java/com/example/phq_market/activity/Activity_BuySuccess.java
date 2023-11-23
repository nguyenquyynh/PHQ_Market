package com.example.phq_market.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phq_market.R;

public class Activity_BuySuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_success);


        Button btn_continueShopping = findViewById(R.id.btn_continueShopping);
        Button btn_revieworder = findViewById(R.id.btn_revieworder);
        ImageView IMG_Back  = findViewById(R.id.IMG_Back);

        btn_continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_BuySuccess.this,Activity_Main.class);
                intent.putExtra("where","discover");
                startActivity(intent);
            }
        });

        btn_revieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_BuySuccess.this, Activity_Status.class));
            }
        });

        IMG_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_BuySuccess.this,Activity_Main.class);
                intent.putExtra("where","cart");
                startActivity(intent);
            }
        });
    }
}