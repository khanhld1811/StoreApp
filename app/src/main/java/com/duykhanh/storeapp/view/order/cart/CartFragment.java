package com.duykhanh.storeapp.view.order.cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.adapter.cart.CartAdapter;
import com.duykhanh.storeapp.model.CartItem;
import com.duykhanh.storeapp.presenter.order.CartContract;
import com.duykhanh.storeapp.presenter.order.CartPresenter;
import com.duykhanh.storeapp.utils.Formater;
import com.duykhanh.storeapp.view.order.payment.PaymentActivity;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements CartContract.View, OnCartItemClickListener, View.OnClickListener {
    final String TAG = this.getClass().toString();
    int total;
    List<CartItem> cartItems;

    CartAdapter cartAdapter;
    LinearLayoutManager layoutManager;

    View view;
    RecyclerView rvCart;
    ProgressBar pbLoading;
    TextView tvTotal;
    Button btnPay;

    CartPresenter cartPresenter;

    Formater formater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        initView();
        initComponent();
        settingCartRecyclerView();

        btnPay.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        cartPresenter.requestCartItems();
    }

    @Override
    public void setCartItemsToCartRv(List<CartItem> cartItemss) {
        Log.d(TAG, "setCartItemsToCartRv: ");
        cartItems.clear();
        total = 0;
        cartItems.addAll(cartItemss);
        for (CartItem cartItem : cartItems) {
        }
        cartAdapter.notifyDataSetChanged();
        for (CartItem cartItem : cartItems) {
            total += (cartItem.getQuantity() * cartItem.getPrice());
        }
        tvTotal.setText(formater.formatMoney(total) + " đ");
        if (total == 0) {
            btnPay.setEnabled(false);
            btnPay.setText("Giỏ hàng hiện đang trống,\nVui lòng chọn sản phẩm!");
            btnPay.setBackgroundColor(getContext().getColor(R.color.colorGrey));
        }
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
        layoutManager = new LinearLayoutManager(getContext());
        formater = new Formater();
    }

    private void initView() {
        rvCart = view.findViewById(R.id.rcl_cart);
        pbLoading = view.findViewById(R.id.pbCartsLoading);
        tvTotal = view.findViewById(R.id.txt_sumMoneyCart);
        btnPay = view.findViewById(R.id.btnToPay);
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
    public void onDestroy() {
        super.onDestroy();
        cartPresenter.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnToPay:
                startActivity(new Intent(getContext(), PaymentActivity.class));
                break;
        }
    }
}
