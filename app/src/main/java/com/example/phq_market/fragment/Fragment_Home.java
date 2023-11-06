package com.example.phq_market.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_ItemDetail;
import com.example.phq_market.adapter.Adapter_NewProduct;
import com.example.phq_market.adapter.Adapter_PopularProduct;
import com.example.phq_market.api.api;
import com.example.phq_market.model.NEWPRODUCT;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Home extends Fragment {
    private Adapter_PopularProduct adapterPopularProduct;
    private GridLayoutManager LayoutManager;
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


        Button btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_ItemDetail.class));
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
                            Toast.makeText(getContext(), "THnafh công"+list.size(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }else {
                            Toast.makeText(getContext(), "Lỗi 1", Toast.LENGTH_SHORT).show();
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
    }
}