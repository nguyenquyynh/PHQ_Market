package com.example.phq_market.fragment;

import android.app.AlertDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    ArrayList<NEWPRODUCT> list_like;
    EditText Edt_Search;
    ImageButton Btn_filter;
    RecyclerView Recycler_view;
    Adapter_Like adapter_like;
    GridLayoutManager layoutManager;
    Handler handler = new Handler();
    ProgressDialog progressDialog;

    Button btn;
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
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
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
                            list_like.clear();
                            list_like.addAll(list);
                            adapter_like.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ArrayList<NEWPRODUCT>> call, Throwable t) {

                        }
                    });
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
}