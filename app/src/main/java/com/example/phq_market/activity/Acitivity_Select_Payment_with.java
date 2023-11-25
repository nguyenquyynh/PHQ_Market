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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.momo.momo_partner.AppMoMoLib;
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
        Button Btn_momo = findViewById(R.id.Btn_momo);


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
                payByZalo();
            }
        });

        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        Btn_momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payBymomo();
            }
        });
    }

    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "PHQ market";
    private String merchantCode = "PHQ01";
    private String merchantNameLabel = "PHQ market";
    private String description = "Thanh toán dịch vụ momo";
    private void payBymomo(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(Acitivity_Select_Payment_with.this);
                progressDialog.setMessage("Loading....");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        });

        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", giaTien); //Kiểu integer
        eventValue.put("orderId", "orderId123456789"); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee",giaTien); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        addpurchase(pay);
                        Toast.makeText(Acitivity_Select_Payment_with.this, "Thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("message: ","not_receive_info");
                        Toast.makeText(Acitivity_Select_Payment_with.this, "Đã hủy", Toast.LENGTH_SHORT).show();
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("message: ",message+"");
                    Toast.makeText(Acitivity_Select_Payment_with.this, "Đã hủy", Toast.LENGTH_SHORT).show();
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("message: ","not_receive_info");
                    Toast.makeText(Acitivity_Select_Payment_with.this, "Đã hủy", Toast.LENGTH_SHORT).show();
                } else {
                    //TOKEN FAIL
                    Log.d("message: ","not_receive_info");
                    Toast.makeText(Acitivity_Select_Payment_with.this, "Đã hủy", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("message: ","not_receive_info");
                Toast.makeText(Acitivity_Select_Payment_with.this, "Đã hủy", Toast.LENGTH_SHORT).show();
            }
            progressDialog.cancel();
        } else {
            Log.d("message: ","not_receive_info");
            Toast.makeText(Acitivity_Select_Payment_with.this, "Đã hủy", Toast.LENGTH_SHORT).show();
            progressDialog.cancel();
        }
    }

    private void payByZalo(){
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