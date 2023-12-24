package com.example.phq_market.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_Checkout;
import com.example.phq_market.activity.Activity_Login;
import com.example.phq_market.adapter.Adapter_Cart;
import com.example.phq_market.api.api;
import com.example.phq_market.model.CART;
import com.example.phq_market.model.CARTCHECKBOX;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_Cart extends Fragment {

    private RecyclerView rcvCart;
    private ArrayList<CARTCHECKBOX> list_CARTCHECKBOX;
    private Adapter_Cart adapterCart;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences sharedPreferences;
    private  String email;
    private String pass;
    private TextView tvCost;
    private Dialog dialog;
    private String url = api.url;
    private DecimalFormat formatter = new DecimalFormat("#,###");
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
        list_CARTCHECKBOX = new ArrayList<>();
        tvCost = view.findViewById(R.id.tvCost);

        linearLayoutManager = new LinearLayoutManager(getContext());
        rcvCart.setLayoutManager(linearLayoutManager);
        adapterCart = new Adapter_Cart(getContext(),list_CARTCHECKBOX);
        rcvCart.setAdapter(adapterCart);

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CARTCHECKBOX> list_purchase = new ArrayList<>();
                for (CARTCHECKBOX ca : list_CARTCHECKBOX){
                    if(ca.isCheck()){
                        list_purchase.add(ca);
                    }
                }
                if(list_purchase.size()>0){
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

    private void setPrice(){
        tvCost.setText("0");
        float sum = 0;
        for(CARTCHECKBOX ca : list_CARTCHECKBOX){
            if (ca.isCheck()){
                sum += ca.getPRICE()*ca.getQUANTITY();
            }
        }
        tvCost.setText(formatter.format(sum)+"");
    }

    @Override
    public void onResume() {
        super.onResume();
        tvCost.setText("0");
        showLoading();

        getLiset();
    }

    private void getLiset(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit_catalog = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api api_cart = retrofit_catalog.create(api.class);

                email = sharedPreferences.getString("Email","null");
                pass = sharedPreferences.getString("Pass","null");
                Call<ArrayList<CART>> call = api_cart.get_Listcart(email,pass);
                call.enqueue(new Callback<ArrayList<CART>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CART>> call, Response<ArrayList<CART>> response) {
                        if(response.isSuccessful() && response.body() != null){
                            ArrayList<CART> list = response.body();
                            if(list.size()>0){
                                list_CARTCHECKBOX.clear();
                                addlist(list);
                                adapterCart.notifyDataSetChanged();
                                adapterCart.SetCheckKerListener(new Adapter_Cart.CheckKerListener() {
                                    @Override
                                    public void check(int position, boolean check) {
                                        list_CARTCHECKBOX.get(position).setCheck(check);
                                        setPrice();
                                        adapterCart.notifyDataSetChanged();
                                    }
                                });

                                adapterCart.SetUpdateQuantity(new Adapter_Cart.UpdateQuantity() {
                                    @Override
                                    public void quantity(int position, int quantity) {
                                        list_CARTCHECKBOX.get(position).setQUANTITY(quantity);
                                        adapterCart.notifyDataSetChanged();
                                        setPrice();
                                    }
                                });

                                adapterCart.OnDeleteProductListener(new Adapter_Cart.DeleteProduct() {
                                    @Override
                                    public void delete(int position) {
                                        deleteItemCart(position);
                                        setPrice();
                                    }
                                });
                            }
                            dialog.cancel();
                        }else {
                            list_CARTCHECKBOX.clear();
                            adapterCart.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CART>> call, Throwable t) {
                        list_CARTCHECKBOX.clear();
                        adapterCart.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

            }
        }).start();
    }

    private void deleteItemCart(Integer id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit_catalog = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api api_cart = retrofit_catalog.create(api.class);
                Call<String> call = api_cart.delete_ItemInCart(id);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String a = response.body();
                        Toast.makeText(getContext(), a+"", Toast.LENGTH_SHORT).show();
                        getLiset();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });

            }
        }).start();
    }
    private String load;
    private void showLoading(){
        dialog = new Dialog(getContext(),R.style.Theme_PHQ_Market);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_wellcome,null);
        dialog.setContentView(view);

        TextView Tv_Well = view.findViewById(R.id.Tv_Well);
        TextView Tv_To = view.findViewById(R.id.Tv_To);

        Tv_Well.setText("Loading");
        Tv_To.setText(".");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                load = load == "." ? ".." :  load == ".." ? "..." :  "." ;
                Tv_To.setText(load);
                handler.postDelayed(this, 500);
            }
        }, 500);
        dialog.show();
    }

    private void addlist(ArrayList<CART> list) {
        for(CART a: list){
            list_CARTCHECKBOX.add(new CARTCHECKBOX(a.getID(),a.getNAME(),a.getPRICE(),a.getQUANTITY(),a.getIMG(),a.getPRODUCTQUANTITY(),false));
        }
    }
}