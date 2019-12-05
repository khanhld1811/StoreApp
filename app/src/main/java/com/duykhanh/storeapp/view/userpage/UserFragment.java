package com.duykhanh.storeapp.view.userpage;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.model.User;
import com.duykhanh.storeapp.presenter.user.UserContract;
import com.duykhanh.storeapp.presenter.user.UserPresenter;
import com.duykhanh.storeapp.view.userpage.account.AccountActivity;
import com.duykhanh.storeapp.view.userpage.orders.OrdersActivity;
import com.duykhanh.storeapp.view.userpage.userinfo.UserInfoActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements UserContract.View, View.OnClickListener {
    final String TAG = this.getClass().toString();

    Context context;
    RelativeLayout rlctnUserCard;
    LinearLayout llLoginRequire;
    View view;
    Button btnToLogIn, btnLogOut;
    TextView tvUserName, tvUserEmail,
            tvOrderStatus0, tvOrderStatus1, tvOrderStatus2, tvOrderStatus3, tvOrderStatus4, tvBoughtProduct;
    ImageView ivUserImage, ivEdit;

    UserPresenter presenter;
    private int mOffset = 0;
    private int mScrollY = 0;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initView();
        initComponent(view);

        llLoginRequire.setOnClickListener(this);
        rlctnUserCard.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
        tvOrderStatus0.setOnClickListener(this);
        tvOrderStatus1.setOnClickListener(this);
        tvOrderStatus2.setOnClickListener(this);
        tvOrderStatus3.setOnClickListener(this);
        tvOrderStatus4.setOnClickListener(this);
//        tvBoughtProduct.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestGetCurrentUser();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void requestCurrentUserSuccess(User user, Bitmap bmImage) {
        //Email
        tvUserEmail.setText(user.getEmail());
        //Name
        if (user.getName().equals("")) {
            tvUserName.setText("(Unknown)");
        } else {
            tvUserName.setText(user.getName());
        }
        //Image
        if (user.getPhoto().equals("")) {
            ivUserImage.setImageResource(R.drawable.unknown);
        } else {
            ivUserImage.setImageBitmap(bmImage);
        }
    }

    @Override
    public void requestLogOutSuccess() {
        presenter.requestGetCurrentUser();
    }

    @Override
    public void requestUserFailure(Throwable throwable) {
        Log.e(TAG, "requestUserFailure: ", throwable);
    }

    @Override
    public void requestLogOutFailure(Throwable throwable) {
        Toast.makeText(getContext(), "Đăng xuất thất bại!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "requestLogOutFailure: ", throwable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llLoginRequire:
                startActivity(new Intent(getContext(), AccountActivity.class));
                break;
            case R.id.btnLogOut:
                presenter.requestLogOut();
                break;
            case R.id.rlctnUserCart:
                startActivity(new Intent(getContext(), UserInfoActivity.class));
                break;
            case R.id.tvOrderStatus0:
                goOrder(0);
                break;
            case R.id.tvOrderStatus1:
                goOrder(1);
                break;
            case R.id.tvOrderStatus2:
                goOrder(2);
                break;
            case R.id.tvOrderStatus3:
                goOrder(3);
                break;
            case R.id.tvOrderStatus4:
                goOrder(4);
                break;
//            case R.id.tvBoughtProduct:
//                break;
        }
    }

    private void goOrder(int orderStatus){
        Intent intent = new Intent(getContext(), OrdersActivity.class);
        intent.putExtra("orderStatus",orderStatus);
        startActivity(intent);
    }

    private void initComponent(View view) {
        presenter = new UserPresenter(this);
    }

    private void initView() {
        llLoginRequire = view.findViewById(R.id.llLoginRequire);
        rlctnUserCard = view.findViewById(R.id.rlctnUserCart);
        btnToLogIn = view.findViewById(R.id.btnToLogIn);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        ivUserImage = view.findViewById(R.id.ivUserImage);
        tvOrderStatus0 = view.findViewById(R.id.tvOrderStatus0);
        tvOrderStatus1 = view.findViewById(R.id.tvOrderStatus1);
        tvOrderStatus2 = view.findViewById(R.id.tvOrderStatus2);
        tvOrderStatus3 = view.findViewById(R.id.tvOrderStatus3);
        tvOrderStatus4 = view.findViewById(R.id.tvOrderStatus4);
//        tvBoughtProduct = view.findViewById(R.id.tvBoughtProduct);
    }

    @Override
    public void showLoginRequire() {
        llLoginRequire.setVisibility(View.VISIBLE);
        btnLogOut.setVisibility(View.GONE);
    }

    @Override
    public void hideLoginRequire() {
        llLoginRequire.setVisibility(View.GONE);
        btnLogOut.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}