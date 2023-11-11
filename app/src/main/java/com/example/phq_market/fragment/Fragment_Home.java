package com.example.phq_market.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_ItemDetail;
import com.example.phq_market.adapter.Adapter_Banner;
import com.example.phq_market.adapter.Adapter_Image_Slide_ItemDetail;
import com.example.phq_market.adapter.Adapter_NewProduct;
import com.example.phq_market.adapter.Adapter_PopularProduct;
import com.example.phq_market.api.api;
import com.example.phq_market.model.NEWPRODUCT;
import com.example.phq_market.model.ONLYIMAGE;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Home extends Fragment {
    ArrayList<ONLYIMAGE> list_banner;
    private Adapter_PopularProduct adapterPopularProduct;
    private GridLayoutManager LayoutManager;

    //Slide
    ViewPager2 view_banner;
    Adapter_Banner banner;
    private int currenPage = 0;
    private final Handler handler_banner = new Handler();
    private final int delay = 2500;
    //==============================================
    private ArrayList<NEWPRODUCT> listProduct;
    private  RecyclerView rcv_Home;
    ProgressDialog progressDialog;
    Handler handler = new Handler();
    public Fragment_Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__home, container, false);
        rcv_Home = view.findViewById(R.id.rcv_Home);
        listProduct = new ArrayList<>();
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
                Call<ArrayList<NEWPRODUCT>> call = api_product.get_listpopularproduct();
                call.enqueue(new Callback<ArrayList<NEWPRODUCT>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NEWPRODUCT>> call, Response<ArrayList<NEWPRODUCT>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ArrayList<NEWPRODUCT> list = response.body();
                            listProduct.clear();
                            listProduct.addAll(list);
                            adapterPopularProduct.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }else {
                            Log.d(">>>>>>>>>>>>>>>>>>>", "Lỗi");
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<NEWPRODUCT>> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi !!"+ t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).start();

        LayoutManager = new GridLayoutManager(getContext(),2);
        rcv_Home.setLayoutManager(LayoutManager);
        adapterPopularProduct = new Adapter_PopularProduct(listProduct,getContext());
        rcv_Home.setAdapter(adapterPopularProduct);


        adapterPopularProduct.setOnClickProduct(new Adapter_PopularProduct.OnClickProduct() {
            @Override
            public void clickproduct(int ID) {
                Intent intent = new Intent(getContext(), Activity_ItemDetail.class);
                intent.putExtra("IDPRODUCT", ID);
                startActivity(intent);
            }
        });
    }

}