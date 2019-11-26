package com.duykhanh.storeapp.view.homepage;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.adapter.ProductAdapter;
import com.duykhanh.storeapp.adapter.ProductAdapterListener;
import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.view.productDetails.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.duykhanh.storeapp.utils.Constants.KEY_RELEASE_TO;


/**
 * Created by Duy Khánh on 11/6/2019.
 * <p>
 * View Trang chủ chứa layout hiển thị danh sách Product đượcw lấy từ model
 * bằng cách implement interface {@link ProductListContract.View}
 * thông qua {@link HomePresenter} lớp Presenter bởi .
 */
public class HomeFragment extends Fragment implements ProductListContract.View, ProductItemClickListener {

    private static final String TAG = HomeFragment.class.getSimpleName();
    // Load more
    private static ProductAdapterListener productAdapterListener;
    // Presenter interface dùng cho view
    private static ProductListContract.Presenter mPresenter;
    // Khởi tạo view
    View view;
    // view xml
    ProgressBar progressBarLoadProduct;
    NestedScrollView nestedScrollViewHome;
    SwipeRefreshLayout swipeRefreshLayoutHome;
    RecyclerView recyclerViewProduct;
    TextView edFind;
    ImageButton btnSizeShopHome;
    TextView txtSizeShoppingHome;
    // Khởi tạo adapter
    ProductAdapter productAdapter;
    GridLayoutManager mLayoutManager;
    // Danh sách sản phẩm
    private List<Product> productList;
    private int pageNo = 1;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static void setOnProductAdapterListener(ProductAdapterListener listener) {
        productAdapterListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        // Ánh xạ giao diện xml
        initUI();
        // Khởi tạo các thành phân cần thiết
        initializationComponent();
        // Lắng nghe các tương tác của người dùng với view
//        setListeners();
        registerListener();

        mPresenter.requestDataFromServer();
        return view;
    }

    private void initUI() {


        progressBarLoadProduct = view.findViewById(R.id.progressbarLoadProduct);
        nestedScrollViewHome = view.findViewById(R.id.nestedScrollViewContainerHome);
        swipeRefreshLayoutHome = view.findViewById(R.id.swipeRefreshLayout);
        recyclerViewProduct = view.findViewById(R.id.recyclerProducts);
        edFind = view.findViewById(R.id.edtFind);
        btnSizeShopHome = view.findViewById(R.id.imgbtnSizeShop);
        txtSizeShoppingHome = view.findViewById(R.id.txtSizeShoppingHome);
    }

    private void initializationComponent() {

        productList = new ArrayList<>();
        mPresenter = new HomePresenter(this);

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewProduct.setLayoutManager(mLayoutManager);
        recyclerViewProduct.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(this, productList);
        recyclerViewProduct.setAdapter(productAdapter);

        // Gửi yếu cầu lên server
        Log.d(TAG, "initializationComponent: ");
    }

    // Làm mới layout
    private void registerListener() {
        swipeRefreshLayoutHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initializationComponent();
                        swipeRefreshLayoutHome.setRefreshing(false);
                    }
                }, 1000);

            }
        });
    }

//    private void setListeners() {
//        nestedScrollViewHome.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (v.getChildAt(v.getChildCount() - 1) != null) {
//                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
//                            scrollY > oldScrollY) {
//
//
//                    }
//                }
//            }
//        });
//
//    }

    @Override
    public void showProgress() {
        progressBarLoadProduct.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarLoadProduct.setVisibility(View.GONE);
    }

    // Nhận list product được gửi từ presenter
    @Override
    public void sendDataToRecyclerView(List<Product> movieArrayList) {
        productList.addAll(movieArrayList);
        productAdapter.notifyDataSetChanged();
    }

    // Nhận thông báo lỗi được gửi từ presenter
    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        Toast.makeText(getContext(), getString(R.string.communication_error), Toast.LENGTH_SHORT).show();
    }

    //TODO : Click vào mỗi item sản phẩm
    @Override
    public void onMovieItemClick(int position) {
        Intent detailIntent = new Intent(getContext(), ProductDetailActivity.class);
        detailIntent.putExtra(KEY_RELEASE_TO, productList.get(position).getId());
        startActivity(detailIntent);
    }
}
