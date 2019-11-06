package com.duykhanh.storeapp.view.homepage;

import android.util.Log;

import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.network.ApiUtils;
import com.duykhanh.storeapp.network.DataClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Duy Khánh on 11/6/2019.
 * Lớp này xử lý yêu cầu giữa client với server và nhận dữ liệu trả về.
 * Có thể xem nó được tách ra từ Presenter để thuận tiện cho việc xử lý dữ liệu
 */
public class ProductHandle implements ProductListContract.Handle {

    private final String TAG = "ProductHandle";

    // Hàm xử lý dữ liệu từ server
    @Override
    public void getProductList(OnFinishedListener onFinishedListener, int pageNo) {
        DataClient apiService = ApiUtils.getProductList();
        /*
        * Gửi yêu cầu trả về 1 danh sách dữ liệu (List<Product>)
        */
        Call<List<Product>> call = apiService.getDataProduct();
        call.enqueue(new Callback<List<Product>>() {
            // Khi nhận được phản hồi
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                // Thành công gửi dữ liệu dưới dáng danh sách sản phẩm
                if(response.isSuccessful()) {
                    List<Product> products = response.body();
                    Log.d(TAG, "onResponse: " + response.body().size());
                    // Gửi dữ liệu cho presenter
                    onFinishedListener.onFinished(products);
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