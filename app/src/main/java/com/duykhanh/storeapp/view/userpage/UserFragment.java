package com.duykhanh.storeapp.view.userpage;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.view.userpage.account.AccountActivity;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements UserContract.View, View.OnClickListener {
    final String TAG = this.getClass().toString();
    Context context;
    View view;
    FrameLayout inclLogInRequire;
    Button btnToLogIn, btnLogOut;
    UserPresenter presenter;
    private int mOffset = 0;
    private int mScrollY = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initView();
        initComponent(view);

        btnToLogIn.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestGetCurrentUser();
    }

    @Override
    public void requestCurrentUserSuccess(FirebaseUser firebaseUser) {

    }

    @Override
    public void requestLogOutSuccess() {
        presenter.requestGetCurrentUser();
    }

    @Override
    public void requestCurrentUserFailure(Throwable throwable) {
        Log.e(TAG, "requestCurrentUserFailure: ", throwable);
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
        }
    }

    private void initComponent(View view) {
        presenter = new UserPresenter(this);
    }

    private void initView() {
        inclLogInRequire = view.findViewById(R.id.inclLogInRequire);
        btnToLogIn = view.findViewById(R.id.btnToLogIn);
        btnLogOut = view.findViewById(R.id.btnLogOut);
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