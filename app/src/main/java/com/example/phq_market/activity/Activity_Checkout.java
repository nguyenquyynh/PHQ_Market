package com.example.phq_market.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_Checkout;
import com.example.phq_market.api.api;
import com.example.phq_market.model.CART;
import com.example.phq_market.model.CHECKOUT;
import com.example.phq_market.model.PURCHASE;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Checkout extends AppCompatActivity {

    private RecyclerView rcvListPurchase;
    private Adapter_Checkout adapter_checkout;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<CHECKOUT> list;
    private ArrayList<PURCHASE> listCart;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ImageView img_back = findViewById(R.id.Img_back);
        TextView Txt_name = findViewById(R.id.Txt_name);
        TextView Txt_adress = findViewById(R.id.Txt_adress);
        TextView Txt_phone = findViewById(R.id.Txt_phone);
        rcvListPurchase = findViewById(R.id.rcvListPurchase);
        RadioButton Chk_direct = findViewById(R.id.Chk_direct);
        RadioButton Chk_Online = findViewById(R.id.Chk_Online);
        TextView Txt_totalCostItem = findViewById(R.id.Txt_totalCostItem);
        TextView Txt_transportFee = findViewById(R.id.Txt_transportFee);
        TextView Txt_totalPayment = findViewById(R.id.Txt_totalPayment);
        TextView Txt_totalPaymentPart2 = findViewById(R.id.Txt_totalPaymentPart2);
        Button Btn_order = findViewById(R.id.Btn_order);

        Intent intent = getIntent();
        listCart = (ArrayList<PURCHASE>) intent.getSerializableExtra("list_purchase");

        list = new ArrayList<>();
        getList(new Gson().toJson(listCart));
        Log.e("--->>>>>",new Gson().toJson(listCart)+"");
        Btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cart = new Gson().toJson(listCart);
                addpurchase(cart);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addpurchase(String cart) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(Activity_Checkout.this);
                        progressDialog.setMessage("Loading......");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                });

                Retrofit retrofit_catalog = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/purchase/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                api api_cart = retrofit_catalog.create(api.class);
                Call<String> call = api_cart.add_Purchase(cart);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful() && response.body() !=null){
                            progressDialog.dismiss();
                            startActivity(new Intent(Activity_Checkout.this, Activity_Main.class));
                        }else {
                            progressDialog.dismiss();
                            Log.e("----->",response.body()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("----->",t+"");
                        Toast.makeText(Activity_Checkout.this, "error for connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    private void getList(String cart){
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(Activity_Checkout.this);
                        progressDialog.setMessage("Loading......");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                });

                Retrofit retrofit_catalog = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/purchase/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                api api_cart = retrofit_catalog.create(api.class);
                Call<ArrayList<CHECKOUT>> call = api_cart.getCheckOut(cart);
                call.enqueue(new Callback<ArrayList<CHECKOUT>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CHECKOUT>> call, Response<ArrayList<CHECKOUT>> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            ArrayList<CHECKOUT> listcheckot = response.body();
                            Log.e("->>>>>>>>>>",response.body()+"");
                            list.addAll(listcheckot);
                            for(int i=0; i<list.size();i++){
                                list.get(i).setQUANTITY(listCart.get(i).getQUANTITY());
                            }
                            adapter_checkout = new Adapter_Checkout(Activity_Checkout.this,list);
                            linearLayoutManager = new LinearLayoutManager(Activity_Checkout.this);
                            rcvListPurchase.setLayoutManager(linearLayoutManager);
                            rcvListPurchase.setAdapter(adapter_checkout);
                            progressDialog.dismiss();
                        }else {
                            Log.e("->>>>>>>>>>>>>>>>",response.body()+"");
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CHECKOUT>> call, Throwable t) {
                        Log.e("->>>>>>>>>>>>>>>>",t+"");
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();
    }
}