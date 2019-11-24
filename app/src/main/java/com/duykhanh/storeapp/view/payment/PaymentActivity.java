package com.duykhanh.storeapp.view.payment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.model.Order;
import com.duykhanh.storeapp.model.OrderDetail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, PaymentContract.View {
    final String TAG = this.getClass().toString();

    RecyclerView rvBuyingProducts;
    TextView tvTotal, tvDiscount, tvShipTax, tvTotalPay;
    ProgressBar pbLoading;
    Button btnPay;

    PaymentPresenter paymentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
        initComponent();

        btnPay.setOnClickListener(this);
    }

    @Override
    public void onResponsePayedFailure(Throwable throwable) {
        Log.e(TAG, "onResponsePayedFailure: ", throwable);
        Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPayed() {
        finish();
        finish();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPay:
                Order order =  new Order("StringUID", Calendar.getInstance().getTime());
                paymentPresenter.requestOrderDetailsFromSql(order);
                break;
        }
    }

    private void initComponent() {
        paymentPresenter = new PaymentPresenter(PaymentActivity.this);
    }

    private void initView() {
        rvBuyingProducts = findViewById(R.id.rvBuyingProducts);
        tvTotal = findViewById(R.id.tvPaymentTotal);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvShipTax = findViewById(R.id.tvShipTax);
        tvTotalPay = findViewById(R.id.tvTotalPay);
        pbLoading = findViewById(R.id.pbPayLoading);
        btnPay = findViewById(R.id.btnPay);
    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }
}
