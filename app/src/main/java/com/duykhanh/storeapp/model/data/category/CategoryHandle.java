package com.duykhanh.storeapp.model.data.category;

import android.util.Log;

import com.duykhanh.storeapp.model.Category;
import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.network.ApiUtils;
import com.duykhanh.storeapp.network.DataClient;
import com.duykhanh.storeapp.presenter.category.CategoryContract;
import com.duykhanh.storeapp.presenter.category.CategoryProductListContract;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Duy Khánh on 11/25/2019.
 */
public class CategoryHandle implements CategoryContract.Handle, CategoryProductListContract.Handle {

    private static final String TAG = CategoryHandle.class.getSimpleName();

    @Override
    public void getCategory(CategoryContract.Handle.OnFinishedListener onFinishedListener) {
        DataClient apiService = ApiUtils.getProductList();
        /*
         * Gửi yêu cầu trả về 1 danh sách dữ liệu (List<Product>)
         */
        Call<List<Category>> call = apiService.getCategory();
        call.enqueue(new Callback<List<Category>>() {
            // Khi nhận được phản hồi
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                // Thành công gửi dữ liệu dưới dáng danh sách sản phẩm
                if (response.isSuccessful()) {
                    List<Category> categoryList = response.body();
                    Log.d(TAG, "onResponse: " + response.body().size());
                    // Gửi dữ liệu cho presenter

                }
            }

            // Lỗi khi đang giao tiếp với server
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                // Gửi đi thông báo lỗi cho presenter
                onFinishedListener.onFailure(t);
            }
        });
    }


    @Override
    public void getProductListCategory(CategoryProductListContract.Handle.OnFinishedListener onFinishedListener, String id_category) {
        DataClient apiService = ApiUtils.getProductList();
        /*
         * Gửi yêu cầu trả về 1 danh sách dữ liệu (List<Product>)
         */
        Call<List<Product>> call = apiService.getProductListCategory(id_category);
        call.enqueue(new Callback<List<Product>>() {
            // Khi nhận được phản hồi
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                // Thành công gửi dữ liệu dưới dáng danh sách sản phẩm
                if(response.isSuccessful()) {
                    List<Product> productList = response.body();
                    Log.d(TAG, "onResponsecategory: " + response.body().size());
                    // Gửi dữ liệu cho presenter
                    onFinishedListener.onFinished(productList);
                }
            }
            // Lỗi khi đang giao tiếp với server
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Gửi đi thông báo lỗi cho presenter
                onFinishedListener.onFailure(t);
            }
        });
    }
}
