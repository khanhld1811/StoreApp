package com.duykhanh.storeapp.view.categorypage;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.adapter.category.ProductAdapterCategory;
import com.duykhanh.storeapp.adapter.category.SliderAdapterCategory;
import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.presenter.category.CategoryProductListContract;
import com.duykhanh.storeapp.presenter.category.CategoryProductListPresenter;
import com.duykhanh.storeapp.view.MainActivity;
import com.ligl.android.widget.iosdialog.IOSSheetDialog;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import static com.duykhanh.storeapp.utils.Constants.*;
/**
 * Created by Duy Khánh on 11/26/2019.
 */
public class CategoryListProductActivity extends AppCompatActivity implements CategoryProductListContract.View,View.OnClickListener, DialogInterface.OnClickListener{

    private static final String TAG = CategoryListProductActivity.class.getSimpleName();
    SliderView sliderView;
    SliderAdapterCategory adapter;
    CategoryProductListContract.Presenter mPresenter;

    View icl_product_list_category;
    View icl_slide_show_cateogry;
    View icl_toolbar_category;

    RecyclerView rcl_product_list;
    NestedScrollView ncv_category_product;
    TextView titleCategory;
    TextView txt_size_shop;
    TextView btn_filter;
    ImageView imgBackCategory;
    ImageButton btn_category_to_cart;
    ProgressBar pb_load;

    TextView edFind;

    List<Product> listProduct;
    ProductAdapterCategory adapterProduct;

    GridLayoutManager mLayoutManager;

    String id_category, title_category;

    int pageNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_product);

        getData();

        initUI();
        initSlideShow();
        initRecyclerView();
        registerListener();
        onListenerScrollView();
    }

    private void initUI() {
        icl_product_list_category = findViewById(R.id.icl_product_list_category);
        icl_slide_show_cateogry = findViewById(R.id.icl_slide_show_cateogry);
        icl_toolbar_category = findViewById(R.id.icl_toolbar_category);

        edFind = icl_toolbar_category.findViewById(R.id.edtFind);
        txt_size_shop = icl_toolbar_category.findViewById(R.id.txtSizeShoppingHome);

        sliderView = icl_slide_show_cateogry.findViewById(R.id.imageSlider);

        rcl_product_list = icl_product_list_category.findViewById(R.id.rcl_CategoryProductList);
        ncv_category_product  = findViewById(R.id.ncv_category_product);
        titleCategory = icl_product_list_category.findViewById(R.id.txtTitleCategory);
        imgBackCategory = icl_toolbar_category.findViewById(R.id.img_back_category);
        btn_category_to_cart = icl_toolbar_category.findViewById(R.id.imgbtnSizeShop);
        btn_filter = icl_product_list_category.findViewById(R.id.btn_filter_price);
        pb_load = icl_product_list_category.findViewById(R.id.pb_load_category);

        titleCategory.setText(title_category);
    }

    // Khởi tạo các object cần thiết

    private void initSlideShow() {

        pageNo = 1;

        adapter = new SliderAdapterCategory(this);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        mPresenter = new CategoryProductListPresenter(this,this);

        //Gửi yêu vầu và key category lên server
        mPresenter.requestDataFromServer(id_category,pageNo);
        mPresenter.requestDataCountFormDB();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestDataCountFormDB();
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
        edFind.setOnClickListener(this);
        imgBackCategory.setOnClickListener(this);
        btn_category_to_cart.setOnClickListener(this);
        btn_filter.setOnClickListener(this);
    }

    private void onListenerScrollView() {
        ncv_category_product.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY == (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        Log.d(TAG, "onScrollChange: AAAA" );
                        pb_load.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.getMoreListCategory(id_category,pageNo);
                            }
                        }, 1000);

                    }
                }
            }
        });
    }

    // // Nhận list product được gửi từ presenter
    @Override
    public void senDataToRecyclerView(List<Product> productList) {
        listProduct.addAll(productList);
        adapterProduct.notifyDataSetChanged();

        pageNo ++;
    }

    @Override
    public void sendDataToRecyclerViewHigh(List<Product> productHight) {
        listProduct.clear();
        Log.d(TAG, "sendDataToRecyclerViewHigh: ");
        listProduct.addAll(productHight);
        adapterProduct.notifyDataSetChanged();

        pageNo++;
    }

    @Override
    public void sendDataToRecyclerViewLow(List<Product> productView) {
        listProduct.clear();
        Log.d(TAG, "sendDataToRecyclerViewPrice: ");
        listProduct.addAll(productView);
        adapterProduct.notifyDataSetChanged();

        pageNo++;
    }

    @Override
    public void sendCountProduct(int countProduct) {
        txt_size_shop.setText("" + countProduct);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d("ERROR", "onResponseFailure: " + throwable);
    }


    @Override
    public void hiddenProgressBar() {
        pb_load.setVisibility(View.GONE);
    }

    @Override
    public void showSizeCart() {
        txt_size_shop.setVisibility(View.VISIBLE);
    }

    @Override
    public void hiddenSizeCart() {
        txt_size_shop.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back_category:
                finish();
                break;
            case R.id.imgbtnSizeShop:
                Intent iViewProductCart = new Intent(CategoryListProductActivity.this, MainActivity.class);
                iViewProductCart.putExtra("KEY_START_CATEGORY_PRODUCT_CART",KEY_DATA_CATEGORY_PRODUCT_CART);
                setResult(RESULT_OK, iViewProductCart);
                finish();
                break;
            case R.id.edtFind:
                Intent iViewProductSearch = new Intent(CategoryListProductActivity.this, MainActivity.class);
                iViewProductSearch.putExtra("KEY_START_CATEGORY_PRODUCT_SEARCH",KEY_DATA_CATEGORY_PRODUCT_SEARCH);
                setResult(RESULT_OK, iViewProductSearch);
                finish();
                break;
            case R.id.btn_filter_price:
               dialogSortBuy();
                break;
        }
    }

    private void dialogSortBuy() {
        IOSSheetDialog.SheetItem[] items = new IOSSheetDialog.SheetItem[2];
        items[0] = new IOSSheetDialog.SheetItem("Giá cao nhất", IOSSheetDialog.SheetItem.BLUE);
        items[1] = new IOSSheetDialog.SheetItem("Giá thấp nhất", IOSSheetDialog.SheetItem.BLUE);
        new IOSSheetDialog.Builder(this)
                .setData(items, this::onClick).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Intent iViewProduct = new Intent(CategoryListProductActivity.this, MainActivity.class);
            iViewProduct.putExtra("KEY_START_CATEGORY_PRODUCT_CART",KEY_DATA_CATEGORY_PRODUCT_CART);
            setResult(RESULT_OK, iViewProduct);
            finish();
        }
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(i == 0){
            pageNo = 1;
            mPresenter.requestDataHighPriceFromServer(id_category,pageNo);
        }
        else{
            pageNo = 1;
            mPresenter.requestDataLowPriceFromServer(id_category,pageNo);
        }
    }
}
