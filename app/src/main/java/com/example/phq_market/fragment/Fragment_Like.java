package com.example.phq_market.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_ItemDetail;
import com.example.phq_market.adapter.Adapter_Like;
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

public class Fragment_Like extends Fragment {
    private ArrayList<NEWPRODUCT> list_like;
    private EditText Edt_Search;
    private ImageButton Btn_filter;
    private RecyclerView Recycler_view;
    private Adapter_Like adapter_like;
    private GridLayoutManager layoutManager;
    private ArrayList<NEWPRODUCT> listsearch;
    private Dialog dialog;
    private LinearLayout Filter_show;
    private ToggleButton Btn_Price;
    private ToggleButton Btn_Name;

    public Fragment_Like() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__like, container, false);
        list_like = new ArrayList<>();
        Edt_Search = view.findViewById(R.id.Edt_Search);
        Btn_filter = view.findViewById(R.id.Btn_filter);
        Recycler_view = view.findViewById(R.id.Recycler_view);
        listsearch = new ArrayList<>();
        Filter_show = view.findViewById(R.id.Filter_show);
        Btn_Price = view.findViewById(R.id.Btn_Price);
        Btn_Name = view.findViewById(R.id.Btn_Name);
        return view;
    }

    private void getListLike(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                if (!sharedPreferences.getString("Email", "").isEmpty() && !sharedPreferences.getString("Pass", "").isEmpty()) {
                    Retrofit retrofit_like = new Retrofit.Builder()
                            .baseUrl("https://phqmarket.online/controller/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    api api_like = retrofit_like.create(api.class);
                    Call<ArrayList<NEWPRODUCT>> call_like = api_like.get_listlike(sharedPreferences.getString("Email", null),sharedPreferences.getString("Pass", null));
                    call_like.enqueue(new Callback<ArrayList<NEWPRODUCT>>() {
                        @Override
                        public void onResponse(Call<ArrayList<NEWPRODUCT>> call, Response<ArrayList<NEWPRODUCT>> response) {
                            if(response.isSuccessful() && response.body()!=null){
                                ArrayList<NEWPRODUCT> list = response.body();
                                listsearch.clear();
                                listsearch.addAll(list);
                                list_like.clear();
                                list_like.addAll(list);
                                adapter_like.notifyDataSetChanged();
                            }
                            dialog.cancel();
                            Log.e("------>",response.body()+"");
                        }

                        @Override
                        public void onFailure(Call<ArrayList<NEWPRODUCT>> call, Throwable t) {
                            dialog.cancel();
                            Log.e("------>",t+"");
                        }
                    });
                }else {
                    dialog.cancel();
                }

            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading();
        Edt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                if (!s1.isEmpty()) {
                    list_like.clear();
                    for (NEWPRODUCT pro: listsearch) {
                        if (pro.getNAME().contains(s1)) {
                            list_like.add(pro);
                        }
                    }
                }else {
                    list_like.clear();
                    list_like.addAll(listsearch);
                }
                adapter_like.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        getListLike();

        layoutManager = new GridLayoutManager(getContext(),2);
        Recycler_view.setLayoutManager(layoutManager);
        adapter_like = new Adapter_Like(list_like,getContext());
        Recycler_view.setAdapter(adapter_like);

        adapter_like.setOnClickProduct(new Adapter_Like.OnClickProduct() {
            @Override
            public void clickproduct(int ID) {
                Intent intent = new Intent(getContext(), Activity_ItemDetail.class);
                intent.putExtra("IDPRODUCT", ID);
                startActivity(intent);
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

    private void sortNameASC(){
        Collections.sort(list_like, new Comparator<NEWPRODUCT>() {
            @Override
            public int compare(NEWPRODUCT o1, NEWPRODUCT o2) {
                return o1.getNAME().compareTo(o2.getNAME());
            }
        });
        adapter_like.notifyDataSetChanged();
    }

    private void sortNameDESC(){
        Collections.sort(list_like, new Comparator<NEWPRODUCT>() {
            @Override
            public int compare(NEWPRODUCT o1, NEWPRODUCT o2) {
                return o2.getNAME().compareTo(o1.getNAME());
            }
        });
        adapter_like.notifyDataSetChanged();
    }

    private void sortPriceASC(){
        Collections.sort(list_like, new Comparator<NEWPRODUCT>() {
            @Override
            public int compare(NEWPRODUCT o1, NEWPRODUCT o2) {
                return o1.getPRICE().compareTo(o2.getPRICE());
            }
        });
        adapter_like.notifyDataSetChanged();
    }

    private void sortPriceDESC(){
        Collections.sort(list_like, new Comparator<NEWPRODUCT>() {
            @Override
            public int compare(NEWPRODUCT o1, NEWPRODUCT o2) {
                return o2.getPRICE().compareTo(o1.getPRICE());
            }
        });
        adapter_like.notifyDataSetChanged();
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
                load = load == "." ? ".." :  load == ".." ? "..." :  ".";
                Tv_To.setText(load);
                handler.postDelayed(this, 500);
            }
        }, 500);
        dialog.show();
    }
}