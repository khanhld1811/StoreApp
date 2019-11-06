package com.duykhanh.storeapp.network;

import com.duykhanh.storeapp.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Duy Khánh on 11/5/2019.
 */
public interface DataClient {

    // Yêu cầu tất cả dữ liệu sản phẩm
    @GET("str1/product")
    Call<List<Product>> getDataProduct();

    // Yêu cầu dữ liệu sản phảm theo id
    @GET("str1/product/{idProduct}")
    Call<Product> getProductDetail(@Path("idProduct") String idProduct);
}