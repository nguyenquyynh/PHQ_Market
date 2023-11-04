package com.example.phq_market.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.MainThread;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_Catalog;
import com.example.phq_market.adapter.Adapter_NewProduct;
import com.example.phq_market.adapter.Adapter_PopularProduct;
import com.example.phq_market.api.api;
import com.example.phq_market.model.CATALOG;
import com.example.phq_market.model.CATALOGSHOW;
import com.example.phq_market.model.NEWPRODUCT;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Discovery extends Fragment {

    public RecyclerView Recycler_viewnew, Recycler_view_catalog;
    ArrayList<NEWPRODUCT> list_product;
    ArrayList<CATALOGSHOW> list_catalog;
    LinearLayoutManager layoutManager;
    Adapter_NewProduct adapter_newProduct;
    ProgressDialog progressDialog;
    Handler handler = new Handler();

    LinearLayoutManager layoutManager_catalog;
    Adapter_Catalog adapter_catalog;
    ProgressDialog progressDialog_catalog;
    Handler handler_catalog = new Handler();

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
        Recycler_view_catalog = view.findViewById(R.id.Recycler_view_catalog);
        list_product = new ArrayList<>();
        list_catalog = new ArrayList<>();
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
                        progressDialog_catalog = new ProgressDialog(getContext());
                        progressDialog_catalog.setMessage("Dữ liệu đang chạy");
                        progressDialog_catalog.setCancelable(false);
                        progressDialog_catalog.show();
                    }
                });
                Retrofit retrofit_catalog = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/catalog/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api api_catalog = retrofit_catalog.create(api.class);
                Call<ArrayList<CATALOGSHOW>> call = api_catalog.get_Listcatalog();
                call.enqueue(new Callback<ArrayList<CATALOGSHOW>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CATALOGSHOW>> call, Response<ArrayList<CATALOGSHOW>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ArrayList<CATALOGSHOW> list = response.body();
                            list_catalog.clear();
                            list_catalog.addAll(list);
                            adapter_catalog.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Thành công "+list.size(), Toast.LENGTH_SHORT).show();
                            progressDialog_catalog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<CATALOGSHOW>> call, Throwable t) {
                        Toast.makeText(getContext(), "Thất bại !!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).start();

        layoutManager_catalog = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        Recycler_view_catalog.setLayoutManager(layoutManager_catalog);
        adapter_catalog = new Adapter_Catalog(list_catalog,getContext());
        Recycler_view_catalog.setAdapter(adapter_catalog);
//        ===============================================New Product===================================================
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
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<NEWPRODUCT>> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi !!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }).start();

        layoutManager = new LinearLayoutManager(getContext());
        Recycler_viewnew.setLayoutManager(layoutManager);
        adapter_newProduct = new Adapter_NewProduct(list_product,getContext());
        Recycler_viewnew.setAdapter(adapter_newProduct);
    }

}