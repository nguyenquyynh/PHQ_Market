package com.example.phq_market.api;

import com.example.phq_market.model.ACCOUNT;
import com.example.phq_market.model.CART;
import com.example.phq_market.model.CATALOGSHOW;
import com.example.phq_market.model.CUSTOMER;
import com.example.phq_market.model.FEEDBACKSHOW;
import com.example.phq_market.model.MONTHANDDAY;
import com.example.phq_market.model.NEWPRODUCT;
import com.example.phq_market.model.NOTIFICATION;
import com.example.phq_market.model.ONLYIMAGE;
import com.example.phq_market.model.ORDERANDFEEDBACK;
import com.example.phq_market.model.ORDERCONFIRM;
import com.example.phq_market.model.PRODUCTDETAIL;
import com.example.phq_market.select_adress.City;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface api {
    String url = "https://phqmarket.online/";
    //account
    @GET("Customer/Login")
    Call<CUSTOMER> login(@Query("EMAIL") String name , @Query("PASS") String pass);
    @GET("Customer/Register")
    Call<String> register(@Query("NAME") String name , @Query("PASS") String pass, @Query("PHONE") String phone,@Query("EMAIL") String email);
    @GET("Customer/UpdateCustomer")
    Call<String> updatecustomer(@Query("NAME") String name, @Query("EMAIL") String email , @Query("PASS") String pass, @Query("PHONE") String phone, @Query("ADRESS") String adress, @Query("IMG") String img, @Query("OLDEMAIL") String oldemail, @Query("OLDPASS") String oldpass);
    @GET("Customer/Accountdetail")
    Call<ACCOUNT> getDetailAccount(@Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("Customer/ResetPassword")
    Call<String> resetPassword(@Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("Index/GetCode")
    Call<String> getCode(@Query("EMAIL") String email);
    //catalog
    @GET("Catalog/Getlistcatalog")
    Call<ArrayList<CATALOGSHOW>> get_Listcatalog();
    //product
    @GET("Product/Getlistnewproduct")
    Call<ArrayList<NEWPRODUCT>> get_Listnewproduct();
    @GET("Product/Getonlyproduct")
    Call<PRODUCTDETAIL> getonlyproduct(@Query("IDPRODUCT")Integer id);
    @GET("Product/Getlistpopularproduct")
    Call<ArrayList<NEWPRODUCT>> get_listpopularproduct();
    @GET("Product/Getlistbestsaleproduct")
    Call<ArrayList<NEWPRODUCT>> get_listbestsaleproduct();
    @GET("Product/Getlistallproduct")
    Call<ArrayList<NEWPRODUCT>> get_listall();
    @GET("Product/GetlistasCaTaLogproduct")
    Call<ArrayList<NEWPRODUCT>> get_listasCaTaLog(@Query("IDCATALOG") Integer IDCATALOG);
    //image
    @GET("Image/Getimageonlyproduct")
    Call<ArrayList<ONLYIMAGE>> get_imageonlyproduct(@Query("IDPRODUCT")Integer id);
   //feedback
    @GET("Feedback/GetListFeedBackInProduct")
    Call<ArrayList<FEEDBACKSHOW>> get_feedbackproduct(@Query("IDPRODUCT")Integer id);
    @GET("Feedback/SendFeedBack")
    Call<String> add_feedback(@Query("EVALUATE") Integer evaluate, @Query("CONTENT") String content, @Query("IDPURCHASE") Integer idpurchase, @Query("EMAIL") String email, @Query("PASS") String pass);
    //cart
    @GET("Cart/GetListCart")
    Call<ArrayList<CART>> get_Listcart(@Query("EMAIL") String name , @Query("PASS") String pass);
    @GET("Cart/AddToCart")
    Call<String> add_ItemInCart(@Query("IDPRODUCT") Integer idproduct, @Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("Cart/DeleteItemInCart")
    Call<String> delete_ItemInCart(@Query("IDCART") Integer idcart);
    //banner
    @GET("Banner/Getlistbanner")
    Call<ArrayList<ONLYIMAGE>> get_listbanner();
    // liked
    @GET("Liked/Addlike")
    Call<String> add_ItemLike(@Query("IDPRODUCT") Integer idproduct, @Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("Liked/GetListLiked")
    Call<ArrayList<NEWPRODUCT>> get_listlike(@Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("Liked/Status")
    Call<String> check_status(@Query("IDPRODUCT") Integer idproduct, @Query("EMAIL") String email, @Query("PASS") String pass);
    //purchase
    @GET("Order/Addpurchase")
    Call<String> add_Purchase(@Query("listpurchase") String cart, @Query("ADRESS")  String Adress,@Query("EMAIL") String email,@Query("PASS") String pass,@Query("PAYMENT") int payment);
    @GET("Order/GetListOrderConfirm")
    Call<ArrayList<ORDERCONFIRM>> get_listOrderConfirm(@Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("Order/GetListOrderShipping")
    Call<ArrayList<ORDERANDFEEDBACK>> get_listOrderShipping(@Query("EMAIL") String email,@Query("PASS") String pass);
    @GET("Order/GetListOrderDone")
    Call<ArrayList<ORDERANDFEEDBACK>> get_listOrderDone(@Query("EMAIL") String email,@Query("PASS") String pass);
    @GET("Order/GetListOrderCancel")
    Call<ArrayList<ORDERANDFEEDBACK>> get_listOrderCancel(@Query("EMAIL") String email,@Query("PASS") String pass);
    @GET("Order/UpdateStatus")
    Call<String> update_Status(@Query("ID") int ID);
    @GET("Order/GetDateAndPay")
    Call<ArrayList<MONTHANDDAY>> get_DateAndPay(@Query("EMAIL") String email, @Query("PASS") String pass);
    // notification
    @GET("News/GetListNotification")
    Call<ArrayList<NOTIFICATION>> get_ListNotifcation(@Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("News/Readall")
    Call<String> readall(@Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("data.json")
    Call<ArrayList<City>> get_listAdress();
}
