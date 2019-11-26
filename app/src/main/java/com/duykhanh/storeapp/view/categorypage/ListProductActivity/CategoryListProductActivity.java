package com.duykhanh.storeapp.view.categorypage.ListProductActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.adapter.ProductAdapter;
import com.duykhanh.storeapp.adapter.category.ProductAdapterCategory;
import com.duykhanh.storeapp.adapter.category.SliderAdapterCategory;
import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.view.cart.CartActivity;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import static com.duykhanh.storeapp.utils.Constants.*;
/**
 * Created by Duy Khánh on 11/26/2019.
 */
public class CategoryListProductActivity extends AppCompatActivity implements CategoryProductListContract.View,View.OnClickListener {

    SliderView sliderView;
    SliderAdapterCategory adapter;
    CategoryProductListContract.Presenter mPresenter;

    View icl_product_list_category;
    View icl_slide_show_cateogry;
    View icl_toolbar_category;

    RecyclerView rcl_product_list;
    TextView titleCategory;
    ImageView imgBackCategory;
    ImageButton imgbtnSizeShop;

    List<Product> listProduct;
    ProductAdapterCategory adapterProduct;

    GridLayoutManager mLayoutManager;

    String id_category, title_category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_product);

        getData();

        initUI();
        initSlideShow();
        initRecyclerView();
        registerListener();
    }

    private void initUI() {
        icl_product_list_category = findViewById(R.id.icl_product_list_category);
        icl_slide_show_cateogry = findViewById(R.id.icl_slide_show_cateogry);
        icl_toolbar_category = findViewById(R.id.icl_toolbar_category);

        sliderView = icl_slide_show_cateogry.findViewById(R.id.imageSlider);

        rcl_product_list = icl_product_list_category.findViewById(R.id.rcl_CategoryProductList);
        titleCategory = icl_product_list_category.findViewById(R.id.txtTitleCategory);
        imgBackCategory = icl_toolbar_category.findViewById(R.id.img_back_category);
        imgbtnSizeShop = icl_toolbar_category.findViewById(R.id.imgbtnSizeShop);


        titleCategory.setText(title_category);

    }

    // Khởi tạo các object cần thiết
    private void initSlideShow() {
        adapter = new SliderAdapterCategory(this);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        mPresenter = new CategoryProductListPresenter(this);

        //Gửi yêu vầu và key category lên server
        mPresenter.requestDataFromServer(id_category);
    }

    // Khởi tạo recyclerview
    private void initRecyclerView() {
        listProduct = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(this, 2);
        rcl_product_list.setLayoutManager(mLayoutManager);
        rcl_product_list.setItemAnimator(new DefaultItemAnimator());
        adapterProduct = new ProductAdapterCategory(this, listProduct);
        rcl_product_list.setAdapter(adapterProduct);

    }

    // Lấy key category truyền từ CategoryFragment
    private void getData() {
        Intent intent = getIntent();
        id_category = intent.getStringExtra(KEY_CATEGORY);
        title_category = intent.getStringExtra(KEY_TITLE);
    }

    private void registerListener(){
        imgBackCategory.setOnClickListener(this);
        imgbtnSizeShop.setOnClickListener(this);
    }

    // // Nhận list product được gửi từ presenter
    @Override
    public void senDataToRecyclerView(List<Product> productList) {
        listProduct.addAll(productList);
        Log.d("sendData", "senDataToRecyclerView: " + productList.get(0).getNameproduct());
        adapterProduct.notifyDataSetChanged();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d("ERROR", "onResponseFailure: " + throwable);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back_category:
                finish();
                break;
            case R.id.imgbtnSizeShop:
                startActivity(new Intent(CategoryListProductActivity.this, CartActivity.class));
                break;
        }
    }
}
