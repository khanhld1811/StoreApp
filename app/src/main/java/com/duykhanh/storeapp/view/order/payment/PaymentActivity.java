package com.duykhanh.storeapp.view.order.payment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.adapter.BuyingProductAdapter;
import com.duykhanh.storeapp.model.CartItem;
import com.duykhanh.storeapp.model.Order;
import com.duykhanh.storeapp.model.User;
import com.duykhanh.storeapp.utils.Formater;
import com.duykhanh.storeapp.view.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, PaymentContract.View {
    final String TAG = this.getClass().toString();
    int total;
    List<CartItem> cartItems;
    User user;

    RecyclerView rvBuyingProducts;
    TextView tvAddress, tvPhone,
            tvTotal, tvDiscount, tvShipTax, tvTotalPay;
    EditText etNewAddress, etNewPhone;
    ProgressBar pbLoading;
    Button btnChangeAddress,
            btnPay;
    AlertDialog alertDialog;

    PaymentPresenter presenter;
    LinearLayoutManager layoutManager;
    BuyingProductAdapter adapter;

    Formater formater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
        initComponent();
        settingRecyclerView();
        total = 0;
        //Lấy thông tin người dùng
        presenter.requestCurrentUser();
        //Lấy thông tin giỏ hàng
        presenter.requestCartItemsFromSql();

        btnChangeAddress.setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override //Hoàn thành lấy trạng thái đăng nhập
    public void requestCurrentUserComplete(User userr) {
        user = userr;
        //Đưa địa chỉ, sđt vào view
        if (user.getAddress().trim().equals("")) {
            tvAddress.setText(Html.fromHtml("<b>Địa chỉ: </b>(Chưa có)"));
            if (user.getPhone().trim().equals("")) {
                tvPhone.setText(Html.fromHtml("<b>SĐT: </b>(Chưa có)"));
            }
            return;
        }
        tvAddress.setText(Html.fromHtml("<b>Địa chỉ: </b> ") + user.getAddress());
        tvPhone.setText(Html.fromHtml("<b>SĐT: </b>") + user.getPhone());
    }

    @SuppressLint("SetTextI18n")
    @Override //Hoàn thành yêu cầu lấy thông tin giỏ hàng
    public void requestCartItemsComplete(List<CartItem> cartItemss) {
        //Danh sách hàng sẽ mua
        cartItems.clear();
        cartItems.addAll(cartItemss);
        adapter.notifyDataSetChanged();
        //Tính toán chi phí
        float discount = 0.05f;
        int shipTax = 50000;
        for (CartItem cartItem : cartItems) {
            total += (cartItem.getQuantity() * cartItem.getPrice());
            Log.d(TAG, "requestCartItemsComplete: total" + total);
        }
        float pay = total - (total * discount) + shipTax;
        Log.d(TAG, "requestCartItemsComplete: pay" + pay);
        //Hiển thị
        tvTotal.setText("Tổng cộng: " + formater.formatMoney(total));
        tvDiscount.setText("Giảm giá (" + discount * 100 + "%): " + formater.formatMoney((int) (total * discount)));
        tvShipTax.setText("Chi phí vận chuyển: " + formater.formatMoney(shipTax));
        tvTotalPay.setText("Thành tiền: " + formater.formatMoney((int) pay));
    }

    @Override
    public void onPayed() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void requestPayedFailure(Throwable throwable) {
        Log.e(TAG, "requestPayedFailure: ", throwable);
        Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPay: //Nút mua hàng
                Order order = new Order("StringUID", Calendar.getInstance().getTime());
                presenter.requestOrderDetailsFromSql(order);
                break;
            case R.id.btnChangePhoneAndAddress: //Nút hiện form thay đổi phone và số điện thoại
                showDialogChangeInfo();
                break;
        }
    }

    private void showDialogChangeInfo() { // Custom Dialog thay đổi thông tin
        //Tạo, khai báo Layout và View cho Dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.layout_form_changeaddressandphone, null);
        etNewAddress = dialog.findViewById(R.id.etNewAddress);
        etNewPhone = dialog.findViewById(R.id.etNewPhone);
        //Tạo Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog)
                .setPositiveButton("Thay đổi", null)
                .setNegativeButton("Hủy bỏ", null);
        alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = etNewAddress.getText().toString();
                String phone = etNewPhone.getText().toString();
                if (address.equals("") || phone.equals("")) {
                    Toast.makeText(PaymentActivity.this, "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    user.setAddress(address);
                    user.setPhone(phone);
                    presenter.requestUpdateUserInfo(user);
                    alertDialog.dismiss();
                }
            }
        });
    }

    private void settingRecyclerView() {
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvBuyingProducts.setLayoutManager(layoutManager);
        rvBuyingProducts.setAdapter(adapter);
    }

    private void initComponent() {
        //Formater
        formater = new Formater();
        //User
        user = new User();
        //Presenter
        presenter = new PaymentPresenter(PaymentActivity.this);
        //RecyclerView
        cartItems = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        adapter = new BuyingProductAdapter(R.layout.item_payment, this, cartItems);
    }

    private void initView() {
        tvAddress = findViewById(R.id.tvPUserAddress);
        tvPhone = findViewById(R.id.tvPUserPhone);
        btnChangeAddress = findViewById(R.id.btnChangePhoneAndAddress);
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
