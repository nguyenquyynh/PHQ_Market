package com.example.phq_market.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_Checkout;
import com.example.phq_market.activity.Activity_Login;
import com.example.phq_market.adapter.Adapter_Cart;
import com.example.phq_market.api.api;
import com.example.phq_market.model.CART;
import com.example.phq_market.model.PURCHASE;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_Cart extends Fragment {

    private RecyclerView rcvCart;
    private ArrayList<CART> list_CART;
    private Adapter_Cart adapterCart;
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog progressDialog_cart;
    private Handler handler_cart = new Handler();
    private SharedPreferences sharedPreferences;
    private  String email;
    private String pass;
    public Fragment_Cart() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("account",MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__cart, container, false);
        Button btnCheckOut = view.findViewById(R.id.btnCheckOut);
        rcvCart = view.findViewById(R.id.rcvCart);
        list_CART= new ArrayList<>();

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list_CART.size()>0){
                    ArrayList<PURCHASE> list_purchase = new ArrayList<>();
                    for (CART ca : list_CART){
                        list_purchase.add(new PURCHASE(ca.getID(), email,pass,ca.getQUANTITY()));
                    }
                    Intent intent = new Intent(getContext(), Activity_Checkout.class);
                    intent.putExtra("list_purchase",list_purchase);
                    startActivity(intent);
                } else if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(getContext(), "Hãy đăng nhập", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), Activity_Login.class));
                } else {
                    Toast.makeText(getContext(), "Hãy mua hàng", Toast.LENGTH_SHORT).show();
                }


            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler_cart.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog_cart = new ProgressDialog(getContext());
                        progressDialog_cart.setMessage("Loading......");
                        progressDialog_cart.setCancelable(false);
                        progressDialog_cart.show();
                    }
                });
                Retrofit retrofit_catalog = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/cart/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api api_cart = retrofit_catalog.create(api.class);

                email = sharedPreferences.getString("Email",null);
                pass = sharedPreferences.getString("Pass",null);
                Call<ArrayList<CART>> call = api_cart.get_Listcart(email,pass);
                call.enqueue(new Callback<ArrayList<CART>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CART>> call, Response<ArrayList<CART>> response) {
                       if(response.isSuccessful() && response.body() != null){
                           ArrayList<CART> list = response.body();
                           if(list.size()>0){
                               list_CART.clear();
                               list_CART.addAll(list);
                               adapterCart.notifyDataSetChanged();
                           }

                           progressDialog_cart.dismiss();
                       }else {
                           list_CART.clear();
                           adapterCart.notifyDataSetChanged();
                           progressDialog_cart.dismiss();
                       }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CART>> call, Throwable t) {
                        list_CART.clear();
                        adapterCart.notifyDataSetChanged();
                        progressDialog_cart.dismiss();
                    }
                });

            }
        }).start();

        linearLayoutManager = new LinearLayoutManager(getContext());
        rcvCart.setLayoutManager(linearLayoutManager);
        adapterCart = new Adapter_Cart(getContext(),list_CART);
        rcvCart.setAdapter(adapterCart);
    }
}