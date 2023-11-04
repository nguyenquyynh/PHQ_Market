package com.example.phq_market.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_Cart;
import com.example.phq_market.adapter.Adapter_Catalog;
import com.example.phq_market.api.api;
import com.example.phq_market.model.CART;
import com.example.phq_market.model.CATALOGSHOW;

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
    private Handler handler_catalog = new Handler();

    public Fragment_Cart() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__cart, container, false);
        rcvCart = view.findViewById(R.id.rcvCart);
        list_CART= new ArrayList<>();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler_catalog.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog_cart = new ProgressDialog(getContext());
                        progressDialog_cart.setMessage("Dữ liệu đang chạy");
                        progressDialog_cart.setCancelable(false);
                        progressDialog_cart.show();
                    }
                });
                Retrofit retrofit_catalog = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/cart/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api api_catalog = retrofit_catalog.create(api.class);
                Call<ArrayList<CART>> call = api_catalog.get_Listcart();
                call.enqueue(new Callback<ArrayList<CART>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CART>> call, Response<ArrayList<CART>> response) {
                       if(response.isSuccessful() && response.body() != null){
                           ArrayList<CART> list = response.body();
                           list_CART.clear();
                           list_CART.addAll(list);
                           adapterCart.notifyDataSetChanged();
                           Toast.makeText(getContext(), "Thành công "+list.size(), Toast.LENGTH_SHORT).show();
                           progressDialog_cart.dismiss();
                       }else {
                           Toast.makeText(getContext(), "lỗi ", Toast.LENGTH_SHORT).show();

                       }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CART>> call, Throwable t) {
                        Toast.makeText(getContext(), "lỗi ", Toast.LENGTH_SHORT).show();
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