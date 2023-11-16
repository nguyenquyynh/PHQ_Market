package com.example.phq_market.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_ItemDetail;
import com.example.phq_market.activity.Activity_Login;
import com.example.phq_market.adapter.Adapter_Like;
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

public class Fragment_Like extends Fragment {
    private ArrayList<NEWPRODUCT> list_like;
    private EditText Edt_Search;
    private ImageButton Btn_filter;
    private RecyclerView Recycler_view;
    private Adapter_Like adapter_like;
    private GridLayoutManager layoutManager;
    private ArrayList<NEWPRODUCT> listsearch;
    private Dialog dialog;

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
        return view;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                if (!sharedPreferences.getString("Email", "").isEmpty() && !sharedPreferences.getString("Pass", "").isEmpty()) {
                    Retrofit retrofit_like = new Retrofit.Builder()
                            .baseUrl("https://phqmarket.000webhostapp.com/liked/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    api api_like = retrofit_like.create(api.class);
                    Call<ArrayList<NEWPRODUCT>> call_like = api_like.get_listlike(sharedPreferences.getString("Email", null),sharedPreferences.getString("Pass", null));
                    call_like.enqueue(new Callback<ArrayList<NEWPRODUCT>>() {
                        @Override
                        public void onResponse(Call<ArrayList<NEWPRODUCT>> call, Response<ArrayList<NEWPRODUCT>> response) {
                            ArrayList<NEWPRODUCT> list = response.body();
                            listsearch.clear();
                            listsearch.addAll(list);
                            list_like.clear();
                            list_like.addAll(list);
                            adapter_like.notifyDataSetChanged();
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
    }

    private String load;
    private void showLoading(){
        dialog = new Dialog(getContext(),R.style.Theme_PHQ_Market);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_wellcome,null);
        dialog.setContentView(view);

        TextView Tv_Well = view.findViewById(R.id.Tv_Well);
        TextView Tv_To = view.findViewById(R.id.Tv_To);

        Tv_Well.setText("Loading");
        Tv_To.setText(".");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                load = load == "." ? ".." :  load == ".." ? "..." :  load == "..." ? "." : ".";
                Tv_To.setText(load);
                handler.postDelayed(this, 500);
            }
        }, 500);
        dialog.show();
    }
}