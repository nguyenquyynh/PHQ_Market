package com.example.phq_market.api;

import com.example.phq_market.model.ACCOUNT;
import com.example.phq_market.model.CART;
import com.example.phq_market.model.CATALOGSHOW;
import com.example.phq_market.model.CUSTOMER;
import com.example.phq_market.model.FEEDBACKSHOW;
import com.example.phq_market.model.NEWPRODUCT;
import com.example.phq_market.model.ONLYIMAGE;
import com.example.phq_market.model.PRODUCTDETAIL;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface api {
    @GET("login.php")
    Call<CUSTOMER> login(@Query("NAME") String name , @Query("PASS") String pass);
    @GET("register.php")
    Call<String> register(@Query("NAME") String name , @Query("PASS") String pass, @Query("PHONE") int phone,@Query("EMAIL") String email);
    @GET("account.php")
    Call<ACCOUNT> getDetailAccount();
    @GET("getlistcatalog.php")
    Call<ArrayList<CATALOGSHOW>> get_Listcatalog();
    @GET("getlistnewproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_Listnewproduct();
    @GET("getimageonlyproduct.php")
    Call<ArrayList<ONLYIMAGE>> get_imageonlyproduct(@Query("IDPRODUCT")Integer id);
    @GET("getonlyproduct.php")
    Call<PRODUCTDETAIL> getonlyproduct(@Query("IDPRODUCT")Integer id);
    @GET("getfeedbackproduct.php")
    Call<ArrayList<FEEDBACKSHOW>> get_feedbackproduct(@Query("IDPRODUCT")Integer id);
    @GET("getlistcart.php")
    Call<ArrayList<CART>> get_Listcart(@Query("EMAIL") String name , @Query("PASS") String pass);
    @GET("getlistpopularproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_listpopularproduct();
    @GET("getlistbestsaleproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_listbestsaleproduct();
    @GET("addtocart.php")
    Call<String> add_ItemInCart(@Query("IDPRODUCT") Integer idproduct, @Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("deleteItemInCart.php")
    Call<String> delete_ItemInCart(@Query("IDCART") Integer idcart);
    @GET("updateItemInCart.php")
    Call<String> update_ItemInCart(@Query("IDCART") Integer idcart, @Query("QUANTITY") Integer QUANTITY);
    @GET("getlistbanner.php")
    Call<ArrayList<ONLYIMAGE>> get_listbanner();
}
