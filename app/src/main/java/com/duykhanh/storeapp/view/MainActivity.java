package com.duykhanh.storeapp.view;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.duykhanh.storeapp.R;
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
public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

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
}
