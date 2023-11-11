package com.example.phq_market.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.phq_market.R;
import com.example.phq_market.activity.Activity_EditAccount;
import com.example.phq_market.activity.Activity_Login;
import com.example.phq_market.activity.Activity_Order;
import com.example.phq_market.activity.Activity_Signup;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

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
    EDITACCOUNT acc;
    SharedPreferences s;

    private String email = "";
    private String pass = "";
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
        acc = new EDITACCOUNT();
        listmonthandday = new ArrayList<>();
        sharedPreferences = getContext().getSharedPreferences("account",MODE_PRIVATE);
        checkLogin(btn_setUp);
        btn_setUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_setUp.getText() == "Log out"){
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

        lnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_Order.class));
            }
        });

        return view;
    }

    private void SetChart(){
        lineChart.getDescription().setEnabled(false);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawGridBackground(true);

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(getContext(), "Value: "
                        + e.getY()
                        + ", index: "
                        + h.getX()
                        + ", DataSet index: "
                        + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


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
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        //add đưuòng vào biểu đồ
        lineData.addDataSet(set);
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

    @Override
    public void onResume() {
        super.onResume();
        s = getContext().getSharedPreferences("account", MODE_PRIVATE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!sharedPreferences.getString("Email", "").isEmpty() && !sharedPreferences.getString("Pass", "").isEmpty()) {
                    Retrofit retrofit_account = new Retrofit.Builder()
                            .baseUrl("https://phqmarket.000webhostapp.com/account/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    api api_account = retrofit_account.create(api.class);
                    Call<ACCOUNT> call_account = api_account.getDetailAccount(s.getString("Email", null), s.getString("Pass", null));
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
                                acc.setPASS(s.getString("Pass",null));
                                acc.setPHONE(account.getPHONE());
                                Log.d(">>>>>>>>>>>>>>>>>>>>>>>", acc+"");
                            } else {
                                Log.d(">>>>>>>>>>>>>>>>>>>>>>>,", response.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<ACCOUNT> call, Throwable t) {

                        }
                    });
                }
            ;}
        }).start();

        try {
            email = sharedPreferences.getString("Email", "");
            pass = sharedPreferences.getString("Pass", "");
        } catch (Exception e) {

        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (TextUtils.isEmpty(email) ||TextUtils.isEmpty(pass)){
                    Txt_name.setText("");
                    Txt_email.setText("");
                    Txt_phone.setText("");
                    Txt_address.setText("");
                    Txt_like.setText("0");
                    Txt_order.setText("0");
                    Txt_cart.setText("0");
                    ic_Edit.setVisibility(View.GONE);
                    lnOrder.setEnabled(false);
                }else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://phqmarket.000webhostapp.com/purchase/")
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
                            }else {
                                listmonthandday.clear();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<MONTHANDDAY>> call, Throwable t) {

                        }
                    });
                }
            }
        }).start();


    }
}
