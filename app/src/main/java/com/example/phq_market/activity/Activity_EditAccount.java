package com.example.phq_market.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_Avata;
import com.example.phq_market.adapter.Adapter_Like;
import com.example.phq_market.adapter.Adapter_Recycleview;
import com.example.phq_market.api.api;
import com.example.phq_market.model.EDITACCOUNT;
import com.example.phq_market.select_adress.City;
import com.example.phq_market.select_adress.Districts;
import com.example.phq_market.select_adress.Wards;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_EditAccount extends AppCompatActivity {
    private TextInputEditText Edt_fullName, Edt_email, Edt_address, Edt_number, Edt_password;
    private ImageView Img_avata, Img_done, Img_showpass;
    Button Btn_changeavata;
    String oldemail, oldpass;
    ArrayList<String> avatarURLs = new ArrayList<>();
    int stt =0;

    private static final String API_URL = "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/";
    private Adapter_Recycleview adapter_recycleview;
    private ArrayList<String> listString;
    private RecyclerView rcv;
    private ArrayList<City> city;
    private ArrayList<Districts> districts;
    private ArrayList<Wards> wards;
    private int posotioncity;
    private int positiondisstric;
    EditText Edt_DetailAddress;
    private TextView Txt_city;
    private TextView Txt_districs ;
    private TextView Txt_ward ;
    private String url = api.url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        ImageView IMG_Back = findViewById(R.id.IMG_Back);
        Img_done = findViewById(R.id.Img_done);
        Edt_fullName = findViewById(R.id.Edt_fullName);
        Edt_email = findViewById(R.id.Edt_email);
        Edt_address = findViewById(R.id.Edt_address);
        Img_showpass = findViewById(R.id.Img_showpass);
        Edt_number = findViewById(R.id.Edt_number);
        Img_avata = findViewById(R.id.Img_avata);
        Edt_password = findViewById(R.id.Edt_password);
        Btn_changeavata = findViewById(R.id.Btn_changeavata);
        avatarURLs.add("https://img.lovepik.com/free-png/20211130/lovepik-cartoon-avatar-png-image_401205251_wh1200.png");
        avatarURLs.add("https://img.lovepik.com/free-png/20211204/lovepik-cartoon-avatar-png-image_401302777_wh1200.png");
        avatarURLs.add("https://img.lovepik.com/free-png/20211130/lovepik-cartoon-avatar-png-image_401205251_wh1200.png");
        avatarURLs.add("https://img.lovepik.com/free-png/20211204/lovepik-cartoon-avatar-png-image_401302777_wh1200.png");
        avatarURLs.add("https://img.lovepik.com/element/40144/0477.png_860.png");
        avatarURLs.add("https://img.lovepik.com/element/40144/0421.png_860.png");
        avatarURLs.add("https://img.lovepik.com/free-png/20211130/lovepik-cartoon-avatar-png-image_401205594_wh1200.png");
        avatarURLs.add("https://img.lovepik.com/free-png/20211206/lovepik-cartoon-avatar-png-image_401349915_wh1200.png");
        avatarURLs.add("https://png.pngtree.com/png-vector/20190115/ourlarge/pngtree-cartoon-cute-boy-avatar-cartoon-avatar-lovely-png-image_338645.jpg");
        avatarURLs.add("https://img.lovepik.com/element/40120/5298.png_860.png");
        avatarURLs.add("https://img.lovepik.com/free-png/20220108/lovepik-cartoon-avatar-png-image_401306868_wh860.png");
        avatarURLs.add("https://img.lovepik.com/free-png/20210926/lovepik-cartoon-avatar-png-image_401440426_wh1200.png");
        avatarURLs.add("https://png.pngtree.com/element_our/20190528/ourlarge/pngtree-couple-boy-cute-avatar-image_1153281.jpg");
        avatarURLs.add("https://img.lovepik.com/free-png/20211129/lovepik-girl-cartoon-hand-drawn-cute-illustration-avatar-png-image_401193454_wh1200.png");
        avatarURLs.add("https://png.pngtree.com/png-clipart/20201223/ourlarge/pngtree-cute-cartoon-boy-avatar-material-illustration-element-png-png-image_2604806.jpg");
        avatarURLs.add("https://img.lovepik.com/free-png/20211204/lovepik-cartoon-avatar-png-image_401311650_wh1200.png");
        avatarURLs.add("https://hanoitop10.com/wp-content/uploads/2023/02/anh-avatar-cute_10.jpg");
        avatarURLs.add("https://img.lovepik.com/element/40129/1971.png_860.png");
        avatarURLs.add("https://png.pngtree.com/png-clipart/20201225/ourlarge/pngtree-hand-drawn-cartoon-girl-avatar-cute-style-couple-avatar-female-png-image_2592986.jpg");
        avatarURLs.add("https://png.pngtree.com/png-clipart/20220111/original/pngtree-q-cute-little-boy-looking-up-png-image_7091569.png");
        avatarURLs.add("https://png.pngtree.com/png-vector/20190115/ourlarge/pngtree-cartoon-avatar-smiley-face-lovely-png-image_338649.jpg");
        avatarURLs.add("https://img.lovepik.com/free-png/20211231/lovepik-hand-painted-cartoon-male-head-png-image_401090813_wh860.png");
        avatarURLs.add("https://img.lovepik.com/free-png/20210922/lovepik-hand-painted-cartoon-male-head-png-image_401073216_wh1200.png");
        avatarURLs.add("https://png.pngtree.com/element_our/20190529/ourlarge/pngtree-avatar-pattern-flat-cartoon-user-image_1200105.jpg");
        avatarURLs.add("https://img.lovepik.com/original_origin_pic/18/05/19/d7e8f555c5178469f6a1c0cec9c3e874.png_wh860.png");
        avatarURLs.add("https://img.lovepik.com/free-png/20211204/lovepik-girl-avatar-png-image_401305348_wh1200.png");
        avatarURLs.add("https://png.pngtree.com/png-clipart/20220429/original/pngtree-cute-girl-cartoon-avatar-png-image_7577842.png");
        avatarURLs.add("https://png.pngtree.com/png-clipart/20190905/original/pngtree-summer-pineapple-girl-anthropomorphic-cartoon-fruit-avatar-png-image_4546602.jpg");
        avatarURLs.add("https://png.pngtree.com/png-vector/20210128/ourlarge/pngtree-flat-man-head-png-image_2850348.jpg");
        IMG_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            EDITACCOUNT acc = (EDITACCOUNT) bundle.getSerializable("acc");
            try {
                Glide.with(Activity_EditAccount.this)
                        .load(acc.getIMG())
                        .into(Img_avata);
                Edt_fullName.setText(acc.getNAME());
                Edt_email.setText(acc.getEMAIL());
                Edt_address.setText(acc.getADDRESS());
                Edt_number.setText(acc.getPHONE());
                Edt_password.setText(acc.getPASS());
                oldemail = acc.getEMAIL();
                oldpass = acc.getPASS();
            } catch (Exception e) {}
        }

        Img_showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Edt_password.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD){
                    Img_showpass.setImageResource(R.drawable.open_eye);
                    Edt_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    Edt_password.setTransformationMethod(null);
                }else {
                    Img_showpass.setImageResource(R.drawable.close_eye);
                    Edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD );
                    Edt_password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        Edt_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpWriteAdress();
            }
        });
    }

    private void setUpWriteAdress(){
        Dialog dialog = new Dialog(Activity_EditAccount.this, R.style.Theme_PHQ_Market);
        View view = LayoutInflater.from(Activity_EditAccount.this).inflate(R.layout.layout_select_adress,null);
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
                    Toast.makeText(Activity_EditAccount.this, "Hãy chọn địa chỉ ", Toast.LENGTH_SHORT).show();
                }else {
                    Edt_address.setText(Edt_DetailAddress.getText().toString()+ ", " + Txt_ward.getText().toString()+", "+Txt_districs.getText().toString()+", "+Txt_city.getText().toString());
                    dialog.cancel();
                }

            }
        });

        dialog.show();
    }
    private void getListAddress(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_EditAccount.this);
        rcv.setLayoutManager(linearLayoutManager);
        listString = new ArrayList<>();
        adapter_recycleview= new Adapter_Recycleview(Activity_EditAccount.this, listString);
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

    private void showIcon(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_EditAccount.this);
        View view = LayoutInflater.from(Activity_EditAccount.this).inflate(R.layout.layout_item_list_avata,null);
        builder.setTitle("Chọn ảnh đại diện ");
        RecyclerView Recycler_viewavata = view.findViewById(R.id.Recycler_viewavata);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Activity_EditAccount.this,3);
        Adapter_Avata adapter_avata = new Adapter_Avata(avatarURLs, Activity_EditAccount.this);
        Recycler_viewavata.setLayoutManager(gridLayoutManager);
        Recycler_viewavata.setAdapter(adapter_avata);
        builder.setView(view);
        Dialog dialog = builder.create();
        adapter_avata.setOnClickProduct(new Adapter_Like.OnClickProduct() {
            @Override
            public void clickproduct(int ID) {
                Glide.with(Activity_EditAccount.this)
                        .load(avatarURLs.get(ID))
                        .into(Img_avata);
                stt = ID;
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void updateAccount(){
        EDITACCOUNT editacc = new EDITACCOUNT();
        editacc.setIMG(avatarURLs.get(stt));
        editacc.setNAME(Edt_fullName.getText().toString());
        editacc.setEMAIL(Edt_email.getText().toString());
        editacc.setADDRESS(Edt_address.getText().toString());
        editacc.setPHONE(Edt_number.getText().toString());
        editacc.setPASS(Edt_password.getText().toString());
        if (!TextUtils.isEmpty(editacc.getNAME()) && !TextUtils.isEmpty(editacc.getEMAIL()) && !TextUtils.isEmpty(editacc.getPASS()) && !TextUtils.isEmpty(editacc.getADDRESS()) && !TextUtils.isEmpty(editacc.getPHONE()) && !TextUtils.isEmpty(editacc.getIMG())) {
            Retrofit retrofit_acc = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api api_acc = retrofit_acc.create(api.class);
            Call<String> call_acc = api_acc.updatecustomer(editacc.getNAME(),editacc.getEMAIL(),editacc.getPASS(),editacc.getPHONE(),editacc.getADDRESS(),editacc.getIMG(),oldemail, oldpass);
            call_acc.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(Activity_EditAccount.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                        SharedPreferences s = getSharedPreferences("account", MODE_PRIVATE);
                        SharedPreferences.Editor e = s.edit();
                        e.putString("Email",editacc.getEMAIL());
                        e.putString("Pass",editacc.getPASS());
                        e.apply();
                        finish();
                    } else {
                        Toast.makeText(Activity_EditAccount.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d(">>>>>>>>>>>>>>>>>>>>>>>>", t.getMessage());
                }
            });
        } else {
            Toast.makeText(Activity_EditAccount.this, "Looxi gif ddos maf t ddeos bieets", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Btn_changeavata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showIcon();
            }
        });

        Img_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               updateAccount();
            }
        });

    }
}