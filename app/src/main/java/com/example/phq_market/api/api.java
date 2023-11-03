package com.example.phq_market.api;

import com.example.phq_market.model.CATALOG;
import com.example.phq_market.model.CATALOGSHOW;
import com.example.phq_market.model.NEWPRODUCT;
import com.example.phq_market.model.PRODUCT;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface api {

    @GET("getlistcatalog.php")
    Call<ArrayList<CATALOGSHOW>> get_Listcatalog();
    @GET("getlistproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_Listproduct();
}
