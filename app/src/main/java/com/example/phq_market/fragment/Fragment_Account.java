package com.example.phq_market.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_EditAccount;
import com.example.phq_market.activity.Activity_Login;
import com.example.phq_market.activity.Activity_Main;
import com.example.phq_market.activity.Activity_Signup;
import com.example.phq_market.activity.Activity_Status;
import com.example.phq_market.api.api;
import com.example.phq_market.model.ACCOUNT;
import com.example.phq_market.model.EDITACCOUNT;
import com.example.phq_market.model.MONTHANDDAY;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Account extends Fragment {
    public Fragment_Account() {
    }
    Button btn_setUp;
    ImageView Img_account;
    private LinearLayout lnOrder;
    private TextView Txt_name, Txt_like, Txt_order, Txt_cart;
    private TextView Txt_email;
    private TextView Txt_phone;
    private TextView Txt_address;
    private SharedPreferences sharedPreferences;
    private ArrayList<MONTHANDDAY> listmonthandday;
    private LineChart lineChart;
    private LinearLayout ic_Edit;

    private LinearLayout lnCart;

    private LinearLayout lnLike;
    EDITACCOUNT acc;
    private Dialog dialog;
    private String email = "";
    private String pass = "";
    private String url = api.url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__account, container, false);

        ic_Edit = view.findViewById(R.id.ic_Edit);
        lnOrder = view.findViewById(R.id.lnOrder);
        Img_account = view.findViewById(R.id.Img_account);
        Txt_name = view.findViewById(R.id.Txt_name);
        Txt_like = view.findViewById(R.id.Txt_like);
        Txt_order = view.findViewById(R.id.Txt_order);
        Txt_cart = view.findViewById(R.id.Txt_cart);
        Txt_email = view.findViewById(R.id.Txt_email);
        Txt_phone = view.findViewById(R.id.Txt_phone);
        Txt_address = view.findViewById(R.id.Txt_address);
        btn_setUp = view.findViewById(R.id.btn_setUp);
        lineChart = view.findViewById(R.id.lineChart);
        lnCart = view.findViewById(R.id.lnCart);
        lnLike = view.findViewById(R.id.lnLike);
        acc = new EDITACCOUNT();
        listmonthandday = new ArrayList<>();

        sharedPreferences = getContext().getSharedPreferences("account",MODE_PRIVATE);

        btn_setUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_setUp.getText() == "Log out"){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Email","");
                    editor.putString("Pass","");
                    editor.putBoolean("remember",false);
                    editor.apply();
                    startActivity(new Intent(getContext(), Activity_Login.class));
                }else {
                    startActivity(new Intent(getContext(), Activity_Signup.class));
                }
            }
        });
        ic_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_EditAccount.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("acc",acc);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        lnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Main.class);
                intent.putExtra("where","cart");
                startActivity(intent);
            }
        });

        lnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Main.class);
                intent.putExtra("where","like");
                startActivity(intent);
            }
        });

        lnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_Status.class));
            }
        });

        return view;
    }

    private void SetChart(){
        lineChart.getDescription().setEnabled(false);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawGridBackground(true);


        // cột bên phải
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        //cột bên trái
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        final List<String> xLabel = new ArrayList<>();
        xLabel.add("Jan");
        xLabel.add("Feb");
        xLabel.add("Mar");
        xLabel.add("Apr");
        xLabel.add("May");
        xLabel.add("Jun");
        xLabel.add("Jul");
        xLabel.add("Aug");
        xLabel.add("Sep");
        xLabel.add("Oct");
        xLabel.add("Nov");
        xLabel.add("Dec");

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setLabelCount(12);
        xAxis.setGranularity(1f); // tạo khoảng cách giữa cách tháng
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabel));

        LineData data = new LineData();
        dataChart(data);
        //đặt giới hạn hiển thị của đường để không bị cắt xnes
        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        // hiển thị biểu đồ
        lineChart.setData(data);
        lineChart.invalidate();
    }

    private void dataChart(LineData lineData) {

        // dữ liệu

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for(MONTHANDDAY pr: listmonthandday){
            entries.add(new Entry(pr.getMONTH(), pr.getPAY()));
        }

        //chỉnh sửa màu và hiệu ứng cho đường vẽ
        LineDataSet set = new LineDataSet(entries, "Payment");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        //add đưuòng vào biểu đồ
        lineData.addDataSet(set);
    }

     private void checkLogin(Button btn_setUp){
         if(email.isEmpty() || pass.isEmpty()){
             btn_setUp.setText("Log in");
         }else {
             btn_setUp.setText("Log out");
         }
    }

    private void getPersonalInformation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!sharedPreferences.getString("Email", "").isEmpty() && !sharedPreferences.getString("Pass", "").isEmpty()) {
                    Retrofit retrofit_account = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    api api_account = retrofit_account.create(api.class);
                    Call<ACCOUNT> call_account = api_account.getDetailAccount(sharedPreferences.getString("Email", null), sharedPreferences.getString("Pass", null));
                    call_account.enqueue(new Callback<ACCOUNT>() {
                        @Override
                        public void onResponse(Call<ACCOUNT> call, Response<ACCOUNT> response) {
                            if (response.isSuccessful() && response.body() != null){
                                ACCOUNT account = response.body();
                                Glide.with(getContext())
                                        .load(account.getIMG())
                                        .into(Img_account);
                                Txt_name.setText(account.getNAME());
                                Txt_email.setText(account.getEMAIL());
                                Txt_phone.setText(account.getPHONE());
                                Txt_address.setText(account.getADDRESS());
                                Txt_like.setText(String.valueOf(account.getLIKED()));
                                Txt_order.setText(String.valueOf(account.getPURCHASE()));
                                Txt_cart.setText(String.valueOf(account.getCART()));
                                // set data
                                acc.setIMG(account.getIMG());
                                acc.setADDRESS(account.getADDRESS());
                                acc.setEMAIL(account.getEMAIL());
                                acc.setNAME(account.getNAME());
                                acc.setPASS(sharedPreferences.getString("Pass",null));
                                acc.setPHONE(account.getPHONE());
                                dialog.cancel();
                                Log.d(">>>>>>>>>>>>>>>>>>>>>>>", acc+"");
                            } else {
                                Log.d(">>>>>>>>>>>>>>>>>>>>>>>,", response.toString());
                                dialog.cancel();
                            }
                        }

                        @Override
                        public void onFailure(Call<ACCOUNT> call, Throwable t) {
                            dialog.cancel();
                        }
                    });
                }else {
                    dialog.cancel();
                }
            }
        }).start();
    }

    private void getDataChart(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (email.isEmpty() || pass.isEmpty()){
                    Txt_name.setText("");
                    Txt_email.setText("");
                    Txt_phone.setText("");
                    Txt_address.setText("");
                    Txt_like.setText("0");
                    Txt_order.setText("0");
                    Txt_cart.setText("0");
                    ic_Edit.setVisibility(View.GONE);
                    lnOrder.setEnabled(false);
                    dialog.cancel();
                }else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    api Api = retrofit.create(api.class);
                    Call<ArrayList<MONTHANDDAY>> call = Api.get_DateAndPay(sharedPreferences.getString("Email", ""),sharedPreferences.getString("Pass", ""));
                    call.enqueue(new Callback<ArrayList<MONTHANDDAY>>() {
                        @Override
                        public void onResponse(Call<ArrayList<MONTHANDDAY>> call, Response<ArrayList<MONTHANDDAY>> response) {
                            if(response.isSuccessful() && response.body()!=null){
                                ArrayList<MONTHANDDAY> list = response.body();
                                listmonthandday.clear();
                                listmonthandday.addAll(list);
                                SetChart();
                                dialog.cancel();
                            }else {
                                listmonthandday.clear();
                                dialog.cancel();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<MONTHANDDAY>> call, Throwable t) {
                            dialog.cancel();
                        }
                    });
                }
            }
        }).start();
    }
    @Override
    public void onResume() {
        super.onResume();
        email = sharedPreferences.getString("Email", "");
        pass = sharedPreferences.getString("Pass", "");
        checkLogin(btn_setUp);
        showLoading();
        getPersonalInformation();
        getDataChart();


    }

    private String load;
    private void showLoading(){
        dialog = new Dialog(getContext(),R.style.Theme_PHQ_Market);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_wellcome,null);
        dialog.setContentView(view);

        TextView Tv_Well = view.findViewById(R.id.Tv_Well);
        TextView Tv_To = view.findViewById(R.id.Tv_To);

        Tv_Well.setText("Loading");
        Tv_To.setText(".");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                load = load == "." ? ".." :  load == ".." ? "..." :  ".";
                Tv_To.setText(load);
                handler.postDelayed(this, 500);
            }
        }, 500);
        dialog.show();
    }
}
