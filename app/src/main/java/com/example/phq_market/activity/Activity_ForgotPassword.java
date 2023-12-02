package com.example.phq_market.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phq_market.R;
import com.example.phq_market.api.api;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_ForgotPassword extends AppCompatActivity {
    private String url = api.url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        TextInputEditText edtPassword = findViewById(R.id.edtPassword);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        TextInputEditText edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        Intent getEmail = getIntent();
        String email = getEmail.getStringExtra("email");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = edtPassword.getText().toString();
                String confirmpassword= edtConfirmPassword.getText().toString();

                if(password.isEmpty() || confirmpassword.isEmpty()){
                    Toast.makeText(Activity_ForgotPassword.this, "The input box cannot be left blank", Toast.LENGTH_SHORT).show();
                } else if (!isPasswordValid(password)) {
                    Toast.makeText(Activity_ForgotPassword.this, "The password need at least 1 lowercase letter,1 uppercase letter , 1 number and special characters ", Toast.LENGTH_SHORT).show();
                }else {
                    resetPassword(email,password);
                }
            }
        });
    }

    private void resetPassword(String email, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api Api = retrofit.create(api.class);
        Call<String> call = Api.resetPassword(email,password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Toast.makeText(Activity_ForgotPassword.this, "Reset succesfuly", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Activity_ForgotPassword.this,Activity_Login.class));
                }else {
                    Toast.makeText(Activity_ForgotPassword.this, "errror", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Activity_ForgotPassword.this, "Lost connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isPasswordValid(String password) {
        // Yêu cầu ít nhất 1 chữ cái thường, 1 chữ cái hoa, 1 kí tự đặt biệt và 1 số
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordPattern);
    }
}