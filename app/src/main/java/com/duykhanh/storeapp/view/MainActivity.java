package com.duykhanh.storeapp.view;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.view.cart.CartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/*
* Điều khiển hoạt động chính của ứng dụng với bố cục 'R.layout.activity_main'
* chứa Navigation (@link https://developer.android.com/guide/navigation) hiển thị danh sách thông tin về :
*   + Trang chủ
*   + Danh mục
*   + Cá nhân
*   + Thông báo
*
*   @author Lê Duy Khánh
*/
import static com.duykhanh.storeapp.utils.Constants.*;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    int key = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
         /*
         * Mỗi id button trong menu tương ứng với id menu khai báo trong navigation
         */
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


    }
    // Ánh xạ giao diện
    private void initUI() {
        bottomNavigationView = findViewById(R.id.nav_view);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK) {
                Fragment navhost = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController c = NavHostFragment.findNavController(navhost);
                c.navigate(R.id.navCart);
            }

    }
}
