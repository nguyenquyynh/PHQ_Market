package com.example.phq_market.api;

import com.example.phq_market.model.CART;
import com.example.phq_market.model.CATALOG;
import com.example.phq_market.model.CATALOGSHOW;
import com.example.phq_market.model.IDCUSTOMER;
import com.example.phq_market.model.NEWPRODUCT;
import com.example.phq_market.model.PRODUCT;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface api {
    @GET("login.php")
    Call<IDCUSTOMER> login(@Query("NAME") String name , @Query("PASS") String pass);
    @GET("register.php")
    Call<String> register(@Query("NAME") String name , @Query("PASS") String pass, @Query("PHONE") int phone,@Query("EMAIL") String email);
    @GET("getlistcatalog.php")
    Call<ArrayList<CATALOGSHOW>> get_Listcatalog();
    @GET("getlistproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_Listproduct();
    @GET("getlistcart.php")
    Call<ArrayList<CART>> get_Listcart(@Query("EMAIL") String name , @Query("PASS") String pass);
    @GET("getlistpopularproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_listpopularproduct();
    @GET("deleteItemInCart.php")
    Call<String> delete_ItemInCart(@Query("IDCART") Integer idcart);
    @GET("updateItemInCart.php")
    Call<String> update_ItemInCart(@Query("IDCART") Integer idcart, @Query("QUANTITY") Integer QUANTITY);

}
