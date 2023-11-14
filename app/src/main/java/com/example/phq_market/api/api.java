package com.example.phq_market.api;

import com.example.phq_market.model.ACCOUNT;
import com.example.phq_market.model.CART;
import com.example.phq_market.model.CATALOGSHOW;
import com.example.phq_market.model.CHECKOUT;
import com.example.phq_market.model.CUSTOMER;
import com.example.phq_market.model.FEEDBACKSHOW;
import com.example.phq_market.model.MONTHANDDAY;
import com.example.phq_market.model.NEWPRODUCT;
import com.example.phq_market.model.ONLYIMAGE;
import com.example.phq_market.model.ORDERANDFEEDBACK;
import com.example.phq_market.model.PRODUCTDETAIL;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface api {
    @GET("login.php")
    Call<CUSTOMER> login(@Query("NAME") String name , @Query("PASS") String pass);
    @GET("register.php")
    Call<String> register(@Query("NAME") String name , @Query("PASS") String pass, @Query("PHONE") String phone,@Query("EMAIL") String email);
    @GET("updatecustomer.php")
    Call<String> updatecustomer(@Query("NAME") String name, @Query("EMAIL") String email , @Query("PASS") String pass, @Query("PHONE") String phone, @Query("ADRESS") String adress, @Query("IMG") String img, @Query("OLDEMAIL") String oldemail, @Query("OLDPASS") String oldpass);
    @GET("accountdetail.php")
    Call<ACCOUNT> getDetailAccount(@Query("EMAIL") String email, @Query("PASS") String pass);
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
    @GET("addlike.php")
    Call<String> add_ItemLike(@Query("IDPRODUCT") Integer idproduct, @Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("getlistlike.php")
    Call<ArrayList<NEWPRODUCT>> get_listlike(@Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("status.php")
    Call<String> check_status(@Query("IDPRODUCT") Integer idproduct, @Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("addpurchase.php")
    Call<String> add_Purchase(@Query("listpurchase") String cart);
    @GET("getitemtoaddpurchase.php")
    Call<ArrayList<CHECKOUT>> getCheckOut(@Query("listcart") String cart);
    @GET("getListOrder.php")
    Call<ArrayList<ORDERANDFEEDBACK>> get_listOrder(@Query("EMAIL") String email, @Query("PASS") String pass);

    @GET("getDateAndPay.php")
    Call<ArrayList<MONTHANDDAY>> get_DateAndPay(@Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("getlistallproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_listall();
    @GET("getlistasCaTaLogproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_listasCaTaLog(@Query("IDCATALOG") Integer IDCATALOG);
    @GET("sendfeedback.php")
    Call<String> add_feedback(@Query("EVALUATE") Integer evaluate, @Query("CONTENT") String content, @Query("IDPURCHASE") Integer idpurchase, @Query("EMAIL") String email, @Query("PASS") String pass);
}
