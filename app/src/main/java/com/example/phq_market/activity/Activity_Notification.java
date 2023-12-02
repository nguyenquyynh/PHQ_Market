package com.example.phq_market.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_Notification;
import com.example.phq_market.api.api;
import com.example.phq_market.model.NOTIFICATION;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Notification extends AppCompatActivity {

    TextView Txt_ReadAll;
    RecyclerView Rcv_Notifi;
    ArrayList<NOTIFICATION> list_notification;
    Adapter_Notification adapter;
    String email;
    String pass;
    String url = api.url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ImageView IMG_Back = findViewById(R.id.IMG_Back);
        Txt_ReadAll = findViewById(R.id.Txt_ReadAll);
        Rcv_Notifi = findViewById(R.id.Rcv_Notifi);

        SharedPreferences sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);
        email = sharedPreferences.getString("Email","");
        pass = sharedPreferences.getString("Pass","");

        list_notification = new ArrayList<>();
        adapter = new Adapter_Notification(Activity_Notification.this,list_notification);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_Notification.this);
        Rcv_Notifi.setAdapter(adapter);
        Rcv_Notifi.setLayoutManager(linearLayoutManager);
        IMG_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Txt_ReadAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activity_Notification.this, "Deleting", Toast.LENGTH_SHORT).show();
                readall();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListNotification();
    }

    private void readall(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api Api = retrofit.create(api.class);
                Call<String> call = Api.readall(email,pass);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        getListNotification();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(Activity_Notification.this, "Check your internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
    private void getListNotification(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api Api = retrofit.create(api.class);
                Call<ArrayList<NOTIFICATION>> call = Api.get_ListNotifcation(email,pass);
                call.enqueue(new Callback<ArrayList<NOTIFICATION>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NOTIFICATION>> call, Response<ArrayList<NOTIFICATION>> response) {
                        if(response.isSuccessful() && response.body() !=null){
                            ArrayList<NOTIFICATION> list = response.body();
                            list_notification.clear();
                            list_notification.addAll(list);
                            adapter.notifyDataSetChanged();
                        }else {
                            Log.e("----------->","looix"+response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<NOTIFICATION>> call, Throwable t) {
                        Toast.makeText(Activity_Notification.this, "Check your internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}