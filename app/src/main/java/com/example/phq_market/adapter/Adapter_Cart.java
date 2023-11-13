package com.example.phq_market.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.api.api;
import com.example.phq_market.fragment.Fragment_Cart;
import com.example.phq_market.model.CART;
import com.example.phq_market.model.CARTCHECKBOX;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_Cart extends RecyclerView.Adapter<Adapter_Cart.ViewHolder> {
    private Context context;
    private ArrayList<CARTCHECKBOX> list_CART;
    private ProgressDialog progressDialog_cart;
    private Handler handler_cart = new Handler();

    public Adapter_Cart(Context context, ArrayList<CARTCHECKBOX> list_CART) {
        this.context = context;
        this.list_CART = list_CART;
    }

    public interface CheckKerListener{
        void check(int position , boolean check);
    }
    private CheckKerListener checkKerListener;
    public void SetCheckKerListener(CheckKerListener checkKerListener){
        this.checkKerListener = checkKerListener;
    }

    public interface UpdateQuantity{
        void quantity(int position, int quantity);
    }
    private UpdateQuantity updateQuantity;
    public void SetUpdateQuantity(UpdateQuantity updateQuantity){
        this.updateQuantity = updateQuantity;
    }

    @NonNull
    @Override
    public Adapter_Cart.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_cart,null);
        return new ViewHolder(view);
    }
    int quantityAtPosition;
    @Override
    public void onBindViewHolder(@NonNull Adapter_Cart.ViewHolder holder, int position) {
        CARTCHECKBOX cart = list_CART.get(holder.getAdapterPosition());

        try {
            Glide.with(context)
                    .load(cart.getIMG())
                    .into(holder.imgAnh);
            holder.tvName.setText(cart.getNAME());
            holder.tvquantity.setText(String.valueOf(cart.getQUANTITY()));
            holder.tvCost.setText(String.valueOf(cart.getPRICE()));
            holder.Chk_check.setChecked(cart.isCheck());
        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>>", e.getMessage());
        }
        holder.Chk_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_CART.get(holder.getAdapterPosition()).setCheck(holder.Chk_check.isChecked());
                checkKerListener.check(holder.getAdapterPosition(),holder.Chk_check.isChecked());
            }
        });
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityAtPosition = list_CART.get(holder.getAdapterPosition()).getQUANTITY();
                quantityAtPosition+=1;
                holder.tvquantity.setText(String.valueOf(quantityAtPosition));
                updateQuantity.quantity(holder.getAdapterPosition(),quantityAtPosition);
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantityAtPosition >1){
                    quantityAtPosition = list_CART.get(holder.getAdapterPosition()).getQUANTITY();
                    quantityAtPosition -=1;
                    holder.tvquantity.setText(String.valueOf(quantityAtPosition));
                    updateQuantity.quantity(holder.getAdapterPosition(),quantityAtPosition);
                }
            }
        });

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri = list_CART.get(holder.getAdapterPosition()).getID();
                deleteItemCart(vitri);
            }
        });
    }

    private void setPrice(){
        Fragment_Cart fragmentCart = ((Activity)context).getSystemService(Fragment_Cart.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list",list_CART);
        if(fragmentCart != null){
            fragmentCart.setArguments(bundle);
            fragmentCart.onResume();
        }

    }

    private void deleteItemCart(Integer id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler_cart.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog_cart = new ProgressDialog(context);
                        progressDialog_cart.setMessage("Dữ liệu đang chạy");
                        progressDialog_cart.setCancelable(false);
                        progressDialog_cart.show();
                    }
                });
                Retrofit retrofit_catalog = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/cart/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api api_cart = retrofit_catalog.create(api.class);
                Call<String> call = api_cart.delete_ItemInCart(id);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        progressDialog_cart.dismiss();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog_cart.dismiss();
                    }
                });

            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return list_CART.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnh,btnPlus,btnMinus,btndelete;
        TextView tvName,tvCost,tvquantity;
        CheckBox Chk_check;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            tvName = itemView.findViewById(R.id.tvName);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvquantity = itemView.findViewById(R.id.tvquantity);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btndelete = itemView.findViewById(R.id.btndelete);
            Chk_check = itemView.findViewById(R.id.Chk_check);
        }
    }
}
