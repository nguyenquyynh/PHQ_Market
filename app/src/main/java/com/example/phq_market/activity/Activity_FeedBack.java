package com.example.phq_market.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.model.ORDERANDFEEDBACK;

public class Activity_FeedBack extends AppCompatActivity {

    private ImageView Img_done, Img_product, Img_star1, Img_star2, Img_star3, Img_star4, Img_star5;
    private TextView Txt_status, Txt_name;
    private EditText Edt_content;

    int evaluate = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        Img_done = findViewById(R.id.Img_done);
        Img_product = findViewById(R.id.Img_product);
        Img_star1 = findViewById(R.id.Img_star1);
        Img_star2 = findViewById(R.id.Img_star2);
        Img_star3 = findViewById(R.id.Img_star3);
        Img_star4 = findViewById(R.id.Img_star4);
        Img_star5 = findViewById(R.id.Img_star5);
        Txt_status = findViewById(R.id.Txt_status);
        Txt_name = findViewById(R.id.Txt_name);
        Edt_content = findViewById(R.id.Edt_content);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            ORDERANDFEEDBACK item = (ORDERANDFEEDBACK) bundle.getSerializable("ITEM");
            Txt_name.setText(item.getNAME());
            Glide.with(Activity_FeedBack.this)
                    .load(item.getIMG())
                    .into(Img_product);
            Toast.makeText(this, "" +item , Toast.LENGTH_SHORT).show();
        }

        ImageView IMG_Back = findViewById(R.id.IMG_Back);
        IMG_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void feedback(int evaluate) {
        switch (evaluate) {
            case 1 :
                Img_star1.setImageResource(R.drawable.start);
                break;
            case 2 :
                Img_star1.setImageResource(R.drawable.start);
                Img_star2.setImageResource(R.drawable.start);
                break;
            case 3:
                Img_star1.setImageResource(R.drawable.start);
                Img_star2.setImageResource(R.drawable.start);
                Img_star3.setImageResource(R.drawable.start);
                break;
            case 4 :
                Img_star1.setImageResource(R.drawable.start);
                Img_star2.setImageResource(R.drawable.start);
                Img_star3.setImageResource(R.drawable.start);
                Img_star4.setImageResource(R.drawable.start);
                break;
            case 5:
                Img_star1.setImageResource(R.drawable.start);
                Img_star2.setImageResource(R.drawable.start);
                Img_star3.setImageResource(R.drawable.start);
                Img_star4.setImageResource(R.drawable.start);
                Img_star5.setImageResource(R.drawable.start);
                break;
            default:
                Log.d("??>.............", "");
                break;
        }
    }
    private void unfeedback(){
        Img_star1.setImageResource(R.drawable.star_white);
        Img_star2.setImageResource(R.drawable.star_white);
        Img_star3.setImageResource(R.drawable.star_white);
        Img_star4.setImageResource(R.drawable.star_white);
        Img_star5.setImageResource(R.drawable.star_white);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Img_star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfeedback();
                evaluate = 1;
                feedback(evaluate);
            }

        });
        Img_star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfeedback();
                evaluate = 2;
                feedback(evaluate);
            }

        });
        Img_star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfeedback();
                evaluate = 3;
                feedback(evaluate);
            }

        });
        Img_star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfeedback();
                evaluate = 4;
                feedback(evaluate);
            }

        });
        Img_star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfeedback();
                evaluate = 5;
                feedback(evaluate);
            }

        });

        Img_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}