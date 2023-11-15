package com.example.phq_market.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.phq_market.model.ACCOUNT;
import com.example.phq_market.model.CART;
import com.example.phq_market.model.CHECKOUT;
import com.example.phq_market.model.CUSTOMER;
import com.example.phq_market.model.PURCHASE;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Checkout extends AppCompatActivity {

    private TextView Txt_totalCostItem;
    private TextView Txt_transportFee;
    private TextView Txt_totalPayment;
    private TextView Txt_totalPaymentPart2;
    private TextView Txt_name;
    private TextView Txt_adress;
    private TextView Txt_phone;
    private RadioButton Chk_direct;
    private RadioButton Chk_Online;
    private RecyclerView rcvListPurchase;
    private Adapter_Checkout adapter_checkout;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<CHECKOUT> list;
    private ArrayList<PURCHASE> listCart;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    private String street;
    DecimalFormat formatter = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout);
        ImageView img_back = findViewById(R.id.Img_back);
        Txt_name = findViewById(R.id.Txt_name);
        Txt_adress = findViewById(R.id.Txt_adress);
        Txt_phone = findViewById(R.id.Txt_phone);
        rcvListPurchase = findViewById(R.id.rcvListPurchase);
        Chk_direct = findViewById(R.id.Chk_direct);
        Chk_Online = findViewById(R.id.Chk_Online);
        Txt_totalCostItem = findViewById(R.id.Txt_totalCostItem);
        Txt_transportFee = findViewById(R.id.Txt_transportFee);
        Txt_totalPayment = findViewById(R.id.Txt_totalPayment);
        Txt_totalPaymentPart2 = findViewById(R.id.Txt_totalPaymentPart2);
        Button Btn_order = findViewById(R.id.Btn_order);

        Intent intent = getIntent();
        listCart = (ArrayList<PURCHASE>) intent.getSerializableExtra("list_purchase");

        list = new ArrayList<>();
        getList(new Gson().toJson(listCart));
        Log.e("--->>>>>",new Gson().toJson(listCart)+"");
        Btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpaymenttolist();
                if(street == null){
                    Toast.makeText(Activity_Checkout.this, "Hãy vào cài đặt để thêm địa chỉ khi đặt hàng", Toast.LENGTH_SHORT).show();
                }else {
                    String cart = new Gson().toJson(listCart);
                    addpurchase(cart);
                }

            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void addpaymenttolist(){
        for(PURCHASE pu: listCart){
            pu.setPAYMENT(checkpayment());
        }
    }

    private int checkpayment(){
        return Chk_direct.isChecked() ? 1 : 0;
    }

    private void updatecost(){
        float totalCostItem = 0;
        float Fee = 30000;
        float totalPayment = 0;
        for (CHECKOUT pu : list){
            totalCostItem+=pu.getPRICE()* pu.getQUANTITY();
        }
        totalPayment = totalCostItem + Fee;

        Txt_totalCostItem.setText(formatter.format(totalCostItem));
        Txt_totalPayment.setText(formatter.format(totalPayment)+" VND");
        Txt_transportFee.setText(formatter.format(Fee));
        Txt_totalPaymentPart2.setText(formatter.format(totalPayment));
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
                            startActivity(new Intent(Activity_Checkout.this, Activity_Main.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog.dismiss();
                        startActivity(new Intent(Activity_Checkout.this, Activity_Main.class));
                        Log.e("----->",t+"");
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
                            updatecost();
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

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);
                String email = sharedPreferences.getString("Email",null);
                String pass = sharedPreferences.getString("Pass",null);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/account/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api Api  = retrofit.create(api.class);
                Call<ACCOUNT> call = Api.getDetailAccount(email,pass);
                call.enqueue(new Callback<ACCOUNT>() {
                    @Override
                    public void onResponse(Call<ACCOUNT> call, Response<ACCOUNT> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            ACCOUNT acc = response.body();
                            Txt_name.setText("Name: "+acc.getNAME());
                            street = acc.getADDRESS();
                            Txt_adress.setText("Address: "+street);
                            Txt_phone.setText("Phone: "+acc.getPHONE());
                        }
                    }

                    @Override
                    public void onFailure(Call<ACCOUNT> call, Throwable t) {

                    }
                });
            }
        }).start();
    }
}