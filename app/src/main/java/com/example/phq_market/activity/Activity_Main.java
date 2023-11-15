package com.example.phq_market.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phq_market.R;
import com.example.phq_market.fragment.Fragment_Account;
import com.example.phq_market.fragment.Fragment_Cart;
import com.example.phq_market.fragment.Fragment_Discovery;
import com.example.phq_market.fragment.Fragment_Home;
import com.example.phq_market.fragment.Fragment_Like;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Activity_Main extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    TextView Title_fragment;
    RelativeLayout layout1;
    ImageView Img_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.Bottom_navigation);
        frameLayout = findViewById(R.id.Frame_layout);
        Img_search = findViewById(R.id.Img_search);
        Title_fragment = findViewById(R.id.Title_fragment);
        layout1 = findViewById(R.id.layout1);
        changeFragment(new Fragment_Home());
        Title_fragment.setText("Home");

        showWelcome();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Main.this, Activity_Search.class));
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showLoading();
                layout1.setVisibility(View.VISIBLE);
                Img_search.setVisibility(View.VISIBLE);
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
    String load;
    private void showLoading(){
        Dialog dialog = new Dialog(this,R.style.Theme_PHQ_Market);
        View view = LayoutInflater.from(Activity_Main.this).inflate(R.layout.activity_wellcome,null);
        dialog.setContentView(view);

        TextView Tv_Well = view.findViewById(R.id.Tv_Well);
        TextView Tv_To = view.findViewById(R.id.Tv_To);

        Tv_Well.setText("Loading");
        Tv_To.setText(".");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                load = load == "." ? ".." :  load == ".." ? "..." :  load == "..." ? "." : ".";
                Tv_To.setText(load);
                handler.postDelayed(this, 500);
            }
        }, 500);
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
            }
        },3000);
    }

    private void showWelcome(){
        Dialog dialog = new Dialog(this,R.style.Theme_PHQ_Market);
        dialog.setContentView( LayoutInflater.from(Activity_Main.this).inflate(R.layout.activity_wellcome,null));
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
}