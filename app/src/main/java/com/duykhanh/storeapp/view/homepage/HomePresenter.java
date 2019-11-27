package com.duykhanh.storeapp.view.homepage;

import android.util.Log;

import com.duykhanh.storeapp.model.Product;

import java.util.List;

/**
 * Created by Duy Khánh on 11/6/2019.
 * Lớp trung gian giao giao tiếp giữu Handle và View
 */
public class HomePresenter implements ProductListContract.Presenter,
        ProductListContract.Handle.OnFinishedListener,
        ProductListContract.Handle.OnFinishedListenerView{

    // Presenter interface dùng cho view
    private ProductListContract.View productListView;

    // Presenter interface dùng cho Handle
    private ProductListContract.Handle handleProductList;

    /*
    * Hàm khởi tạo của HomePresenter với tham số là ProductListContract.View.
    * Tham số thể hiện dùng để giao tiếp giữu HomePresenter và HomeFragment
    */
    public HomePresenter(ProductListContract.View productListView) {
        this.productListView = productListView;
        handleProductList = new ProductHandle();
    }

    /*
    * Nhận dữ liệu từ class xử lý data ProductHandle.class và gửi dữ liệu cho view
    */
    @Override
    public void onFinished(List<Product> productArrayList) {
        productListView.sendDataToRecyclerView(productArrayList);
        if(productListView != null){
            productListView.hideProgress();
        }
    }

    @Override
    public void onFinishedLoadMore() {
        if(productListView != null){
            productListView.hideProgress();
        }
    }

    // Lỗi kết nối được gọi bởi ProductHandle.class và gửi cho view
    @Override
    public void onFailure(Throwable t) {
        // Gửi lỗi cho view
        productListView.onResponseFailure(t);
    }


    @Override
    public void getMoreData(int pageNo) {
        if(productListView != null){
            productListView.showProgress();
        }
        handleProductList.getProductList(this,pageNo);
    }

    @Override
    public void getMoreDataView(int pageView) {
        handleProductList.getProductView(this,pageView);
    }

    /*
    * Gửi yêu cầu từ view và được xử lý bởi ProductHandle.class
    */
    @Override
    public void requestDataFromServer() {
        if(productListView != null){
            productListView.showProgress();
        }
        handleProductList.getProductList(this,1);
    }

    @Override
    public void requestDataFromServerView() {
        if(productListView != null){
            productListView.showProgress();
        }
        handleProductList.getProductView(this,1);
    }

    /*
    * hận dữ liệu từ class xử lý data ProductHandle.class và gửi dữ liệu cho view
    */
    @Override
    public void onFinishedView(List<Product> viewProductArrayList) {
        productListView.sendDataToHorizontalView(viewProductArrayList);
        if(productListView != null){
            productListView.hideProgress();
        }
    }

    @Override
    public void onFailureView(Throwable t) {
        if(productListView != null){
            productListView.hideProgress();
        }
        productListView.onResponseFailure(t);
    }
}
