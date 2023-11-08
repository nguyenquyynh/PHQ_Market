package com.example.phq_market.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_EditAccount;
import com.example.phq_market.activity.Activity_Login;
import com.example.phq_market.activity.Activity_Signup;

public class Fragment_Account extends Fragment {
    public Fragment_Account() {
    }

    private TextView tv_Email;
    private TextView tv_Phone;
    private TextView tv_Address;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__account, container, false);

        LinearLayout ic_Edit = view.findViewById(R.id.ic_Edit);
        tv_Email = view.findViewById(R.id.tv_Email);
        tv_Phone = view.findViewById(R.id.tv_Phone);
        tv_Address = view.findViewById(R.id.tv_Address);
        Button btn_setUp = view.findViewById(R.id.btn_setUp);


        sharedPreferences = getContext().getSharedPreferences("account",MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        checkLogin(btn_setUp);
        btn_setUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_setUp.getText() == "Log out"){
                    startActivity(new Intent(getContext(), Activity_Login.class));
                    editor.putString("Email","");
                    editor.putString("Pass","");
                    editor.putBoolean("remember",false);
                    editor.apply();
                }else {
                    startActivity(new Intent(getContext(), Activity_Signup.class));
                }
            }
        });
        ic_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_EditAccount.class));
            }
        });




        return view;
    }

    void checkLogin(Button btn_setUp){
        try {
            String email = sharedPreferences.getString("Email",null);
            String pass = sharedPreferences.getString("Pass",null);
            if(email.isEmpty() || pass.isEmpty()){
                btn_setUp.setText("Log in");
            }else {
                btn_setUp.setText("Log out");
            }

        }catch (Exception e){
            Log.e("->>>>>>>>>>>",e+"");
            btn_setUp.setText("Log in");
        }
    }
}
