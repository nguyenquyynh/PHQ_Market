package com.example.phq_market.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.phq_market.R;
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

public class Activity_Search extends AppCompatActivity {

    private ImageView Img_back;
    private EditText Edt_Search;
    private RecyclerView Recycler_view;
    private ImageButton Btn_filter;
    ArrayList<NEWPRODUCT> list_all;
    ArrayList<NEWPRODUCT> listsearch;
    GridLayoutManager layoutManager;
    Adapter_PopularProduct adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Img_back = findViewById(R.id.Img_back);
        Edt_Search = findViewById(R.id.Edt_Search);
        Recycler_view = findViewById(R.id.Recycler_view);
        Btn_filter = findViewById(R.id.Btn_filter);
        list_all = new ArrayList<>();
        layoutManager = new GridLayoutManager(Activity_Search.this,2);
        adapter = new Adapter_PopularProduct(list_all,Activity_Search.this);
        Recycler_view.setLayoutManager(layoutManager);
        Recycler_view.setAdapter(adapter);
        listsearch = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit_all = new Retrofit.Builder()
                .baseUrl("https://phqmarket.000webhostapp.com/product/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api api_all = retrofit_all.create(api.class);
                Call<ArrayList<NEWPRODUCT>> call_all = api_all.get_listall();
                call_all.enqueue(new Callback<ArrayList<NEWPRODUCT>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NEWPRODUCT>> call, Response<ArrayList<NEWPRODUCT>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ArrayList<NEWPRODUCT> list = response.body();
                            listsearch.clear();
                            list_all.clear();
                            list_all.addAll(list);
                            listsearch.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<NEWPRODUCT>> call, Throwable t) {
                        Log.d(">>>>>>>>>>>>>>", t.getMessage());
                    }
                });
            }
        }).start();

     adapter.setOnClickProduct(new Adapter_PopularProduct.OnClickProduct() {
         @Override
         public void clickproduct(int ID) {
             Intent intent = new Intent(Activity_Search.this, Activity_ItemDetail.class);
             intent.putExtra("IDPRODUCT", ID);
             startActivity(intent);
         }
     });
        Edt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                if (!s1.isEmpty()) {
                    list_all.clear();
                    for (NEWPRODUCT pro: listsearch) {
                        if (pro.getNAME().contains(s1)) {
                            list_all.add(pro);
                        }
                    }
                }else {
                    list_all.clear();
                    list_all.addAll(listsearch);
                }
                Log.e(">>>>>>>>>>>>>>>>", ""+list_all.size() + listsearch.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}