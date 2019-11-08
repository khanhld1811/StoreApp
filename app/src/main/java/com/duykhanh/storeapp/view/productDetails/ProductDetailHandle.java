package com.duykhanh.storeapp.view.productDetails;

import android.util.Log;

import com.duykhanh.storeapp.model.Comment;
import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.network.ApiUtils;
import com.duykhanh.storeapp.network.DataClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailHandle implements ProductDetailContract.Handle {
    final String TAG = this.getClass().toString();

    @Override
    public void getProductDetail(OnGetProductDetailListener listener, String productId) {
        DataClient apiService = ApiUtils.getProductList();

        Call<Product> call = apiService.getProductDetail(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();
                    Log.d(TAG, "onResponse: " + response.body());
                    listener.onGetProductDetailFinished(product);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                listener.onGetProductDetailFailure(t);
            }
        });
    }

    @Override
    public void getCommentByIdp(OnGetCommentByIdpListener listener, String productId) {
        DataClient apiService = ApiUtils.getProductList();

        Call<List<Comment>> call = apiService.getCommentByIdp(productId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.code());
                    return;
                }
                List<Comment> comments = response.body();
                listener.onGetCommentByIdpFinished(comments);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                listener.onGetCommentByIdpFailure(t);
            }
        });
    }
}
