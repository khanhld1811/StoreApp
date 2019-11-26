package com.duykhanh.storeapp.view.userpage.account;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.adapter.ViewPagerAccountAdapter;
import com.duykhanh.storeapp.view.userpage.account.login.LoginFragment;
import com.duykhanh.storeapp.view.userpage.account.register.RegisterFragment;

public class AccountActivity extends AppCompatActivity {
    final String TAG = this.getClass().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ViewPager viewPager = findViewById(R.id.vpAccount);

        ViewPagerAccountAdapter pagerAdapter = new ViewPagerAccountAdapter(getSupportFragmentManager());
        pagerAdapter.addFragmet(new LoginFragment());
        pagerAdapter.addFragmet(new RegisterFragment());
        viewPager.setAdapter(pagerAdapter);
    }
}