package com.duykhanh.storeapp.view.search;

import android.util.Log;

import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.network.ApiUtils;
import com.duykhanh.storeapp.network.DataClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchHandle implements SearchContract.Handle {
    final String TAG = this.getClass().toString();

    @Override
    public void getProductByKey(OnGetProductByKeyListener listener, String searchingKey) {
        DataClient apiService = ApiUtils.getProductList();
        Call<List<Product>> call = apiService.getProductByKey(searchingKey);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()){
                    Log.w(TAG, "onResponse: " + response.code());
                    return;
                }
                List<Product> products = response.body();
                listener.onGetProductByKeyFinished(products);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                listener.onGetProductByKeyFailure(t);
            }
        });

    }
}
