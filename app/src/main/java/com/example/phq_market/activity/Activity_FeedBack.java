package com.example.phq_market.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.api.api;
import com.example.phq_market.model.ORDERANDFEEDBACK;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_FeedBack extends AppCompatActivity {

    private ImageView Img_done, Img_product, Img_star1, Img_star2, Img_star3, Img_star4, Img_star5;
    private TextView Txt_status, Txt_name;
    private EditText Edt_content;
    private ORDERANDFEEDBACK item;

    int evaluate = 5;
    private String url = api.url;
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

        feedback(evaluate);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            item = (ORDERANDFEEDBACK) bundle.getSerializable("ITEM");
            Txt_name.setText(item.getNAME());
            Glide.with(Activity_FeedBack.this)
                    .load(item.getIMG())
                    .into(Img_product);
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
                Img_star1.setImageResource(R.drawable.star_yellow);
                break;
            case 2 :
                Img_star1.setImageResource(R.drawable.star_yellow);
                Img_star2.setImageResource(R.drawable.star_yellow);
                break;
            case 3:
                Img_star1.setImageResource(R.drawable.star_yellow);
                Img_star2.setImageResource(R.drawable.star_yellow);
                Img_star3.setImageResource(R.drawable.star_yellow);
                break;
            case 4 :
                Img_star1.setImageResource(R.drawable.star_yellow);
                Img_star2.setImageResource(R.drawable.star_yellow);
                Img_star3.setImageResource(R.drawable.star_yellow);
                Img_star4.setImageResource(R.drawable.star_yellow);
                break;
            case 5:
                Img_star1.setImageResource(R.drawable.star_yellow);
                Img_star2.setImageResource(R.drawable.star_yellow);
                Img_star3.setImageResource(R.drawable.star_yellow);
                Img_star4.setImageResource(R.drawable.star_yellow);
                Img_star5.setImageResource(R.drawable.star_yellow);
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

    private void clickStar(){
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
    }
    private void confirm(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences s = getSharedPreferences("account", MODE_PRIVATE);
                if (!s.getString("Email","").isEmpty() && !s.getString("Pass","").isEmpty()) {
                    String content = Edt_content.getText().toString();
                    if (content.isEmpty()) {content = Txt_status.getText().toString();
                    }
                    Retrofit retrofit_feedback = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    api api_feedback = retrofit_feedback.create(api.class);
                    Call<String> call_feedback = api_feedback.add_feedback(evaluate,content,item.getID(),s.getString("Email",""), s.getString("Pass",""));
                    call_feedback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(Activity_FeedBack.this, response.body(), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(Activity_FeedBack.this, response.body(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }

            }
        }).start();
    }
    @Override
    protected void onResume() {
        super.onResume();
        clickStar();
        Img_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
    }
}