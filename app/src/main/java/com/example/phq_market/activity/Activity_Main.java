package com.example.phq_market.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Frame_layout, fragment).commit();
    }
}