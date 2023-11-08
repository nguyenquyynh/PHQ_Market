package com.example.phq_market.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_ItemDetail;
import com.example.phq_market.adapter.Adapter_Catalog;
import com.example.phq_market.adapter.Adapter_NewProduct;
import com.example.phq_market.adapter.Adapter_SaleProduct;
import com.example.phq_market.api.api;
import com.example.phq_market.model.CATALOGSHOW;
import com.example.phq_market.model.NEWPRODUCT;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Discovery extends Fragment {

    RecyclerView Recycler_viewnew, Recycler_view_catalog, Recycler_viewbest;
    ArrayList<NEWPRODUCT> list_product, list_saleproduct;
    ArrayList<CATALOGSHOW> list_catalog;
    LinearLayoutManager layoutManager, layoutManager_catalog, layoutManager_sale;
    Adapter_Catalog adapter_catalog;
    Adapter_NewProduct adapter_newProduct;
    Adapter_SaleProduct adapter_saleProduct;
    ProgressDialog progressDialog_catalog, progressDialog, progressDialog_saleproduct;
    Handler handler = new Handler();
    Handler handler_catalog = new Handler();
    Handler handler_saleproduct = new Handler();

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
        Recycler_viewbest = view.findViewById(R.id.Recycler_viewbest);
        list_product = new ArrayList<>();
        list_catalog = new ArrayList<>();
        list_saleproduct = new ArrayList<>();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        //hiển thị danh mục
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
                            progressDialog_catalog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<CATALOGSHOW>> call, Throwable t) {
                        Log.d(">>>>>>>>>>>>>>>>>>>", t.getMessage());
                    }
                });

            }
        }).start();
        layoutManager_catalog = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        Recycler_view_catalog.setLayoutManager(layoutManager_catalog);
        adapter_catalog = new Adapter_Catalog(list_catalog,getContext());
        Recycler_view_catalog.setAdapter(adapter_catalog);
//        ===============================================New Product===================================================
        //hiển thị sản phẩm mới
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
                Call<ArrayList<NEWPRODUCT>> call = api_product.get_Listnewproduct();
                call.enqueue(new Callback<ArrayList<NEWPRODUCT>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NEWPRODUCT>> call, Response<ArrayList<NEWPRODUCT>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ArrayList<NEWPRODUCT> list = response.body();
                            list_product.clear();
                            list_product.addAll(list);
                            adapter_newProduct.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<NEWPRODUCT>> call, Throwable t) {
                        Log.d(">>>>>>>>>>>>>>>>>>>>>>>", t.getMessage());
                    }
                });

            }
        }).start();
        layoutManager = new LinearLayoutManager(getContext());
        Recycler_viewnew.setLayoutManager(layoutManager);
        adapter_newProduct = new Adapter_NewProduct(list_product,getContext());
        Recycler_viewnew.setAdapter(adapter_newProduct);
//        ===============================================Catagori===================================================
        //HIển thị best sale của app
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler_saleproduct.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog_saleproduct = new ProgressDialog(getContext());
                        progressDialog_saleproduct.setMessage("Dữ liệu đang chạy");
                        progressDialog_saleproduct.setCancelable(false);
                        progressDialog_saleproduct.show();
                    }
                });
                Retrofit retrofit_sale = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/product/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api api_sale = retrofit_sale.create(api.class);
                Call<ArrayList<NEWPRODUCT>> call_sale = api_sale.get_listbestsaleproduct();
                call_sale.enqueue(new Callback<ArrayList<NEWPRODUCT>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NEWPRODUCT>> call, Response<ArrayList<NEWPRODUCT>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ArrayList<NEWPRODUCT> list = response.body();
                            list_saleproduct.clear();
                            list_saleproduct.addAll(list);
                            adapter_saleProduct.notifyDataSetChanged();
                            progressDialog_saleproduct.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<NEWPRODUCT>> call, Throwable t) {
                        Log.d(">>>>>>>>>>>>>>>>>>>>>>>", t.getMessage());
                    }
                });

            }
        }).start();
        layoutManager_sale= new LinearLayoutManager(getContext());
        Recycler_viewbest.setLayoutManager(layoutManager_sale);
        adapter_saleProduct = new Adapter_SaleProduct(list_saleproduct,getContext());
        Recycler_viewbest.setAdapter(adapter_saleProduct);
//=======================================================Best saler====================================================

        adapter_newProduct.setOnClickProduct(new Adapter_NewProduct.OnClickProduct() {
            @Override
            public void clickproduct(int ID) {
                Intent intent = new Intent(getContext(), Activity_ItemDetail.class);
                intent.putExtra("IDPRODUCT", ID);
                startActivity(intent);
            }
        });
        adapter_saleProduct.setOnClickProduct(new Adapter_SaleProduct.OnClickProduct() {
            @Override
            public void clickproduct(int ID) {
                Intent intent = new Intent(getContext(), Activity_ItemDetail.class);
                intent.putExtra("IDPRODUCT", ID);
                startActivity(intent);
            }
        });
    }

}