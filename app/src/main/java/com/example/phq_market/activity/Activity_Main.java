package com.example.phq_market.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.phq_market.R;
import com.example.phq_market.fragment.Fragment_Cart;
import com.example.phq_market.fragment.Fragment_Discovery;
import com.example.phq_market.fragment.Fragment_Home;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Activity_Main extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    TextView Title_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.Bottom_navigation);
        frameLayout = findViewById(R.id.Frame_layout);
        Title_fragment = findViewById(R.id.Title_fragment);

        changeFragment(new Fragment_Home());
        Title_fragment.setText("Home");

    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.Frame_layout, new Fragment_Home()).commit();
                    Title_fragment.setText("Like");
                }
                if (item.getItemId() == R.id.Account) {
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.Frame_layout, new Fragment_Home()).commit();
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