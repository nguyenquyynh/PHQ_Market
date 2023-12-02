package com.example.phq_market.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.phq_market.R;
import com.example.phq_market.fragment.Fragment_Account;
import com.example.phq_market.fragment.Fragment_Cart;
import com.example.phq_market.fragment.Fragment_Discovery;
import com.example.phq_market.fragment.Fragment_Home;
import com.example.phq_market.fragment.Fragment_Like;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.shape.ShapeAppearanceModel;

public class Activity_Main extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private TextView Title_fragment;
    private RelativeLayout layout1;
    private ImageView Img_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.Bottom_navigation);
        frameLayout = findViewById(R.id.Frame_layout);
        Img_search = findViewById(R.id.Img_search);
        Title_fragment = findViewById(R.id.Title_fragment);
        layout1 = findViewById(R.id.layout1);
        ImageView Img_notification = findViewById(R.id.Img_notification);
        changeFragment(new Fragment_Home());
        Title_fragment.setText("Home");

        Intent getwhere = getIntent();
        try {
            Log.e("------>",getwhere+"");
            if(getwhere!= null){
                String where = getwhere.getStringExtra("where");
                layout1.setVisibility(View.VISIBLE);
                Img_search.setVisibility(View.VISIBLE);

                if(where.equals("home")){
                    bottomNavigationView.setSelectedItemId(R.id.Home);
                    changeFragment(new Fragment_Home());
                    Title_fragment.setText("Home");
                } else if (where.equals("cart")) {
                    bottomNavigationView.setSelectedItemId(R.id.Cart);
                    changeFragment(new Fragment_Cart());
                    Title_fragment.setText("Cart");
                } else if (where.equals("discover")) {
                    bottomNavigationView.setSelectedItemId(R.id.Discovery);
                    changeFragment(new Fragment_Discovery());
                    Title_fragment.setText("Discovery");
                } else if (where.equals("like")) {
                    bottomNavigationView.setSelectedItemId(R.id.Like);
                    changeFragment(new Fragment_Like());
                    Img_search.setVisibility(View.GONE);
                    Title_fragment.setText("Like");
                }else {
                    bottomNavigationView.setSelectedItemId(R.id.Account);
                    layout1.setVisibility(View.GONE);
                    changeFragment(new Fragment_Account());
                    Title_fragment.setText("Account");
                }
            }
        }catch (Exception e){
            showWelcome();
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int densityDpi = displayMetrics.densityDpi;

        int px =(int) (40*((float)densityDpi/160.0));

        bottomNavigationView.setItemActiveIndicatorColor(ColorStateList.valueOf(Color.argb(255,56,151,46)));
        bottomNavigationView.setItemActiveIndicatorEnabled(true);
        bottomNavigationView.setItemActiveIndicatorWidth(px);
        bottomNavigationView.setItemActiveIndicatorHeight(px);
        bottomNavigationView.setItemActiveIndicatorShapeAppearance(new ShapeAppearanceModel().withCornerSize(30).toBuilder().build());


        Img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Main.this, Activity_Search.class));
            }
        });
        Img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Main.this,Activity_Notification.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                layout1.setVisibility(View.VISIBLE);
                Img_search.setVisibility(View.VISIBLE);

                int currentItem = bottomNavigationView.getSelectedItemId();
                if (currentItem == item.getItemId()) {
                    // Ngăn chặn việc chuyển đổi
                    return false;
                }

                if (item.getItemId() == R.id.Home) {
                    changeFragment(new Fragment_Home());
                    Title_fragment.setText("Home");
                }

                if (item.getItemId() == R.id.Cart) {
                    changeFragment(new Fragment_Cart());
                    Title_fragment.setText("Cart");
                }

                if (item.getItemId() == R.id.Discovery) {
                    changeFragment(new Fragment_Discovery());
                    Title_fragment.setText("Discovery");
                }
                if (item.getItemId() == R.id.Like) {
                    changeFragment(new Fragment_Like());
                    Img_search.setVisibility(View.GONE);
                    Title_fragment.setText("Like");
                }
                if (item.getItemId() == R.id.Account) {
                    layout1.setVisibility(View.GONE);
                    changeFragment(new Fragment_Account());
                    Title_fragment.setText("Account");
                }
                return true;
            }
        });
    }

    private void showWelcome(){
        Dialog dialog = new Dialog(this,R.style.Theme_PHQ_Market);
        dialog.setContentView( LayoutInflater.from(Activity_Main.this).inflate(R.layout.layout_wellcome,null));
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
            }
        },3000);
    }

    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Frame_layout, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onResume();
    }
}