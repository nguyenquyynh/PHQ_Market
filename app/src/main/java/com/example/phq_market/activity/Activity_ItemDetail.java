package com.example.phq_market.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_Image_Slide_ItemDetail;

import java.util.ArrayList;


public class Activity_ItemDetail extends AppCompatActivity {

    private Adapter_Image_Slide_ItemDetail adapter_img;
    private ViewPager2 viewPager;
    private ArrayList<String> imageList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);


        viewPager = findViewById(R.id.view_pager);
        LinearLayout indicatorLayout = findViewById(R.id.indicator_layout);

        imageList = new ArrayList<>();

        imageList.add("1212");
        imageList.add("1212");
        imageList.add("1212");
        imageList.add("1212");
        imageList.add("1212");

        adapter_img = new Adapter_Image_Slide_ItemDetail(imageList);
        viewPager.setAdapter(adapter_img);

        setupSlideshow(indicatorLayout);

        ImageView icBack = findViewById(R.id.icBack);
        TextView tvAddcart = findViewById(R.id.tvAddcart);
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_ItemDetail.this, Activity_Login.class));
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
    }

}