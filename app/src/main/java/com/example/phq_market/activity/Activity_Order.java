package com.example.phq_market.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_Order_FeedBack;
import com.example.phq_market.api.api;
import com.example.phq_market.model.NEWPRODUCT;
import com.example.phq_market.model.ORDERANDFEEDBACK;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Order extends AppCompatActivity {
    private RecyclerView rcv_order;
    private Adapter_Order_FeedBack adapterOrderFeedBack;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ORDERANDFEEDBACK> listOrder;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);
        rcv_order = findViewById(R.id.rcv_order);
        listOrder = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://phqmarket.000webhostapp.com/purchase/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String email = sharedPreferences.getString("Email",null);
        String pass = sharedPreferences.getString("Pass",null);

        api Api = retrofit.create(api.class);
        Call<ArrayList<ORDERANDFEEDBACK>> call = Api.get_listOrder(email,pass);
        call.enqueue(new Callback<ArrayList<ORDERANDFEEDBACK>>() {
            @Override
            public void onResponse(Call<ArrayList<ORDERANDFEEDBACK>> call, Response<ArrayList<ORDERANDFEEDBACK>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    ArrayList<ORDERANDFEEDBACK> list = response.body();
                    if(list.size()>0){
                        listOrder.clear();
                        listOrder.addAll(list);
                        linearLayoutManager = new LinearLayoutManager(Activity_Order.this);
                        rcv_order.setLayoutManager(linearLayoutManager);
                        adapterOrderFeedBack = new Adapter_Order_FeedBack(Activity_Order.this,listOrder);
                        rcv_order.setAdapter(adapterOrderFeedBack);
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
}