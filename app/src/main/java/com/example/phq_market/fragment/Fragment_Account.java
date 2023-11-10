package com.example.phq_market.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_EditAccount;
import com.example.phq_market.activity.Activity_Login;
import com.example.phq_market.activity.Activity_Order;
import com.example.phq_market.activity.Activity_Signup;
import com.example.phq_market.api.api;
import com.example.phq_market.model.ACCOUNT;
import com.example.phq_market.model.EDITACCOUNT;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Account extends Fragment {
    public Fragment_Account() {
    }
    Button btn_setUp;
    ImageView Img_account;
    private TextView Txt_name, Txt_like, Txt_order, Txt_cart;
    private TextView Txt_email;
    private TextView Txt_phone;
    private TextView Txt_address;
    private SharedPreferences sharedPreferences;
    EDITACCOUNT acc;
    SharedPreferences s;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__account, container, false);

        LinearLayout ic_Edit = view.findViewById(R.id.ic_Edit);
        LinearLayout lnOrder = view.findViewById(R.id.lnOrder);
        Img_account = view.findViewById(R.id.Img_account);
        Txt_name = view.findViewById(R.id.Txt_name);
        Txt_like = view.findViewById(R.id.Txt_like);
        Txt_order = view.findViewById(R.id.Txt_order);
        Txt_cart = view.findViewById(R.id.Txt_cart);
        Txt_email = view.findViewById(R.id.Txt_email);
        Txt_phone = view.findViewById(R.id.Txt_phone);
        Txt_address = view.findViewById(R.id.Txt_address);
        btn_setUp = view.findViewById(R.id.btn_setUp);
        acc = new EDITACCOUNT();
        sharedPreferences = getContext().getSharedPreferences("account",MODE_PRIVATE);
        checkLogin(btn_setUp);
        btn_setUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_setUp.getText() == "Log out"){
                    startActivity(new Intent(getContext(), Activity_Login.class));
                }else {
                    startActivity(new Intent(getContext(), Activity_Signup.class));
                }
            }
        });
        ic_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_EditAccount.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("acc",acc);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        lnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_Order.class));
            }
        });
        return view;
    }

    void checkLogin(Button btn_setUp){
        try {
            String email = sharedPreferences.getString("Email",null);
            String pass = sharedPreferences.getString("Pass",null);
            if(email.isEmpty() || pass.isEmpty()){
                btn_setUp.setText("Log in");
            }else {
                btn_setUp.setText("Log out");
            }

        }catch (Exception e){
            Log.e("->>>>>>>>>>>",e+"");
            btn_setUp.setText("Log in");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        s = getContext().getSharedPreferences("account", MODE_PRIVATE);
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (!sharedPreferences.getString("Email", "").isEmpty() && !sharedPreferences.getString("Pass", "").isEmpty()) {
                    Retrofit retrofit_account = new Retrofit.Builder()
                            .baseUrl("https://phqmarket.000webhostapp.com/account/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    api api_account = retrofit_account.create(api.class);
                    Call<ACCOUNT> call_account = api_account.getDetailAccount(s.getString("Email", null), s.getString("Pass", null));
                    call_account.enqueue(new Callback<ACCOUNT>() {
                        @Override
                        public void onResponse(Call<ACCOUNT> call, Response<ACCOUNT> response) {
                            if (response.isSuccessful() && response.body() != null){
                                ACCOUNT account = response.body();
                                Glide.with(getContext())
                                                .load(account.getIMG())
                                                        .into(Img_account);
                                Txt_name.setText(account.getNAME());
                                Txt_email.setText(account.getEMAIL());
                                Txt_phone.setText(account.getPHONE());
                                Txt_address.setText(account.getADDRESS());
                                Txt_like.setText(String.valueOf(account.getLIKED()));
                                Txt_order.setText(String.valueOf(account.getPURCHASE()));
                                Txt_cart.setText(String.valueOf(account.getCART()));
                                // set data
                                acc.setIMG(account.getIMG());
                                acc.setADDRESS(account.getADDRESS());
                                acc.setEMAIL(account.getEMAIL());
                                acc.setNAME(account.getNAME());
                                acc.setPASS(s.getString("Pass",null));
                                acc.setPHONE(account.getPHONE());
                                Log.d(">>>>>>>>>>>>>>>>>>>>>>>", acc+"");
                            } else {
                                Log.d(">>>>>>>>>>>>>>>>>>>>>>>,", response.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<ACCOUNT> call, Throwable t) {

                        }
                    });
                }
            ;}
        }).start();
    }
}
