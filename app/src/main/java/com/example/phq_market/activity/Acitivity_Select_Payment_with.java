package com.example.phq_market.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phq_market.R;
import com.example.phq_market.api.api;
import com.example.phq_market.zalopay.CreateOrder;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class Acitivity_Select_Payment_with extends AppCompatActivity {
    private String giaTien;
    private String diachi;
    private String pay;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivity_select_payment_with);

        ImageView IMG_Back = findViewById(R.id.IMG_Back);
        Button Btn_zalo = findViewById(R.id.Btn_zalo);

        Intent getCosst = getIntent();
        giaTien = getCosst.getStringExtra("cosst");
        diachi = getCosst.getStringExtra("diachi");
        pay = getCosst.getStringExtra("lisst");
        Log.e("--------------------->",giaTien+diachi+pay);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2553, Environment.SANDBOX);


        IMG_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Btn_zalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(Acitivity_Select_Payment_with.this);
                        progressDialog.setMessage("Loading....");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                });


                try {
                    JSONObject data = orderApi.createOrder(giaTien);
                    String code = data.getString("return_code");
                    Log.d("------------>","return_code: " + code);
                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(Acitivity_Select_Payment_with.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                Toast.makeText(Acitivity_Select_Payment_with.this, "Thành công", Toast.LENGTH_SHORT).show();
                                addpurchase(pay);
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Toast.makeText(Acitivity_Select_Payment_with.this, "Đã hủy", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Toast.makeText(Acitivity_Select_Payment_with.this, "Erorrr", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void addpurchase(String cart) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit_catalog = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.online/controller/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                api api_cart = retrofit_catalog.create(api.class);
                Call<String> call = api_cart.add_Purchase(cart,diachi);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful() && response.body() !=null){
                            progressDialog.cancel();
                            startActivity(new Intent(Acitivity_Select_Payment_with.this, Activity_BuySuccess.class));
                        }else {
                            Log.e("--------->",response.body()+"");
                            startActivity(new Intent(Acitivity_Select_Payment_with.this, Activity_BuySuccess.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        startActivity(new Intent(Acitivity_Select_Payment_with.this, Activity_BuySuccess.class));
                        Log.e("----->",t+"");
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}