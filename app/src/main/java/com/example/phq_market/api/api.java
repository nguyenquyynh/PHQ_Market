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
import com.example.phq_market.select_adress.City;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface api {
    @GET("account/login.php")
    Call<CUSTOMER> login(@Query("NAME") String name , @Query("PASS") String pass);
    @GET("account/register.php")
    Call<String> register(@Query("NAME") String name , @Query("PASS") String pass, @Query("PHONE") String phone,@Query("EMAIL") String email);
    @GET("account/updatecustomer.php")
    Call<String> updatecustomer(@Query("NAME") String name, @Query("EMAIL") String email , @Query("PASS") String pass, @Query("PHONE") String phone, @Query("ADRESS") String adress, @Query("IMG") String img, @Query("OLDEMAIL") String oldemail, @Query("OLDPASS") String oldpass);
    @GET("account/accountdetail.php")
    Call<ACCOUNT> getDetailAccount(@Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("catalog/getlistcatalog.php")
    Call<ArrayList<CATALOGSHOW>> get_Listcatalog();
    @GET("product/getlistnewproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_Listnewproduct();
    @GET("image/getimageonlyproduct.php")
    Call<ArrayList<ONLYIMAGE>> get_imageonlyproduct(@Query("IDPRODUCT")Integer id);
    @GET("product/getonlyproduct.php")
    Call<PRODUCTDETAIL> getonlyproduct(@Query("IDPRODUCT")Integer id);
    @GET("feedback/getfeedbackproduct.php")
    Call<ArrayList<FEEDBACKSHOW>> get_feedbackproduct(@Query("IDPRODUCT")Integer id);
    @GET("cart/getlistcart.php")
    Call<ArrayList<CART>> get_Listcart(@Query("EMAIL") String name , @Query("PASS") String pass);
    @GET("product/getlistpopularproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_listpopularproduct();
    @GET("product/getlistbestsaleproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_listbestsaleproduct();
    @GET("cart/addtocart.php")
    Call<String> add_ItemInCart(@Query("IDPRODUCT") Integer idproduct, @Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("cart/deleteItemInCart.php")
    Call<String> delete_ItemInCart(@Query("IDCART") Integer idcart);
    @GET("cart/updateItemInCart.php")
    Call<String> update_ItemInCart(@Query("IDCART") Integer idcart, @Query("QUANTITY") Integer QUANTITY);
    @GET("banner/getlistbanner.php")
    Call<ArrayList<ONLYIMAGE>> get_listbanner();
    @GET("liked/addlike.php")
    Call<String> add_ItemLike(@Query("IDPRODUCT") Integer idproduct, @Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("liked/getlistlike.php")
    Call<ArrayList<NEWPRODUCT>> get_listlike(@Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("liked/status.php")
    Call<String> check_status(@Query("IDPRODUCT") Integer idproduct, @Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("purchase/addpurchase.php")
    Call<String> add_Purchase(@Query("listpurchase") String cart, @Query("ADRESS")  String Adress);
    @GET("purchase/getitemtoaddpurchase.php")
    Call<ArrayList<CHECKOUT>> getCheckOut(@Query("listcart") String cart);
    @GET("getListOrder.php")
    Call<ArrayList<ORDERANDFEEDBACK>> get_listOrder(@Query("EMAIL") String email, @Query("PASS") String pass);

    @GET("purchase/getListOrderConfirm.php")
    Call<ArrayList<ORDERANDFEEDBACK>> get_listOrderConfirm(@Query("EMAIL") String email,@Query("PASS") String pass);
    @GET("purchase/getListOrderShipping.php")
    Call<ArrayList<ORDERANDFEEDBACK>> get_listOrderShipping(@Query("EMAIL") String email,@Query("PASS") String pass);
    @GET("purchase/getListOrderDone.php")
    Call<ArrayList<ORDERANDFEEDBACK>> get_listOrderDone(@Query("EMAIL") String email,@Query("PASS") String pass);
    @GET("purchase/getListOrderCancel.php")
    Call<ArrayList<ORDERANDFEEDBACK>> get_listOrderCancel(@Query("EMAIL") String email,@Query("PASS") String pass);
    @GET("purchase/updateStatus.php")
    Call<String> update_Status(@Query("ID") int ID);
    @GET("purchase/getDateAndPay.php")
    Call<ArrayList<MONTHANDDAY>> get_DateAndPay(@Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("product/getlistallproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_listall();
    @GET("product/getlistasCaTaLogproduct.php")
    Call<ArrayList<NEWPRODUCT>> get_listasCaTaLog(@Query("IDCATALOG") Integer IDCATALOG);
    @GET("feedback/sendfeedback.php")
    Call<String> add_feedback(@Query("EVALUATE") Integer evaluate, @Query("CONTENT") String content, @Query("IDPURCHASE") Integer idpurchase, @Query("EMAIL") String email, @Query("PASS") String pass);
    @GET("data.json")
    Call<ArrayList<City>> get_listAdress();
}
