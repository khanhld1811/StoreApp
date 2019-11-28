package com.duykhanh.storeapp.view.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.network.ApiUtils;
import com.duykhanh.storeapp.network.DataClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchHandle implements SearchContract.Handle {
    final String TAG = this.getClass().toString();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> searchKeys;

    public SearchHandle(SearchContract.View iView) {
        sharedPreferences = iView.getContext().getSharedPreferences("Keys", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear().commit();
        searchKeys = new ArrayList<>();
    }

    @Override
    public void getProductByKey(OnGetProductByKeyListener listener, String searchingKey, int pageNo) {
        DataClient apiService = ApiUtils.getProductList();
        Call<List<Product>> call = apiService.getProductByKey(searchingKey, pageNo);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
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

    @Override
    public void saveSearchKey(OnSaveSearchKeyListener listener, String searchKey) {
        try {
            editor.clear().apply();
            Set<String> setSearchKeys = new HashSet<>();
            searchKeys.add(searchKey);
            setSearchKeys.addAll(searchKeys);
            editor.putStringSet("SearchKeys", setSearchKeys);
            editor.apply();
            listener.onSaveSearchKeyFinished();
        } catch (Exception e) {
            listener.onSaveSearchKeyFailure(e);
        }
    }

    @Override
    public void getSearchKeys(OnGetSearchKeysListener listener) {
        try {
            searchKeys.clear();
            Set<String> setSearchKeys = sharedPreferences.getStringSet("SearchKeys", null);
            if (setSearchKeys == null) {
                return;
            }
            searchKeys.addAll(setSearchKeys);
            listener.onGetSearchKeysFinished(searchKeys);
        } catch (Exception e) {
            listener.onGetSearchKeyFailure(e);
        }
    }
}
