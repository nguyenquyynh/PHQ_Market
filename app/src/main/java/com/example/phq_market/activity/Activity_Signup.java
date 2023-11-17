package com.example.phq_market.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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

public class Activity_Signup extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    private TextInputEditText edtemail;
    TextInputEditText edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextInputEditText edtUsername = findViewById(R.id.edtUsername);
        edtemail = findViewById(R.id.edtemail);
        TextInputEditText edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        TextInputEditText edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        ImageView imgShowPass = findViewById(R.id.imgShowPass);
        ImageView imgShowConfirmPass = findViewById(R.id.imgShowConfirmPass);
        CheckBox chkAgreeCondition = findViewById(R.id.chkAgreeCondition);

        imgShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD){
                    imgShowPass.setImageResource(R.drawable.open_eye);
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    edtPassword.setTransformationMethod(null);
                }else {
                    imgShowPass.setImageResource(R.drawable.close_eye);
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD );
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        imgShowConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtConfirmPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD){
                    imgShowConfirmPass.setImageResource(R.drawable.open_eye);
                    edtConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    edtConfirmPassword.setTransformationMethod(null);
                }else {
                    imgShowConfirmPass.setImageResource(R.drawable.close_eye);
                    edtConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD );
                    edtConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        TextView tvLogin = findViewById(R.id.tvLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String phone = edtPhone.getText().toString();
                String email = edtemail.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword= edtConfirmPassword.getText().toString();

                if(username.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(Activity_Signup.this,"The input box cannot be left blank", Toast.LENGTH_SHORT).show();
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(Activity_Signup.this,"Enter correct gmail", Toast.LENGTH_SHORT).show();
                } else if (phone.length()<10) {
                    Toast.makeText(Activity_Signup.this,"Phone number need 10 number", Toast.LENGTH_SHORT).show();
                } else if (password.length()<8) {
                    Toast.makeText(Activity_Signup.this, "The password need more than 8 letter", Toast.LENGTH_SHORT).show();
                } else if (!isPasswordValid(password)) {
                    Toast.makeText(Activity_Signup.this, "The password need at least 1 lowercase letter,1 uppercase letter , 1 number and special characters ", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    edtConfirmPassword.requestFocus();
                    Toast.makeText(Activity_Signup.this, "Enter the same password", Toast.LENGTH_SHORT).show();
                } else if (!chkAgreeCondition.isChecked()) {
                    Toast.makeText(Activity_Signup.this, "Please confirm saving information", Toast.LENGTH_SHORT).show();
                } else {
                    register_account(username,phone,email,password);
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Signup.this, Activity_Login.class));
            }
        });
    }


    private void register_account(String username, String phone, String email, String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(Activity_Signup.this);
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
                Call<String> call = apiAccount.register(username,password, phone,email);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful() && response.body() != null){
                            Toast.makeText(Activity_Signup.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            SharedPreferences sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("remember",false);
                            editor.putString("Email",edtemail.getText().toString());
                            editor.putString("Pass",edtPassword.getText().toString());
                            editor.apply();

                            startActivity(new Intent(Activity_Signup.this, Activity_Main.class));
                        }else {
                            Toast.makeText(Activity_Signup.this, "Eror", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(Activity_Signup.this, "Error in registered email or phone or name", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();
    }


    private boolean isPasswordValid(String password) {
        // Yêu cầu ít nhất 1 chữ cái thường, 1 chữ cái hoa, 1 kí tự đặt biệt và 1 số
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordPattern);
    }
}