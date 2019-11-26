package com.duykhanh.storeapp.view.userpage;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;


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

import com.scwang.smartrefresh.layout.util.SmartUtil;



/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements UserContract.View, View.OnClickListener, UserContract.Handle.OnGetCurrentUserListener {
    final String TAG = this.getClass().toString();

    Context context;
    View view;
    FrameLayout inclLogInRequire;
    Button btnToLogIn, btnLogOut;

    UserPresenter presenter;

    private static final String TAG = UserFragment.class.getSimpleName();
    private int mOffset = 0;
    private int mScrollY = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        final Toolbar toolbar = root.findViewById(R.id.toolbar);

        // view image background
        final View parallax = root.findViewById(R.id.parallax);

        final View buttonBar = root.findViewById(R.id.buttonBarLayout);
        final NestedScrollView scrollView = root.findViewById(R.id.scrollView);

        //scrollView
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = SmartUtil.dp2px(170);
            //color toolbar
            private int color = ContextCompat.getColor(getActivity().getApplicationContext(),R.color.colorIcon) & 0x00ffffff;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    buttonBar.setAlpha(1f * mScrollY / h);
                    // set background when show toolbar
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    //scroll image
                    parallax.setTranslationY(mOffset - mScrollY);
                }
                lastScrollY = scrollY;
            }
        });


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
    public void requestCurrentUserFailure(Throwable throwable) {
        Log.e(TAG, "requestCurrentUserFailure: ", throwable);
    }

    @Override
    public void requestLogOutSuccess() {
        presenter.requestGetCurrentUser();
    }

    @Override
    public void requestLogOutFailure(Throwable throwable) {
        Toast.makeText(context, "Đăng xuất thất bại!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "requestLogOutFailure: ", throwable);
=======
        buttonBar.setAlpha(0);
        toolbar.setBackgroundColor(0);

        return root;

    }

    @Override
    public void onGetCurrentUserFinished(FirebaseUser firebaseUser) {

    }

    @Override
    public void onGetCurrentUserFailure(Throwable throwable) {

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
