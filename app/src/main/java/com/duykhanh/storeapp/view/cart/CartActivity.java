package com.duykhanh.storeapp.view.cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.adapter.CartAdapter;
import com.duykhanh.storeapp.model.CartItem;
import com.duykhanh.storeapp.utils.Formater;
import com.duykhanh.storeapp.view.payment.PaymentActivity;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartContract.View, OnCartItemClickListener, View.OnClickListener {
    final String TAG = this.getClass().toString();
    int total;
    List<CartItem> cartItems;

    CartAdapter cartAdapter;
    LinearLayoutManager layoutManager;

    RecyclerView rvCart;
    ProgressBar pbLoading;
    TextView tvTotal;
    Button btnPay;

    CartPresenter cartPresenter;

    Formater formater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        initComponent();
        settingCartRecyclerView();
        cartPresenter.requestCartItems();

        btnPay.setOnClickListener(this);
    }

    @Override
    public void setCartItemsToCartRv(List<CartItem> cartItemss) {
        cartItems.clear();
        total = 0;
        cartItems.addAll(cartItemss);
        for (CartItem cartItem : cartItems) {
        }
        cartAdapter.notifyDataSetChanged();
        for (CartItem cartItem : cartItems){
            total += (cartItem.getQuantity() * cartItem.getPrice());
        }
        tvTotal.setText(formater.formatMoney(total) + " đ");
    }

    @Override
    public void onCartItemClick(int position) {
        CartItem cartItem = cartItems.get(position);
    }

    @Override
    public void onDeleteButtonClick(int position) {
        CartItem cartItem = cartItems.get(position);
        cartPresenter.requestDeleteCartItem(cartItem.getProductid());
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onIncreaseButtonClick(int position) {
        CartItem cartItem = cartItems.get(position);
        cartPresenter.requestIncreaseQuantity(cartItem.getProductid());
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDecreaseButtonClick(int position) {
        CartItem cartItem = cartItems.get(position);
        cartPresenter.requestDecreaseQuantity(cartItem.getProductid());
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCartItemsResponseFailure(Throwable throwable) {

    }

    private void settingCartRecyclerView() {
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvCart.setLayoutManager(layoutManager);
        rvCart.setAdapter(cartAdapter);
    }

    private void initComponent() {
        cartItems = new ArrayList<>();
        cartAdapter = new CartAdapter(this, cartItems, R.layout.item_cartitem);
        cartPresenter = new CartPresenter(this);
        layoutManager = new LinearLayoutManager(this);
        formater = new Formater();
    }

    private void initView() {
        rvCart = findViewById(R.id.rcl_cart);
        pbLoading = findViewById(R.id.pbCartsLoading);
        tvTotal = findViewById(R.id.txt_sumMoneyCart);
        btnPay=findViewById(R.id.btnToPay);
    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cartPresenter.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnToPay:
                startActivity(new Intent(CartActivity.this, PaymentActivity.class));
                break;
        }
    }
}
