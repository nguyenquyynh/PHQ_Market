package com.example.phq_market.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.api.api;
import com.example.phq_market.model.CART;
import com.example.phq_market.model.CATALOGSHOW;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_Cart extends RecyclerView.Adapter<Adapter_Cart.ViewHolder> {
    private Context context;
    private ArrayList<CART> list_CART;
    private ProgressDialog progressDialog_cart;
    private Handler handler_cart = new Handler();
    private SharedPreferences sharedPreferences;

    public Adapter_Cart(Context context, ArrayList<CART> list_CART) {
        this.context = context;
        this.list_CART = list_CART;
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
        CART cart = list_CART.get(position);
        try {
            Glide.with(context)
                    .load(cart.getIMG())
                    .into(holder.imgAnh);
            holder.tvName.setText(cart.getNAME());
            holder.tvquantity.setText(String.valueOf(cart.getQUANTITY()));
            holder.tvCost.setText(String.valueOf(cart.getPRICE()));
        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>>", e.getMessage());
        }
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityAtPosition = list_CART.get(holder.getAdapterPosition()).getQUANTITY();
                quantityAtPosition+=1;
                holder.tvquantity.setText(String.valueOf(quantityAtPosition));
                list_CART.get(holder.getAdapterPosition()).setQUANTITY(quantityAtPosition);
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantityAtPosition >1){
                    quantityAtPosition = list_CART.get(holder.getAdapterPosition()).getQUANTITY();
                    quantityAtPosition -=1;
                    holder.tvquantity.setText(String.valueOf(quantityAtPosition));
                    list_CART.get(holder.getAdapterPosition()).setQUANTITY(quantityAtPosition);
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
                        loadList();
                        progressDialog_cart.dismiss();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        loadList();
                        progressDialog_cart.dismiss();
                    }
                });

            }
        }).start();
    }

    private void loadList(){
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

                sharedPreferences = context.getSharedPreferences("account",MODE_PRIVATE);
                String email = sharedPreferences.getString("Email",null);
                String pass = sharedPreferences.getString("Pass",null);
                Call<ArrayList<CART>> call = api_cart.get_Listcart(email,pass);
                call.enqueue(new Callback<ArrayList<CART>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CART>> call, Response<ArrayList<CART>> response) {
                        if(response.isSuccessful() && response.body() != null){
                            ArrayList<CART> list = response.body();
                            list_CART.clear();
                            list_CART.addAll(list);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Thành công "+list.size(), Toast.LENGTH_SHORT).show();
                            progressDialog_cart.dismiss();
                        }else {
                            list_CART.clear();
                            notifyDataSetChanged();
                            progressDialog_cart.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CART>> call, Throwable t) {
                        list_CART.clear();
                        notifyDataSetChanged();
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            tvName = itemView.findViewById(R.id.tvName);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvquantity = itemView.findViewById(R.id.tvquantity);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btndelete = itemView.findViewById(R.id.btndelete);

        }
    }
}
