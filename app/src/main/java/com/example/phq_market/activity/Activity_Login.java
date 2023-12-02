package com.example.phq_market.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phq_market.R;
import com.example.phq_market.api.api;
import com.example.phq_market.model.CUSTOMER;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Login extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private CheckBox chk_Remember;
    private String code;
    private String url = api.url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tvSignUp = findViewById(R.id.tvSignUp);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextInputEditText edt_Email = findViewById(R.id.edt_Email);
        TextInputEditText edt_Password = findViewById(R.id.edt_Password);
        chk_Remember = findViewById(R.id.chk_Remember);
        ImageView img_showpas = findViewById(R.id.img_showpas);
        TextView Tv_Forgot_password = findViewById(R.id.Tv_Forgot_password);

        sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (!sharedPreferences.getBoolean("remember",false)){
            chk_Remember.setChecked(false);
            editor.putString("Email","");
            editor.putString("Pass","");
            editor.apply();
        }else {
            chk_Remember.setChecked(true);
            edt_Email.setText(sharedPreferences.getString("Email",null));
            edt_Password.setText(sharedPreferences.getString("Pass",null));
        }

        img_showpas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_Password.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD){
                    img_showpas.setImageResource(R.drawable.open_eye);
                    edt_Password.setInputType(InputType.TYPE_CLASS_TEXT);
                    edt_Password.setTransformationMethod(null);
                }else {
                    img_showpas.setImageResource(R.drawable.close_eye);
                    edt_Password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD );
                    edt_Password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edt_Email.getText().toString();
                String password = edt_Password.getText().toString();

                if (email.isEmpty()||password.isEmpty()){
                    Toast.makeText(Activity_Login.this, "Không được bỏ trống ô", Toast.LENGTH_SHORT).show();
                }else {
                    login_account(email, password);
                }


            }
        });

        Tv_Forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpAlertDialogForgot();
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Login.this, Activity_Signup.class));
            }
        });
    }

    private void setUpAlertDialogForgot(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Login.this);
        View view = LayoutInflater.from(Activity_Login.this).inflate(R.layout.layout_submit_email,null);
        builder.setView(view);

        EditText Edt_Email = view.findViewById(R.id.Edt_Email);
        EditText Edt_Code = view.findViewById(R.id.Edt_Code);
        Button Btn_Send = view.findViewById(R.id.Btn_Send);
        Button Btn_Code = view.findViewById(R.id.Btn_Code);

        AlertDialog dialog = builder.create();
        Btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Edt_Email.getText().toString();
                if(email.isEmpty()){

                    Toast.makeText(Activity_Login.this, "Cannot be left blank", Toast.LENGTH_SHORT).show();
                }else {
                    Edt_Email.setVisibility(View.GONE);
                    Btn_Send.setVisibility(View.GONE);
                    Edt_Code.setVisibility(View.VISIBLE);
                    Btn_Code.setVisibility(View.VISIBLE);
                    Toast.makeText(Activity_Login.this, "Check your email", Toast.LENGTH_SHORT).show();
                    forgotPass(email);
                }
            }
        });

        Btn_Code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = Edt_Code.getText().toString();
                if(code.equals(check)){
                    Intent intent = new Intent(Activity_Login.this, Activity_ForgotPassword.class);
                    intent.putExtra("email",Edt_Email.getText().toString());
                    startActivity(intent);
                }else {
                    Toast.makeText(Activity_Login.this, "Check the code again", Toast.LENGTH_SHORT).show();
                }
            }
        });


        dialog.show();
    }

    private void forgotPass(String email){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api Api = retrofit.create(api.class);
                Call<String> call = Api.getCode(email);
                call.enqueue(new Callback<String>() {
                    @Override

                    public void onResponse(Call<String> call, Response<String> response) {
                        code = response.body()+"";
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(Activity_Login.this, "Lost conection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
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
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api apiAccount = retrofit.create(api.class);
                Call<CUSTOMER> call = apiAccount.login(username,password);
                call.enqueue(new Callback<CUSTOMER>() {
                    @Override
                    public void onResponse(Call<CUSTOMER> call, Response<CUSTOMER> response) {
                        if(response.isSuccessful() && response.body() != null){
                            Toast.makeText(Activity_Login.this, "Login Success", Toast.LENGTH_SHORT).show();

                            if(chk_Remember.isChecked()){
                                editor.putBoolean("remember",true);
                            }else {
                                editor.putBoolean("remember",false);
                            }

                            editor.putString("Email",response.body().getEMAIL());
                            editor.putString("Pass",response.body().getPASS());
                            editor.apply();
                            startActivity(new Intent(Activity_Login.this, Activity_Main.class));
                            progressDialog.dismiss();
                        }else {
                            Toast.makeText(Activity_Login.this, "Email or pass is uncorrect"+response.body(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<CUSTOMER> call, Throwable t) {
                        Toast.makeText(Activity_Login.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d("->>>>>>>>>>>>>>>>>>>",t.toString());
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();
    }
}