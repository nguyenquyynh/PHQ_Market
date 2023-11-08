package com.example.phq_market.api;

import com.example.phq_market.model.CART;
import com.example.phq_market.model.CATALOG;
import com.example.phq_market.model.CATALOGSHOW;
import com.example.phq_market.model.FEEDBACKSHOW;
import com.example.phq_market.model.NEWPRODUCT;
import com.example.phq_market.model.ONLYIMAGE;
import com.example.phq_market.model.PRODUCTDETAIL;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface api {
    @GET("register.php")
    Call<String> register(@Query("NAME") String name , @Query("PASS") String pass, @Query("PHONE") int phone,@Query("EMAIL") String email);
    @GET("getlistcatalog.php")
    Call<ArrayList<CATALOGSHOW>> get_Listcatalog();
    @GET("getlistproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_Listproduct();
    @GET("getimageonlyproduct.php")
    Call<ArrayList<ONLYIMAGE>> get_imageonlyproduct(@Query("IDPRODUCT")Integer id);
    @GET("getonlyproduct.php")
    Call<PRODUCTDETAIL> getonlyproduct(@Query("IDPRODUCT")Integer id);
    @GET("getfeedbackproduct.php")
    Call<ArrayList<FEEDBACKSHOW>> get_feedbackproduct(@Query("IDPRODUCT")Integer id);
    @GET("getlistcart.php")
    Call<ArrayList<CART>> get_Listcart();
    @GET("getlistpopularproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_listpopularproduct();
    @GET("deleteItemInCart.php")
    Call<String> delete_ItemInCart(@Query("IDCART") Integer idcart);
    @GET("updateItemInCart.php")
    Call<String> update_ItemInCart(@Query("IDCART") Integer idcart, @Query("QUANTITY") Integer QUANTITY);
}
