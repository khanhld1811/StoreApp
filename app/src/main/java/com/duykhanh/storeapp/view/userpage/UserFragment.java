package com.duykhanh.storeapp.view.userpage;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.model.User;
import com.duykhanh.storeapp.view.userpage.account.AccountActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements UserContract.View, View.OnClickListener {
    final String TAG = this.getClass().toString();

    Context context;
    View view;
    FrameLayout inclLogInRequire;
    Button btnToLogIn, btnLogOut;
    TextView tvUserName, tvUserEmail;
    ImageView ivUserImage, ivEdit;

    UserIdPresenter presenter;
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

        btnToLogIn.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
        ivEdit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestGetUserId();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void requestUserIdSuccess(User user) {
        //Email
        tvUserEmail.setText(user.getEmail());
        //Name
        if (user.getName().equals("")){
            tvUserName.setText("(Unknown)");
        }
        else {
            tvUserName.setText(user.getName());
        }
        //Image
        if (user.getPhoto().equals("")){
            //Drawable to Bitmap
//            Bitmap bmImage = BitmapFactory.decodeResource(getResources(),R.drawable.unknown);
//            ivUserImage.setImageBitmap(bmImage);
            ivUserImage.setImageResource(R.drawable.unknown);
        }
        else {
            //hinhanhs
        }
    }

    @Override
    public void requestLogOutSuccess() {
        presenter.requestGetUserId();
    }

    @Override
    public void requestUserIdFailure(Throwable throwable) {
        Log.e(TAG, "requestUserIdFailure: ", throwable);
    }

    @Override
    public void requestLogOutFailure(Throwable throwable) {
        Toast.makeText(context, "Đăng xuất thất bại!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "requestLogOutFailure: ", throwable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnToLogIn:
                startActivity(new Intent(getContext(), AccountActivity.class));
                break;
            case R.id.btnLogOut:
                presenter.requestLogOut();
                break;
            case R.id.ivEdit:

                break;
        }
    }

    private void initComponent(View view) {
        presenter = new UserIdPresenter(this);
    }

    private void initView() {
        inclLogInRequire = view.findViewById(R.id.inclLogInRequire);
        btnToLogIn = view.findViewById(R.id.btnToLogIn);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        ivEdit = view.findViewById(R.id.ivEdit);
        ivUserImage = view.findViewById(R.id.ivUserImage);
    }

    @Override
    public void showLoginRequire() {
        inclLogInRequire.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginRequire() {
        inclLogInRequire.setVisibility(View.GONE);
    }
}