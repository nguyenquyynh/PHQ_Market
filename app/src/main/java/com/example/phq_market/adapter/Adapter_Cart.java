package com.example.phq_market.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.model.CARTCHECKBOX;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Cart extends RecyclerView.Adapter<Adapter_Cart.ViewHolder> {
    private Context context;
    private ArrayList<CARTCHECKBOX> list_CART;

    public Adapter_Cart(Context context, ArrayList<CARTCHECKBOX> list_CART) {
        this.context = context;
        this.list_CART = list_CART;
    }

    // check item
    public interface CheckKerListener{
        void check(int position , boolean check);
    }
    private CheckKerListener checkKerListener;
    public void SetCheckKerListener(CheckKerListener checkKerListener){
        this.checkKerListener = checkKerListener;
    }
    //update item
    public interface UpdateQuantity{
        void quantity(int position, int quantity);
    }
    private UpdateQuantity updateQuantity;
    public void SetUpdateQuantity(UpdateQuantity updateQuantity){
        this.updateQuantity = updateQuantity;
    }
    //delete item
    public interface DeleteProduct{
        void delete(int position);
    }
    private DeleteProduct deleteProduct;
    public void OnDeleteProductListener( DeleteProduct deleteProduct){
        this.deleteProduct = deleteProduct;
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
        DecimalFormat formatter = new DecimalFormat("#,###");
        try {
            Glide.with(context)
                    .load(cart.getIMG())
                    .into(holder.imgAnh);
            holder.tvName.setText(cart.getNAME());
            holder.tvquantity.setText(String.valueOf(cart.getQUANTITY()));
            holder.tvCost.setText(formatter.format(cart.getPRICE()));
            holder.Chk_check.setChecked(cart.isCheck());
        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>>", e.getMessage());
        }

        if(cart.getPRODUCTQUANTITY() == 0 || (cart.getPRODUCTQUANTITY()- cart.getQUANTITY())<0){
            holder.imgAnh.setColorFilter(Color.parseColor("#808080"));
            holder.Chk_check.setEnabled(false);
            holder.btnPlus.setEnabled(false);
            holder.btnMinus.setEnabled(false);
            holder.tvName.setPaintFlags(holder.tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvCost.setText("Out off stock");
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
                if(list_CART.get(holder.getAdapterPosition()).getPRODUCTQUANTITY() < quantityAtPosition){
                    Toast.makeText(context,  "Too much quantity in stock", Toast.LENGTH_SHORT).show();
                }else {
                    holder.tvquantity.setText(String.valueOf(quantityAtPosition));
                    updateQuantity.quantity(holder.getAdapterPosition(),quantityAtPosition);
                }
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityAtPosition = list_CART.get(holder.getAdapterPosition()).getQUANTITY();
                if(quantityAtPosition >1){
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
                deleteProduct.delete(vitri);
            }
        });
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
