package com.example.phq_market.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_Order_FeedBack;
import com.example.phq_market.api.api;
import com.example.phq_market.model.ORDERANDFEEDBACK;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Status extends AppCompatActivity {
    private ImageView Img_back;
    private ImageView Img_notification;
    private TextView Tv_Confirming;
    private TextView Tv_Shipping;
    private TextView Tv_Done;
    private TextView Tv_Canceled;
    private RecyclerView Rcv_status;

    private Adapter_Order_FeedBack adapterOrderFeedBack;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ORDERANDFEEDBACK> listOrder;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Img_back = findViewById(R.id.Img_back);
        Img_notification = findViewById(R.id.Img_notification);
        Tv_Confirming = findViewById(R.id.Tv_Confirming);
        Tv_Shipping = findViewById(R.id.Tv_Shipping);
        Tv_Done = findViewById(R.id.Tv_Done);
        Tv_Canceled = findViewById(R.id.Tv_Canceled);
        Rcv_status = findViewById(R.id.Rcv_status);

        sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);

        listOrder = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(Activity_Status.this);
        Rcv_status.setLayoutManager(linearLayoutManager);
        adapterOrderFeedBack = new Adapter_Order_FeedBack(Activity_Status.this,listOrder);
        Rcv_status.setAdapter(adapterOrderFeedBack);

        Img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Tv_Confirming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirming();
            }
        });

        Tv_Shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipping();
            }
        });
        Tv_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });

        Tv_Canceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void cancel(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/purchase/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                String email = sharedPreferences.getString("Email",null);
                String pass = sharedPreferences.getString("Pass",null);

                api Api = retrofit.create(api.class);
                Call<ArrayList<ORDERANDFEEDBACK>> call = Api.get_listOrderCancel(email,pass);
                call.enqueue(new Callback<ArrayList<ORDERANDFEEDBACK>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ORDERANDFEEDBACK>> call, Response<ArrayList<ORDERANDFEEDBACK>> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            ArrayList<ORDERANDFEEDBACK> list = response.body();
                            if(list.size()>0){
                                listOrder.clear();
                                listOrder.addAll(list);
                                adapterOrderFeedBack.notifyDataSetChanged();
                            }
                        }else {
                            Log.e("------>",response.body()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ORDERANDFEEDBACK>> call, Throwable t) {
                        Log.e("------>",t+"");
                    }
                });
            }
        }).start();
    }


    private void done (){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/purchase/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                String email = sharedPreferences.getString("Email",null);
                String pass = sharedPreferences.getString("Pass",null);

                api Api = retrofit.create(api.class);
                Call<ArrayList<ORDERANDFEEDBACK>> call = Api.get_listOrderDone(email,pass);
                call.enqueue(new Callback<ArrayList<ORDERANDFEEDBACK>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ORDERANDFEEDBACK>> call, Response<ArrayList<ORDERANDFEEDBACK>> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            ArrayList<ORDERANDFEEDBACK> list = response.body();
                            if(list.size()>0){
                                listOrder.clear();
                                listOrder.addAll(list);
                                adapterOrderFeedBack.notifyDataSetChanged();
                            }
                        }else {
                            Log.e("------>",response.body()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ORDERANDFEEDBACK>> call, Throwable t) {
                        Log.e("------>",t+"");
                    }
                });
            }
        }).start();
    }


    private void shipping (){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/purchase/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                String email = sharedPreferences.getString("Email",null);
                String pass = sharedPreferences.getString("Pass",null);

                api Api = retrofit.create(api.class);
                Call<ArrayList<ORDERANDFEEDBACK>> call = Api.get_listOrderShipping(email,pass);
                call.enqueue(new Callback<ArrayList<ORDERANDFEEDBACK>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ORDERANDFEEDBACK>> call, Response<ArrayList<ORDERANDFEEDBACK>> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            ArrayList<ORDERANDFEEDBACK> list = response.body();
                            if(list.size()>0){
                                listOrder.clear();
                                listOrder.addAll(list);
                                adapterOrderFeedBack.notifyDataSetChanged();
                            }
                        }else {
                            Log.e("------>",response.body()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ORDERANDFEEDBACK>> call, Throwable t) {
                        Log.e("------>",t+"");
                    }
                });
            }
        }).start();
    }


    private void Confirming (){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/purchase/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                String email = sharedPreferences.getString("Email",null);
                String pass = sharedPreferences.getString("Pass",null);

                api Api = retrofit.create(api.class);
                Call<ArrayList<ORDERANDFEEDBACK>> call = Api.get_listOrderConfirm(email,pass);
                call.enqueue(new Callback<ArrayList<ORDERANDFEEDBACK>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ORDERANDFEEDBACK>> call, Response<ArrayList<ORDERANDFEEDBACK>> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            ArrayList<ORDERANDFEEDBACK> list = response.body();
                            if(list.size()>0){
                                listOrder.clear();
                                listOrder.addAll(list);
                               adapterOrderFeedBack.notifyDataSetChanged();
                            }
                        }else {
                            Log.e("------>",response.body()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ORDERANDFEEDBACK>> call, Throwable t) {
                        Log.e("------>",t+"");
                    }
                });
            }
        }).start();
    }
}