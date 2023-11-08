package com.example.phq_market.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_Feedback;
import com.example.phq_market.adapter.Adapter_Image_Slide_ItemDetail;
import com.example.phq_market.api.api;
import com.example.phq_market.model.FEEDBACKSHOW;
import com.example.phq_market.model.ONLYIMAGE;
import com.example.phq_market.model.PRODUCTDETAIL;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Activity_ItemDetail extends AppCompatActivity {

    private Adapter_Image_Slide_ItemDetail adapter_img;
    private ViewPager2 viewPager;
    LinearLayout indicatorLayout;
    int ID;
    //Feedback
    LinearLayoutManager layoutManager_feedback;
    Adapter_Feedback adapter_feedback;
    RecyclerView Recycler_feedback;
    TextView Txt_evaluateproduct, Txt_descriptionproduct, Txt_more;
    TextView Txt_priceproduct, Txt_priceaddtocart;
    TextView Txt_addcart;
    ProgressDialog progressDialog;
    TextView Txt_nameproduct;
    private ArrayList<ONLYIMAGE> imageList;
    private  ArrayList<FEEDBACKSHOW> list_feedback;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        viewPager = findViewById(R.id.view_pager);
        Txt_evaluateproduct = findViewById(R.id.Txt_evaluateproduct);
        Txt_priceproduct = findViewById(R.id.Txt_priceproduct);
        Txt_priceaddtocart = findViewById(R.id.Txt_priceaddtocart);
        Txt_nameproduct = findViewById(R.id.Txt_nameproduct);
        Txt_more = findViewById(R.id.Txt_more);
        Txt_descriptionproduct = findViewById(R.id.Txt_descriptionproduct);
        Recycler_feedback = findViewById(R.id.Recycler_feedback);
        indicatorLayout = findViewById(R.id.indicator_layout);
        imageList = new ArrayList<>();
        list_feedback = new ArrayList<>();
        ID = getIntent().getIntExtra("IDPRODUCT", -1);

        ImageView icBack = findViewById(R.id.icBack);
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    //Lấy product
        Retrofit retrofit_product = new Retrofit.Builder()
                .baseUrl("https://phqmarket.000webhostapp.com/product/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api api_product = retrofit_product.create(api.class);
        Call<PRODUCTDETAIL> call_product = api_product.getonlyproduct(ID);
        call_product.enqueue(new Callback<PRODUCTDETAIL>() {
            @Override
            public void onResponse(Call<PRODUCTDETAIL> call, Response<PRODUCTDETAIL> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(Activity_ItemDetail.this, ""+ ID, Toast.LENGTH_SHORT).show();
                    PRODUCTDETAIL product = response.body();
                    Txt_nameproduct.setText(product.getNAME());
                    Txt_evaluateproduct.setText(String.format("%.3s",product.getEVALUATE()));
                    Txt_priceproduct.setText(String.valueOf(product.getPRICE()));
                    Txt_priceaddtocart.setText(String.valueOf(product.getPRICE()));
                    Txt_descriptionproduct.setText(product.getDESCRIBED());
                    Log.d(">>>>>>>>>>>>>>>", "Succesfull");
                }
            }

            @Override
            public void onFailure(Call<PRODUCTDETAIL> call, Throwable t) {
                Log.d(">>>>>>>>>>>>>>>", "Faile !!!");
            }
        });

//        //lấy list ảnh
        Retrofit retrofit_image = new Retrofit.Builder()
                .baseUrl("https://phqmarket.000webhostapp.com/image/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api api_image = retrofit_image.create(api.class);
        Call<ArrayList<ONLYIMAGE>> call_image = api_image.get_imageonlyproduct(ID);
        call_image.enqueue(new Callback<ArrayList<ONLYIMAGE>>() {
            @Override
            public void onResponse(Call<ArrayList<ONLYIMAGE>> call, Response<ArrayList<ONLYIMAGE>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<ONLYIMAGE> list = response.body();
                    imageList.clear();
                    imageList.addAll(list);
                    adapter_img = new Adapter_Image_Slide_ItemDetail(imageList, Activity_ItemDetail.this);
                    viewPager.setAdapter(adapter_img);
                    setupSlideshow(indicatorLayout);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ONLYIMAGE>> call, Throwable t) {

            }
        });
        //Lấy list feaadback
        Retrofit retrofit_feedback = new Retrofit.Builder()
                .baseUrl("https://phqmarket.000webhostapp.com/feedback/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api api_feedback = retrofit_feedback.create(api.class);
        Call<ArrayList<FEEDBACKSHOW>> call_feedback = api_feedback.get_feedbackproduct(25);
        call_feedback.enqueue(new Callback<ArrayList<FEEDBACKSHOW>>() {
            @Override
            public void onResponse(Call<ArrayList<FEEDBACKSHOW>> call, Response<ArrayList<FEEDBACKSHOW>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    ArrayList<FEEDBACKSHOW> list = response.body();
                    list_feedback.clear();
                    list_feedback.addAll(list);
                    layoutManager_feedback = new LinearLayoutManager(Activity_ItemDetail.this);
                    Recycler_feedback.setLayoutManager(layoutManager_feedback);
                    adapter_feedback = new Adapter_Feedback(list_feedback,Activity_ItemDetail.this);
                    Recycler_feedback.setAdapter(adapter_feedback);
                }else {
                    Log.d(">>>>>>>>>>>>>", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<FEEDBACKSHOW>> call, Throwable t) {
                Log.d(">>>>>>>>" , "false");
            }
        });

    }

    private void setupSlideshow(LinearLayout indicatorLayout){
        // create a dot under the slide
        int indicatorCount = imageList.size();
        for (int i = 0; i < indicatorCount; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.unselected_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);// create khoảng cách dotr
            dot.setLayoutParams(params);
            indicatorLayout.addView(dot);
        }

        ImageView dot = (ImageView) indicatorLayout.getChildAt(0);
        dot.setImageDrawable(ContextCompat.getDrawable(
                Activity_ItemDetail.this,R.drawable.selected_dot
        ));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                for (int i = 0; i < indicatorCount; i++) {
                    ImageView dot = (ImageView) indicatorLayout.getChildAt(i);
                    dot.setImageDrawable(ContextCompat.getDrawable(
                            Activity_ItemDetail.this,

                            i == position ? R.drawable.selected_dot : R.drawable.unselected_dot
                    ));
                }
            }
        });


        // Create slide show add img
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                for (int i = 0; i < indicatorCount; i++) {
                    ImageView dot = (ImageView) indicatorLayout.getChildAt(i);
                    if (i == position) {
                        dot.setImageDrawable(ContextCompat.getDrawable(Activity_ItemDetail.this, R.drawable.selected_dot));
                    } else {
                        dot.setImageDrawable(ContextCompat.getDrawable(Activity_ItemDetail.this, R.drawable.unselected_dot));
                    }
                    // Áp dụng animation chậm lại cho chấm tròn
                    dot.setAlpha(1f); // Đặt độ mờ ban đầu
                    dot.animate().alpha(1.0f).setDuration(2000).start(); // Áp dụng animation chậm lại
                }
            }
        });
        Txt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Txt_more.getText()=="More") {
                    Txt_descriptionproduct.setMaxLines(100);
                    Txt_more.setText("Collapse");
                } else {
                    Txt_more.setText("More");
                    Txt_descriptionproduct.setMaxLines(2);
                }

            }
        });

    }

}