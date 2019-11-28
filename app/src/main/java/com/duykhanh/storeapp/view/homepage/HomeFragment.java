package com.duykhanh.storeapp.view.homepage;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.adapter.homescreen.ProductAdapter;
import com.duykhanh.storeapp.adapter.homescreen.ProductAdapterListener;
import com.duykhanh.storeapp.adapter.homescreen.SlideshowAdapter;
import com.duykhanh.storeapp.adapter.viewproduct.ViewProductAdapter;
import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.view.homepage.viewproductpage.ViewProductActivity;
import com.duykhanh.storeapp.view.productDetails.ProductDetailActivity;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import static com.duykhanh.storeapp.utils.Constants.*;


/**
 * Created by Duy Khánh on 11/6/2019.
 * <p>
 * View Trang chủ chứa layout hiển thị danh sách Product đượcw lấy từ model
 * bằng cách implement interface {@link ProductListContract.View}
 * thông qua {@link HomePresenter} lớp Presenter bởi .
 */
public class HomeFragment extends Fragment implements ProductListContract.View,
        ProductItemClickListener, View.OnClickListener {

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
    RecyclerView recyclerViewProduct, rcl_view_product;

    SliderView sliderView;

    TextView txt_view_all;

    TextView edFind;
    ImageButton btnSizeShopHome;
    TextView txtSizeShoppingHome;

    // Khởi tạo adapter
    SlideshowAdapter slideshowAdapter;
    ProductAdapter productAdapter;
    GridLayoutManager mLayoutManager;

    ViewProductAdapter viewProductAdapter;
    LinearLayoutManager linearLayoutManager;
    // Danh sách sản phẩm
    private List<Product> viewProductList;
    private List<Product> productList;

    private int pageNo = 0;
    private int pageView = 0;

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    //Slideshow
    AdapterViewFlipper avfSlideshow;

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

        // Khởi tạo slide show
        initSlideShow();

        // Khởi tạo các thành phân cần thiết
        initializationComponent();

        // Lắng nghe các tương tác của người dùng với view
        setListeners();

        registerListener();

        return view;
    }

    private void initUI() {
        //SlideShow
        sliderView = view.findViewById(R.id.imageSlider);
        //Lượt xem
        rcl_view_product = view.findViewById(R.id.rcl_view_product);
        txt_view_all = view.findViewById(R.id.txt_view_all);

        progressBarLoadProduct = view.findViewById(R.id.progressbarLoadProduct);
        nestedScrollViewHome = view.findViewById(R.id.nestedScrollViewContainerHome);
        swipeRefreshLayoutHome = view.findViewById(R.id.swipeRefreshLayout);
        recyclerViewProduct = view.findViewById(R.id.recyclerProducts);
        edFind = view.findViewById(R.id.edtFind);
        btnSizeShopHome = view.findViewById(R.id.imgbtnSizeShop);
        txtSizeShoppingHome = view.findViewById(R.id.txtSizeShoppingHome);
    }

    // Khởi tạo các object cần thiết
    private void initSlideShow() {
        slideshowAdapter = new SlideshowAdapter(getContext());

        sliderView.setSliderAdapter(slideshowAdapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }

    private void initializationComponent() {
        mPresenter = new HomePresenter(this);

        viewProductList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcl_view_product.setLayoutManager(linearLayoutManager);
        rcl_view_product.setItemAnimator(new DefaultItemAnimator());
        viewProductAdapter = new ViewProductAdapter(this,viewProductList);
        rcl_view_product.setAdapter(viewProductAdapter);

        productList = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewProduct.setLayoutManager(mLayoutManager);
        recyclerViewProduct.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(this, productList);
        recyclerViewProduct.setAdapter(productAdapter);

        pageView = 1;
        pageNo = 1;

        loading = true;


        mPresenter.requestDataFromServer();
        mPresenter.requestDataFromServerView();

        // Gửi yếu cầu lên server
        Log.d(TAG, "initializationComponent: ");
    }


    // Làm mới layout
    private void registerListener() {
        edFind.setOnClickListener(this);
        txt_view_all.setOnClickListener(this);
        btnSizeShopHome.setOnClickListener(this);

        swipeRefreshLayoutHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        previousTotal = 0;
                        loading = true;

                        initializationComponent();

                        swipeRefreshLayoutHome.setRefreshing(false);
                    }
                }, 1000);

            }
        });
    }

    private void setListeners() {
        nestedScrollViewHome.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        mPresenter.getMoreData(pageNo);
                    }
                }
            }
        });

        // Xử lý sự kiện phân trang danh sách lượt xem
        rcl_view_product.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading) {
                    pageView++;
                    Log.d(TAG, "onScrolled: AAA");
                    mPresenter.getMoreDataView(pageView);

                    loading = true;
                }
            }
        });
    }

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

        pageNo++;
    }

    @Override
    public void sendDataToHorizontalView(List<Product> viewProductArrayList) {
        viewProductList.addAll(viewProductArrayList);
        viewProductAdapter.notifyDataSetChanged();

    }

    // Nhận thông báo lỗi được gửi từ presenter
    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        Toast.makeText(getContext(), getString(R.string.communication_error), Toast.LENGTH_SHORT).show();
    }

    //TODO: Bắt sự kiện click view thực hiện hành động nào đó
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_view_all:
                Intent iViewProduct = new Intent(getContext(), ViewProductActivity.class);
                getActivity().startActivityForResult(iViewProduct,KEY_START_VIEW_PRODUCT);
                break;
            case R.id.imgbtnSizeShop:
                Fragment navCart = getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController cCart = NavHostFragment.findNavController(navCart);
                cCart.navigate(R.id.navCart);
                break;
            case R.id.edtFind:
                Fragment navSearch = getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController cSearch = NavHostFragment.findNavController(navSearch);
                cSearch.navigate(R.id.navSearch);
                break;
        }
    }

    @Override
    public void onProductItemClick(int position) {
        Intent detailIntent = new Intent(getContext(), ProductDetailActivity.class);
        detailIntent.putExtra(KEY_RELEASE_TO, productList.get(position).getId());
        detailIntent.putExtra("KEY_START_HOMESCREEN",KEY_DATA_HOME_TO_DETAIL_PRODUCT);
        startActivityForResult(detailIntent,KEY_START_DETAIL_PRODUCT);
    }

    //TODO : Click vào mỗi item sản phẩm
    @Override
    public void onProductItemViewclick(int position) {
        Intent detailIntent = new Intent(getContext(), ProductDetailActivity.class);
        detailIntent.putExtra(KEY_RELEASE_TO, viewProductList.get(position).getId());
        detailIntent.putExtra("KEY_START_HOMESCREEN",KEY_DATA_HOME_TO_DETAIL_PRODUCT);
        startActivityForResult(detailIntent,KEY_START_DETAIL_PRODUCT);
        Toast.makeText(getActivity(), "" + viewProductList.get(position).getNameproduct(), Toast.LENGTH_SHORT).show();
    }
}
