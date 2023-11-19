package com.example.phq_market.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_Order_Confirm;
import com.example.phq_market.adapter.Adapter_Order_Done;
import com.example.phq_market.adapter.Adapter_Order_Shipping;
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
    private LinearLayout Tab_selected;
    private Adapter_Order_Done adapterOrderFeedBack;
    private Adapter_Order_Confirm adapterOrderConfirm;
    private Adapter_Order_Shipping adapterOrderShipping;
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
        Tab_selected = findViewById(R.id.Tab_selected);

        sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);

        listOrder = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(Activity_Status.this);
        Rcv_status.setLayoutManager(linearLayoutManager);

        // bắt đầu vào thì hiển thị list của confirm
        Confirming();
        setSelected(Tv_Confirming);
        Tab_selected.animate().translationX(0).setDuration(2000).start();

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
                setSelected(Tv_Confirming);
                Tab_selected.animate().translationX(0).setDuration(2000).start();
            }
        });

        Tv_Shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipping();
                setSelected(Tv_Shipping);
                Tab_selected.animate().translationX(240).setDuration(2000).start();
            }
        });
        Tv_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
                setSelected(Tv_Done);
                Tab_selected.animate().translationX(480).setDuration(2000).start();
            }
        });

        Tv_Canceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                setSelected(Tv_Canceled);
                Tab_selected.animate().translationX(720).setDuration(2000).start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void setSelected(TextView tv){
        setAllNon();
        tv.setTextColor(Color.YELLOW);
        tv.setBackgroundColor(Color.argb(255,56,151,46));
    }

    private void setAllNon(){
        Tv_Confirming.setBackgroundColor(Color.WHITE);
        Tv_Confirming.setTextColor(Color.BLACK);

        Tv_Canceled.setBackgroundColor(Color.WHITE);
        Tv_Canceled.setTextColor(Color.BLACK);

        Tv_Done.setBackgroundColor(Color.WHITE);
        Tv_Done.setTextColor(Color.BLACK);

        Tv_Shipping.setBackgroundColor(Color.WHITE);
        Tv_Shipping.setTextColor(Color.BLACK);
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
                                adapterOrderShipping = new Adapter_Order_Shipping(Activity_Status.this,listOrder);
                                Rcv_status.setAdapter(adapterOrderShipping);
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
                                adapterOrderFeedBack = new Adapter_Order_Done(Activity_Status.this,listOrder);
                                Rcv_status.setAdapter(adapterOrderFeedBack);
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
                                adapterOrderShipping = new Adapter_Order_Shipping(Activity_Status.this,listOrder);
                                Rcv_status.setAdapter(adapterOrderShipping);
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
                                adapterOrderConfirm = new Adapter_Order_Confirm(Activity_Status.this,listOrder);
                                Rcv_status.setAdapter(adapterOrderConfirm);
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