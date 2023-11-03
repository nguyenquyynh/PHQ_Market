package com.example.phq_market.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.MainThread;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_NewProduct;
import com.example.phq_market.api.api;
import com.example.phq_market.model.NEWPRODUCT;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Discovery extends Fragment {

    public RecyclerView Recycler_viewnew;
    ArrayList<NEWPRODUCT> list_product;
    LinearLayoutManager layoutManager;
    Adapter_NewProduct adapter_newProduct;
    ProgressDialog progressDialog;
    Handler handler = new Handler();

    public Fragment_Discovery() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__discovery, container, false);
        Recycler_viewnew = view.findViewById(R.id.Recycler_viewnew);
        list_product = new ArrayList<>();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Dữ liệu đang chạy");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                });
                get_Listproduct();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog.isShowing()) {

                            layoutManager = new LinearLayoutManager(getContext());
                            Recycler_viewnew.setLayoutManager(layoutManager);
                            adapter_newProduct = new Adapter_NewProduct(list_product,getContext());
                            Recycler_viewnew.setAdapter(adapter_newProduct);

                            progressDialog.dismiss();
                        }
                    }
                },1000);

            }
        }).start();

    }
    public void get_Listproduct() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://phqmarket.000webhostapp.com/product/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api api_product = retrofit.create(api.class);
        Call<ArrayList<NEWPRODUCT>> call = api_product.get_Listproduct();
        call.enqueue(new Callback<ArrayList<NEWPRODUCT>>() {
            @Override
            public void onResponse(Call<ArrayList<NEWPRODUCT>> call, Response<ArrayList<NEWPRODUCT>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<NEWPRODUCT> list = response.body();
                    list_product.clear();
                    list_product.addAll(list);
                    adapter_newProduct.notifyDataSetChanged();
                    Toast.makeText(getContext(), "THnafh công"+list.size(), Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<ArrayList<NEWPRODUCT>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi !!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}