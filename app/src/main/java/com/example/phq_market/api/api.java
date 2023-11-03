package com.example.phq_market.api;

import com.example.phq_market.model.NEWPRODUCT;
import com.example.phq_market.model.PRODUCT;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface api {

    @GET("getlistproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_Listproduct();
}
