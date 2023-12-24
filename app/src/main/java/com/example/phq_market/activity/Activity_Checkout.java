package com.example.phq_market.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_Checkout;
import com.example.phq_market.adapter.Adapter_Recycleview;
import com.example.phq_market.api.api;
import com.example.phq_market.model.ACCOUNT;
import com.example.phq_market.model.CARTCHECKBOX;
import com.example.phq_market.model.CHECKOUT;
import com.example.phq_market.select_adress.City;
import com.example.phq_market.select_adress.Districts;
import com.example.phq_market.select_adress.Wards;
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
    private RelativeLayout Rlt_Adress;
    private RadioButton Chk_direct;
    private RadioButton Chk_Online;
    private RecyclerView rcvListPurchase;
    private Adapter_Checkout adapter_checkout;
    private LinearLayoutManager linearLayoutManager;

    private ArrayList<CARTCHECKBOX> listCart;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    DecimalFormat formatter = new DecimalFormat("#,###");
    private static final String API_URL = "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/";
    private RecyclerView rcv;
    private ArrayList<City> city;
    private ArrayList<Districts> districts;
    private ArrayList<Wards> wards;
    private Adapter_Recycleview adapter_recycleview;
    private ArrayList<String> listString;
    private int posotioncity;
    private int positiondisstric;
    private TextView Txt_city;
    private TextView Txt_districs ;
    private TextView Txt_ward ;
    private EditText Edt_DetailAddress;
    private String url = api.url;
    private String email;
    private String pass;


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
        Rlt_Adress = findViewById(R.id.Rlt_Adress);
        Button Btn_order = findViewById(R.id.Btn_order);

        Intent intent = getIntent();
        listCart = (ArrayList<CARTCHECKBOX>) intent.getSerializableExtra("list_purchase");


        adapter_checkout = new Adapter_Checkout(Activity_Checkout.this,listCart);
        linearLayoutManager = new LinearLayoutManager(Activity_Checkout.this);
        rcvListPurchase.setLayoutManager(linearLayoutManager);
        rcvListPurchase.setAdapter(adapter_checkout);
        updatecost();

        Btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_adress.getText().toString().equals("null") || Txt_adress.getText().toString().isEmpty() ){
                    Toast.makeText(Activity_Checkout.this, "Hãy thêm địa chỉ khi đặt hàng", Toast.LENGTH_SHORT).show();
                } else if (Chk_Online.isChecked()) {
                    String cart = new Gson().toJson(changeList());
                    Intent giatien =new Intent(Activity_Checkout.this, Acitivity_Select_Payment_with.class);
                    giatien.putExtra("cosst",(int)totalPayment+"");
                    giatien.putExtra("payment",checkpayment());
                    giatien.putExtra("lisst",cart);
                    giatien.putExtra("diachi", Txt_adress.getText().toString());
                    startActivity(giatien);

                } else {
                    String cart = new Gson().toJson(changeList());
                    Log.e("--------->",cart);
                    addpurchase(cart);
                }

            }
        });

        Rlt_Adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAdress();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void editAdress(){
        Dialog dialog = new Dialog(Activity_Checkout.this, R.style.Theme_PHQ_Market);
        View view = LayoutInflater.from(Activity_Checkout.this).inflate(R.layout.layout_select_adress,null);
        dialog.setContentView(view);
        Txt_city = view.findViewById(R.id.Txt_city);
        Txt_districs = view.findViewById(R.id.Txt_districs);
        Txt_ward = view.findViewById(R.id.Txt_ward);
        Edt_DetailAddress = view.findViewById(R.id.Edt_DetailAddress);
        Button Btn_Confirm = view.findViewById(R.id.Btn_Confirm);
        ImageView Img_Cancel = view.findViewById(R.id.Img_Cancel);
        rcv = view.findViewById(R.id.rcv);

        city = new ArrayList<>();
        districts = new ArrayList<>();
        wards = new ArrayList<>();

        getListAddress();

        Img_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        Txt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Txt_districs.setVisibility(View.GONE);
                Txt_districs.setText("");
                Txt_ward.setVisibility(View.GONE);
                Txt_ward.setText("");

                rcv.setVisibility(View.VISIBLE);
                listString.clear();
                listString.addAll(listStringCity());
                adapter_recycleview.notifyDataSetChanged();
            }
        });

        Txt_districs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Txt_ward.setVisibility(View.GONE);
                Txt_ward.setText("");
                rcv.setVisibility(View.VISIBLE);
                listString.clear();
                listString.addAll(listStringDistrics(posotioncity));
                adapter_recycleview.notifyDataSetChanged();
            }
        });

        Txt_ward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcv.setVisibility(View.VISIBLE);
                listString.clear();
                listString.addAll(listStringWards(positiondisstric));
            }
        });

        Btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Edt_DetailAddress.getText().toString().isEmpty() || Txt_ward.getText().toString().isEmpty() || Txt_districs.getText().toString().isEmpty() || Txt_city.getText().toString().isEmpty()){
                    Toast.makeText(Activity_Checkout.this, "Hãy chọn địa chỉ ", Toast.LENGTH_SHORT).show();
                }else {
                    Txt_adress.setText(Edt_DetailAddress.getText().toString() + ", "+Txt_ward.getText().toString()+", "+Txt_districs.getText().toString()+", "+Txt_city.getText().toString());
                    dialog.cancel();
                }

            }
        });


        dialog.show();
    }

    private ArrayList<String> listStringCity(){
        ArrayList<String> string = new ArrayList<>();
        for (City a: city){
            string.add(a.getName());
        }
        return string;
    }

    private ArrayList<String> listStringDistrics(int posotion){
        ArrayList<String> string = new ArrayList<>();
        districts.clear();
        districts.addAll(city.get(posotion).getDistricts());
        for (Districts a: districts){
            string.add(a.getName());
        }
        return string;
    }
    private ArrayList<String> listStringWards(int posotion){
        ArrayList<String> string = new ArrayList<>();
        wards.clear();
        wards.addAll(districts.get(posotion).getWards());
        for (Wards a: wards){
            string.add(a.getName());
        }
        return string;
    }

    private void getListAddress(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_Checkout.this);
        rcv.setLayoutManager(linearLayoutManager);
        listString = new ArrayList<>();
        adapter_recycleview= new Adapter_Recycleview(Activity_Checkout.this, listString);
        rcv.setAdapter(adapter_recycleview);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api api = retrofit.create(com.example.phq_market.api.api.class);
        Call<ArrayList<City>> call = api.get_listAdress();
        call.enqueue(new Callback<ArrayList<City>>() {
            @Override
            public void onResponse(Call<ArrayList<City>> call, Response<ArrayList<City>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    ArrayList<City> cities = response.body();
                    city.clear();
                    city.addAll(cities);
                    listString.clear();
                    listString.addAll(listStringCity());
                    adapter_recycleview.notifyDataSetChanged();
                    adapter_recycleview.onGetIDListener(new Adapter_Recycleview.GetIDListener() {
                        @Override
                        public void id(int posotion, String name) {
                            if(Txt_districs.getVisibility() == View.GONE){
                                Txt_city.setVisibility(View.VISIBLE);
                                Txt_districs.setVisibility(View.VISIBLE);
                                Txt_city.setText(name);

                                posotioncity = posotion;
                                listString.clear();
                                listString.addAll(listStringDistrics(posotion));
                                adapter_recycleview.notifyDataSetChanged();
                            } else if (Txt_ward.getVisibility() == View.GONE) {
                                Txt_districs.setVisibility(View.VISIBLE);
                                Txt_ward.setVisibility(View.VISIBLE);
                                Txt_districs.setText(name);

                                positiondisstric = posotion;

                                listString.clear();
                                listString.addAll(listStringWards(posotion));
                                adapter_recycleview.notifyDataSetChanged();
                            } else{
                                Txt_ward.setText(name);

                                rcv.setVisibility(View.GONE);
                                adapter_recycleview.notifyDataSetChanged();
                            }

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<City>> call, Throwable t) {

            }
        });
    }

    private int checkpayment(){
        return Chk_direct.isChecked() ? 1 : 0;
    }
    float totalPayment;
    private void updatecost(){
        float totalCostItem = 0;
        float Fee = 30000;
        for (CARTCHECKBOX pu : listCart){
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
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                api api_cart = retrofit_catalog.create(api.class);
                Call<String> call = api_cart.add_Purchase(cart,Txt_adress.getText().toString().replace("/","!"),email,pass,checkpayment());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful() && response.body() !=null){
                            progressDialog.dismiss();
                            startActivity(new Intent(Activity_Checkout.this, Activity_BuySuccess.class));
                        }else {
                            progressDialog.dismiss();
                            Log.e("--------->",response.body()+"");
                            startActivity(new Intent(Activity_Checkout.this, Activity_BuySuccess.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog.dismiss();
                        startActivity(new Intent(Activity_Checkout.this, Activity_BuySuccess.class));
                        Log.e("----->",t+"");
                    }
                });
            }
        }).start();
    }

    private void getPersonalInformation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);
                email = sharedPreferences.getString("Email",null);
                pass = sharedPreferences.getString("Pass",null);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
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
                            Txt_adress.setText(acc.getADDRESS());
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

    private ArrayList<CHECKOUT> changeList(){
        ArrayList<CHECKOUT> change = new ArrayList<>();

        for (CARTCHECKBOX a: listCart) {
            change.add(new CHECKOUT(a.getID(),a.getQUANTITY()));
        }

        return change;
    }

    @Override
    protected void onResume() {
        super.onResume();

        getPersonalInformation();
    }
}