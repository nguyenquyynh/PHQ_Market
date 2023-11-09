package com.example.phq_market.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_ItemDetail;
import com.example.phq_market.adapter.Adapter_Banner;
import com.example.phq_market.adapter.Adapter_Catalog;
import com.example.phq_market.adapter.Adapter_NewProduct;
import com.example.phq_market.adapter.Adapter_SaleProduct;
import com.example.phq_market.api.api;
import com.example.phq_market.model.CATALOGSHOW;
import com.example.phq_market.model.NEWPRODUCT;
import com.example.phq_market.model.ONLYIMAGE;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Discovery extends Fragment {
    //Slide
    ArrayList<ONLYIMAGE> list_banner;
    ViewPager2 view_banner;
    Adapter_Banner banner;
    private int currenPage = 0;
    private final Handler handler_banner = new Handler();
    private final int delay = 2000;
    //==============================================
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
        list_banner = new ArrayList<>();
        view_banner = view.findViewById(R.id.Banner);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        Retrofit retrofit_banner = new Retrofit.Builder()
                .baseUrl("https://phqmarket.000webhostapp.com/banner/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api api_banner = retrofit_banner.create(api.class);
        Call<ArrayList<ONLYIMAGE>> call_banner = api_banner.get_listbanner();
        call_banner.enqueue(new Callback<ArrayList<ONLYIMAGE>>() {
            @Override
            public void onResponse(Call<ArrayList<ONLYIMAGE>> call, Response<ArrayList<ONLYIMAGE>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<ONLYIMAGE> list = response.body();
                    list_banner.clear();
                    list_banner.addAll(list);
                    banner.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ONLYIMAGE>> call, Throwable t) {

            }
        });

        banner = new Adapter_Banner(list_banner, getContext());
        view_banner.setAdapter(banner);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currenPage == banner.getItemCount() -1) {
                    currenPage = 0;
                } else currenPage++;
                view_banner.setCurrentItem(currenPage);
                handler_banner.postDelayed(this, delay);
            }
        };
        handler_banner.postDelayed(runnable, delay);
        view_banner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handler_banner.removeCallbacks(runnable);
                return false;
            }
        });
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