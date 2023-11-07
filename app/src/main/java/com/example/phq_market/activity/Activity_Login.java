package com.example.phq_market.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phq_market.R;
import com.example.phq_market.api.api;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Login extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tvSignUp = findViewById(R.id.tvSignUp);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextInputEditText edt_Email = findViewById(R.id.edt_Email);
        TextInputEditText edt_Password = findViewById(R.id.edt_Password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edt_Email.getText().toString();
                String password = edt_Password.getText().toString();

                if (email.isEmpty()||password.isEmpty()){
                    Toast.makeText(Activity_Login.this, "Không được bỏ trống ô", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Activity_Login.this, " Đang nhập thành công", Toast.LENGTH_SHORT).show();
                    login_account(email, password);
                }


            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Login.this, Activity_Signup.class));
            }
        });
    }

    private void login_account(String username, String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(Activity_Login.this);
                        progressDialog.setMessage("Loading....");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                });

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://phqmarket.000webhostapp.com/account/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api apiAccount = retrofit.create(api.class);
                Call<String> call = apiAccount.login(username,password);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful() && response.body() != null){
                            Toast.makeText(Activity_Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                            finish();
                            progressDialog.dismiss();
                            startActivity(new Intent(Activity_Login.this, Activity_Main.class));
                        }else {
                            Toast.makeText(Activity_Login.this, "Eror", Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(Activity_Login.this, "Error in registered email or phone or name", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();
    }
}