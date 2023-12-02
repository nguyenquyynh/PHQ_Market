package com.example.phq_market.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phq_market.R;
import com.example.phq_market.adapter.Adapter_PopularProduct;
import com.example.phq_market.api.api;
import com.example.phq_market.model.NEWPRODUCT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    private LinearLayout Filter_show;
    private ToggleButton Btn_Price;
    private ToggleButton Btn_Name;
    ArrayList<NEWPRODUCT> list_all;
    ArrayList<NEWPRODUCT> listsearch;
    GridLayoutManager layoutManager;
    Adapter_PopularProduct adapter;
    private String url = api.url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Img_back = findViewById(R.id.Img_back);
        Edt_Search = findViewById(R.id.Edt_Search);
        Recycler_view = findViewById(R.id.Recycler_view);
        Btn_filter = findViewById(R.id.Btn_filter);
        Filter_show = findViewById(R.id.Filter_show);
        Btn_Price = findViewById(R.id.Btn_Price);
        Btn_Name = findViewById(R.id.Btn_Name);

        list_all = new ArrayList<>();
        layoutManager = new GridLayoutManager(Activity_Search.this,2);
        adapter = new Adapter_PopularProduct(list_all,Activity_Search.this);
        Recycler_view.setLayoutManager(layoutManager);
        Recycler_view.setAdapter(adapter);
        listsearch = new ArrayList<>();

        Edt_Search.requestFocus();

        if(getIntent().getIntExtra("IDCATALOG",0)>0){
            getListHasCataLog(getIntent().getIntExtra("IDCATALOG",0));
            Edt_Search.setText(getIntent().getStringExtra("NAMECATALOG"));
        }else {
            getAllList();
        }

        Img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Filter_show.getVisibility() == View.VISIBLE)
                    Filter_show.setVisibility(View.GONE);
                else
                    Filter_show.setVisibility(View.VISIBLE);
            }
        });
        Btn_Price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Btn_Price.isChecked()){
                    sortPriceASC();
                }else {
                    sortPriceDESC();
                }
                Filter_show.setVisibility(View.GONE);
            }
        });

        Btn_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Btn_Name.isChecked()){
                    Btn_Name.setText("Sort name ascending");
                    sortNameASC();
                }else {
                    Btn_Name.setText("Sort name descending");
                    sortNameDESC();
                }
                Filter_show.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

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
                list_all.clear();
                list_all.addAll(listsearch);
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
                    getAllList();
                }
                Log.e(">>>>>>>>>>>>>>>>", ""+list_all.size() + listsearch.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void getAllList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit_all = new Retrofit.Builder()
                        .baseUrl(url)
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
                            listsearch.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<NEWPRODUCT>> call, Throwable t) {
                        Toast.makeText(Activity_Search.this, "Check wifi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    private void getListHasCataLog(int idCatalog){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit_all = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api api_all = retrofit_all.create(api.class);
                Call<ArrayList<NEWPRODUCT>> call_all = api_all.get_listasCaTaLog(idCatalog);
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
                        Toast.makeText(Activity_Search.this, "Check wifi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    private void sortNameASC(){
        Collections.sort(list_all, new Comparator<NEWPRODUCT>() {
            @Override
            public int compare(NEWPRODUCT o1, NEWPRODUCT o2) {
                return o1.getNAME().compareTo(o2.getNAME());
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void sortNameDESC(){
        Collections.sort(list_all, new Comparator<NEWPRODUCT>() {
            @Override
            public int compare(NEWPRODUCT o1, NEWPRODUCT o2) {
                return o2.getNAME().compareTo(o1.getNAME());
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void sortPriceASC(){
        Collections.sort(list_all, new Comparator<NEWPRODUCT>() {
            @Override
            public int compare(NEWPRODUCT o1, NEWPRODUCT o2) {
                return o1.getPRICE().compareTo(o2.getPRICE());
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void sortPriceDESC(){
        Collections.sort(list_all, new Comparator<NEWPRODUCT>() {
            @Override
            public int compare(NEWPRODUCT o1, NEWPRODUCT o2) {
                return o2.getPRICE().compareTo(o1.getPRICE());
            }
        });
        adapter.notifyDataSetChanged();
    }

}